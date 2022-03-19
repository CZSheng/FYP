package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Game3_story extends AppCompatActivity {

    ImageView story_image;
    TextView story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3_story);

        String text = "UTAR Library is an education library. To provide quality resources and services in support of the advancement of teaching, learning and research of the student and lecture.";

        //story_image =(ImageView)findViewById(R.id.Story_imageView);
        //story_image.setImageResource(R.drawable.bronze_sculptures_of_confucius_and_einstein);
        story = (TextView)findViewById(R.id.Story_text);
        story.setText(text);

    }

    public void to_game3_page(View view){
        Intent intent = new Intent (Game3_story.this, Game2_page.class);
        startActivity(intent);
    }
}