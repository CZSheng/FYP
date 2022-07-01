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

            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                mView = itemView;
                point =(TextView) mView.findViewById(R.id.leaderboard_user_point);
                name = (TextView)  mView.findViewById(R.id.leaderboard_user_full_name);
                number = (TextView) mView.findViewById(R.id.Order_in_leaderboard);

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
            String str = "No. "+String.valueOf(position+1);
            String str_point = String.valueOf(DisplayUser.get(position).getTotal_point());
            holder.name.setText(DisplayUser.get(position).getUser_name());
            holder.point.setText(str_point);
            holder.number.setText(str);
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