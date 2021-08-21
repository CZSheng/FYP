package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Game_Story extends AppCompatActivity {
    ImageView story_image;
    TextView story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__story);

        String text = "The bronze sculptures of Confucius and Einstein are the pride of Universiti Tunku Abdul Rahman (UTAR). Located at the open space in front of the Heritage Hall Building in UTAR Kampar Campus, the sculptures symbolise the Universality of Learning and Thinking with the convergence of wisdoms from both the East and the West. ";

        story_image =(ImageView)findViewById(R.id.Story_imageView);
        story_image.setImageResource(R.drawable.bronze_sculptures_of_confucius_and_einstein);
        story = (TextView)findViewById(R.id.Story_text);
        story.setText(text);



    }

    public void to_game_page(View view){
        Intent intent = new Intent (Game_Story.this, Gamepage.class);
        startActivity(intent);
    }
}