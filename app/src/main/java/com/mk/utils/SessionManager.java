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

    public SessionManager(Context cont) {
        Context = cont;
        Pref = Context.getSharedPreferences(PREF_NAME, 0);
        editor = Pref.edit();
        // TODO Auto-generated constructor stub
    }

    public void createLoginSession(String id, String name, String prename, String photoUrl, String adress) {

        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PRENAME, prename);
        editor.putString(KEY_PHOTOURL, photoUrl);
        editor.putString(KEY_ADRESS, adress);
        editor.putString(KEY_ID, id);
        editor.commit();
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
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

}
