package com.example.fyp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class UserAdapter extends FirebaseRecyclerAdapter<Users, UserAdapter.userViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_leaderboard_item, parent, false);
        return new userViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull userViewHolder holder, int position, @NonNull Users model) {
        String str = "No. "+String.valueOf(position+1);
        holder.name.setText(model.getUser_name());
        holder.point.setText(String.valueOf(model.getPoints()));
        holder.number.setText(str);
    }

    class userViewHolder extends RecyclerView.ViewHolder{
        TextView name, point, number;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            point =(TextView) itemView.findViewById(R.id.leaderboard_user_point);
            name = (TextView)  itemView.findViewById(R.id.leaderboard_user_full_name);
            number = (TextView) itemView.findViewById(R.id.Order_in_leaderboard);
        }
    }
}