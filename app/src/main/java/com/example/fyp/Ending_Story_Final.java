package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Ending_Story_Final extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending_story_final);

        setdialog();
    }


    private void setdialog() {
        Dialog dialog;
        dialog = new Dialog(this);

        dialog.setContentView(R.layout.pass_layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageView = dialog.findViewById(R.id.pass_dialog_img);
        TextView textView = dialog.findViewById(R.id.note_pass_dialog);
        imageView.setImageResource(R.drawable.treasures_100);
        textView.setText("Congratulations, you unlocked the successful treasure chest and got the treasure!");

        Button OKbtn = dialog.findViewById(R.id.OKbtn);
        OKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void to_home_page(View view) {

        Intent intent = new Intent(Ending_Story_Final.this, Homepage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}