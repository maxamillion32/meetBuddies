package com.mk.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseConnector {

    String DATA_BASE_NAME = "a1878292_MeetBud";

    Context context;

    private DataBaseOpenHelper dbOpenHelper;

    private SQLiteDatabase db;

    public DataBaseConnector(Context context) {
        this.context = context;
        dbOpenHelper = new DataBaseOpenHelper(context, DATA_BASE_NAME, null, 1);
    }

    public void open() {
        db = dbOpenHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void insertUser(int id, String name, String prename, String login, String password, String photoUrl, String adress, String currentLocation, String phone, String group, String pref1, String pref2, String pref3, String pref4, String pref5, Boolean isGroupOrg) {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("name", name);
        cv.put("prename", prename);
        cv.put("login", login);
        cv.put("password", password);
        cv.put("photo", photoUrl);
        cv.put("photo", photoUrl);
        cv.put("adress", adress);
        cv.put("organizer", "");
        cv.put("groupN", group);
        cv.put("pref1", pref1);
        cv.put("pref2", pref2);
        cv.put("pref3", pref3);
        cv.put("pref4", pref4);
        cv.put("pref5", pref5);

        open();
        dbOpenHelper.onUpgrade(db, 0, 1);
        db.insert(DataBaseOpenHelper.TABLE_USER, null, cv);
        close();
    }

    public void updateUser(int id, String name, String prename, String login, String password, String photoUrl, String adress, String group, Boolean organisateur, String p1, String p2, String p3, String p4, String p5) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("prename", prename);
        cv.put("login", login);
        cv.put("password", password);
        cv.put("photo", photoUrl);
        cv.put("adress", adress);
        cv.put("groupN", group);
        cv.put("organizer", organisateur);
        cv.put("pref1", p1);
        cv.put("pref2", p2);
        cv.put("pref3", p3);
        cv.put("pref4", p4);
        cv.put("pref5", p5);

        open();
        db.update(DataBaseOpenHelper.TABLE_USER, cv, "_id=" + id, null);
        close();
    }

    public void modifyUser(int id, String name, String prename, String photoUrl, String adress, String phone) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("prename", prename);
        cv.put("photo", photoUrl);
        cv.put("adress", adress);
        cv.put("phone", phone);

        open();
        db.update(DataBaseOpenHelper.TABLE_USER, cv, "_id=" + id, null);
        close();
    }

    public void addInfo(int id, String group, String p1, String p2, String p3, String p4, String p5, Boolean organisateur) {
        ContentValues cv = new ContentValues();
        cv.put("groupN", group);
        cv.put("organizer", organisateur.toString());
        cv.put("pref1", p1);
        cv.put("pref2", p2);
        cv.put("pref3", p3);
        cv.put("pref4", p4);
        cv.put("pref5", p5);

        open();
        db.update(DataBaseOpenHelper.TABLE_USER, cv, "_id=" + id, null);
        close();
    }

    public Cursor getAllUsers() {
        open();
        Cursor var = db.query(DataBaseOpenHelper.TABLE_USER, null, null,
                null, null, null, "_id", null);
        // close();
        return var;

    }

    public Cursor getOneUser(int id) {
        open();
        Cursor var = db.query(DataBaseOpenHelper.TABLE_USER, null, "_id="
                + id, null, null, null, "_id", null);
        // close();
        return var;

    }

    public void deleteUser(int id) {
        open();
        db.delete(DataBaseOpenHelper.TABLE_USER, "_id=" + id, null);
        close();
    }

    public void deleteAll() {
        open();
        dbOpenHelper.onUpgrade(db, 0, 1);
    }

    public Boolean verifyTable(){
        open();
        return dbOpenHelper.verifyTable(db);
    }
    public Boolean verifyTableCalendar(){
        open();
        return dbOpenHelper.VerifyCalendar(db);
    }
    public void addCalendarInfo(String id, String title, String date, String time){
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("title", title);
        cv.put("date", date);
        cv.put("time", time);
        open();
        dbOpenHelper.onUpgrade(db, 0, 1);
        db.insert(DataBaseOpenHelper.TABLE_CALENDAR, null, cv);
        close();
    }
    public void updateCalendarInfo(String id, String title, String date, String time){
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("date", date);
        cv.put("time", time);
        open();
        db.update(DataBaseOpenHelper.TABLE_CALENDAR, cv, "_id=" + id, null);
        close();
    }


}
