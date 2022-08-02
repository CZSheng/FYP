package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Leaderboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, all_user;
    String currentUserID;
    private RecyclerView leader_board;
    UserAdapter userAdapter;
    private TextView no1name, no1point, no2name, no2point, no3name, no3point;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        all_user = FirebaseDatabase.getInstance().getReference().child("Users");

        leader_board = (RecyclerView) findViewById(R.id.search_result_list);
        leader_board.setHasFixedSize(true);
        leader_board.setLayoutManager(new LinearLayoutManager(this));

        no1name = (TextView) findViewById(R.id.no_1_name);
        no2name = (TextView) findViewById(R.id.no_2_name);
        no3name = (TextView) findViewById(R.id.no_3_name);
        no1point = (TextView) findViewById(R.id.no1_point);
        no2point = (TextView) findViewById(R.id.no_2_point);
        no3point = (TextView) findViewById(R.id.no_3_point);



//        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
//                .setQuery(all_user.orderByValue(), Users.class)
//                .build();


        //userAdapter = new UserAdapter(options);
        //leader_board.setAdapter(userAdapter);
        Display_leaderboard();

    }


    @Override
    protected void onStart() {
        super.onStart();
        //userAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //userAdapter.stopListening();
    }

    class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

        ArrayList<Users> DisplayUser = new ArrayList<>();
        Context context;

        public UserAdapter(Context ct, ArrayList<Users> userlist){
            this.context = ct;
            this.DisplayUser = userlist;
        }

        public class UserViewHolder extends RecyclerView.ViewHolder{

            View mView;
            TextView name, point, number;
            ImageView num_img;

            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                mView = itemView;
                point =(TextView) mView.findViewById(R.id.leaderboard_user_point);
                name = (TextView)  mView.findViewById(R.id.leaderboard_user_full_name);
                number = (TextView) mView.findViewById(R.id.Order_in_leaderboard);
                num_img = (ImageView) mView.findViewById(R.id.number_img);

            }
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.display_leaderboard_item,parent,false);
            return new UserViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
            String str = "No. "+String.valueOf(position+4);
            String str_point = String.valueOf(DisplayUser.get(position).getTotal_point());
            holder.name.setText(DisplayUser.get(position).getUser_name());
            holder.point.setText(str_point);
            holder.number.setText(str);
//            int num = position+1;
//            if(num == 1){
//                holder.num_img.setImageResource(R.drawable.n1st);
//                holder.num_img.setVisibility(View.VISIBLE);
//            }
//            else if(num == 2){
//                holder.num_img.setImageResource(R.drawable.n2nd);
//                holder.num_img.setVisibility(View.VISIBLE);
//            }
//            else if(num == 3){
//                holder.num_img.setImageResource(R.drawable.n3rd);
//                holder.num_img.setVisibility(View.VISIBLE);
//            }
        }

        @Override
        public int getItemCount() {
            return DisplayUser.size();
        }
    }

    private void Display_leaderboard() {

        ArrayList<Users> usersArrayList =new ArrayList<Users>();
        //ArrayList<Users> Result_usersArrayList =new ArrayList<Users>();

        ValueEventListener event =new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Users> Result_usersArrayList =new ArrayList<Users>();
                if(snapshot.exists()){
                    for(DataSnapshot ds:snapshot.getChildren()){
                        Users users = new Users();
                        int get_points = Integer.valueOf(ds.child("total_point").getValue().toString());
                        //users.setPoints(points);
                        users.setTotal_point(get_points);
                        users.setUser_name(ds.child("user_name").getValue(String.class));
//                        Log.e("name", ""+users.getUser_name());
//                        Log.e("point", ""+users.getPoints());
                        usersArrayList.add(users);
                    }

                    Comparator<Users> comparepoint = new Comparator<Users>() {
                        @Override
                        public int compare(Users o1, Users o2) {
                            //int in1 = new Integer(o1.getPoint());
                            //int in2 = new Integer(o1.getPoint());


//                            int u1 = o1.getPoints();
//                            int u2 = o2.getPoints();
                            int u1 = o1.getTotal_point();
                            int u2 = o2.getTotal_point();
                            return u2 - u1;
                        }
                    };

                    Collections.sort(usersArrayList, comparepoint);
                    Result_usersArrayList = (ArrayList<Users>)usersArrayList.clone();


                    if(Result_usersArrayList.size()>0){
                        no1name.setText(String.valueOf(Result_usersArrayList.get(0).getUser_name()));
                        no1point.setText(String.valueOf(Result_usersArrayList.get(0).getTotal_point()));
                    }
                    if(Result_usersArrayList.size()>1){
                        no2name.setText(String.valueOf(Result_usersArrayList.get(1).getUser_name()));
                        no2point.setText(String.valueOf(Result_usersArrayList.get(1).getTotal_point()));
                    }
                    if(Result_usersArrayList.size()>2){
                        no3name.setText(String.valueOf(Result_usersArrayList.get(2).getUser_name()));
                        no3point.setText(String.valueOf(Result_usersArrayList.get(2).getTotal_point()));
                    }

                    for(int i =0; i<3;i++){
                        Result_usersArrayList.remove(0);
                    }


//                    no1name.setText(String.valueOf("jason"));
//                    no1point.setText("1230");

//                    for(int i =0; i<3;i++){
//                        Users user = Result_usersArrayList.get(0);
//                        if(i == 0){
//                            no1name.setText(user.getUser_name());
//                            no1point.setText(user.getTotal_point());
//                        }
//                        else if(i == 1){
//                            no2name.setText(user.getUser_name());
//                            no2point.setText(user.getTotal_point());
//                        }
//                        else if(i == 2){
//                            no3name.setText(user.getUser_name());
//                            no3point.setText(user.getTotal_point());
//                        }
//                        Result_usersArrayList.remove(0);
//                    }

                    Log.e("List", ""+usersArrayList);
                    UserAdapter userAdapter = new UserAdapter(getApplicationContext(), Result_usersArrayList);
                    leader_board.setAdapter(userAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        all_user.addListenerForSingleValueEvent(event);
    }

    /*private void Display_leaderboard() {

        Log.d("1","Display_leaderboard: 1");

        FirebaseRecyclerAdapter<Users,UsersViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Users, UsersViewHolder>
                        (
                                Users.class,
                                R.layout.display_leaderboard_item,
                                UsersViewHolder.class,
                                all_user
                        )
                {
                    @Override
                    protected void populateViewHolder(UsersViewHolder usersViewHolder, Users users, int i) {
                        Log.d("2","Display_leaderboard: 2");
                        usersViewHolder.setUser_name(users.getUsername());
                        usersViewHolder.setPoint(users.getPoint());
                        usersViewHolder.setNumber(i+1);



                    }
                };
        Log.d("3","Display_leaderboard: 3");

        leader_board.setAdapter(firebaseRecyclerAdapter);
        Log.d("4","Display_leaderboard: 4");
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;

        TextView point, user_name, number;

        public UsersViewHolder(@NonNull View itemView) {

            super(itemView);
            mView = itemView;

            point =(TextView) mView.findViewById(R.id.leaderboard_user_point);
            user_name = (TextView)  mView.findViewById(R.id.leaderboard_user_full_name);
            number = (TextView) mView.findViewById(R.id.Order_in_leaderboard);

        }
        public void setPoint(String input_point){
            point.setText(input_point);

        }

        public void setUser_name(String input_user_name){
            user_name.setText(input_user_name);
        }

        public void setNumber(int input_number){
            String combine_with_No = "No. "+ input_number;
            number.setText(combine_with_No);
        }


    }*/
}