package com.just.test.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.CustomRect;
import com.just.test.custom.CustomRound;
import com.just.test.widget.MyActionBar;

/**
 * 飞行模式
 *
 * @author user
 *
 */
public class AirPlainModel extends Activity {
	private Button btn_setting_airpPlainModel;
	private EditText et_setting_airplainmodel;
	private Button btn_airplain;
	private String airStatusString;// 手机飞行模式的当前状态
	private ContentResolver cr;
	private int openTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.airplainmodel_layout);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "飞行模式");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

//		btn_setting_airpPlainModel = (Button) findViewById(R.id.btn_setting_airpPlainModel);
//		getAirPlainModelStatus();
//		et_setting_airplainmodel = (EditText) findViewById(R.id.et_setting_airplainmodel);
//		btn_airplain = (Button) findViewById(R.id.btn_airplain);
//
//		btn_setting_airpPlainModel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				handler.sendEmptyMessage(0);
//			}
//		});
//
//		btn_airplain.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				String time = et_setting_airplainmodel.getText().toString()
//						.trim();
//				if (!time.equals("")) {
//					openTime = Integer.parseInt(time) * 1000 * 60;
//					openTime = openTime * 1000 * 60;
//					String toastString = "手机将在" + time + "分钟后进入飞行模式";
//					handler.removeCallbacks(runnable);
//					if (airStatusString.equals("0")) {
//						Toast.makeText(AirPlainModel.this, toastString,
//								Toast.LENGTH_LONG).show();
//						handler.postDelayed(runnable, openTime);
//					} else {
//						Toast.makeText(AirPlainModel.this, "当前已经是飞行模式了哟",
//								Toast.LENGTH_LONG).show();
//					}
//				}else {
//					Toast.makeText(AirPlainModel.this, "请输入开启时间",
//							Toast.LENGTH_LONG).show();
//				}
//			}
//		});

//		CustomRect rect = new CustomRect(AirPlainModel.this,10,10);
		CustomRect custom_rect = (CustomRect) findViewById(R.id.custom_rect);
		custom_rect.setCount(15,15,1000,600);

		CustomRound custom_round = (CustomRound) findViewById(R.id.custom_round);
		custom_round.setCount(500,500,8,8);
	}

//	/**
//	 * 获取当前手机飞行模式的状态
//	 */
//	private void getAirPlainModelStatus() {
//		// 0为关闭飞行,1为开启飞行
//		cr = getContentResolver();
//		airStatusString = Settings.System.getString(cr,
//				Settings.System.AIRPLANE_MODE_ON);
//
//		if (airStatusString.equals("0")) {
//			btn_setting_airpPlainModel.setText("开启飞行模式");
//		} else {
//			btn_setting_airpPlainModel.setText("关闭飞行模式");
//		}
//
//	}
//
//	private void setAirPlain() {
//		if (airStatusString.equals("0")) {
//			btn_setting_airpPlainModel.setText("关闭飞行模式");
//			Settings.System
//					.putString(cr, Settings.System.AIRPLANE_MODE_ON, "1");
//			Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);//.putExtra("state", true);
//			sendBroadcast(intent);
//		} else if (airStatusString.equals("1")) {
//			btn_setting_airpPlainModel.setText("开启飞行模式");
//			Settings.System
//					.putString(cr, Settings.System.AIRPLANE_MODE_ON, "0");
//			Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);//.putExtra("state", false);
//			sendBroadcast(intent);
//		}
//	}
//
//	private void setAirPlain2() {
//		if (airStatusString.equals("0")) {
//			Settings.System
//					.putString(cr, Settings.System.AIRPLANE_MODE_ON, "1");
//			Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED).putExtra("state", true);
//			sendBroadcast(intent);
//		} else if (airStatusString.equals("1")) {
//			Settings.System
//					.putString(cr, Settings.System.AIRPLANE_MODE_ON, "0");
//			Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED).putExtra("state", false);
//			sendBroadcast(intent);
//		}
//		handler.removeCallbacks(runnable);
//	}
//
//	Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			if (msg.what == 0) {
//				setAirPlain();
//			}
//		};
//	};
//	Runnable runnable = new Runnable() {
//
//		@Override
//		public void run() {
//			setAirPlain2();
//			et_setting_airplainmodel.setText("您的手机将在" + openTime + "分钟后进入飞行模式");
//		}
//	};
}
