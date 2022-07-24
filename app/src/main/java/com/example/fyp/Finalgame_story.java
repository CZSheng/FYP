package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Finalgame_story extends AppCompatActivity {

    ImageView story_image1, story_image2, story_image3, story_image4, story_image5;
    TextView story1, story2, story3, story4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalgame_story);


        String text1 = "It was named after the illustrious founding father of Universiti Tunku Abdul Rahman (UTAR), this massive hall is now a paramount host to the students.";
        String text2 = "This place is called \"Tian Yuan Di Fang 天圆地方\", which symbolizes the balance and harmony between human and nature.";
        String text3 = "There are two full-moon windows at two corners of the hall’s main entrance which afford framed views of the scenery of mountains, scenic lakes and other buildings of the campus.";
        String text4 = "The two dark pools beside the walkway represent traditional Chinese inkwells.  Bridging each ink pool is a paver slab with protruding blocks on both sides. These protruding blocks are the ink blocks that are emblazoned with Chinese characters, each block representing one of the Six Educational Pillars of UTAR education.";

        story_image1 =(ImageView)findViewById(R.id.Story_imageView1);
        story_image2 =(ImageView)findViewById(R.id.Story_imageView2);
        story_image3 =(ImageView)findViewById(R.id.Story_imageView3);
        story_image4 =(ImageView)findViewById(R.id.Story_imageView4);
        story_image5 =(ImageView)findViewById(R.id.Story_imageView5);


        story1 = (TextView)findViewById(R.id.Story_text1);
        story2 = (TextView)findViewById(R.id.Story_text2);
        story3 = (TextView)findViewById(R.id.Story_text3);
        story4 = (TextView)findViewById(R.id.Story_text4);


        story_image1.setImageResource(R.drawable.dewan_tun_dr_ling_liong_sik);
        story_image2.setImageResource(R.drawable.tian_yuan_di_fang);
        story_image3.setImageResource(R.drawable.full_moon_windows);
        story_image4.setImageResource(R.drawable.chinese_inkwells);
        story_image5.setImageResource(R.drawable.ink_blocks);

        story1.setText(text1);
        story2.setText(text2);
        story3.setText(text3);
        story4.setText(text4);

        story_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Finalgame_story.this, Detail_story.class);
                intent.putExtra("story","hall");
                startActivity(intent);
            }
        });

        story_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Finalgame_story.this, Detail_story.class);
                intent.putExtra("story","tian_yuan_di_fang");
                startActivity(intent);
            }
        });


        story_image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Finalgame_story.this, Detail_story.class);
                intent.putExtra("story","full_moon_windows");
                startActivity(intent);
            }
        });


        story_image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Finalgame_story.this, Detail_story.class);
                intent.putExtra("story","chinese_inkwells");
                startActivity(intent);
            }
        });

        story_image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Finalgame_story.this, Detail_story.class);
                intent.putExtra("story","ink_blocks");
                startActivity(intent);
            }
        });




    }

    public void go_to_final_gamepage(View view){
        Intent intent = new Intent (Finalgame_story.this, Finalgame_page.class);
        startActivity(intent);
    }




}