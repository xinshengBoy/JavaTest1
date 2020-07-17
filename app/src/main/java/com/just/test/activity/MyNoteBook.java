package com.just.test.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.just.test.R;
import com.just.test.adapter.MyNoteBookAdapter;
import com.just.test.tools.JSONDateKey;
import com.just.test.tools.MyDataBase;
import com.just.test.bean.NoteBook;
import com.just.test.bean.NoteBookID;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindCallback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyNoteBook extends Activity {

	private Button btn_noteBook;
	private ListView listView_notebook;
	private TextView txt_initNoteBook;
	public static ArrayList<NoteBook> list;
	private LayoutInflater inflater;
	private NoteBook nBook;
	private ArrayList<NoteBookID> array;
	private MyDataBase mdb;
	private ArrayList<Bundle> jsonData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mynotebook);

		//初始化bmob sdk
		//第二个参数是应用的Application ID
		Bmob.initialize(this,"a86877cd04c3e357f156b485a6662687");

		jsonData = new ArrayList<Bundle>();
		mdb=new MyDataBase(this);
		array=mdb.getArray();
		for (int i = 0; i < array.size(); i++) {
			System.out.println("id表："+array.toString());

		}

		nBook = new NoteBook();
		list = new ArrayList<NoteBook>();
		QueryData();
		System.out.println("内容表："+jsonData.size());
		inflater=getLayoutInflater();

		listView_notebook = (ListView)findViewById(R.id.listView_notebook);
		btn_noteBook = (Button)findViewById(R.id.btn_noteBook);
		txt_initNoteBook = (TextView) findViewById(R.id.txt_initNoteBook);
		if (array == null || array.size() == 0) {
			txt_initNoteBook.setVisibility(View.VISIBLE);
			listView_notebook.setVisibility(View.GONE);
		}else {
			txt_initNoteBook.setVisibility(View.GONE);
			listView_notebook.setVisibility(View.VISIBLE);
		}

		MyNoteBookAdapter adapter = new MyNoteBookAdapter(MyNoteBook.this, jsonData,R.layout.notebook_item);
		listView_notebook.setAdapter(adapter);

		/*
		 * 点击listView里面的item,进入到第二个页面，用来修改日记
		 */
		listView_notebook.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(),AddNoteBook.class);
				intent.putExtra("ids",list.get(position).getObjectId());
				startActivity(intent);
				MyNoteBook.this.finish();
			}
		});
		/*
		 * 长点后来判断是否删除数据
		 */
		listView_notebook.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
										   final int position, long id) {
				// TODO Auto-generated method stub
				//AlertDialog,来判断是否删除日记。
				new AlertDialog.Builder(MyNoteBook.this)
						.setTitle("删除")
						.setMessage("是否删除笔记")
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub

							}
						})
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								DeleteData(position);
								MyNoteBookAdapter adapter = new MyNoteBookAdapter(MyNoteBook.this, jsonData,R.layout.notebook_item);
								listView_notebook.setAdapter(adapter);
							}
						})
						.create().show();
				return true;
			}
		});
		/*
		 * 按钮点击事件，用来新建日记
		 */
		btn_noteBook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MyNoteBook.this,AddNoteBook.class);
				startActivity(intent);
				MyNoteBook.this.finish();
			}
		});
	}

	private void QueryData() {
//		BmobQuery<NoteBook> bmobQuery = new BmobQuery<NoteBook>();
//		bmobQuery.getObject(MyNoteBookDefault.this,new GetListener<NoteBook>() {
//
//			@Override
//			public void onFailure(int arg0, String msg) {
//				Toast.makeText(MyNoteBookDefault.this, "查询失败："+msg, Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onSuccess(NoteBook object) {
//				if (object == null) {
//					Toast.makeText(MyNoteBookDefault.this, "暂无数据", Toast.LENGTH_SHORT).show();
//				} else{
//					Toast.makeText(MyNoteBookDefault.this, "查询成功："+object.getCreatedAt(), Toast.LENGTH_SHORT).show();
////					txt_queryAll.setText("查询结果：---姓名："+object.getTitle()+"---地址："+object.getTime());
////					for (int i = 0; i < array.size(); i++) {
////						list.add(object.getTitle());
////						list.add(object.getContent());
////						list.add(object.getTime());
////					}
//					list.add(object);
//				}
//			}
//		});


		BmobQuery query = new BmobQuery("NoteBook");
		query.findObjects(this, new FindCallback() {

			@SuppressLint("NewApi") @Override
			public void onSuccess(JSONArray source) {
				JSONArray jsonArray;
				try {
					jsonArray = new JSONArray(source);
					for (int i = 0; i < jsonArray.length(); i++) {
						Bundle bundle = new Bundle();
						JSONObject object = jsonArray.getJSONObject(i);
						bundle.putString(JSONDateKey.NOTEBOOKTITLE, object.getString(JSONDateKey.NOTEBOOKTITLE));
						bundle.putString(JSONDateKey.NOTEBOOKCONTENT, object.getString(JSONDateKey.NOTEBOOKCONTENT));
						bundle.putString(JSONDateKey.NOTEBOOKTIME, object.getString(JSONDateKey.NOTEBOOKTIME));
//	    				Object noteBookID = object.get(JSONDateKey.NOTEBOOKOBJECTID);
						jsonData.add(bundle);
//	    				array.add((NoteBookID) noteBookID);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				//注意：查询的结果是JSONArray,需要自行解析
//	        	Toast.makeText(MyNoteBookDefault.this, "查询成功："+source.length(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(MyNoteBook.this, "查询失败："+arg1, Toast.LENGTH_SHORT).show();
			}
		});


	}

	/**
	 * 删除数据的方法
	 */
	private void DeleteData(final int position) {
		if (list !=null || list.size() != 0) {
			nBook.setObjectId(list.get(position).getObjectId());
			nBook.delete(MyNoteBook.this,new DeleteListener() {

				@Override
				public void onSuccess() {
					Toast.makeText(MyNoteBook.this, "删除成功："+nBook.getTitle(), Toast.LENGTH_SHORT).show();
//					txt_deleteAll.setText("删除结果    ：---姓名："+person.getName()+"---地址:"+person.getAddress()+"---编号:"+person.getObjectId());
					mdb.toDelete(array.get(position).getNoteID());
					array = mdb.getArray();
				}

				@Override
				public void onFailure(int code, String msg) {
					Toast.makeText(MyNoteBook.this, "删除失败："+msg, Toast.LENGTH_SHORT).show();

				}
			});
		} else {
			Toast.makeText(MyNoteBook.this, "没有数据，不能执行此操作", Toast.LENGTH_SHORT).show();
		}
	}
}
