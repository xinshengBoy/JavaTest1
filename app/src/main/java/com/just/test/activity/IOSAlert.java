package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 仿照ios提示框
 * compile 'com.bigkoo:alertview:1.0.3'
 * https://github.com/saiwu-bigkoo/Android-AlertView
 * Created by admin on 2017/5/15.
 */

public class IOSAlert extends Activity implements OnItemClickListener{

    private InputMethodManager manager;
    private AlertView alertView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ios_alert);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "仿照ios提示框");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void clicks(View view) {
        switch (view.getId()){
            case R.id.btn_iosalert1:
                new AlertView("通知","明天带上身份证原件和银行卡在公司前台办理浦发银行银行卡的激活",null,new String[]{"确定"},
                    null,IOSAlert.this,AlertView.Style.Alert,IOSAlert.this).show();
                break;
            case R.id.btn_iosalert2:
                new AlertView(null,null,null,new String[]{"北京","上海","深圳","广州","长沙","重庆","成都","武汉"},
                        new String[]{"香港","台湾","澳门"},
                        IOSAlert.this,AlertView.Style.Alert,IOSAlert.this).show();
                break;
            case R.id.btn_iosalert3:
                new AlertView("通知","今晚健身去","取消",null,null,IOSAlert.this, AlertView.Style.ActionSheet,IOSAlert.this).show();
                break;
            case R.id.btn_iosalert4:
                new AlertView("上传头像",null,"取消",null,new String[]{"拍照","从相册选择"},IOSAlert.this, AlertView.Style.ActionSheet,IOSAlert.this).show();
                break;
            case R.id.btn_iosalert5:
                alertView = new AlertView("提示","请完善个人资料！","取消",null,new String[]{"完成"},IOSAlert.this, AlertView.Style.Alert,IOSAlert.this);
                ViewGroup group = (ViewGroup) LayoutInflater.from(IOSAlert.this).inflate(R.layout.item_iosalert_login,null);
                EditText et_iosalert = (EditText) group.findViewById(R.id.et_iosalert);
                et_iosalert.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        boolean isOpen = manager.isActive();
                        alertView.setMarginBottom(isOpen&&b ? 120 : 0);
                    }
                });
                alertView.addExtView(group).show();
                break;
        }
    }

    @Override
    public void onItemClick(Object o, int position) {

    }
}
