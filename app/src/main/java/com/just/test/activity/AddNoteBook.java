package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.tools.MyDataBase;
import com.just.test.bean.NoteBook;
import com.just.test.bean.NoteBookID;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddNoteBook extends Activity {

	private EditText et_addTitle;
	private EditText et_addContent;
	private Button btn_save_notebook;
	//	private ArrayList<NoteBook> list;
	private int ids;
	private NoteBook nBook;
	private String times;
	private String title;
	private String content;
	private MyDataBase myDatabase;
	private String id;
	private NoteBookID nb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notebook_addandupdate);

		et_addTitle = (EditText) findViewById(R.id.et_addTitle);
		et_addContent = (EditText) findViewById(R.id.et_addContent);
		btn_save_notebook = (Button) findViewById(R.id.btn_save_notebook);

		myDatabase=new MyDataBase(this);
		nBook = new NoteBook();
//		list = new ArrayList<NoteBook>();
		Intent intent = this.getIntent();
		ids = intent.getIntExtra("ids", 0);
		id = String.valueOf(ids);
		if (ids != 0) {
			et_addTitle.setText(nBook.getTitle());
			et_addContent.setText(nBook.getContent());
		}

		// 保存按钮的点击事件，他和返回按钮是一样的功能，所以都调用isSave()方法；
		btn_save_notebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isSave();
			}
		});
	}

	@Override
	public void onBackPressed() {
		isSave();
	}

	private void isSave() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy.MM.dd  HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		times = formatter.format(curDate);
		title = et_addTitle.getText().toString();
		content = et_addContent.getText().toString();
		// 是要修改数据
		if (ids != 0) {
			UpdateData();
			Intent intent = new Intent(AddNoteBook.this, MyNoteBook.class);
			startActivity(intent);
			AddNoteBook.this.finish();
		}
		// 新建日记
		else {
			if (title.equals("") && content.equals("")) {
				Intent intent = new Intent(AddNoteBook.this, MyNoteBook.class);
				startActivity(intent);
				AddNoteBook.this.finish();
			} else {
				AddNote();
				Intent intent = new Intent(AddNoteBook.this, MyNoteBook.class);
				startActivity(intent);
				AddNoteBook.this.finish();
			}

		}
	}

	/**
	 * 添加数据
	 */
	private void AddNote() {
		nBook.setTitle(title);
		nBook.setContent(content);
		nBook.setTime(times);
		nBook.save(AddNoteBook.this, new SaveListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(AddNoteBook.this,
						"添加数据成功，返回的objectId为：" + nBook.getObjectId(),
						Toast.LENGTH_SHORT).show();
				MyNoteBook.list.add(nBook);
				id = nBook.getObjectId();
				nb = new NoteBookID(id);
				myDatabase.toInsert(nb);
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(AddNoteBook.this, "创建数据失败：" + msg,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 更新数据的方法
	 */
	private void UpdateData() {
		nBook.setTitle(title);
		nBook.setContent(content);
		nBook.setTime(times);
		nBook.update(AddNoteBook.this, MyNoteBook.list.get(ids).getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(AddNoteBook.this, "更新成功：", Toast.LENGTH_SHORT)
						.show();
				// txt_updateAll.setText("更新结果：---姓名："+person.getName()+"---地址："+person.getAddress()+"---更新时间："+person.getUpdatedAt());
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(AddNoteBook.this, "更新失败：" + msg,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}
