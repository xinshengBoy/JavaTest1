package com.just.test.activity;

import com.just.test.R;
import com.just.test.tools.KeyboardUtil;
import com.just.test.tools.RoundImage;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
/**
 * 自定义键盘
 * @author user
 *
 */
public class CustomKeyBroad extends Activity {
	private Context ctx;
	private Activity act;
	private EditText edit;
	private EditText edit1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_suofang);
		ctx = this;
		act = this;

		edit = (EditText) this.findViewById(R.id.edit_suofang);
		edit.setInputType(InputType.TYPE_NULL);

		edit1 = (EditText) this.findViewById(R.id.edit1_suofang);

		edit.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				new KeyboardUtil(act, ctx, edit).showKeyboard();
				return false;
			}
		});

		edit1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int inputback = edit1.getInputType();
				edit1.setInputType(InputType.TYPE_NULL);
				new KeyboardUtil(act, ctx, edit1).showKeyboard();
				edit1.setInputType(inputback);
				return false;
			}
		});
		ImageView iv_roundImage = (ImageView)findViewById(R.id.iv_roundImage);
		iv_roundImage.setBackgroundDrawable(new BitmapDrawable(RoundImage.toRoundBitmap(this, "tup.jpg")));
		iv_roundImage.getBackground().setAlpha(0);
		iv_roundImage.setImageBitmap(RoundImage.toRoundBitmap(this, "tup.jpg"));
	}
}
