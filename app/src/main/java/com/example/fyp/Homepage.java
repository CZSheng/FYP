package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Homepage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private final double EARTH_RADIUS = 6378137.0;

    private final double pointlattitude = 4.3270214;
    private final double pointlongitude = 101.1434877;


    private final double point1_lattitude = 4.3354244;
    private final double point1_longitude = 101.1410784;

    private final double point2_lattitude = 4.2271354;
    private final double point2_longitude = 101.1422985;

    private final double point3_lattitude = 4.339759;
    private final double point3_longitude = 101.1431734;

    private final double fpoint_lattitude = 4.3399622;
    private final double fpoint_longitude = 101.13749;

    Button search_btn;
    ProgressBar progressBar;
    Double distamce_between, distamce_between_point1, distamce_between_point2, distamce_between_point3, distamce_between_fpoint;
    private int point1, point2, point3, point4;
    ImageView playgame, Leaderboard, timeleaderboard, badge, logout, game2, game3, finalgame;
    boolean flag_pass = false, display_toast = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //search_btn = findViewById(R.id.playgame);
        progressBar = findViewById(R.id.search_progressBar);
        playgame = (ImageView)findViewById(R.id.playimg);
        Leaderboard = (ImageView)findViewById(R.id.lerderboardimg);
        timeleaderboard = (ImageView)findViewById(R.id.timeleaderimg);
        badge = (ImageView)findViewById(R.id.badgeimg);
        logout = (ImageView)findViewById(R.id.logoutimg);
        game2 = (ImageView)findViewById(R.id.game2img);
        game3 = (ImageView)findViewById(R.id.game3img);
        finalgame = (ImageView)findViewById(R.id.finalgameimg);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        playgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Homepage.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                            , REQUEST_CODE_LOCATION_PERMISSION);
                } else {
                    getcurrentlocation();
                }
            }
        });

        Leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_leader_board_page(view);
            }
        });

        timeleaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_time_leader_board_page(view);
            }
        });

        badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Homepage.this, Badges.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_login_page(view);
            }
        });

        game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_game2_page(view);
            }
        });

        game3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_game3_page(view);
            }
        });
        finalgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_final_game_page(view);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getcurrentlocation();
            } else {
                Toast.makeText(this, "permission denied!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getcurrentlocation() {

        progressBar.setVisibility(View.VISIBLE);

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(Homepage.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(Homepage.this)
                                .removeLocationUpdates(this);

                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocationindex = locationResult.getLocations().size() - 1;

                            double latidute = locationResult.getLocations().get(latestlocationindex).getLatitude();
                            double longtidute = locationResult.getLocations().get(latestlocationindex).getLongitude();

                            distamce_between = calculate_distance(latidute, longtidute, pointlattitude, pointlongitude);

                            distamce_between_point1 = calculate_distance(latidute, longtidute, point1_lattitude, point1_longitude);
                            distamce_between_point2 = calculate_distance(latidute, longtidute, point2_lattitude, point2_longitude);
                            distamce_between_point3 = calculate_distance(latidute, longtidute, point3_lattitude, point3_longitude);
                            distamce_between_fpoint = calculate_distance(latidute, longtidute, fpoint_lattitude, fpoint_longitude);

                        }

                        progressBar.setVisibility(View.GONE);

                        if(distamce_between < 10){
                            check_other_finish();
                            //go to game 1 AR game and story
                            if(flag_pass){
                                Intent intent = new Intent (Homepage.this, FindTheSoul_AR.class);
                                intent.putExtra("gamepage","game1");
                                startActivity(intent);
                            }
                        }
                        else if(distamce_between_point1 < 60){
                            //go to game 1 AR game and story
                            Intent intent = new Intent (Homepage.this, FindTheSoul_AR.class);
                            intent.putExtra("gamepage","game1");
                            startActivity(intent);

                        }else if(distamce_between_point2 < 40){
                            //go to game 2 AR game and story
                            Intent intent = new Intent (Homepage.this, FindTheSoul_AR.class);
                            intent.putExtra("gamepage","game2");
                            startActivity(intent);
                        }else if(distamce_between_point3 < 60){
                            //go to game 3 AR game and story
                            Intent intent = new Intent (Homepage.this, FindTheSoul_AR.class);
                            intent.putExtra("gamepage","game3");
                            startActivity(intent);
                        }else if(distamce_between_fpoint < 150){
                            check_other_finish();
                            if(flag_pass) {
                                //go to final game AR game and story
                                Intent intent = new Intent(Homepage.this, FindTheSoul_AR.class);
                                intent.putExtra("gamepage", "finalgame");
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            if(display_toast){
                                not_near_toast();
                            }
                            display_toast = true;

                        }

                    }
                }, Looper.getMainLooper());



    }

    private void check_other_finish() {

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    point1 = Integer.valueOf(snapshot.child("points").getValue().toString());
                    point2 = Integer.valueOf(snapshot.child("point2").getValue().toString());
                    point3 = Integer.valueOf(snapshot.child("point3").getValue().toString());
                }

                if((point1 != 0)&&(point2 != 0)&&(point3 != 0)){
                    flag_pass = true;
                }
                else{
                    finish_other_first();
                    display_toast = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        UsersRef.addListenerForSingleValueEvent(event);
    }

    private void finish_other_first() {
        Toast.makeText(this,"You need to finish other game point, then this game point will be unlock",Toast.LENGTH_LONG).show();
    }

    public void not_near_toast (){
        Toast.makeText(this,"There are no game spots near you, look elsewhere",Toast.LENGTH_LONG).show();
    }


    private double calculate_distance(double lat_a, double lng_a, double lat_b, double lng_b) {

        double radLat1 = (lat_a * Math.PI / 180.0);

        double radLat2 = (lat_b * Math.PI / 180.0);

        double a = radLat1 - radLat2;

        double b = (lng_a - lng_b) * Math.PI / 180.0;

        double result = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)

                + Math.cos(radLat1) * Math.cos(radLat2)

                * Math.pow(Math.sin(b / 2), 2)));

        result = result * EARTH_RADIUS;

        result = Math.round(result * 10000) / 10000;

        return result;
    }

    public void to_game_page(View view){
        Intent intent = new Intent (Homepage.this, Game_Story.class);
        startActivity(intent);
    }

    public void to_leader_board_page(View view){
        Intent intent = new Intent (Homepage.this, Leaderboard.class);
        startActivity(intent);
    }

    public void to_time_leader_board_page(View view){
        Intent intent = new Intent (Homepage.this, Time_leaderboard.class);
        startActivity(intent);
    }

    public void to_badges_page(View view){
        Intent intent = new Intent (Homepage.this, Badges.class);
        startActivity(intent);
    }

    //testing
    public void to_game2_page(View view){
        Intent intent = new Intent (Homepage.this, FindTheSoul_AR.class);
        intent.putExtra("gamepage","game2");
        startActivity(intent);
    }

    public void to_game3_page(View view){
        Intent intent = new Intent (Homepage.this, FindTheSoul_AR.class);
        intent.putExtra("gamepage","game3");
        startActivity(intent);
    }

    public void to_final_game_page(View view){
        Intent intent = new Intent (Homepage.this, FindTheSoul_AR.class);
        intent.putExtra("gamepage","finalgame");
        startActivity(intent);
    }
    //t

    public void to_login_page(View view){
        mAuth.signOut();
        Intent intent = new Intent (Homepage.this, Login.class);
        startActivity(intent);
        finish();
    }



}