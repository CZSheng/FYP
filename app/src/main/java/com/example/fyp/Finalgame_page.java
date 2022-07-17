package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Finalgame_page extends AppCompatActivity {


    private ImageView story_image;
    private  Button submit_btn;
    private String Answer = "";
    private Users users = new Users();
    private EditText savepoint, saveusername;
    private TextView question;
    private RadioGroup Answer_group;
    private RadioButton answer1, answer2, answer3, answer4;
    private ProgressBar progressbar;
    private int current_progress = 0;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;

    //timer
    long start_time,end_time;
    int question_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalgame_page);


        story_image =(ImageView)findViewById(R.id.Story_imageView);
        story_image.setImageResource(R.drawable.library_img);
        submit_btn = (Button)findViewById(R.id.submit_button);
        savepoint = (EditText)findViewById(R.id.userpoint);
        saveusername = (EditText)findViewById(R.id.username);
        question = (TextView)findViewById(R.id.Question);
        answer1 = findViewById(R.id.Answer1);
        answer2 = findViewById(R.id.Answer2);
        answer3 = findViewById(R.id.Answer3);
        answer4 = findViewById(R.id.Answer4);
        progressbar = findViewById(R.id.progressbar);
        Answer_group = findViewById(R.id.Answer_group);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        start_time = System.currentTimeMillis();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckSubmitResult();
            }
        });

        setquestoin1();
    }

    private void setquestoin1() {
        question_num = 1;
        String text1 = "question1";
        story_image.setImageResource(R.drawable.dewan_tun_dr_ling_liong_sik);
        question.setText("What is this building?");
        answer1.setText("Dewan Tun Dr Ling Liong Sik");
        answer2.setText("Library");
        answer3.setText("Blocl L");
        answer4.setText("Canteen");



    }
    private void setquestoin2() {

        question_num = 2;
        String text2 = "What is this place called?";
        story_image.setImageResource(R.drawable.tian_yuan_di_fang);
        question.setText(text2);
        answer1.setText("Tian Fang Di Yuan");
        answer2.setText("Tian Di Fang Yuan");
        answer3.setText("Tian Yuan Di Fang");
        answer4.setText("Tian Fang Yuan Di");
    }

    private void setquestoin3() {

        question_num = 3;
        String text3 = "Where can see the beautiful scenery?";
        story_image.setImageResource(R.drawable.library_img);
        question.setText(text3);
        answer1.setText("Crescent window");
        answer2.setText("Full-moon windows");
        answer3.setText("Last quarter moon window");
        answer4.setText("Sun window");
    }

    private void setquestoin4() {

        question_num = 4;
        String text4 = "What does a dark floor in a pool represent?";
        story_image.setImageResource(R.drawable.library_img);
        question.setText(text4);
        answer1.setText("Rice paper");
        answer2.setText("Brush for Chinese calligraphy");
        answer3.setText("Ink block");
        answer4.setText("Traditional Chinese inkwells");
    }


    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        if(question_num==1){
            switch(view.getId()) {
                case R.id.Answer1:
                    if (checked)
                        Answer = "Correct";
                    break;
                case R.id.Answer2:
                case R.id.Answer4:
                case R.id.Answer3:
                    if (checked)
                        Answer = "Wrong";
                    break;
            }
        }
        if(question_num==2){
            switch(view.getId()) {
                case R.id.Answer3:
                    if (checked)
                        Answer = "Correct";
                    break;
                case R.id.Answer2:
                case R.id.Answer4:
                case R.id.Answer1:
                    if (checked)
                        Answer = "Wrong";
                    break;
            }
        }
        if(question_num==3){
            switch(view.getId()) {
                case R.id.Answer2:
                    if (checked)
                        Answer = "Correct";
                    break;
                case R.id.Answer1:
                case R.id.Answer4:
                case R.id.Answer3:
                    if (checked)
                        Answer = "Wrong";
                    break;
            }
        }
        if(question_num==4){
            switch(view.getId()) {
                case R.id.Answer4:
                    if (checked)
                        Answer = "Correct";
                    break;
                case R.id.Answer2:
                case R.id.Answer1:
                case R.id.Answer3:
                    if (checked)
                        Answer = "Wrong";
                    break;
            }
        }

    }


    private void CheckSubmitResult() {

        if(Answer.equals("")){
            Toast.makeText(this, "Please choose one answer", Toast.LENGTH_SHORT).show();
        }else if(Answer.equals("Wrong"))
        {
            Toast.makeText(this, "Ohoh this is not the correct answer", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(question_num == 1)
            {
                Toast.makeText(this, "This is correct answer. 1/4", Toast.LENGTH_SHORT).show();
                current_progress = 25;
                progressbar.setProgress(current_progress);
                progressbar.setMax(100);
                Answer_group.clearCheck();
                setquestoin2();
            }
            else if(question_num == 2){
                Toast.makeText(this, "This is correct answer. 2/4", Toast.LENGTH_SHORT).show();
                current_progress = 50;
                progressbar.setProgress(current_progress);
                progressbar.setMax(100);
                Answer_group.clearCheck();
                setquestoin3();
            }
            else if(question_num == 3){
                Toast.makeText(this, "This is correct answer. 3/4", Toast.LENGTH_SHORT).show();
                current_progress = 75;
                progressbar.setProgress(current_progress);
                progressbar.setMax(100);
                Answer_group.clearCheck();
                setquestoin4();
            }
            else if(question_num == 4){
                current_progress = 100;
                progressbar.setProgress(current_progress);
                progressbar.setMax(100);
                end_time = System.currentTimeMillis();
                CalculateAndUpdatePoint();
                to_Ending_Story_page();
                Toast.makeText(this, "Congratulations! This is correct answer", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void CalculateAndUpdatePoint() {

        UsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String username = dataSnapshot.child("user_name").getValue().toString();
                    String point = dataSnapshot.child("points").getValue().toString();

                    //2022/6/7
                    int point1 = Integer.valueOf(point);
                    int point2 = Integer.valueOf(dataSnapshot.child("point2").getValue().toString());
                    int point3 = Integer.valueOf(dataSnapshot.child("point3").getValue().toString());
                    int point4 = Integer.valueOf(dataSnapshot.child("point4").getValue().toString());
                    int point1_time = Integer.valueOf(dataSnapshot.child("point1_time").getValue().toString());
                    int point2_time = Integer.valueOf(dataSnapshot.child("point2_time").getValue().toString());
                    int point3_time = Integer.valueOf(dataSnapshot.child("point3_time").getValue().toString());
                    int point4_time = Integer.valueOf(dataSnapshot.child("point4_time").getValue().toString());

                    int total_point = Integer.valueOf(dataSnapshot.child("total_point").getValue().toString());
                    int total_time = Integer.valueOf(dataSnapshot.child("total_time").getValue().toString());
                    //2022/6/7

                    //int newpoint = Integer.valueOf(point);
                    int nowpoint;
                    long this_game_total_time = end_time - start_time;
                    int this_game_time_second = (int)this_game_total_time / 1000;

                    //count point
                    int max_time = 60;
                    int point_get;

                    if((max_time-this_game_time_second)<0) {
                        nowpoint = 10;
                    }else {
                        nowpoint = (max_time-this_game_time_second) * 10;
                    }
                    //newpoint += 100;

                    total_time = 0;
                    total_time = this_game_time_second + point1_time + point2_time + point3_time;

                    total_point = 0;
                    total_point = nowpoint + point1 + point2 + point3;

                    HashMap userMap = new HashMap();
                    userMap.put("user_name", username);
                    userMap.put("points", point1);
                    userMap.put("point2", point2);
                    userMap.put("point3", point3);
                    userMap.put("point4", nowpoint);
                    userMap.put("point1_time", point1_time);
                    userMap.put("point2_time", point2_time);
                    userMap.put("point3_time", point3_time);
                    userMap.put("point4_time", this_game_time_second);
                    userMap.put("total_point", total_point);
                    userMap.put("total_time", total_time);


                    UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                to_Ending_Story_page();
                                Toast.makeText(Finalgame_page.this, "Update point Successful", Toast.LENGTH_LONG).show();
                                finish();

                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(Finalgame_page.this, "Error Occur: " + message, Toast.LENGTH_SHORT).show();


                            }
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void to_Ending_Story_page(){
        Intent intent = new Intent (Finalgame_page.this, Ending_Story_Final.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}