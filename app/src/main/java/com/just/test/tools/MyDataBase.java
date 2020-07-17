package com.just.test.tools;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.just.test.bean.NoteBookID;

/*
 * 专门用来数据库操作的类，这个类的建立是非常正确的，把所有的数据库操作都放到了这里来，其他直接调用就好了。
 * 数据的增，删，改，查，都在这里实现
 */
public class MyDataBase {
	Context context;
	MyOpenHelper myHelper;
	SQLiteDatabase myDatabase;
	/*
	 * 别的类实例化这个类的同时，创建数据库
	 */
	public MyDataBase(Context con){
		this.context=con;
		myHelper=new MyOpenHelper(context);
	}
	/*
	 * 得到填充ListView用的array数据，从数据库里查找后解析。第一个界面调用
	 */
	public ArrayList<NoteBookID> getArray(){
		ArrayList<NoteBookID> array=new ArrayList<NoteBookID>();
		ArrayList<NoteBookID> array1=new ArrayList<NoteBookID>();
		myDatabase=myHelper.getWritableDatabase();
		Cursor cursor=myDatabase.rawQuery("select id from mynotebook" , null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			String id=cursor.getString(cursor.getColumnIndex("id"));
			NoteBookID cun=new NoteBookID(id);
			array.add(cun);
			cursor.moveToNext();
		}
		myDatabase.close();
		for (int i = array.size(); i >0; i--) {
			array1.add(array.get(i-1));
		}
		return array1;
	}
	/*
	 *第二个界面调用，用来增加新的日记
	 */
	public void toInsert(NoteBookID cun){
		myDatabase=myHelper.getWritableDatabase();
		myDatabase.execSQL("insert into mynotebook(id)values('"+ cun.getNoteID()+"')");
		myDatabase.close();
	}
	/*
	 * 第一个界面调用，长按点击后选择删除日记
	 */
	public void toDelete(String id){
		myDatabase=myHelper.getWritableDatabase();
		myDatabase.execSQL("delete  from mynotebook where id="+id+"");
		myDatabase.close();
	}
}
