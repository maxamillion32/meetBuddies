package com.mk.meetbuddies.fragments;

/**
 * Created by mourad on 2016-03-23.
 */
public class Meetings {
    private String location, date, time, description;

    public Meetings(String location, String date, String time, String description) {
        this.location = location;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
