package com.just.test.activity;

import com.just.test.R;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 自动检测wifi状态
 * @author user
 *
 */
public class AutoCheckWifiState extends Activity {
	private TextView txt_checkWifi;
	private WifiManager manager;
	private static Handler handler = new Handler();
	int i = 0;
	private TimeCount timeCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_check_wifi);
		timeCount = new TimeCount(60000, 1000);

		LinearLayout layout_wifi = (LinearLayout) findViewById(R.id.layout_wifi);
		txt_checkWifi = (TextView) findViewById(R.id.txt_checkWifi);
		layout_wifi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				timeCount.start();
			}
		});
		manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		checkWifiState();
	}

	private void checkWifiState() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						if (manager != null) {
							int wifiState = manager.getWifiState();

							if (wifiState == 0) {
								txt_checkWifi.setText("当前WIFI状态：正在断开");
							}else if (wifiState == 1) {
								txt_checkWifi.setText("当前WIFI状态：已经断开");
							}else if (wifiState == 2) {
								txt_checkWifi.setText("当前WIFI状态：正在连接");
							}else if (wifiState == 3) {
								txt_checkWifi.setText("当前WIFI状态：已经连接"+i);
							}
							i++;
						}
					}
				},1000);
			}
		}).start();
	}

	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// 总时长，间隔时间
		}

		@Override
		public void onTick(long millisUntilFinished) {
			if (manager != null) {
				int wifiState = manager.getWifiState();
				if (wifiState == 0) {
					txt_checkWifi.setText("当前WIFI状态：正在断开");
				}else if (wifiState == 1) {
					txt_checkWifi.setText("当前WIFI状态：已经断开");
				}else if (wifiState == 2) {
					txt_checkWifi.setText("当前WIFI状态：正在连接");
				}else if (wifiState == 3) {
					txt_checkWifi.setText("当前WIFI状态：已经连接"+millisUntilFinished / 1000);
				}
			}
		}

		@Override
		public void onFinish() {
			txt_checkWifi.setText("当前WIFI状态：已经连接完成");
		}

	}
}
