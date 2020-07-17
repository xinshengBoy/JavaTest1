package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.just.test.R;
import com.jaeger.library.StatusBarUtil;

/**
 * 状态栏工具类
 * https://github.com/laobie/StatusBarUtil
 * compile 'com.jaeger.statusbarutil:library:1.4.0'
 * Created by admin on 2017/6/22.
 */

public class StatusBarUtilTest extends Activity {

    private Spinner spinner_statusBar;
    private String [] barStyle = new String[]{"设置状态栏颜色","设置状态栏半透明","设置状态栏全透明","手动改变状态栏透明度"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_statusbar_util);

        spinner_statusBar = (Spinner)findViewById(R.id.spinner_statusBar);
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,barStyle);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_statusBar.setAdapter(adapter);
        spinner_statusBar.setSelection(0);
        StatusBarUtil.setColor(StatusBarUtilTest.this,Color.RED);

        spinner_statusBar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        StatusBarUtil.setColor(StatusBarUtilTest.this, Color.RED);
                        break;
                    case 1:
                        StatusBarUtil.setTranslucent(StatusBarUtilTest.this,127);//透明度0--255
                        break;
                    case 2:
                        StatusBarUtil.setTranslucent(StatusBarUtilTest.this);
                        break;
                    case 3:
                        StatusBarUtil.setTranslucent(StatusBarUtilTest.this,0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
