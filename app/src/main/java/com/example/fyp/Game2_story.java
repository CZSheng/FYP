package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Game2_story extends AppCompatActivity {

    ImageView story_image;
    TextView story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2_story);

        String text = "This lake is in front of block C and is known as a tin lake, and is a remnant of the time when tin was produced in Kampar, Kampar also prospered because of these tin mines.";

        story_image =(ImageView)findViewById(R.id.Story_imageView);
        story_image.setImageResource(R.drawable.block_b_lake);
        story = (TextView)findViewById(R.id.Story_text);
        story.setText(text);
    }

    public void to_game2_page(View view){
        Intent intent = new Intent (Game2_story.this, Game2_page.class);
        startActivity(intent);
    }
}