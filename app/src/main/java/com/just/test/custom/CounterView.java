package com.just.test.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * 计数
 * @author user
 *
 */
public class CounterView extends View implements OnClickListener{

	private Paint mPaint;//画笔
	private Rect mRect;//矩形
	private int mCount;//计数

	public CounterView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mRect = new Rect();
		setOnClickListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		//设置画笔颜色
		mPaint.setColor(Color.BLUE);
		//画一个矩形(定制宽高)
		canvas.drawRect(0, 0,getWidth(),getHeight(),mPaint);

		mPaint.setColor(Color.YELLOW);
		mPaint.setTextSize(100);
		String text = String.valueOf(mCount);
		//计算文字的宽高
		mPaint.getTextBounds(text, 0, text.length(), mRect);
		float textWidth = mRect.width();
		float textHeight = mRect.height();

		//绘制文字
		canvas.drawText(text, getWidth()/2 - textWidth/2, getHeight()/2 + textHeight/2, mPaint);
	}

	@Override
	public void onClick(View v) {
		mCount++;
		//重绘
		invalidate();
	}

}
