package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.just.test.R;

/**
 * 代码安全等相关,
 * @author user
 *
 */
public class FileEncryPtionMain extends Activity implements OnClickListener{

	private Button btn_select_checkall;//查询所有类型相关的sd卡文件
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_filesafe_main);
		//查询所有类型相关的sd卡文件
		btn_select_checkall = (Button) findViewById(R.id.btn_select_checkall);
		btn_select_checkall.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if (v == btn_select_checkall) {
			Intent intent = new Intent(FileEncryPtionMain.this,CheckAllFile.class);
			startActivity(intent);
		}

	}
}
