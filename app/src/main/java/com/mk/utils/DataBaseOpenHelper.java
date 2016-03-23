package com.mk.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    public final static String TABLE_USER = "Users";
    public final static String TABLE_CALENDAR = "Calendar";

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
                + " (_id INTEGER PRIMARY KEY,name TEXT ,prename TEXT ,login TEXT, password TEXT, photo TEXT, adress TEXT, groupN TEXT, pref1 TEXT, pref2 TEXT, pref3 TEXT, pref4 TEXT, pref5 TEXT, organizer TEXT, lat DOUBLE PRECISION, lon DOUBLE PRECISION);)";
        String reqCalendar = "CREATE TABLE "
                + TABLE_CALENDAR
                + " (_id TEXT PRIMARY KEY,title TEXT ,date TEXT ,time TEXT);)";
        db.execSQL(req);
        db.execSQL(reqCalendar);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_USER + ";");
        db.execSQL("DROP TABLE " + TABLE_CALENDAR + ";");
        onCreate(db);
    }

    public Boolean verifyTable(SQLiteDatabase db) {
        String req = "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_USER + "'";
        Cursor cursor = db.rawQuery(req, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
    public Boolean VerifyCalendar(SQLiteDatabase db){
        String req = "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_CALENDAR + "'";
        Cursor cursor = db.rawQuery(req, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
   public Boolean VerifyCalendarItem(SQLiteDatabase db, String id){
       String req = "select * from "+TABLE_CALENDAR+" where _id = '" + id + "'";
       Cursor cursor = db.rawQuery(req, null);
       if (cursor != null) {
           if (cursor.getCount() > 0) {
               cursor.close();
               return true;
           }
           cursor.close();
       }
       return false;
    }
    public List<String> getCalendarData(SQLiteDatabase db){
    List<String> myCalendarData = new ArrayList<String>();
        String req = "select * from "+TABLE_CALENDAR+";";
        Cursor cursor = db.rawQuery(req, null);
        System.out.println(cursor.getCount());
        if (cursor.moveToFirst()) {
         do {
                 myCalendarData.add(cursor.getString(1)+"/"+cursor.getString(2)+"/"+cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return myCalendarData;
    }
    public void cleanCalendar(SQLiteDatabase db){
        db.execSQL("delete from "+ TABLE_CALENDAR);
    }

}
