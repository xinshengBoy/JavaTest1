package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * Android 5.0新特性  滑动条百分比
 * https://github.com/AnderWeb/discreteSeekBar
 * compile 'org.adw.library:discrete-seekbar:1.0.1'
 * Created by admin on 2017/5/2.
 */

public class SimpleSeekBar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_simple_seekbar);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "滑动条百分比");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);
    }
}
