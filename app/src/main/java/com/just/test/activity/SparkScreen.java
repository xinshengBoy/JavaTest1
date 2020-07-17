package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;

import com.just.test.widget.SparkView;

/**
 * 火花效果
 */
public class SparkScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SparkView sparkView = new SparkView(this);
		setContentView(sparkView);
	}
}
