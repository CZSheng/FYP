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

            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                mView = itemView;
                time =(TextView) mView.findViewById(R.id.time_leaderboard_user_spend_time);
                name = (TextView)  mView.findViewById(R.id.time_leaderboard_user_full_name);
                number = (TextView) mView.findViewById(R.id.Order_in_time_leaderboard);

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