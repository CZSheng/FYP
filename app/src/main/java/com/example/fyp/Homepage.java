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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private final double EARTH_RADIUS = 6378137.0;

    private final double pointlattitude = 4.3270214;
    private final double pointlongitude = 101.1434877;

    Button search_btn;
    ProgressBar progressBar;
    Double distamce_between;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        search_btn = findViewById(R.id.playgame);
        progressBar = findViewById(R.id.search_progressBar);

        mAuth = FirebaseAuth.getInstance();

        search_btn.setOnClickListener(new View.OnClickListener() {
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

                        }

                        progressBar.setVisibility(View.GONE);

                        if(distamce_between < 10){
                            Intent intent = new Intent (Homepage.this, Game_Story.class);
                            startActivity(intent);
                        }
                        else
                        {
                            not_near_toast();
                        }

                    }
                }, Looper.getMainLooper());



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

    public void to_badges_page(View view){
        Intent intent = new Intent (Homepage.this, Badges.class);
        startActivity(intent);
    }

    public void to_login_page(View view){
        mAuth.signOut();
        Intent intent = new Intent (Homepage.this, Login.class);
        startActivity(intent);
    }

}