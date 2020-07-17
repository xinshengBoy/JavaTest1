package com.just.test.tools;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 倒计时工具
 * Created by user on 2016/12/20.
 */

public class TimeCount extends CountDownTimer {

    private TextView text;
    private String message;
    public TimeCount(long millisInFuture, long countDownInterval,TextView textView,String messages) {
        // 总时长，间隔时间
        super(millisInFuture, countDownInterval);
        text = textView;
        message = messages;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // 计时过程中所触发
//        text.setClickable(false);//不可点击
        text.setText(message+"("+millisUntilFinished / 1000 + "秒)");
    }

    @Override
    public void onFinish() {
        //计时完毕时触发
        text.setText(message);
//        text.setClickable(true);//可点击
    }
}
