package com.just.test.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.io.IOException;

/**
 * 点击按钮发出震动并有系统通知的声音
 * 查询电量情况
 * @author user
 * 
 */
public class VibratorActivity extends Activity {

	private Button btn_vibrator_test;
	private Vibrator vibrator;
	private MediaPlayer player;

	private int BatteryN;//目前电量
	private int BatteryV;//电池电压
	private double BatteryT;//电池温度
	private String BatteryStatus;//电池状态
	private String BatteryTemp;//电池使用情况 
	private TextView txt_battery;
	private int soundCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vibrator_layout);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "震动和电量");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);// 震动管理器
		registerReceiver(mBattery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		
		txt_battery = (TextView) findViewById(R.id.txt_battery);
		btn_vibrator_test = (Button) findViewById(R.id.btn_vibrator_test);
		btn_vibrator_test.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				long[] pattern = { 0, 400,800,1200 };// 持续时间
				vibrator.vibrate(pattern, 3);// 最后一个参数为点击一次重复的次数
				// 系统声音获取
				Uri uri = RingtoneManager.getActualDefaultRingtoneUri(
						VibratorActivity.this,
						RingtoneManager.TYPE_NOTIFICATION);
				player = MediaPlayer.create(VibratorActivity.this, uri);
				try {
					player.prepare();
				} catch (IOException e) {
					e.printStackTrace();
				}
				player.start();
				//设置播放次数
				player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mediaPlayer) {
						if (soundCount == 0){
							mediaPlayer.start();
							soundCount++;
						}
					}
				});
			}
		});
	}

	private BroadcastReceiver mBattery = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
				BatteryN = intent.getIntExtra("level", 0);
				BatteryV = intent.getIntExtra("voltage", 0);
				BatteryT = intent.getIntExtra("temperature", 0);

				switch (intent.getIntExtra("status",
						BatteryManager.BATTERY_STATUS_UNKNOWN)) {
				case BatteryManager.BATTERY_STATUS_CHARGING:
					BatteryStatus = "充电状态";
					break;
				case BatteryManager.BATTERY_STATUS_DISCHARGING:
					BatteryStatus = "放电状态";
					break;
				case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
					BatteryStatus = "未充电";
					break;
				case BatteryManager.BATTERY_STATUS_FULL:
					BatteryStatus = "充满电";
					break;
				case BatteryManager.BATTERY_STATUS_UNKNOWN:
					BatteryStatus = "未知道状态";
					break;
				}

				switch (intent.getIntExtra("health",
						BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
				case BatteryManager.BATTERY_HEALTH_UNKNOWN:
					BatteryTemp = "未知错误";
					break;
				case BatteryManager.BATTERY_HEALTH_GOOD:
					BatteryTemp = "状态良好";
					break;
				case BatteryManager.BATTERY_HEALTH_DEAD:
					BatteryTemp = "电池没有电";
					break;
				case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
					BatteryTemp = "电池电压过高";
					break;
				case BatteryManager.BATTERY_HEALTH_OVERHEAT:
					BatteryTemp = "电池过热";
					break;
				}

				txt_battery.setText("目前电量："+BatteryN+"%"+"\n"
				+"电池状态："+BatteryStatus+"\n"
				+"电压："+BatteryV+"mv"+"\n"
				+"当前温度为："+(BatteryT*0.1)+"℃");
			}

		}

	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBattery);
		vibrator.cancel();// 退出取消
		if (player != null) {
			player.stop();// 停止播放声音
		}
	}
}
