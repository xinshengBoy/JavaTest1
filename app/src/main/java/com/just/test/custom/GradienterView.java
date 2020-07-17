package com.just.test.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.just.test.R;

/**
 * 水平仪
 * Created by admin on 2017/4/27.
 */

public class GradienterView extends View {

    public Bitmap bg_view;
    public Bitmap bubble;
    public int bubbleX,bubblleY;
    public GradienterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bg_view = BitmapFactory.decodeResource(getResources(), R.drawable.bg_sensor);
        bubble = BitmapFactory.decodeResource(getResources(),R.drawable.bubble);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bg_view,0,0,null);
        canvas.drawBitmap(bubble,bubbleX,bubblleY,null);
    }
}
