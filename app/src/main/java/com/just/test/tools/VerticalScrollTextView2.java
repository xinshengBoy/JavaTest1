package com.just.test.tools;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * @author xushilin
 *自下而上滚动
 */
public class VerticalScrollTextView2 extends TextView {

	private float step = 0f;
	private Paint mPaint = new Paint();;
	private String text;
	private float width;
	private List<String> textList = new ArrayList<String>(); // 分行保存textview的显示信息。

	public VerticalScrollTextView2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VerticalScrollTextView2(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//获得屏幕宽度
		width = MeasureSpec.getSize(widthMeasureSpec);
		text = getText().toString();
		if (text == null | text.length() == 0) {
			return;
		}

		// 下面的代码是根据宽度和字体大小，来计算textview显示的行数。
		textList.clear();
		StringBuilder builder = null;
		for (int i = 0; i < text.length(); i++) {
			if (i % 12 == 0) {
				builder = new StringBuilder();
			}
			if (i % 12 <= 11) {
				builder.append(text.charAt(i));
			}
			if (i % 12 == 11) {
				textList.add(builder.toString());
			}

		}
		Log.e("textviewscroll", "" + textList.size());
	}

	// 下面代码是利用上面计算的显示行数，将文字画在画布上，实时更新。
	@Override
	public void onDraw(Canvas canvas) {
		if (textList.size() == 0) return;

		mPaint.setTextSize(80f);// 设置字体大小
		for (int i = 0; i < textList.size(); i++) {
			canvas.drawText(textList.get(i), 100, this.getHeight() + (i + 1)
					* mPaint.getTextSize() - step + 30, mPaint);
		}
		invalidate();

		step = step + 0.3f;
		if (step >= this.getHeight() + textList.size() * mPaint.getTextSize()) {
			step = 0;
		}
	}

}
