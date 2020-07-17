package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.just.test.R;

/**
 * 手动调整透明度
 *
 * @author user
 *
 */
public class TouMingDu extends Activity {

	private Button btn_tmd1, btn_tmd2, btn_tmd3, btn_change_tmd,
			btn_change_activitytmd, btn_alpha1,
			btn_alpha2,btn_animation1,btn_animation2,btn_animation3;
	private EditText et_tmd;
	private int MIN_MARK = 0;
	private int MAX_MARK = 255;
	private float WINDOW = 1.0f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_toumingdu);
		// 调整按钮的透明度
		btn_tmd1 = (Button) findViewById(R.id.btn_tmd1);
		btn_tmd2 = (Button) findViewById(R.id.btn_tmd2);
		btn_tmd3 = (Button) findViewById(R.id.btn_tmd3);
		et_tmd = (EditText) findViewById(R.id.et_tmd);
		TouMingDu.this.setRegion(et_tmd);
		btn_change_tmd = (Button) findViewById(R.id.btn_change_buttontmd);
		btn_change_activitytmd = (Button) findViewById(R.id.btn_change_activitytmd);

		// 设置按钮透明度
		btn_change_tmd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tmd = et_tmd.getText().toString();
				if (tmd.equals("")) {
					Toast.makeText(TouMingDu.this, "请输入，数据不能为空",
							Toast.LENGTH_LONG).show();
				} else {
					btn_tmd1.getBackground().setAlpha(Integer.parseInt(tmd));
					btn_tmd2.getBackground().setAlpha(Integer.parseInt(tmd));
					btn_tmd3.getBackground().setAlpha(Integer.parseInt(tmd));
				}
			}
		});
		// 设置窗体透明度,值从0.0f~1.0f,1.0f完全不透明，0.0f完全透明
		btn_change_activitytmd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = WINDOW;
				getWindow().setAttributes(lp);
				WINDOW += 0.1f;
				if (WINDOW > 1.0f) {
					WINDOW = 0.0f;
				}
			}
		});

		//透明度动画--从无到有
		btn_alpha1 = (Button) findViewById(R.id.btn_alpha1);
		Animation mAnimation1 = AnimationUtils.loadAnimation(this, R.anim.button_alpha2);
		btn_alpha1.startAnimation(mAnimation1);
		//透明度动画--从有到无
		btn_alpha2 = (Button) findViewById(R.id.btn_alpha2);
		Animation mAnimation2 = AnimationUtils.loadAnimation(this, R.anim.button_alpha);
		btn_alpha2.startAnimation(mAnimation2);
		//旋转动画
		btn_animation1 = (Button) findViewById(R.id.btn_animation1);
		Animation mAnimation3 = AnimationUtils.loadAnimation(this, R.anim.button_rotate);
		btn_animation1.startAnimation(mAnimation3);
		//缩放动画
		btn_animation2 = (Button) findViewById(R.id.btn_animation2);
		Animation mAnimation4 = AnimationUtils.loadAnimation(this, R.anim.button_scale);
		btn_animation2.startAnimation(mAnimation4);
		//平移动画
		btn_animation3 = (Button) findViewById(R.id.btn_animation3);
		Animation mAnimation5 = AnimationUtils.loadAnimation(this, R.anim.button_translate);
		btn_animation3.startAnimation(mAnimation5);
	}

	/**
	 * 限定edittext输入字符的范围
	 *
	 * @param et
	 */
	private void setRegion(final EditText et) {
		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				if (start > 1) {
					if (MIN_MARK != -1 && MAX_MARK != -1) {
						int num = Integer.parseInt(s.toString());
						if (num > MAX_MARK) {
							s = String.valueOf(MAX_MARK);
							et.setText(s);
						} else if (num < MIN_MARK) {
							s = String.valueOf(MIN_MARK);
							return;
						}
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}

		});
	}
}
