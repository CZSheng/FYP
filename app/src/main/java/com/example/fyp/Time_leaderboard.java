package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Time_leaderboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, all_user;
    String currentUserID;
    private RecyclerView time_leader_board;
    private TextView no1name, no1time, no2name, no2time, no3name, no3time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_leaderboard);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        all_user = FirebaseDatabase.getInstance().getReference().child("Users");

        time_leader_board = (RecyclerView) findViewById(R.id.search_time_result_list);
        time_leader_board.setHasFixedSize(true);
        time_leader_board.setLayoutManager(new LinearLayoutManager(this));


        no1name = (TextView) findViewById(R.id.no_1_name);
        no2name = (TextView) findViewById(R.id.no_2_name);
        no3name = (TextView) findViewById(R.id.no_3_name);
        no1time = (TextView) findViewById(R.id.no1_time);
        no2time = (TextView) findViewById(R.id.no_2_time);
        no3time = (TextView) findViewById(R.id.no_3_time);

        Display_time_leaderboard();


    }

    class UsertimeAdapter extends RecyclerView.Adapter<UsertimeAdapter.UserViewHolder>{


        ArrayList<Users> DisplayUser = new ArrayList<>();
        Context context;

        public UsertimeAdapter(Context ct, ArrayList<Users> userlist){
            this.context = ct;
            this.DisplayUser = userlist;
        }

        public class UserViewHolder extends RecyclerView.ViewHolder{

            View mView;
            TextView name, time, number;
            ImageView time_num_img;

            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                mView = itemView;
                time =(TextView) mView.findViewById(R.id.time_leaderboard_user_spend_time);
                name = (TextView)  mView.findViewById(R.id.time_leaderboard_user_full_name);
                number = (TextView) mView.findViewById(R.id.Order_in_time_leaderboard);
                time_num_img = (ImageView) mView.findViewById(R.id.time_number_img);

            }
        }


        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.display_time_leaderboard_item,parent,false);
            return new UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UsertimeAdapter.UserViewHolder holder, int position) {
            String str = "No. "+String.valueOf(position+1);
            String str_time = String.valueOf(DisplayUser.get(position).getTotal_time());
            holder.name.setText(DisplayUser.get(position).getUser_name());
            holder.time.setText(str_time);
            holder.number.setText(str);

//            int num = position+1;
//            if(num == 1){
//                holder.time_num_img.setImageResource(R.drawable.no1_90);
//                holder.time_num_img.setVisibility(View.VISIBLE);
//            }
//            else if(num == 2){
//                holder.time_num_img.setImageResource(R.drawable.no2_90);
//                holder.time_num_img.setVisibility(View.VISIBLE);
//            }
//            else if(num == 3){
//                holder.time_num_img.setImageResource(R.drawable.no3_90);
//                holder.time_num_img.setVisibility(View.VISIBLE);
//            }
        }

        @Override
        public int getItemCount() {
            return DisplayUser.size();
        }
    }

    private void Display_time_leaderboard() {
        ArrayList<Users> usersArrayList =new ArrayList<Users>();

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Users> Result_usersArrayList =new ArrayList<Users>();

                if(snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Users users = new Users();
                        int get_time = Integer.valueOf(ds.child("total_time").getValue().toString());

                        int get_time1 = Integer.valueOf(ds.child("point1_time").getValue().toString());
                        int get_time2 = Integer.valueOf(ds.child("point2_time").getValue().toString());
                        int get_time3 = Integer.valueOf(ds.child("point3_time").getValue().toString());
                        int get_time4 = Integer.valueOf(ds.child("point4_time").getValue().toString());

                        //users.setPoints(points);
                        users.setTotal_time(get_time);
                        users.setUser_name(ds.child("user_name").getValue(String.class));
//                        Log.e("name", ""+users.getUser_name());
//                        Log.e("point", ""+users.getPoints());

                        if((get_time1 != 0) && (get_time2 != 0) && (get_time3 != 0) && (get_time4 != 0)){
                            usersArrayList.add(users);
                        }
                    }
                }


                Comparator<Users> comparepoint = new Comparator<Users>() {
                    @Override
                    public int compare(Users o1, Users o2) {
                        //int in1 = new Integer(o1.getPoint());
                        //int in2 = new Integer(o1.getPoint());


//                            int u1 = o1.getPoints();
//                            int u2 = o2.getPoints();
                        int u1 = o1.getTotal_time();
                        int u2 = o2.getTotal_time();
                        return u1 - u2;
                    }
                };

                Collections.sort(usersArrayList, comparepoint);
                Result_usersArrayList = (ArrayList<Users>)usersArrayList.clone();


                int item_remove = 0;
                if(Result_usersArrayList.size()>0){
                    no1name.setText(String.valueOf(Result_usersArrayList.get(0).getUser_name()));
                    no1time.setText(String.valueOf(Result_usersArrayList.get(0).getTotal_time())+" second");
                    item_remove++;
                }
                if(Result_usersArrayList.size()>1){
                    no2name.setText(String.valueOf(Result_usersArrayList.get(1).getUser_name()));
                    no2time.setText(String.valueOf(Result_usersArrayList.get(1).getTotal_time())+" second");
                    item_remove++;
                }
                if(Result_usersArrayList.size()>2){
                    no3name.setText(String.valueOf(Result_usersArrayList.get(2).getUser_name()));
                    no3time.setText(String.valueOf(Result_usersArrayList.get(2).getTotal_time())+" second");
                    item_remove++;
                }

                for(int i =0; i<item_remove;i++){
                    Result_usersArrayList.remove(0);
                }


                UsertimeAdapter usertimeAdapter = new UsertimeAdapter(getApplicationContext(), Result_usersArrayList);
                time_leader_board.setAdapter(usertimeAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        all_user.addListenerForSingleValueEvent(event);
    }
}