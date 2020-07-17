package com.just.test.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 第三方字体
 */
public class CustomFontActivity extends Activity {

	private TextView txt_customFont;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_custom_font);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "第三方字体");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		txt_customFont = (TextView)findViewById(R.id.txt_customFont);
		////设置字体
		Typeface type = Typeface.createFromAsset(getAssets(), "google_material_design_icon.ttf");
		txt_customFont.setTypeface(type);
	}
}
