package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * FlycoRoundView 按钮背景实现效果
 * https://github.com/H07000223/FlycoRoundView
 * compile 'com.flyco.roundview:FlycoRoundView_Lib:1.1.4@aar'
 * Created by admin on 2017/6/8.
 */

public class FlycoRoundViewTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flyco_roundview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "按钮背景实现效果");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);
    }

    public void roundOnClick(View view){
        switch (view.getId()){
            case R.id.txt_roundTextView1:

                break;
            case R.id.txt_roundTextView2:

                break;
            case R.id.txt_roundTextView3:

                break;
        }
    }
}
