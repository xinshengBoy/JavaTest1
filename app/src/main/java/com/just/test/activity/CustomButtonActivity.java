package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.just.test.R;
import com.just.test.widget.ImageBtn;
import com.just.test.widget.MyActionBar;
import com.just.test.widget.WaveView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 自定义按钮
 */
public class CustomButtonActivity extends Activity implements OnClickListener{

	private ImageBtn btn_right,btn_error;
	private TextView txt_count;
	private WaveView waveView;
	private ImageButton ib_miss,ib_add;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_btn);
		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "自定义按钮");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		btn_right = (ImageBtn) findViewById(R.id.btn_right);
		btn_error = (ImageBtn) findViewById(R.id.btn_error);
		//设置样式
		btn_right.setImageResource(R.drawable.right);
		btn_right.setTextView("确定");
		btn_error.setImageResource(R.drawable.wrong);
		btn_error.setTextView("取消");
		//水平衡样式
		waveView = (WaveView)findViewById(R.id.waveView);

		btn_right.setOnClickListener(this);
		btn_error.setOnClickListener(this);

		ib_add = (ImageButton)findViewById(R.id.ib_add);
		ib_miss = (ImageButton)findViewById(R.id.ib_miss);
		txt_count = (TextView) findViewById(R.id.txt_count);
		txt_count.setText(count+"");
		ib_add.setOnClickListener(this);
		ib_miss.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == btn_right) {
			Toast.makeText(CustomButtonActivity.this, "It's Success", Toast.LENGTH_SHORT).show();
		} else if (v == btn_error) {
			Toast.makeText(CustomButtonActivity.this, "It's Fail", Toast.LENGTH_SHORT).show();
		} else if (v == ib_miss) {
			count--;
			if (count <= 0) {
				count = 0;
			}
			handler.sendEmptyMessage(0);
		} else if (v == ib_add) {
			count++;
			if (count >= 10) {
				count = 10;
			}
			handler.sendEmptyMessage(0);
		}
		
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				txt_count.setText(count+"");
			}
		};
	};
}
