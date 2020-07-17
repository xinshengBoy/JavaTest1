package com.just.test.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * QQ登录欢迎界面播放视频
 * Created by admin on 2017/6/13.
 */

public class MyQQVideoView  extends VideoView {
    public MyQQVideoView(Context context) {
        super(context);
    }

    public MyQQVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyQQVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        } else{

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
