package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.FallView;
import com.just.test.widget.MyActionBar;

/**
 * 飘落的字体
 * Created by admin on 2017/6/5.
 */

public class FallViewTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fallview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 飘落的字体");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        FallView view_fallview = (FallView) findViewById(R.id.view_fallview);
        view_fallview.setPollutant("这妹子好靓");

    }
}
