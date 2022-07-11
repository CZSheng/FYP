package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Badges extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;
    private int point1, point2, point3;
    ImageView blockA, blockC, Library, hall;
    TextView test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        blockA = (ImageView)findViewById(R.id.bloclAimg);
        blockC = (ImageView)findViewById(R.id.bloclCimg);
        Library = (ImageView)findViewById(R.id.Libraryimg);
        hall = (ImageView)findViewById(R.id.hallimg);




        getpoint();

    }

    private void getpoint() {
        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    point1 = Integer.valueOf(snapshot.child("points").getValue().toString());
                    point2 = Integer.valueOf(snapshot.child("point2").getValue().toString());
                    point3 = Integer.valueOf(snapshot.child("point3").getValue().toString());
                }

                if(point1 > 0){
                    blockA.setImageResource(R.drawable.chess_100);
                }
                if(point2 > 0){
                    blockC.setImageResource(R.drawable.eat_100);
                }
                if(point3 > 0){
                    Library.setImageResource(R.drawable.storytelling_100);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        UsersRef.addListenerForSingleValueEvent(event);
    }
}