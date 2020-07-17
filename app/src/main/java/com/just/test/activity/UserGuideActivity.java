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
 * 第二种：//UserGuideView 也是一个透明的引导图
 compile 'com.zhl.userguideview:Userguidelibrary:1.0.1'
 https://github.com/yilylong/UserGuideView/blob/master/app/src/main/java/com/zhl/userguideview/UserGuideTestActivity.java
 * Created by admin on 2017/4/1.
 */

public class UserGuideActivity extends Activity implements View.OnClickListener{

    private Button btn_userguide1,btn_userguide2,btn_userguide3;
    private UserGuideView view_userguide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_userguide);
        initView();
    }

    private void initView(){
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "UserGuideView");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_userguide1 = (Button)findViewById(R.id.btn_userguide1);
        btn_userguide2 = (Button)findViewById(R.id.btn_userguide2);
        btn_userguide3 = (Button)findViewById(R.id.btn_userguide3);
        view_userguide = (UserGuideView)findViewById(R.id.view_userguide);

        btn_userguide1.setOnClickListener(this);
        btn_userguide2.setOnClickListener(this);
        btn_userguide3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_userguide1){
            view_userguide.setHighLightView(btn_userguide1);
        }else if (view == btn_userguide2){
            view_userguide.setHighLightView(btn_userguide2);
        }else if (view == btn_userguide3){
            view_userguide.setHighLightView(btn_userguide3);
        }
    }
}
