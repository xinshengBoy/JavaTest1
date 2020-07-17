package com.just.test.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 画线
 * Created by admin on 2017/5/18.
 */

public class CustomLines extends View {

    public CustomLines(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xffCF4FBF);

        Paint paint = new Paint();
        paint.setColor(0xff3D3119);

        canvas.drawLine(10,10,500,10,paint);

        Paint paint1 = new Paint();
        paint1.setColor(0xffFF8C19);
        Path path = new Path();
        path.moveTo(50,300);
        path.quadTo(150,310,90,500);
        canvas.drawPath(path,paint1);
    }
}
