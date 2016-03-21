package com.mk.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    public final static String TABLE_USER = "Users";

    public DataBaseOpenHelper(Context context, String name,
                              CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String req = "CREATE TABLE "
                + TABLE_USER
                + " (_id INTEGER PRIMARY KEY,name TEXT ,prename TEXT ,login TEXT, password TEXT, photo TEXT, adress TEXT, group TEXT, pref1 TEXT, pref2 TEXT, pref3 TEXT, pref4 TEXT, pref5 TEXT, organizer TEXT);)";
        db.execSQL(req);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_USER + ";");
        onCreate(db);
    }

}
