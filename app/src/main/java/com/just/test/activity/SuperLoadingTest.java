package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.SuperLoadingProgress;
import com.just.test.widget.MyActionBar;

/**
 * 一个炫酷的加载进度条
 * 参考网址：https://github.com/835127729/SuperLoadingProgress
 * Created by admin on 2017/6/7.
 */

public class SuperLoadingTest extends Activity implements View.OnClickListener{

    private Button btn_superLoading_success,btn_superLoading_error;
    private SuperLoadingProgress pb_superloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_superloading);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "炫酷的加载进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_superLoading_success = (Button)findViewById(R.id.btn_superLoading_success);
        btn_superLoading_error = (Button)findViewById(R.id.btn_superLoading_error);
        pb_superloading = (SuperLoadingProgress)findViewById(R.id.pb_superloading);

        btn_superLoading_success.setOnClickListener(this);
        btn_superLoading_error.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_superLoading_success){
            pb_superloading.setVisibility(View.VISIBLE);
            new Thread(){
                @Override
                public void run() {
                    try {
                        pb_superloading.setProgress(0);
                        while (pb_superloading.getProgress() < 100){
                            Thread.sleep(10);
                            pb_superloading.setProgress(pb_superloading.getProgress() + 1);
                        }
                        pb_superloading.finishSuccess();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }else if (view == btn_superLoading_error){
            pb_superloading.setVisibility(View.VISIBLE);
            new Thread(){
                @Override
                public void run() {
                    try {
                        pb_superloading.setProgress(0);
                        while (pb_superloading.getProgress() < 100){
                            Thread.sleep(10);
                            pb_superloading.setProgress(pb_superloading.getProgress() + 1);
                        }
                        pb_superloading.finishFail();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
    }
}
