package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;

public class TestSharePreference extends Activity {

	private LinearLayout layout_sharePreference;
	private ImageView iv_sharePreference;
	private EditText et_sharePreference;
	private Button btn_sharePreference;
	private TextView txt_sharePreference;
	private CheckBox ck_sharePreference;
	private boolean isVisible,isCheck;
	private Editor editor,editor2,editor3;
	private SharedPreferences sp,sp2,sp3;
	private String SAVEKEY = "savekey";
	private String ISVISIBLE = "isVisible";
	private String ISCHECK = "isCheck";
	private InputMethodManager im;

	/**
	 * 共享首选项
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_testsharepreference);

		im = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

		layout_sharePreference = (LinearLayout) findViewById(R.id.layout_sharePreference);
		iv_sharePreference = (ImageView) findViewById(R.id.iv_sharePreference);
		et_sharePreference = (EditText)findViewById(R.id.et_sharePreference);
		btn_sharePreference = (Button)findViewById(R.id.btn_sharePreference);
		txt_sharePreference = (TextView) findViewById(R.id.txt_sharePreference);
		ck_sharePreference = (CheckBox) findViewById(R.id.ck_sharePreference);

		sp = getSharedPreferences("save", MODE_PRIVATE);
		editor = sp.edit();

		String key = sp.getString(SAVEKEY, "");
		txt_sharePreference.setText("value:"+key);
		btn_sharePreference.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String saveString = et_sharePreference.getText().toString().trim();
				if (saveString == null || saveString.equals("")) {
					Toast.makeText(TestSharePreference.this, "请输入字符串", Toast.LENGTH_SHORT).show();
				}else {
					editor.putString(SAVEKEY, saveString);
					editor.commit();
					Toast.makeText(TestSharePreference.this, "保存成功", Toast.LENGTH_SHORT).show();
					txt_sharePreference.setText("value:"+saveString);
					hideSoftInput(et_sharePreference);//隐藏软键盘
//					im.hideSoftInputFromWindow(et_appstart.getWindowToken(), 0);
				}
			}
		});

		/**
		 * 保存boolean
		 */
		sp2 = getSharedPreferences("bgcolor", MODE_PRIVATE);
		editor2 = sp2.edit();
		isVisible = sp2.getBoolean(ISVISIBLE, false);
		Toast.makeText(TestSharePreference.this, "初始值："+isVisible, Toast.LENGTH_SHORT).show();
		if (isVisible) {
			iv_sharePreference.setVisibility(View.VISIBLE);
		}else {
			iv_sharePreference.setVisibility(View.INVISIBLE);
		}
		layout_sharePreference.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isVisible) {
					iv_sharePreference.setVisibility(View.INVISIBLE);
					isVisible = false;
					editor2.putBoolean(ISVISIBLE, isVisible);
					editor2.commit();
				}else {
					iv_sharePreference.setVisibility(View.VISIBLE);
					isVisible = true;
					editor2.putBoolean(ISVISIBLE, isVisible);
					editor2.commit();
				}
				boolean is = sp2.getBoolean(ISVISIBLE, false);
				Toast.makeText(TestSharePreference.this, "按钮："+is, Toast.LENGTH_SHORT).show();
			}
		});

		//checkbox
		sp3 = getSharedPreferences("checkbox", MODE_PRIVATE);
		editor3 = sp3.edit();
		isCheck = sp3.getBoolean(ISCHECK, false);
		ck_sharePreference.setChecked(isCheck);

		ck_sharePreference.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				editor3.putBoolean(ISCHECK, isChecked);
				editor3.commit();
			}
		});
	}

	/**
	 * 隐藏软键盘的方法
	 */
	private void hideSoftInput(EditText editText) {
		if (this.getCurrentFocus() != null) {
			im.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
