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

public class Login extends AppCompatActivity {

    private EditText login_email, login_password;
    private Button Login;

    private FirebaseAuth mAuth;
    private ProgressDialog loading_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = (EditText) findViewById(R.id.Login_useremail);
        login_password = (EditText)findViewById(R.id.Login_password);
        Login = (Button)findViewById(R.id.Login_acc);
        mAuth = FirebaseAuth.getInstance();
        loading_bar = new ProgressDialog(this);





    }

    public void to_register_page(View view){
        Intent intent = new Intent (Login.this, Register.class);
        startActivity(intent);
    }

    public void login_action(View view){

        String email = login_email.getText().toString();
        String password = login_password.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else{

            loading_bar.setTitle("Login");
            loading_bar.setMessage("Please wait! loading ...");
            loading_bar.show();
            loading_bar.setCanceledOnTouchOutside(true);

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){


                        Sendusertohomepage();

                        Toast.makeText(Login.this,"Login account successful...", Toast.LENGTH_SHORT).show();
                        loading_bar.dismiss();
                    }
                    else{
                        String message = task.getException().getMessage();
                        Toast.makeText(Login.this,"Error occured:"+message, Toast.LENGTH_SHORT).show();
                        loading_bar.dismiss();
                    }
                }
            });
        }



    }



    private void Sendusertohomepage() {
        Intent intent = new Intent (Login.this, Homepage.class);
        startActivity(intent);
    }
}