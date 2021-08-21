package com.example.fyp;

import java.util.Comparator;

public class Users {
    String user_name;
    int points;

    public Users() {

    }

    public Users(String user_name, int points) {
        this.user_name = user_name;
        this.points = points;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
