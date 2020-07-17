package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.tools.PhoneInfo;
import com.just.test.widget.MyActionBar;

/**
 * 获取手机基本信息
 */
public class GetPhoneMessage extends Activity {

	private TextView txt_getPhoneNumber,txt_getPhoneServerName,txt_getPhoneMassge;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phonemessage_layout);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "获取手机基本信息");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		txt_getPhoneNumber = (TextView) findViewById(R.id.txt_getPhoneNumber);
		txt_getPhoneServerName = (TextView) findViewById(R.id.txt_getPhoneServerName);
		txt_getPhoneMassge = (TextView) findViewById(R.id.txt_getPhoneMassge);

		PhoneInfo info = new PhoneInfo(GetPhoneMessage.this);
		String nativePhoneNumber = info.getNativePhoneNumber();
		String providersName = info.getProvidersName();
		String phoneInfo = info.getPhoneInfo();

		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		nativePhoneNumber = tm.getLine1Number();
		txt_getPhoneNumber.setText("手机号码："+nativePhoneNumber);
		txt_getPhoneServerName.setText("手机服务商信息："+providersName);
		txt_getPhoneMassge.setText("手机基本信息："+phoneInfo);
	}

}
