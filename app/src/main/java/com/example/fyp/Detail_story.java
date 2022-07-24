package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Detail_story extends AppCompatActivity {

    private String story_name;
    ImageView story_image;
    TextView story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        String story_text;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            story_name = extras.getString("story");
        }

        story_image =(ImageView)findViewById(R.id.Story_imageView);
        story = (TextView)findViewById(R.id.Story_text);

        if(story_name.equals("hall")){
            story_image.setImageResource(R.drawable.dewan_tun_dr_ling_liong_sik);
            story_text = "It was named after the illustrious founding father of Universiti Tunku Abdul Rahman (UTAR), Tun Dr Ling Liong Sik, who is known and revered for his unflinching vision, wisdom, passion, and tenacity in spearheading the setting up of this university that has substantively touched the lives of countless many.\n" +
                            "This massive hall is now a paramount host to the congregations of graduands for convocations, meeting of bright minds to discuss ideas, gatherings of talents to showcase their work and performances, assemblies of scholars to present their research and creative endeavours, and many other get-togethers.";
            story.setText(story_text);
        }
        else if(story_name.equals("tian_yuan_di_fang")){
            story_image.setImageResource(R.drawable.tian_yuan_di_fang);
            story_text = "In this place dome ceiling and square floor, symbolising heaven and earth or “Tian Yuan Di Fang 天圆地方”, while the pillars symbolise humans on earth. The overall structure of the round dome ceiling, square floor and pillars symbolises the balance and harmony between humans and nature or \"Tian Ren He Yi 天人合一\".";
            story.setText(story_text);
        }
        else if(story_name.equals("full_moon_windows")){
            story_image.setImageResource(R.drawable.full_moon_windows);
            story_text= "An integral part of the architectural design of the hall are two full-moon windows at two corners of the hall’s main entrance which afford framed views of the scenery of mountains, scenic lakes and other buildings of the campus.";
            story.setText(story_text);
        }
        else if(story_name.equals("chinese_inkwells")){
            story_image.setImageResource(R.drawable.chinese_inkwells);
            story_text = "Flanking the pavilion are pools of water with dark floors representing the traditional Chinese inkwells that are used to dip the brush for Chinese painting and calligraphy.";
            story.setText(story_text);
        }
        else if(story_name.equals("ink_blocks")){
            story_image.setImageResource(R.drawable.ink_blocks);
            story_text = "Bridging each ink pool is a paver slab with protruding blocks on both sides. These protruding blocks are the ink blocks that are emblazoned with Chinese characters, each block representing one of the Six Educational Pillars of UTAR education.\n" +
                    "\n" +
                    "Virtue and Morality  德\n" +
                    "Knowledge and Intellect  智\n" +
                    "Physical and Mental Health  体\n" +
                    "Sociality and Humanitarianism  群\n" +
                    "Aesthetics and Harmony  美\n" +
                    "Creativity and Innovation  新  \n" +
                    "\n" +
                    "These ink blocks with Chinese characters are symbolic reminders to all who pass through this grand hall that UTAR graduates are educated with wholesome universal values associated with its Six Educational Pillars.";
            story.setText(story_text);
        }


    }

    public void go_back(View view){
        finish();
    }
}