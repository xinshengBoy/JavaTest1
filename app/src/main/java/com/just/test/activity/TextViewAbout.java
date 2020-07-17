package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**文字相关
 * Created by Administrator on 2017/3/1.
 */

public class TextViewAbout extends Activity implements CompoundButton.OnCheckedChangeListener{

    private ToggleButton tb_toggle1;
    private Switch switch_test;
    private LinearLayout linearlayout_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about_textview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "文字相关");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        tb_toggle1 = (ToggleButton)findViewById(R.id.tb_toggle1);
        switch_test = (Switch)findViewById(R.id.switch_test);
        linearlayout_open = (LinearLayout)findViewById(R.id.linearlayout_open);

        tb_toggle1.setOnCheckedChangeListener(this);
        switch_test.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isCheched) {
        if (isCheched){
            linearlayout_open.setOrientation(LinearLayout.VERTICAL);
        }else {
            linearlayout_open.setOrientation(LinearLayout.HORIZONTAL);
        }
    }
}
