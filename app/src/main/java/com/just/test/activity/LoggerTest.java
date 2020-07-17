package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.tools.Logger;
import com.just.test.tools.MitakeLogger;
import com.just.test.widget.MyActionBar;

/**
 * log打印测试：开关
 */
public class LoggerTest extends Activity implements OnClickListener{

	private Button btn_logTest,btn_logOpen;
	private boolean logState = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logtest);
		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "log打印测试");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		btn_logTest = (Button) findViewById(R.id.btn_logTest);
		btn_logOpen = (Button) findViewById(R.id.btn_logOpen);
		btn_logTest.setOnClickListener(this);
		btn_logOpen.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == btn_logTest) {
			if (logState) {
				MitakeLogger.log(4, "mitake", "我点击了Log测试");
				Logger.log("mitake", "Log测试");
			}else {
				Toast.makeText(LoggerTest.this,"请先开启log打印",Toast.LENGTH_SHORT).show();
			}
		} else if (v == btn_logOpen) {
			if (logState){
				Logger.level = 0;
				MitakeLogger.debugLevel = 0;
				btn_logOpen.setText("开启LOG打印");
				logState = false;
			}else {
				Logger.level = 6;
				MitakeLogger.debugLevel = 6;
				btn_logOpen.setText("关闭LOG打印");
				logState = true;
			}
		}
		
	}
}
