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
				+ " (_id INTEGER PRIMARY KEY,name TEXT ,prename TEXT ,photo TEXT);)";
		db.execSQL(req);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
