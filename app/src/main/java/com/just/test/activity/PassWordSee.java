package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 密码可见或隐藏
 */
public class PassWordSee extends Activity {

	private EditText et_password;
	private ImageView iv_seeOrHide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_see_layout);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "密码可见或隐藏");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		et_password = (EditText) findViewById(R.id.et_password);
		iv_seeOrHide = (ImageView) findViewById(R.id.iv_seeOrHide);

		iv_seeOrHide.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
					et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					iv_seeOrHide.setImageResource(R.drawable.icon_pw01);
				}else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
					et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					iv_seeOrHide.setImageResource(R.drawable.icon_pw02);
				}
				Editable etext = et_password.getText();
				Selection.setSelection(etext, etext.length());//使光标一直在最后面
				return true;
			}
		});
	}
}
