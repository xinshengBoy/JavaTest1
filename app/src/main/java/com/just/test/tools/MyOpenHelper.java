package com.just.test.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * 重写了SQLiteOpenHelper类，用来建立数据库，还有表，自己还应该好好学习一下。
 */
public class MyOpenHelper extends SQLiteOpenHelper{

	public MyOpenHelper(Context context) {
		super(context, "mydates", null, 1);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table mynotebook(id text PRIMARY KEY)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	
}
