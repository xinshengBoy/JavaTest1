package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.github.glomadrian.codeinputlib.CodeInput;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 仿锁屏密码输入样式
 * https://github.com/glomadrian/material-code-input
 *
 * url "http://dl.bintray.com/glomadrian/maven"
 * compile 'com.github.glomadrian:CodeInput:1.1@aar'
 * Created by admin on 2017/6/14.
 */

public class MaterialCode extends Activity {

    private CodeInput codeInput_default,codeInput_custom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_material_code);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "仿锁屏密码输入样式");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        codeInput_default = (CodeInput)findViewById(R.id.codeInput_default);
        codeInput_custom = (CodeInput)findViewById(R.id.codeInput_custom);

        mHandler.sendEmptyMessage(0);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                String code = codeInput_default.getCode().toString();
                if (code != null && code.length() > 0){
                    LemonBubble.showRight(MaterialCode.this,"输入的是："+code,3000);
                }else {
                    mHandler.sendEmptyMessage(0);
                }
            }
        }
    };
}
