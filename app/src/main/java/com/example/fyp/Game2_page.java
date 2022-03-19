package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

public class Game2_page extends AppCompatActivity {

    ImageView story_image;
    Button submit_btn;
    String Answer = "";
    Users users = new Users();
    EditText savepoint, saveusername;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;

    //timer
    long start_time,end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2_page);

        story_image =(ImageView)findViewById(R.id.Story_imageView);
        story_image.setImageResource(R.drawable.bronze_sculptures_of_confucius_and_einstein);
        submit_btn = (Button)findViewById(R.id.submit_button);
        savepoint = (EditText)findViewById(R.id.userpoint);
        saveusername = (EditText)findViewById(R.id.username);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        start_time = System.currentTimeMillis();

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
            case R.id.Answer3:
                if (checked)
                    Answer = "Correct";
                break;
            case R.id.Answer2:
            case R.id.Answer4:
            case R.id.Answer1:
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
            end_time = System.currentTimeMillis();
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
                    //int newpoint = Integer.valueOf(point);
                    int nowpoint;
                    long total_time = end_time - start_time;
                    int time_second = (int)total_time / 1000;

                    //count point
                    int max_time = 30;
                    int point_get;

                    if((max_time-time_second)<0) {
                        nowpoint = 10;
                    }else {
                        nowpoint = (max_time-time_second) * 10;
                    }
                    //newpoint += 100;

                    HashMap userMap = new HashMap();
                    userMap.put("user_name", username);
                    userMap.put("points", nowpoint);
                    userMap.put("time1", time_second);

                    UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                to_Ending_Story_page();
                                Toast.makeText(Game2_page.this, "Update point Successful", Toast.LENGTH_LONG).show();
                                finish();

                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(Game2_page.this, "Error Occur: " + message, Toast.LENGTH_SHORT).show();


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
        Intent intent = new Intent (Game2_page.this, Ending_Story2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}