package com.mk.meetbuddies.fragments;

/**
 * Created by mourad on 2016-03-21.
 */
public class Buddies {
    private String avatar;
    private String pseudo;
    private String text;

    public Buddies(String avatar, String pseudo, String text) {
        this.avatar = avatar;
        this.pseudo = pseudo;
        this.text = text;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setColor(String avatar) {
        this.avatar = avatar;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}