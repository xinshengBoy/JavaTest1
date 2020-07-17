package com.just.test.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.just.test.R;

/**
 * 手机基本信息
 * @author user
 *
 */
public class PhoneBaseInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_phone_baseinfo);

        String osType = "Android " + Build.VERSION.RELEASE;
        String dType = Build.MODEL;

        TextView txt_system = (TextView) findViewById(R.id.txt_system);
        TextView txt_pinpai = (TextView) findViewById(R.id.txt_pinpai);
        TextView txt_fenbianlv = (TextView) findViewById(R.id.txt_fenbianlv);
        // 方法1 Android获得屏幕的宽和高
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        txt_system.setText("手机操作系统及系统版本:"+osType);
        txt_pinpai.setText("手机品牌:"+dType);
        txt_fenbianlv.setText("手机分辨率:"+screenHeight+"x"+screenWidth);
    }
}
