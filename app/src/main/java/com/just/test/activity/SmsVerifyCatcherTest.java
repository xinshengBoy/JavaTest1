package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自动读取手机短信验证码
 * https://github.com/stfalcon-studio/SmsVerifyCatcher
 * compile 'com.github.stfalcon:smsverifycatcher:0.3.1'
 * https://www.ctolib.com/SmsVerifyCatcher.html
 * Created by admin on 2017/7/11.
 */

public class SmsVerifyCatcherTest extends Activity {

    private EditText et_smsverify;
    private SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_smsverifycatcher);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "自动获取手机短信验证码");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView() {
        et_smsverify = (EditText)findViewById(R.id.et_smsverify);
        Button btn_smsverify = (Button) findViewById(R.id.btn_smsverify);
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                Log.d("zzh-2",message);
//                LemonBubble.showRight(SmsVerifyCatcherTest.this,message,3000);
                String code = parseCode(message);
                et_smsverify.setText(code);
            }
        });
        smsVerifyCatcher.setPhoneNumberFilter("");
        btn_smsverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("zzh-1","hahaha");
            }
        });

    }

    private String parseCode(String message){
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()){
            code = m.group(0);
        }
        return code;
    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
