package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 计时器
 * 关于暂停计时时UI会停止时间的更新，但是计时其实还在继续，
 * 要解决这个问题可参考：http://blog.csdn.net/fengyuzhengfan/article/details/38535743
 * 思路，就是用一个变量来记录已计时的时间，重新开始时用当前时间减去原来记录的时间
 * Created by user on 2016/12/28.
 */

public class ChronoterTimer extends Activity implements View.OnClickListener{

    private Chronometer chronoter_time;
    private Button btn_chronoter_start,btn_chronoter_stop,btn_chronoter_reset;
    private long recordingTime = 0;//记录时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chronoter);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "计时器");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        chronoter_time = (Chronometer)findViewById(R.id.chronoter_time);
        btn_chronoter_start = (Button)findViewById(R.id.btn_chronoter_start);
        btn_chronoter_stop = (Button)findViewById(R.id.btn_chronoter_stop);
        btn_chronoter_reset = (Button)findViewById(R.id.btn_chronoter_reset);

        btn_chronoter_start.setOnClickListener(this);
        btn_chronoter_stop.setOnClickListener(this);
        btn_chronoter_reset.setOnClickListener(this);
        chronoter_time.setFormat("计时：(%s)");
    }

    @Override
    public void onClick(View view) {
        if (view == btn_chronoter_start){
            chronoter_time.setBase(SystemClock.elapsedRealtime() - recordingTime);
            chronoter_time.start();
        }else if (view == btn_chronoter_stop){
            chronoter_time.stop();
            recordingTime = SystemClock.elapsedRealtime() - chronoter_time.getBase();
        }else if (view == btn_chronoter_reset){
            recordingTime = 0;
            chronoter_time.setBase(SystemClock.elapsedRealtime());
        }
    }
}
