package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class For_New_User extends AppCompatActivity {

    TextView welcome_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_new_user);
        String username = getIntent().getStringExtra("UserName");

        welcome_user = (TextView)findViewById(R.id.New_User_Name);
        welcome_user.setText("Hello "+ username);




    }


    public void to_home_page(View view) {
        Intent intent = new Intent(For_New_User.this, Homepage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}