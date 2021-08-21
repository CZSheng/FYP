package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Gamepage extends AppCompatActivity {

    ImageView story_image;
    Button submit_btn;
    String Answer = "";
    Users users = new Users();
    EditText savepoint, saveusername;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamepage);

        story_image =(ImageView)findViewById(R.id.Story_imageView);
        story_image.setImageResource(R.drawable.bronze_sculptures_of_confucius_and_einstein);
        submit_btn = (Button)findViewById(R.id.submit_button);
        savepoint = (EditText)findViewById(R.id.userpoint);
        saveusername = (EditText)findViewById(R.id.username);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckSubmitResult();
            }
        });
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.Answer1:
                if (checked)
                    Answer = "Correct";
                    break;
            case R.id.Answer2:
            case R.id.Answer4:
            case R.id.Answer3:
                if (checked)
                    Answer = "Wrong";
                    break;
        }
    }

    private void CheckSubmitResult() {

        if(Answer.equals("")){
            Toast.makeText(this, "Please choose one answer", Toast.LENGTH_SHORT).show();
        }else if(Answer.equals("Wrong"))
        {
            Toast.makeText(this, "Ohoh this is not the correct answer", Toast.LENGTH_SHORT).show();
        }
        else
        {
            CalculateAndUpdatePoint();
            to_Ending_Story_page();
            Toast.makeText(this, "Congratulations! This is correct answer", Toast.LENGTH_SHORT).show();
        }

    }


    private void CalculateAndUpdatePoint() {

        UsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String username = dataSnapshot.child("user_name").getValue().toString();
                    String point = dataSnapshot.child("points").getValue().toString();
                    int newpoint = Integer.valueOf(point);
                    newpoint += 100;

                    HashMap userMap = new HashMap();
                    userMap.put("user_name", username);
                    userMap.put("points", newpoint);

                    UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                to_Ending_Story_page();
                                Toast.makeText(Gamepage.this, "Update point Successful", Toast.LENGTH_LONG).show();
                                finish();

                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(Gamepage.this, "Error Occur: " + message, Toast.LENGTH_SHORT).show();


                            }
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    public void to_Ending_Story_page(){
        Intent intent = new Intent (Gamepage.this, Ending_Story.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}