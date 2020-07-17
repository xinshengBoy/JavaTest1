package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.loop3D.LoopRotarySwitchView;
import com.just.test.widget.MyActionBar;

/**
 * 3D旋转切换效果
 * compile 'com.dalong:loopview:1.0.2'
 * 参考网址：https://github.com/dalong982242260/LoopRotarySwitch
 * Created by admin on 2017/6/7.
 */

public class LoopRotarySwitchTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_looprotary_switch);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "3D旋转切换效果");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        LoopRotarySwitchView view_loopRotarySwitch = (LoopRotarySwitchView)findViewById(R.id.view_loopRotarySwitch);
        view_loopRotarySwitch.setR(300)//设置两个图片之间的间隔
                .setAutoRotation(true)//自动切换
                .setAutoScrollDirection(LoopRotarySwitchView.AutoScrollDirection.right)
                .setAutoRotationTime(2000);
    }
}
