package com.just.test.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.just.test.bean.FilmImage;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储图片的数据库
 * Created by admin on 2017/5/26.
 */

public class ImageDBHelper extends SQLiteOpenHelper {

    private static final String FILMIAMGE_DATABASE_DATA = "filmimagedatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "filmimage";

    private static final String ID = "_id";//电影海报ID
    private static final String FILMIMAGE_NAME = "filmimage_name"; //电影海报名称即电影名称
    private static final String FILMIMAGE_DATE = "filmimage_date";//电影上映时间
    private static final String FILMIMAGE_URL = "filmimage_url";//电影海报链接
//    private static final Blob FILMIMAGE_PITURE = filmimage_piture;

    public ImageDBHelper(Context context) {
        super(context, FILMIAMGE_DATABASE_DATA, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(" + ID + " integer primary key," + FILMIMAGE_NAME + " text not null," +
                FILMIMAGE_DATE + " text not null," + FILMIMAGE_URL + " text not null,filmimage_picture BLOB);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);//删除旧表格
        onCreate(db);
    }

    /**
     * 写入数据
     * @param film 要写入的数据对象
     */
    public void insert(FilmImage film){
        SQLiteDatabase db = this.getWritableDatabase();//可写
        ContentValues values = new ContentValues();
        values.put(FILMIMAGE_NAME,film.getFilmImage_name());
        values.put(FILMIMAGE_DATE,film.getFilmImage_date());
        values.put(FILMIMAGE_URL,film.getFilmImage_url());
        values.put("filmimage_picture",film.getFilmImage_picture());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    /**
     * 根据id删除对应的数据
     * @param id 要删除数据的id
     */
    public void delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"_id=?",new String[]{id});
        db.close();
    }

    /**
     * 删除所有
     */
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

    /**
     * 根据名称查询某条数据
     * @param id 输入的电影id
     * @return  返回的电影的详细信息
     */
    public FilmImage checkFilmImage(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from filmimage where _id="+id,null);
        FilmImage detail = new FilmImage();
        if (cursor.moveToFirst()){
            do {
                detail.setId(cursor.getString(0));
                detail.setFilmImage_name(cursor.getString(1));
                detail.setFilmImage_date(cursor.getString(2));
                detail.setFilmImage_url(cursor.getString(3));
                detail.setFilmImage_picture(cursor.getBlob(cursor.getColumnIndex("filmimage_picture")));
            }while (cursor.moveToNext());
        }
        db.close();
        return detail;
    }

    /**
     * 查询所有
     * @return  所有内容
     */
    public List<FilmImage> checkAllInfo(){
        List<FilmImage> mList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from filmimage",null);
        if (cursor.moveToFirst()){
            do {
                FilmImage detail = new FilmImage();
                detail.setId(cursor.getString(0));
                detail.setFilmImage_name(cursor.getString(1));
                detail.setFilmImage_date(cursor.getString(2));
                detail.setFilmImage_url(cursor.getString(3));
                detail.setFilmImage_picture(cursor.getBlob(cursor.getColumnIndex("filmimage_picture")));

                mList.add(detail);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mList;
    }

    /**
     * 修改某一条数据
     * @param detail 要修改的数据
     */
    public void update(FilmImage detail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FILMIMAGE_NAME,detail.getFilmImage_name());
        values.put(FILMIMAGE_DATE,detail.getFilmImage_date());
        values.put(FILMIMAGE_URL,detail.getFilmImage_url());
        values.put("filmimage_picture",detail.getFilmImage_picture());

        String whereClause = "_id=?";//设置修改的条件
        String [] whereArgs = new String[]{String.valueOf(detail.getId())};
        db.update(TABLE_NAME,values,whereClause,whereArgs);
        db.close();
    }
}
