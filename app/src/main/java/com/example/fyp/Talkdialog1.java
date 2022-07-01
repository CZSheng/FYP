package com.example.fyp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Talkdialog1 extends AppCompatDialogFragment {

    String go_to_where;

    public void setGo_to_where(String go_to_where) {
        this.go_to_where = go_to_where;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Soul")
                .setMessage("I will tell you a story and then ask you a question. If you answer correctly, you will be given clues")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(go_to_where.equals("game1")){
                            Intent intent = new Intent(getActivity(),Game_Story.class);
                            startActivity(intent);
                        }
                        if(go_to_where.equals("game2")){
                            Intent intent = new Intent(getActivity(),Game2_story.class);
                            startActivity(intent);
                        }
                        if(go_to_where.equals("game3")){
                            Intent intent = new Intent(getActivity(),Game3_story.class);
                            startActivity(intent);
                        }

                    }
                });

        return builder.create();
    }
}
