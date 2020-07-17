package com.just.test.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/12.
 */

public class AliPayPassWordView extends EditText {

    private Paint borderPaint;//外框画笔
    private Paint linearPaint;//线条画笔
    private Paint passWordPaint;//密码画笔
    private int passWord_length;//密码输入长度
    private int mWidth;
    private int mHeight;
    private static final int PASSWORD_LENGTH = 6;//密码长度
    private static final int PASSWORD_RADIOS = 15;//密码圆角
    public AliPayPassWordView(Context context) {
        this(context, null);
    }

    public AliPayPassWordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AliPayPassWordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        setFocusable(true);//设置焦点

        borderPaint = new Paint();//外框画笔
        borderPaint.setStrokeWidth(8);
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStyle(Paint.Style.FILL);

        linearPaint = new Paint();//线条画笔
        linearPaint.setColor(Color.parseColor("#838B8B"));
        linearPaint.setStrokeWidth(4);

        passWordPaint = new Paint();//密码画笔
        passWordPaint.setColor(Color.BLACK);
        passWordPaint.setStrokeWidth(12);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        drawRoundRect(canvas);
        drawLine(canvas);
        drawPassWord(canvas);

    }

    /**
     * 绘制圆角矩形背景
     * @param canvas
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawRoundRect(Canvas canvas){
        canvas.drawRoundRect(0,0,mWidth,mHeight,12,12,borderPaint);
    }

    /**
     * 绘制分割线
     * @param canvas
     */
    private void drawLine(Canvas canvas){
        for (int i=0;i<PASSWORD_LENGTH;i++){
            float x = mWidth*i / PASSWORD_LENGTH;//每条线的起始位置
            canvas.drawLine(x,12,x,mHeight-12,linearPaint);
        }
    }

    /**
     * 绘制密码
     * @param canvas
     */
    private void drawPassWord(Canvas canvas){
        float cx,cy = mHeight/2;
        float half = mWidth / PASSWORD_LENGTH / 2;//每个密码显示的位置（中间）
        for (int i=0;i<passWord_length;i++){
            cx = mWidth*i / PASSWORD_LENGTH + half;
            canvas.drawCircle(cx,cy,PASSWORD_RADIOS,passWordPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        passWord_length = text.toString().length();

        if (passWord_length == PASSWORD_LENGTH){
            Toast.makeText(getContext(),"您输入的密码为："+text.toString(),Toast.LENGTH_LONG).show();
        }
        invalidate();//刷新
    }

    /**
     * 重置密码
     */
    public void reSetPassWord(){
        setText("");//清空密码
        invalidate();//重置画布
    }
}
