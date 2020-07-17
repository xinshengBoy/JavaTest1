package com.just.test.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 检测耳机是否插入
 */
public class CheckEarPhone extends Activity{
	private TextView txt_earphone;
	private Button btn_earphone;
	private static final int EARPHONE = 0;
	private int isON;
	private boolean isCheck;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.earphone);
		//actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "检测耳机");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", "完成");
		MyActionBar.actionbar(this,headerLayout,bundle);

		txt_earphone = (TextView) findViewById(R.id.txt_earphone);
		btn_earphone = (Button) findViewById(R.id.btn_earphone);
		btn_earphone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				boolean wiredHeadsetOn = audioManager.isWiredHeadsetOn();
				if (wiredHeadsetOn) {
					isON = 1;
				}else {
					isON = 2;
				}
				Message message = new Message();
				message.what = EARPHONE;
				message.arg1 = isON;
				handler.sendMessage(message);
			}
		});
	}
	
	Handler handler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case EARPHONE:
				if (msg.arg1 == 1) {
					txt_earphone.setText("检测结果：耳机已插入");
				} else if (msg.arg1 == 2) {
					txt_earphone.setText("检测结果：耳机未插入");
				}
				break;
				case 1:
					if (isCheck){
						txt_earphone.setText("检测结果：耳机已插入");
					}else {
						txt_earphone.setText("检测结果：耳机未插入");
					}
					break;
			default:
				break;
			}
			return false;
		}
	});

	/**
	 * 广播监听耳机是否插入
	 */
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)){
				int state = intent.getIntExtra("state",-1);
				switch (state){
					case 0:
						isCheck = false;
						break;
					case 1:
						isCheck = true;
						break;
					default:
						//未知状态
						break;
				}

				handler.sendEmptyMessage(1);
			}
		}
	};

	/**
	 * 注册广播
	 */
	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_HEADSET_PLUG);
		registerReceiver(mReceiver,filter);
	}

	/**
	 * 销毁广播
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
}
