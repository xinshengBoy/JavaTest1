package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;

/**
 * 沉浸式状态栏
 * 状态栏的颜色随着背景颜色的改变而改变
 * 所以在点击按钮切换颜色的时候，改变的颜色应该是背景的颜色，状态栏的颜色才会改变，而不是改变按钮的背景颜色
 * @author user
 *
 */
public class ChangeStatusColume extends Activity implements OnClickListener{

	private Button btn_changeStatusColume1,btn_changeStatusColume2,btn_changeStatusColume3,btn_changeStatusColume4;
	private LinearLayout layout_changestatus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_changestatuscolume);

		layout_changestatus = (LinearLayout) findViewById(R.id.layout_changestatus);
		btn_changeStatusColume1 = (Button)findViewById(R.id.btn_changeStatusColume1);
		btn_changeStatusColume2 = (Button)findViewById(R.id.btn_changeStatusColume2);
		btn_changeStatusColume3 = (Button)findViewById(R.id.btn_changeStatusColume3);
		btn_changeStatusColume4 = (Button)findViewById(R.id.btn_changeStatusColume4);

		btn_changeStatusColume1.setOnClickListener(this);
		btn_changeStatusColume2.setOnClickListener(this);
		btn_changeStatusColume3.setOnClickListener(this);
		btn_changeStatusColume4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == btn_changeStatusColume1) {
			layout_changestatus.setBackgroundColor(0xFFF6AA00);
		}else if (v == btn_changeStatusColume2) {
			layout_changestatus.setBackgroundColor(0xFF2DCD01);
		}else if (v == btn_changeStatusColume3) {
			layout_changestatus.setBackgroundColor(0xFF002A8C);
		}else if (v == btn_changeStatusColume4) {
			layout_changestatus.setBackgroundColor(0xFFE61A5F);
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			//透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
}
