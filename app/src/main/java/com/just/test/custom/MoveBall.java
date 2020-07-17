package com.just.test.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/2/28.
 */

public class MoveBall extends View {

    public float currentX = 40;
    public float currentY = 50;
    Paint p = new Paint();

    public MoveBall(Context context) {
        super(context);
    }

    public MoveBall(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setColor(Color.RED);//设置画板颜色
        canvas.drawCircle(currentX,currentY,20,p);//画一个圆，起始点和半径
    }

    /**
     * 移动事件监听
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();//获取移动到的x点
        currentY = event.getY();//获取移动到的y点
        invalidate();//更新画布
        return true;//返回true表示已经对该事件进行了处理了
    }
}
