package com.just.test.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义圆形
 * Created by admin on 2017/5/18.
 */

public class CustomRound extends View{

    private int width,height,countX,countY;
    private float margin;
    private float itemX,itemY;
    private float radius;

    public CustomRound(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomRound(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xff339AFF);

        Paint paint = new Paint();
        paint.setColor(0xffFFE597);

        itemX = width / countY;
        itemY = height / countY;

        if (itemX >= itemY){
            margin = itemX / 4;
            radius = itemX / 2;
        }else {
            margin = itemY / 4;
            radius = itemY / 2;
        }

        if (countX > 0 && countY >0){
            for (int i=0;i<countX;i++){
                for (int j=0;j<countY;j++){
                    canvas.drawCircle(radius+itemX*i,radius+itemY*j,radius,paint);
//                    canvas.drawCircle(100,100,60,paint);
                }
            }
        }
    }

    public void setCount(int width,int height,int countX,int countY){
        this.width = width;
        this.height = height;
        this.countX = countX;
        this.countY = countY;
    }
}
