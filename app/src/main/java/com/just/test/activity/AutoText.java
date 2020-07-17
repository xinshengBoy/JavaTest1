package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 最简单的自动化提示控件测试
 * @author user
 *
 */
public class AutoText extends Activity {

	private String [] autoString = {"张卫平","张三丰","张卫健","张飞","张子萱","张伟","张思鹏","张密林"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.autotext_layout);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "自动提示");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		AutoCompleteTextView edit_autoComplete = (AutoCompleteTextView) findViewById(R.id.edit_autoComplete);
		edit_autoComplete.setAdapter(new ArrayAdapter<>(AutoText.this, android.R.layout.simple_dropdown_item_1line,autoString));
	}
}
