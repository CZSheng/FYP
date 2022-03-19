package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Time_leaderboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, all_user;
    String currentUserID;
    private RecyclerView leader_board;
    Leaderboard.UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_leaderboard);



    }
}