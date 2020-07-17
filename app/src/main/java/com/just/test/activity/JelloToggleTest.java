package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 文字的拉扯效果
 * 参考网址：http://www.itlanbao.com/code/20150812/10033/100293.html
 * Created by admin on 2017/6/12.
 */

public class JelloToggleTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jellotoggle);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "文字的拉扯效果");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);
    }
}
