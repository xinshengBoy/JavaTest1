package com.just.test.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.just.test.bean.FilmDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 新建电影列表数据库和表
 * Created by admin on 2017/5/23.
 */

public class FilmListDBHelper extends SQLiteOpenHelper {

    private static final String NOTEBOOK_DATABASE_NAME = "filmlistdatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "filmdetail";

    private static final String ID = "_id";//电影id
    private static final String FILMNAME = "filmname";//电影名称
    private static final String FILMTYPE = "filmtype";//电影类别
    private static final String FILMDIRECTOR = "filmdirector";//电影导演
    private static final String FILMSTAR = "filmstar";//主演
    private static final String FILMLONG = "filmlong";//电影片长
    private static final String FILMCOUNTRY = "filmcountry";//电影拍摄国家
    private static final String FILMDATE = "filmdate";//上映时间
    private static final String FILMURL = "filmurl";//电影播放链接
    private static final String FILMINFO = "filminfo";//电影简介

    //sql创建语句
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID + " integer primary key autoincrement,"
            + FILMNAME + " text not null," + FILMTYPE + " text not null," + FILMDIRECTOR + " text not null," + FILMSTAR + " text not null," +
            ""+ FILMLONG + " text not null," + FILMCOUNTRY + " text not null," + FILMDATE + " text not null," + FILMURL + " text not null," + FILMINFO + " text not null);";

    public FilmListDBHelper(Context context) {
        super(context,NOTEBOOK_DATABASE_NAME,null,DATABASE_VERSION);//创建数据库
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);//创建表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);//删除旧表格
        onCreate(db);
    }

    /**
     * 写入数据
     * @param detail 要写入的数据对象
     */
    public void insert(FilmDetail detail){
        SQLiteDatabase db = this.getWritableDatabase();
        //使用contentvalues添加数据
        ContentValues values = new ContentValues();
        values.put(FILMNAME,detail.getFilmname());
        values.put(FILMTYPE,detail.getFilmtype());
        values.put(FILMDIRECTOR,detail.getFilmdirector());
        values.put(FILMSTAR,detail.getFilmstar());
        values.put(FILMLONG,detail.getFilmlong());
        values.put(FILMCOUNTRY,detail.getFilmcountry());
        values.put(FILMDATE,detail.getFilmdate());
        values.put(FILMURL,detail.getFilmurl());
        values.put(FILMINFO,detail.getFilminfo());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    /**
     * 根据id删除对应的数据
     * @param id 要删除数据的id
     */
    public void delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();//可写
        db.delete(TABLE_NAME,"_id=?",new String[]{id});
        db.close();
    }

    /**
     * 删除所有
     */
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();//可写
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

    /**
     * 根据名称查询某条数据
     * @param filmName 输入的电影名称
     * @return  返回的电影的详细信息
     */
    public FilmDetail checkFilm(String filmName){
        SQLiteDatabase db = this.getReadableDatabase();//可读
        Cursor cursor = db.rawQuery("select * from filmdetail where filmname="+filmName,null);
        FilmDetail detail = new FilmDetail();
        if (cursor.moveToFirst()){
            do {

                detail.setId(cursor.getString(0));
                detail.setFilmname(cursor.getString(1));
                detail.setFilmtype(cursor.getString(2));
                detail.setFilmdirector(cursor.getString(3));
                detail.setFilmstar(cursor.getString(4));
                detail.setFilmlong(cursor.getString(5));
                detail.setFilmcountry(cursor.getString(6));
                detail.setFilmdate(cursor.getString(7));
                detail.setFilmurl(cursor.getString(8));
                detail.setFilminfo(cursor.getString(9));
            }while (cursor.moveToNext());
        }
        db.close();
        return detail;
    }
    /**
     * 查询表中所有的条数
     * @return  返回的条数
     */
    public int getListCount(){
        SQLiteDatabase db = this.getReadableDatabase();//可读
        Cursor cursor = db.rawQuery("select * from filmdetail",null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * 查询所有
     * @return  所有内容
     */
    public List<FilmDetail> getFilmAllInfo(){
        List<FilmDetail> mList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();//可读
        Cursor cursor = db.rawQuery("select * from filmdetail",null);
        if (cursor.moveToFirst()){
            do {

                FilmDetail detail = new FilmDetail();
                detail.setId(cursor.getString(0));
                detail.setFilmname(cursor.getString(1));
                detail.setFilmtype(cursor.getString(2));
                detail.setFilmdirector(cursor.getString(3));
                detail.setFilmstar(cursor.getString(4));
                detail.setFilmlong(cursor.getString(5));
                detail.setFilmcountry(cursor.getString(6));
                detail.setFilmdate(cursor.getString(7));
                detail.setFilmurl(cursor.getString(8));
                detail.setFilminfo(cursor.getString(9));

                mList.add(detail);
            }while (cursor.moveToNext());
        }
        db.close();
        return mList;
    }

    /**
     * 修改某一条数据
     * @param detail 要修改的数据
     */
    public void update(FilmDetail detail){
        SQLiteDatabase db = this.getWritableDatabase();//可写
        ContentValues values = new ContentValues();
        values.put(FILMNAME,detail.getFilmname());
        values.put(FILMTYPE,detail.getFilmtype());
        values.put(FILMDIRECTOR,detail.getFilmdirector());
        values.put(FILMSTAR,detail.getFilmstar());
        values.put(FILMLONG,detail.getFilmlong());
        values.put(FILMCOUNTRY,detail.getFilmcountry());
        values.put(FILMDATE,detail.getFilmdate());
        values.put(FILMURL,detail.getFilmurl());
        values.put(FILMINFO,detail.getFilminfo());

        String whereClause = "_id=?";//修改的条件
        String[] whereArgs = new String[]{String.valueOf(detail.getId())};
        db.update(TABLE_NAME,values,whereClause,whereArgs);
        db.close();
    }
}
