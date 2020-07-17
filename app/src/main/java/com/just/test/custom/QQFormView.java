package com.just.test.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.just.test.R;

/**
 * QQ登录界面账号和密码输入页面
 * Created by admin on 2017/6/13.
 */

public class QQFormView extends LinearLayout{
    private static EditText edit1;
    private EditText edit2;

    public QQFormView(Context context) {
        super(context);
        loadView();
    }

    public QQFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadView();
    }

    public QQFormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadView();
    }

    private void loadView(){
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_qq_formview, this);
        edit1 = (EditText) findViewById(R.id.et_qqwelcome_name);
        edit2 = (EditText) findViewById(R.id.et_qqwelcome_password);
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        edit1.setFocusable(focusable);
        edit2.setFocusable(focusable);
    }

    public interface focusChangeListener{
        void focusChange(boolean focus);
    }

    public void focusChanges(final focusChangeListener listener){
        edit1.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                listener.focusChange(b);
            }
        });
        edit2.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                listener.focusChange(b);
            }
        });
    }
}
