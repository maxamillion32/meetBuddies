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

    public void insertUser(int id, String name, String prename, String photoUrl) {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("name", name);
        cv.put("prename", prename);
        cv.put("photo", photoUrl);

        open();
        db.insert(DataBaseOpenHelper.TABLE_USER, null, cv);
        close();
    }

    public void updateUser(int id, String name, String prename, String photoUrl) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("prename", prename);
        cv.put("photo", photoUrl);

        open();
        db.update(DataBaseOpenHelper.TABLE_USER, cv, "_id=" + id, null);
        close();
    }

    public Cursor getAllUsers() {
        open();
        Cursor var = db.query(DataBaseOpenHelper.TABLE_USER, null, null,
                null, null, null, "_id", null);
        close();
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

}
