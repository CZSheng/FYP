package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Setting_detail extends AppCompatActivity {

    private EditText UserName;
    private Button SetupButton;
    private ProgressDialog loading_bar;


    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_detail);
        UserName = (EditText) findViewById(R.id.register_username);
        SetupButton = (Button) findViewById(R.id.create_acc);
        loading_bar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);


        SetupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //13
                SaveAccountSetupInformation();
            }
        });


    }

    private void SaveAccountSetupInformation() {

        String username = UserName.getText().toString();
        int points = 0;

        // 2022/6/7 add
        int point2 = 0;
        int point3 = 0;
        int point4 = 0;
        int point1_time = 0;
        int point2_time = 0;
        int point3_time = 0;
        int point4_time = 0;
        int total_point = 0;
        int total_time = 0;

        //2022/6/7


        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }
        else{
            loading_bar.setTitle("Create new account");
            loading_bar.setMessage("Please wait creating ...");
            loading_bar.show();
            loading_bar.setCanceledOnTouchOutside(true);

            HashMap userMap = new HashMap();
            userMap.put("user_name", username);
            userMap.put("points", points);
            userMap.put("point2", point2);
            userMap.put("point3", point3);
            userMap.put("point4", point4);
            userMap.put("point1_time", point1_time);
            userMap.put("point2_time", point2_time);
            userMap.put("point3_time", point3_time);
            userMap.put("point4_time", point4_time);
            userMap.put("total_point", total_point);
            userMap.put("total_time", total_time);


            UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        SendusertoWelcomeNewUser();
                        Toast.makeText(Setting_detail.this, "Your Account is Created Successfully", Toast.LENGTH_LONG).show();
                        loading_bar.dismiss();
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(Setting_detail.this, "Error Occur: " + message, Toast.LENGTH_SHORT).show();
                        loading_bar.dismiss();

                    }
                }
            });
        }

    }

    private void SendusertoWelcomeNewUser() {

        String username = UserName.getText().toString();

        Intent intent = new Intent(Setting_detail.this, For_New_User.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("UserName", username);

        startActivity(intent);
        finish();
    }
}