package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.zys.brokenview.BrokenCallback;
import com.zys.brokenview.BrokenTouchListener;
import com.zys.brokenview.BrokenView;

/**
 * 玻璃破裂效果
 * http://www.jianshu.com/p/8dc91efb2e3b
 * compile 'com.zys:brokenview:1.0.3'
 * https://github.com/zhanyongsheng/BrokenView
 * Created by admin on 2017/6/20.
 */

public class BrokenViewTest extends Activity {

    private BrokenTouchListener listener;
    private BrokenView brokenView;
    private ImageView iv_brokenview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_brokenview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "玻璃破裂效果");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        iv_brokenview = (ImageView) findViewById(R.id.iv_brokenview);
        brokenView = BrokenView.add2Window(BrokenViewTest.this);
        listener = new BrokenTouchListener.Builder(brokenView)
                .setComplexity(68)//破裂的复杂度，默认为12
                .setBreakDuration(1000)//破裂的持续时间,默认700ms
                .setFallDuration(1000)//破裂的坠落时间，默认700ms
                .setCircleRiftsRadius(200)//设置以触摸点为中心的圆形裂痕半径，默认为66dp
                .build();
        iv_brokenview.setOnTouchListener(listener);
//        init();

        brokenView.setCallback(new BrokenCallback() {
            @Override
            public void onStart(View v) {
                super.onStart(v);
            }

            @Override
            public void onCancel(View v) {
                super.onCancel(v);
            }

            @Override
            public void onRestart(View v) {
                super.onRestart(v);
            }

            @Override
            public void onFalling(View v) {
                super.onFalling(v);
            }

            @Override
            public void onFallingEnd(View v) {
                super.onFallingEnd(v);
                //// TODO: 2017/6/20 重置无效果
                brokenView.reset();
                iv_brokenview.setImageResource(R.drawable.bg01);
                iv_brokenview.setOnTouchListener(listener);
            }

            @Override
            public void onCancelEnd(View v) {
                super.onCancelEnd(v);
            }
        });
    }

}
