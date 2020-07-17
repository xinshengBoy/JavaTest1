package com.just.test.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 画矩形
 * Created by admin on 2017/5/18.
 */

public class CustomRect extends View {

    private final Context context;
    private float itemW, itemH;
    private int numberW, numberH;//矩形个数
    private int width,height;//控件的宽高
    private float margin = 50;
    private Paint mPaint;

    public CustomRect(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomRect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public CustomRect(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xEFFF7735);

        itemW = width / numberW;
        itemH = height / numberH;
        if (itemW >= itemH){
            margin = itemW / 5;
        }else {
            margin = itemH / 5;
        }

        mPaint = new Paint();
        mPaint.setColor(0xff12C343);//设置字体颜色
        if (numberW > 0 && numberH > 0) {
            for (int i = 0; i < numberW; i++) {
                for (int j=0;j<numberH;j++) {
//                    canvas.drawRect(margin + itemW * i, margin, itemW * (1 + i), itemH, mPaint);
                    canvas.drawRect(margin + itemW * i, margin+itemH*j, itemW * (1 + i), itemH*(j+1), mPaint);
                }
            }
        }

    }

    public void setCount(int countW,int countH,int width,int height){
        this.numberW = countW;
        this.numberH = countH;
        this.width = width;
        this.height = height;
    }
}
