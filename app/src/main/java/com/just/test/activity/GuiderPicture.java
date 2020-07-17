package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.jaydenxiao.guider.HighLightGuideView;

/**
 * 第一种：透明引导图
 * 引用：compile 'com.jaydenxiao:guider:1.0.0'
 * http://www.tuicool.com/articles/jQvaEz2
 * Created by admin on 2017/4/1.
 */

public class GuiderPicture extends Activity implements View.OnClickListener{

    private Button btn_guider_fullscreen,btn_guider_single,btn_guider_many;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guiderpicture);

        initView();
    }

    private void initView(){
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "透明引导图");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_guider_fullscreen = (Button)findViewById(R.id.btn_guider_fullscreen);
        btn_guider_single = (Button)findViewById(R.id.btn_guider_single);
        btn_guider_many = (Button)findViewById(R.id.btn_guider_many);

        btn_guider_fullscreen.setOnClickListener(this);
        btn_guider_single.setOnClickListener(this);
        btn_guider_many.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //// TODO: 2017/4/1 样式： MASKBLURSTYLE_SOLID：矩形 VIEWSTYLE_CIRCLE：大圆形  MASKBLURSTYLE_NORMAL:大圆形
        //// TODO: 2017/4/1 VIEWSTYLE_OVAL:椭圆形 VIEWSTYLE_RECT:矩形
        if (view == btn_guider_fullscreen){//全屏显示，不限定与哪个控件
            HighLightGuideView.builder(this).addNoHighLightGuidView(R.drawable.mainmenu)
                    .setMaskColor(Color.GREEN).show();
        }else if (view == btn_guider_single){//显示在某个控件上
            HighLightGuideView.builder(this).addHighLightGuidView(btn_guider_single,R.drawable.mainmenu)
                    .setTouchOutsideDismiss(true)//外部点击是否关闭setTouchOutsideDismiss
                    .setMaskblurstyle(HighLightGuideView.MASKBLURSTYLE_NORMAL)//高亮画笔类型，有平滑和一般，默认平滑setMaskblurstyle
                    .setMaskColor(Color.RED)//蒙层颜色setMaskColor
                    .setHighLisghtPadding(-10)
                    .setHighLightStyle(HighLightGuideView.MASKBLURSTYLE_SOLID).show();
        }else if (view == btn_guider_many){//显示在多个控件上
            HighLightGuideView.builder(this).addHighLightGuidView(btn_guider_single,R.drawable.mainmenu)
                    .addHighLightGuidView(btn_guider_many,R.drawable.mainmenu)
                    .setHighLightStyle(HighLightGuideView.VIEWSTYLE_RECT).show();
        }
    }
}
