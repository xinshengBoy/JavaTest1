package com.just.test.activity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.just.test.R;

public class Compass extends Activity implements SensorEventListener{
	private ImageView iv_compass;
	private Button txt_diction;
	private float currentDegree = 0f;
	private SensorManager sm;
	private float [] mValues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_compass);

		iv_compass = (ImageView) findViewById(R.id.iv_compass);
		txt_diction = (Button) findViewById(R.id.txt_diction);
		// 传感管理器
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//实现注册传感器
		sm.registerListener(Compass.this,
				sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	protected void onPause() {
		super.onPause();
		//取消注册
		sm.unregisterListener(this);
	}

	// 传感器报告新的值(方向改变)
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			float degree = event.values[0];
			/*
			 * RotateAnimation类：旋转变化动画类 参数说明: fromDegrees：旋转的开始角度。
			 * toDegrees：旋转的结束角度。
			 * pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF
			 * 、RELATIVE_TO_PARENT。 pivotXValue：X坐标的伸缩值。
			 * pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE
			 * 、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。 pivotYValue：Y坐标的伸缩值
			 */
			RotateAnimation ra = new RotateAnimation(currentDegree, -degree,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			// 旋转过程持续时间
			ra.setDuration(200);
			//设置动画借宿后的保留状态
			ra.setFillAfter(true);
			// 罗盘图片使用旋转动画
			iv_compass.startAnimation(ra);
			currentDegree = -degree;

			if (txt_diction != null) {
				mValues = event.values;
				float diction = mValues[0];
				if (diction > 22.5f && diction < 157.5f) {
					txt_diction.setText("东");
				}else if (diction >202.5f && diction < 337.5f) {
					txt_diction.setText("西");
				}else if (diction > 112.5f && diction < 247.5f) {
					txt_diction.setText("南");
				}else if (diction < 67.5 || diction > 292.5f) {
					txt_diction.setText("北");
				}
			}
		}
	}

	// 传感器精度的改变
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

}
