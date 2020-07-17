package com.just.test.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;

/**
 * 自定义图片按钮
 */
public class ImageBtn extends LinearLayout {

	private ImageView imageView1;
	private TextView textView1;

	public ImageBtn(Context context) {
		super(context);
	}

	public ImageBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_custombtn, this);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		textView1 = (TextView) findViewById(R.id.textView1);
	}

	public void setImageResource(int resId) {
		imageView1.setImageResource(resId);
	}

	public void setTextView(String text) {
		textView1.setText(text);
	}
}
