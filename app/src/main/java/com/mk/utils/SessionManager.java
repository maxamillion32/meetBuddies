package com.mk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

    public SharedPreferences Pref;
    public Editor editor;
    Context Context;
    private static final String PREF_NAME = "a1878292_MeetBud";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRENAME = "prename";
    public static final String KEY_ID = "id";
    public static final String KEY_PHOTOURL = "photo";
    public static final String KEY_ADRESS = "adress";
    public static final String KEY_GROUP = "groupN";
    public static final String KEY_PREF1 = "pref1";
    public static final String KEY_PREF2 = "pref2";
    public static final String KEY_PREF3 = "pref3";
    public static final String KEY_PREF4 = "pref4";
    public static final String KEY_PREF5 = "pref5";
    public static final String KEY_ORGANIZER = "organizer";

    public SessionManager(Context cont) {
        Context = cont;
        Pref = Context.getSharedPreferences(PREF_NAME, 0);
        editor = Pref.edit();
    }

    public void createLoginSession(String id, String name, String prename, String photoUrl, String adress, String currentLocation, String phone, String group, String pref1, String pref2, String pref3, String pref4, String pref5) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PRENAME, prename);
        editor.putString(KEY_PHOTOURL, photoUrl);
        editor.putString(KEY_ADRESS, adress);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_GROUP, group);
        editor.putString(KEY_PREF1, pref1);
        editor.putString(KEY_PREF2, pref2);
        editor.putString(KEY_PREF3, pref3);
        editor.putString(KEY_PREF4, pref4);
        editor.putString(KEY_PREF5, pref5);
        editor.putString(KEY_PREF1, currentLocation);
        editor.commit();
    }

    public void updateUser(String group, String pref1, String pref2, String pref3, String pref4, String pref5, Boolean organizer) {
        editor.putString(KEY_GROUP, group);
        editor.putString(KEY_PREF1, pref1);
        editor.putString(KEY_PREF2, pref2);
        editor.putString(KEY_PREF3, pref3);
        editor.putString(KEY_PREF4, pref4);
        editor.putString(KEY_PREF5, pref5);
        editor.putString(KEY_ORGANIZER, organizer.toString());
        editor.commit();
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

    }

    public int getId() {
        int id = Integer.parseInt(Pref.getString(KEY_ID, "0"));
        return id;
    }

    public String getName() {
        return Pref.getString(KEY_NAME, "");
    }

    public String getPrename() {
        return Pref.getString(KEY_PRENAME, "");
    }

    public String getPhotourl() {
        return Pref.getString(KEY_PHOTOURL, "");
    }

    public String getAdress() {
        return Pref.getString(KEY_ADRESS, "");
    }

    public String getGroup() {
        return Pref.getString(KEY_GROUP, "");
    }

    public String getPref1() {
        return Pref.getString(KEY_PREF1, "");
    }

    public String getPref2() {
        return Pref.getString(KEY_PREF2, "");
    }

    public String getPref3() {
        return Pref.getString(KEY_PREF3, "");
    }

    public String getPref4() {
        return Pref.getString(KEY_PREF4, "");
    }

    public String getPref5() {
        return Pref.getString(KEY_PREF5, "");
    }

    public Boolean getOrganizer() {
        return Boolean.parseBoolean(Pref.getString(KEY_ORGANIZER, "false"));
    }
}
