package com.just.test.activity;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.LinearLayout;

import com.just.test.R;

/**
 * 摇一摇更换主题
 *
 * @author user
 *
 */
public class BGChangeMoving extends Activity {

	private LinearLayout layout_bgmoving;
	private SensorManager sm;
	private Vibrator vibrator;
	private SensorEventListener s;
	public static int flag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bgchange);
		/**
		 * 获取配置文件中meta_data的值
		 */
		//1,获取application中的meta_data的值
		ApplicationInfo appInfo1 = null;
		//2,获取activity中的meta_data的值
		ActivityInfo appInfo2 = null;
		//3,获取service中meta_data的值
		ServiceInfo appInfo3 = null;
		//4,获取receive中meta_data的值
		ActivityInfo appInfo4 = null;
//		try {
//			appInfo1 = this.getPackageManager().getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
//			String msg1 = appInfo1.metaData.getString("JPUSH_APPKEYS");
//
//			appInfo2 = this.getPackageManager().getActivityInfo(cn1,PackageManager.GET_META_DATA);
//			int msg2 = appInfo2.metaData.getInt("JPUSH_APPKEY");//activity中只能传数字
//
//			ComponentName cn2=new ComponentName(this, DownloadService.class);
//			appInfo3 = this.getPackageManager().getServiceInfo(cn2,PackageManager.GET_META_DATA);
//			String msg3 = appInfo3.metaData.getString("JPUSH_APPKEY");
//
//			ComponentName cn3=new ComponentName(this, PushReceiver.class);
//			appInfo4 = this.getPackageManager().getReceiverInfo(cn3,PackageManager.GET_META_DATA);
//			String msg4 = appInfo4.metaData.getString("JPUSH_APPKEY");

//			Log.d("meta_data", "application  meta_data:"+msg1+"---activity meta_data:"+msg2+"---service meta_data:"+msg3+"---receive meta_data:"+msg4);
//		} catch (NameNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		layout_bgmoving = (LinearLayout) findViewById(R.id.layout_bgmoving);
		layout_bgmoving.setBackgroundResource(R.drawable.bg01);
		// 传感器
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);

		// 震动管理器
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		//监听器
		s = new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				int sensorType = event.sensor.getType();
				// values[0]:X轴，values[1]：Y轴，values[2]：Z轴
				float[] values = event.values;
				if (sensorType == Sensor.TYPE_ACCELEROMETER) {

					if ((Math.abs(values[0]) > 12 || Math.abs(values[1]) > 12 || Math
							.abs(values[2]) > 12)) {
						flag++;
						if (flag > 3)
							flag = 0;
						long[] pattern = { 500, 500 }; // OFF/ON/OFF/ON
						vibrator.vibrate(pattern, -1);
						mHandler.sendEmptyMessage(0);
					}
				}
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}
		};



	}

	@Override
	protected void onResume() {
		super.onResume();
		sm.registerListener((SensorEventListener) s,
				sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				sm.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		super.onStop();
		sm.unregisterListener(s);
	}
	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (flag) {
				case 0:
					layout_bgmoving.setBackgroundResource(R.drawable.bg01);
					break;
				case 1:
					layout_bgmoving.setBackgroundResource(R.drawable.bg02);
					break;
				case 2:
					layout_bgmoving.setBackgroundResource(R.drawable.bg03);
					break;
				case 3:
					layout_bgmoving.setBackgroundResource(R.drawable.bg04);
					break;
			}

		}

	};
}
