package com.just.test.activity;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.tools.Love;
import com.just.test.widget.LoveLayout;
import com.just.test.widget.MyActionBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义心型控件
 */
public class LoveLayoutActivity extends Activity {

    private LoveLayout love;
    private TimerTask task;
    private LinearLayout linear;
    private Love loves;
    private boolean isSatrt = true;//是否已经启动 true ：没有启动
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lovelayout);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "自定义心型控件");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        love = (LoveLayout) findViewById(R.id.love);
        task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        linear = (LinearLayout) findViewById(R.id.linear_dianzan);
        loves = new Love(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, 400);
//        linear.setLayoutParams(params);
        linear.addView(loves);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                love.addLove();
            }
        }
    };

    public void show(View v) {
        if (isSatrt) {
            isSatrt = false;
            timer = new Timer();
            timer.schedule(task, 0, 500);
        }
    }

    private Timer timer;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

}
