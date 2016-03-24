package com.mk.meetbuddies.fragments;

/**
 * Created by mourad on 2016-03-23.
 */
public class Meetings {
    private String location, date, time, description,id,name,status;

    public Meetings(String location, String date, String time, String description,String id,String name, String status) {
        this.location = location;
        this.date = date;
        this.time = time;
        this.description = description;
        this.id=id;
        this.name=name;
        this.status=status;
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
    public String getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String st) {
        this.status = st;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
