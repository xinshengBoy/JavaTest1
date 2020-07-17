package com.just.test.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.just.test.bean.MyNoteBookDefault;

import java.util.ArrayList;
import java.util.List;

/**
 * 笔记数据库
 * Created by user on 2016/12/23.
 */

public class NoteBookDBHelper extends SQLiteOpenHelper {

    private static final String NOTEBOOK_DATABASE_NAME = "notebookdatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "notebookdetail";

    private static final String ID = "_id";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String TIME = "time";

    //sql创建语句
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID + " integer primary key autoincrement,"
             + TITLE + " text not null," + CONTENT + " text not null," + TIME + " text not null);";

    public NoteBookDBHelper(Context context) {
        super(context, NOTEBOOK_DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);//删除旧表格
        onCreate(db);
    }

    public void insert(MyNoteBookDefault nb){
        SQLiteDatabase db = this.getWritableDatabase();
        //使用ContentValues添加数据
        ContentValues cv = new ContentValues();
        cv.put(TITLE,nb.getTitle());
        cv.put(CONTENT,nb.getContent());
        cv.put(TIME,nb.getTime());
        db.insert(TABLE_NAME,null,cv);
        db.close();
    }
    //删除
    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();//可写
        db.delete(TABLE_NAME,"_id=?",new String[]{String.valueOf(id)});
        db.close();
    }
    //获得条数
    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();//可读
        Cursor cursor = db.rawQuery("select * from notebookdetail",null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    //查询所有
    public List<MyNoteBookDefault> getAllInfo(){
        List<MyNoteBookDefault> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from notebookdetail order by _id desc",null);
        if (cursor.moveToFirst()){
            do {
                MyNoteBookDefault bd = new MyNoteBookDefault();
                bd.setId(cursor.getInt(0));
                bd.setTitle(cursor.getString(1));
                bd.setContent(cursor.getString(2));
                bd.setTime(cursor.getString(3));
                list.add(bd);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
