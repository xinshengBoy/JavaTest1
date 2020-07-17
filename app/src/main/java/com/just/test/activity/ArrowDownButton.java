package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.fenjuly.library.ArrowDownloadButton;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 按钮下载进度条
 * https://github.com/fenjuly/ArrowDownloadButton
 * compile 'com.github.fenjuly:ArrowDownloadButton:9e15b85e8a'
 * Created by admin on 2017/5/13.
 */

public class ArrowDownButton extends Activity {

    private ArrowDownloadButton btn_arrowdown;
    private int count = 0 ;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_arrowdown_button);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "按钮下载进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_arrowdown = (ArrowDownloadButton)findViewById(R.id.btn_arrowdown);
        btn_arrowdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((count % 2) == 0){
                    btn_arrowdown.startAnimating();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress += 1;
                                    btn_arrowdown.setProgress(progress);
                                }
                            });
                        }
                    },200,30);
                }else {
                    btn_arrowdown.reset();
                }
                count ++;
            }
        });
    }
}
