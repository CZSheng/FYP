package com.example.fyp;

import java.util.Comparator;

public class Users {
    String user_name;
    int points;

    //2022/6/7 add
    int point1_time;


    int point2;
    int point2_time;


    int point3;
    int point3_time;

    int total_point;
    int total_time;

    public Users() {

    }

    public Users(String user_name, int points, int point1_time, int point2, int point2_time, int point3, int point3_time, int total_point, int total_time) {
        this.user_name = user_name;
        this.points = points;
        this.point1_time = point1_time;
        this.point2 = point2;
        this.point2_time = point2_time;
        this.point3 = point3;
        this.point3_time = point3_time;
        this.total_point = total_point;
        this.total_time = total_time;
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

    public int getPoint1_time() {
        return point1_time;
    }

    public void setPoint1_time(int point1_time) {
        this.point1_time = point1_time;
    }

    public int getPoint2() {
        return point2;
    }

    public void setPoint2(int point2) {
        this.point2 = point2;
    }

    public int getPoint2_time() {
        return point2_time;
    }

    public void setPoint2_time(int point2_time) {
        this.point2_time = point2_time;
    }

    public int getPoint3() {
        return point3;
    }

    public void setPoint3(int point3) {
        this.point3 = point3;
    }

    public int getPoint3_time() {
        return point3_time;
    }

    public void setPoint3_time(int point3_time) {
        this.point3_time = point3_time;
    }

    public int getTotal_point() {
        return total_point;
    }

    public void setTotal_point(int total_point) {
        this.total_point = total_point;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }
}
