package com.mk.meetbuddies.fragments;

public class Buddies {
    private String login, name, prename, photo;

    public Buddies(String login, String name, String prename, String photo) {
        this.login = login;
        this.name = name;
        this.prename = prename;
        this.photo = photo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}