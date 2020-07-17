package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.PercentageBar;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义百分比进度条
 * Created by admin on 2017/5/12.
 */

public class PercentBarTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_percentbar);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "自定义百分比进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        PercentageBar bar_percent = (PercentageBar) findViewById(R.id.bar_percent);

        ArrayList<Float> count = new ArrayList<>();
        count.add(35.0f);
        count.add(20.0f);
        count.add(18.0f);
        count.add(15.0f);
        count.add(9.0f);
        count.add(53.0f);
        ArrayList<String> name = new ArrayList<>();
        name.add("北美洲");
        name.add("欧洲");
        name.add("非洲");
        name.add("南美洲");
        name.add("大洋洲");
        name.add("亚洲");

        bar_percent.setRespectTargetNum(count);//设置百分比
        bar_percent.setRespectName(name);//设置名称target
        bar_percent.setTotalBarNum(6);
        bar_percent.setMax(100);
        bar_percent.setBarWidth(50);
        bar_percent.setVerticalLineNum(6);
        bar_percent.setUnit("亿/人");//单位
    }
}
