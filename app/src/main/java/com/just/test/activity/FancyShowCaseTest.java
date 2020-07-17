package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.zhl.userguideview.UserGuideView;

/**
 * 功能引导页
 * http://www.see-source.com/androidwidget/detail.html?wid=927
 * compile 'com.zhl.userguideview:Userguidelibrary:1.0.1'
 * Created by admin on 2017/5/10.
 */

public class FancyShowCaseTest extends Activity implements View.OnClickListener{

    private Button txt_fancyshow1,txt_fancyshow2,txt_fancyshow3,txt_fancyshow4;
    private UserGuideView view_userguide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fancyshow);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "功能引导页");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView() {
        txt_fancyshow1 = (Button)findViewById(R.id.btn_fancyshow1);
        txt_fancyshow2 = (Button)findViewById(R.id.btn_fancyshow2);
        txt_fancyshow3 = (Button)findViewById(R.id.btn_fancyshow3);
        txt_fancyshow4 = (Button)findViewById(R.id.btn_fancyshow4);
        view_userguide = (UserGuideView)findViewById(R.id.view_userguide);

        txt_fancyshow1.setOnClickListener(this);
        txt_fancyshow2.setOnClickListener(this);
        txt_fancyshow3.setOnClickListener(this);
        txt_fancyshow4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == txt_fancyshow1){
            view_userguide.setHighLightView(txt_fancyshow1);
        }else if (view == txt_fancyshow2){
            view_userguide.setHighLightView(txt_fancyshow2);
        }else if (view == txt_fancyshow3){
            view_userguide.setHighLightView(txt_fancyshow3);
        }else if (view == txt_fancyshow4){
            view_userguide.setHighLightView(txt_fancyshow4);
        }
    }
}
