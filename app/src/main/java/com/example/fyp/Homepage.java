package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    public void to_game_page(View view){
        Intent intent = new Intent (Homepage.this, Game_Story.class);
        startActivity(intent);
    }

    public void to_leader_board_page(View view){
        Intent intent = new Intent (Homepage.this, Leaderboard.class);
        startActivity(intent);
    }

    public void to_badges_page(View view){
        Intent intent = new Intent (Homepage.this, Badges.class);
        startActivity(intent);
    }

    public void to_login_page(View view){
        Intent intent = new Intent (Homepage.this, Login.class);
        startActivity(intent);
    }

}