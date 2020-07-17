package com.just.test.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class DrawImg extends View {

	public DrawImg(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DrawImg(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DrawImg(Context context) {
		super(context);
	}

	// 绘制文本
	private void drawTxtView(Canvas canvas,String drawTxt) {
		Paint txtPaint = new Paint();
		txtPaint.setColor(Color.BLUE);
		// 设置文本位置
		txtPaint.setTextAlign(Align.LEFT);
		// 设置文本大小
		txtPaint.setTextSize(38);
		// 设置画笔的锯齿效果 true表示无锯齿 false 反之
		txtPaint.setAntiAlias(true);
		txtPaint.setTypeface(Typeface.MONOSPACE);
		// 当然也可以设置为"实心"(Paint.Style.FILL), "空心"(Paint.Style.STROKE)
//		txtPaint.setStyle(Paint.Style.STROKE);
		// 设置“空心”的外框的宽度
//		txtPaint.setStrokeWidth(5);
		// x默认是drawTxt这个字符的左边在屏幕的位置，如果设置了setTextAlign(Paint.Align.CENTER);那就是字符的中心，y是指定这个字符baseline在屏幕上的位置。
		canvas.drawText(drawTxt, 16, 80, txtPaint);
	}


	private void drawCircleView(Canvas canvas,float cx,float xy,boolean isSx,int color) {
		Paint circlePaint = new Paint();
		circlePaint.setColor(color);
		if (isSx) {
			circlePaint.setStyle(Paint.Style.FILL);
		}else {
			circlePaint.setStyle(Paint.Style.STROKE);
		}
//		circlePaint.setStrokeCap(Cap.ROUND);
		/**
		 * 画点 ： 参数介绍
		 * 1.起始端点的X坐标。
		 * 2.起始端点的Y坐标。
		 * 3.点直径。
		 * 5.绘制直线所使用的画笔。
		 */
		canvas.drawCircle(cx, xy, 10, circlePaint);
	}

	// 绘制线段
	private void drawSlash(Canvas canvas,float startX,float startY,float stopX,float stopY) {
		Paint paint = new Paint();
		paint.setColor(Color.GRAY);
		// 设置线宽
		paint.setStrokeWidth(5f);
		/**
		 * 画线 ： 参数介绍
		 * 1.起始端点的X坐标。
		 * 2.起始端点的Y坐标。
		 * 3.终止端点的X坐标。
		 * 4.终止端点的Y坐标。
		 * 5.绘制直线所使用的画笔。
		 */
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}

	private void drawText(Canvas canvas,String str,float x,float y) {
		Paint paint = new Paint();
		paint.setColor(Color.DKGRAY);
		paint.setTextSize(28);
		/**
		 * 画文本 ： 参数介绍
		 * 1.文本X坐标假指长度
		 * 2.文本Y坐标假指高度
		 * 3.绘制直线所使用的画笔。
		 */
		canvas.drawText(str, x, y, paint);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawTxtView(canvas, "黄兴路步行街商圈");
		drawCircleView(canvas,100,210, true,Color.CYAN);
		drawSlash(canvas,107,215,230,260);
		drawCircleView(canvas,230,260, true,Color.BLUE);
		drawSlash(canvas,238,260,420,170);
		drawCircleView(canvas,420,170, true,Color.GREEN);
		drawSlash(canvas,427,170,610,210);
		drawCircleView(canvas,610,210, true,Color.RED);
		drawSlash(canvas,50,290,680,290);
		drawText(canvas, "10km/h", 50, 180);
		drawText(canvas, "人民路", 50, 330);
		drawText(canvas, "5km/h", 200, 220);
		drawText(canvas, "蔡锷路", 180, 330);
		drawText(canvas, "40km/h", 370, 140);
		drawText(canvas, "沿江路", 370, 330);
		drawText(canvas, "35km/h", 570, 170);
		drawText(canvas, "五一路", 570, 330);
	}


}
