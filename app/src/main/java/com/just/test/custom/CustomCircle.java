package com.just.test.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomCircle extends View{

	private Context mContext;
	private Paint paint;
	private int radius = 80;
	private int width = 90;

	public CustomCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.paint = new Paint();
		this.paint.setAntiAlias(true);//消除锯齿
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setColor(Color.RED);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 1; i <= 5; i++) {
			this.paint.setColor(Color.BLUE);
			this.paint.setStrokeWidth(10);//设置内圆的厚度
			canvas.drawCircle((width+5)*2*i, width, radius, paint);
			canvas.drawCircle((width+5)*2*i, width*3, radius, paint);
			canvas.drawCircle((width+5)*2*i, width*5, radius, paint);
			canvas.drawCircle(width*7, (width)*2*i, radius, paint);
		}
		super.onDraw(canvas);
	}
}
