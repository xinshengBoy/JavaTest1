package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.just.test.R;
import com.just.test.widget.LovelyToast;
/**
 * 自定义toast
 * @author user
 *
 */
public class MyLovelyToast extends Activity implements OnClickListener{

	private Button btn_successToast,btn_updownToast,btn_leftrightToast,btn_scaleToast,btn_leftToast,btn_rightToast;
	private Button btn_defaultToast,btn_infoToast,btn_confusingToast,btn_errorToast,btn_warningToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mylovelytoast);
		
		btn_successToast = (Button)findViewById(R.id.btn_successToast);
		btn_warningToast = (Button)findViewById(R.id.btn_warningToast);
		btn_errorToast = (Button)findViewById(R.id.btn_errorToast);
		btn_confusingToast = (Button)findViewById(R.id.btn_confusingToast);
		btn_infoToast = (Button)findViewById(R.id.btn_infoToast);
		btn_defaultToast = (Button)findViewById(R.id.btn_defaultToast);
		btn_updownToast = (Button)findViewById(R.id.btn_updownToast);
		btn_leftrightToast = (Button)findViewById(R.id.btn_leftrightToast);
		btn_scaleToast = (Button)findViewById(R.id.btn_scaleToast);
		btn_leftToast = (Button)findViewById(R.id.btn_leftToast);
		btn_rightToast = (Button)findViewById(R.id.btn_rightToast);
		
		btn_successToast.setOnClickListener(this);
		btn_warningToast.setOnClickListener(this);
		btn_errorToast.setOnClickListener(this);
		btn_confusingToast.setOnClickListener(this);
		btn_infoToast.setOnClickListener(this);
		btn_defaultToast.setOnClickListener(this);
		btn_updownToast.setOnClickListener(this);
		btn_leftrightToast.setOnClickListener(this);
		btn_scaleToast.setOnClickListener(this);
		btn_leftToast.setOnClickListener(this);
		btn_rightToast.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == btn_successToast) {
			LovelyToast.makeText(MyLovelyToast.this, "SUCCESS!!",LovelyToast.LENGTH_LONG, LovelyToast.SUCCESS);
		} else if (v == btn_warningToast) {
			LovelyToast.makeText(MyLovelyToast.this, "WARNING!!",LovelyToast.LENGTH_LONG, LovelyToast.WARNING);
		} else if (v == btn_errorToast) {
			LovelyToast.makeText(MyLovelyToast.this, "ERROR!!",LovelyToast.LENGTH_LONG, LovelyToast.ERROR);
		} else if (v == btn_confusingToast) {
			LovelyToast.makeText(MyLovelyToast.this, "CONFUSING!!",LovelyToast.LENGTH_LONG, LovelyToast.CONFUSING);
		} else if (v == btn_infoToast) {
			LovelyToast.makeText(MyLovelyToast.this, "INFO!!",LovelyToast.LENGTH_LONG, LovelyToast.INFO);
		} else if (v == btn_defaultToast) {
			LovelyToast.makeText(MyLovelyToast.this, "DEFAULT!!",LovelyToast.LENGTH_LONG, LovelyToast.DEFAULT);
		} else if (v == btn_updownToast) {
			LovelyToast.makeText(MyLovelyToast.this, "UPDOWN!!",0, LovelyToast.SUCCESS,0, LovelyToast.TOP_DOWN);
		} else if (v == btn_leftrightToast) {
			LovelyToast.makeText(MyLovelyToast.this, "LEFTRIGHT!!",0, LovelyToast.SUCCESS,0, LovelyToast.LEFT_RIGHT);
		} else if (v == btn_scaleToast) {
			LovelyToast.makeText(MyLovelyToast.this, "SCALE!!",0, LovelyToast.SUCCESS,0, LovelyToast.SCALE);
		} else if (v == btn_leftToast) {
			LovelyToast.makeText(MyLovelyToast.this, "LEFT!!",0, LovelyToast.SUCCESS,0, LovelyToast.LEFT);
		} else if (v == btn_rightToast) {
			LovelyToast.makeText(MyLovelyToast.this, "RIGHT!!",0, LovelyToast.SUCCESS,0,LovelyToast.RIGHT);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		LovelyToast.cancel();
	}
}
