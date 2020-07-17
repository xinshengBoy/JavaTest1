package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;

import com.just.test.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * app启动动画，让一张图片显示几秒，然后这张图片消失，进入app首页
 * @author user
 *
 */
public class APPStartAndBufferImage extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_start_buffer);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				APPStartAndBufferImage.this.finish();
			}
		}, 5000);
	}
}
