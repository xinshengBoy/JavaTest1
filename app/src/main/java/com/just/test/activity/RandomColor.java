package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.util.Random;

/**
 * 随机背景
 * Created by admin on 2017/6/5.
 */

public class RandomColor extends Activity {

    private RelativeLayout randomColr_layout;
    private TextView txt_randomColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_random_color);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 随机背景");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        randomColr_layout = (RelativeLayout)findViewById(R.id.randomColr_layout);
        txt_randomColor = (TextView)findViewById(R.id.txt_randomColor);
        handler.postDelayed(runnable,2000);
    }

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this,2000);
            Random random = new Random();
            int result = random.nextInt(100);
            txt_randomColor.setText(""+result);
            if (result < 10){
                randomColr_layout.setBackgroundColor(0xff29B6F6);
            }else if (result > 10 && result < 20){
                randomColr_layout.setBackgroundColor(0xff4CAF50);
            }else if (result > 20 && result < 30){
                randomColr_layout.setBackgroundColor(0xffCDDC39);
            }else if (result > 30 && result < 40){
                randomColr_layout.setBackgroundColor(0xffFFC107);
            }else if (result > 40 && result < 50){
                randomColr_layout.setBackgroundColor(0xffFF5722);
            }else if (result > 50 && result < 60){
                randomColr_layout.setBackgroundColor(0xff795548);
            }else if (result > 60 && result < 70){
                randomColr_layout.setBackgroundColor(0xffFF0000);
            }else if (result > 70 && result < 80){
                randomColr_layout.setBackgroundColor(0xffDC143C);
            }else if (result > 80 && result < 90){
                randomColr_layout.setBackgroundColor(0xffC71585);
            }else if (result > 90 && result <= 100){
                randomColr_layout.setBackgroundColor(0xff8B0000);
            }
        }
    };
}
