package com.just.test.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.DashboardView1;
import com.just.test.custom.DashboardView2;
import com.just.test.custom.DashboardView3;
import com.just.test.custom.DashboardView4;
import com.just.test.widget.MyActionBar;

import java.util.Random;

/**
 * 自定义仪表盘
 * 参考网址：https://github.com/woxingxiao/DashboardView
 * Created by admin on 2017/6/8.
 */

public class DashboardTest extends Activity implements View.OnClickListener{

    private DashboardView1 view_dashboard1;
    private DashboardView2 view_dashboard2;
    private DashboardView3 view_dashboard3;
    private DashboardView4 view_dashboard4;
    private boolean isAnimFinished = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "自定义仪表盘");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView() {
        view_dashboard1 = (DashboardView1) findViewById(R.id.view_dashboard1);
        view_dashboard2 = (DashboardView2) findViewById(R.id.view_dashboard2);
        view_dashboard3 = (DashboardView3) findViewById(R.id.view_dashboard3);
        view_dashboard4 = (DashboardView4) findViewById(R.id.view_dashboard4);

        view_dashboard1.setOnClickListener(this);
        view_dashboard2.setOnClickListener(this);
        view_dashboard3.setOnClickListener(this);
        view_dashboard4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == view_dashboard1){
            view_dashboard1.setRealTimeValue(new Random().nextInt(100));
        }else if (view == view_dashboard2){
            view_dashboard2.setCreditValueWithAnim(new Random().nextInt(950-350)+350);
        }else if (view == view_dashboard3){
            view_dashboard3.setCreditValue(new Random().nextInt(950-350)+350);
        }else if (view == view_dashboard4){
            if (isAnimFinished) {
                ObjectAnimator animator = ObjectAnimator.ofInt(view_dashboard4, "mRealTimeValue",
                        view_dashboard4.getVelocity(), new Random().nextInt(180));
                animator.setDuration(1500).setInterpolator(new LinearInterpolator());
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        isAnimFinished = false;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAnimFinished = true;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        isAnimFinished = true;
                    }
                });
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        view_dashboard4.setVelocity(value);
                    }
                });
                animator.start();
            }
        }
    }
}
