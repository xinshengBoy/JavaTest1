package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.animotion.HeartLayout;
import com.just.test.widget.MyActionBar;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义心形泡泡
 * Created by admin on 2017/5/13.
 */

public class HeartLayoutTest extends Activity {

    private Timer timer = new Timer();
    private Random random = new Random();
    private HeartLayout view_heartlayout1,view_heartlayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_heartlayout);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "心形泡泡");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        view_heartlayout1 = (HeartLayout) findViewById(R.id.view_heartlayout1);
        view_heartlayout2 = (HeartLayout) findViewById(R.id.view_heartlayout2);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                view_heartlayout1.post(new Runnable() {
                    @Override
                    public void run() {
                        view_heartlayout1.addHeart(randomColor());
                    }
                });
                view_heartlayout2.post(new Runnable() {
                    @Override
                    public void run() {
                        view_heartlayout2.addHeart(randomColor());
                    }
                });
            }
        },500,200);

    }

    private int randomColor(){
        return Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
