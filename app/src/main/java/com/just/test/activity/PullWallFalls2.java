package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.just.test.R;

public class PullWallFalls2 extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_pullwallfalls2);
	}

}
