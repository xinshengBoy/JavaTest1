package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.print.PageRange;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.skyfishjy.library.RippleBackground;

/**
 * 波浪效果
 * https://github.com/skyfishjy/android-ripple-background
 * compile 'com.skyfishjy.ripplebackground:library:1.0.1'
 * Created by admin on 2017/5/13.
 */

public class RippleTest extends Activity {

    private RippleBackground bg_ripple;
    private boolean isStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ripple);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "波浪效果");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        bg_ripple = (RippleBackground)findViewById(R.id.bg_ripple);
        ImageView iv_ripple = (ImageView) findViewById(R.id.iv_ripple);
        iv_ripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStart){
                    bg_ripple.stopRippleAnimation();
                    isStart = false;
                }else {
                    bg_ripple.startRippleAnimation();
                    isStart = true;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bg_ripple.stopRippleAnimation();
    }
}
