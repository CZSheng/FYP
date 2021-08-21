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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class Register extends AppCompatActivity {


    private EditText Useremail, User_password, User_confirm_password;
    private Button create_acc_btn;
    private FirebaseAuth mAuth;
    private ProgressDialog loading_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        Useremail = (EditText) findViewById(R.id.register_useremail);
        User_password = (EditText) findViewById(R.id.register_password);
        User_confirm_password = (EditText) findViewById(R.id.confirm_register_password);
        create_acc_btn = (Button)findViewById(R.id.create_acc);
        loading_bar = new ProgressDialog(this);

        create_acc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create_Account();
            }
        });



    }

    private void Create_Account() {

        String email = Useremail.getText().toString();
        String password = User_password.getText().toString();
        String confirm_password = User_confirm_password.getText().toString();




        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(confirm_password))
        {
            Toast.makeText(this, "Please enter your confirm password", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirm_password))
        {
            Toast.makeText(this, "Your password do not match with your confirm password", Toast.LENGTH_SHORT).show();
        }
        else{


            loading_bar.setTitle("Create new account");
            loading_bar.setMessage("Please wait creating ...");
            loading_bar.show();
            loading_bar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                       if(task.isSuccessful()){
                           Sendusertosettingdetail();
                           Toast.makeText(Register.this,"Go to setting detail", Toast.LENGTH_SHORT).show();
                           loading_bar.dismiss();
                       }
                       else{
                           String message = task.getException().getMessage();
                           Toast.makeText(Register.this,"Error occured:"+message, Toast.LENGTH_SHORT).show();
                           loading_bar.dismiss();
                       }
                }
            });



        }
    }


    private void Sendusertosettingdetail() {
        Intent intent = new Intent(Register.this, Setting_detail.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}