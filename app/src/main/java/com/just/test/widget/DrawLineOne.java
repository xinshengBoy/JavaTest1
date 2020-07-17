package com.just.test.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class DrawLineOne extends View{

	private Bitmap bitmap;
	private Canvas canvas;
	private int mov_x;//声明起点坐标
	private int mov_y;
	private Paint paint;

	public DrawLineOne(Context context) {
		super(context);
		//获取屏幕大小
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();

		paint = new Paint(Paint.DITHER_FLAG);
		bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
		canvas = new Canvas();
		canvas.setBitmap(bitmap);

		paint.setStyle(Style.STROKE);//设置非填充
		paint.setStrokeWidth(4);//设置画笔线粗细
		paint.setColor(Color.GREEN);//设置画笔颜色
		paint.setAntiAlias(true);//不显示锯齿
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(bitmap, 0, 0,null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE) {//拖动
			canvas.drawLine(mov_x, mov_y, event.getX(), event.getY(), paint);
			invalidate();//实时更新画面
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击或按下
			mov_x = (int) event.getX();
			mov_y = (int) event.getY();
			canvas.drawPoint(mov_x, mov_y, paint);//画点
			invalidate();
		}
		//保存最新的点位
		mov_x = (int) event.getX();
		mov_y = (int) event.getY();
		return true;
	}

}
