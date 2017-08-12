package com.example.msbomrel.class4.data;

import java.io.Serializable;

/**
 * Created by msbomrel on 7/8/17.
 */

public class Student implements Serializable {
    private int id;
    private String username;
    private String address;
    private int roll;

    public Student(int id, String username, String address, int roll) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.roll = roll;
    }
    public Student(String username, String address, int roll) {
        this.username = username;
        this.address = address;
        this.roll = roll;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }
}
