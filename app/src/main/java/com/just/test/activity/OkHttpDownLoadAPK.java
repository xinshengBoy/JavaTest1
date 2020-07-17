package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 使用okhttp框架进行文件下载动作
 * Created by admin on 2017/5/18.
 */

public class OkHttpDownLoadAPK extends Activity {

    private Button btn_download_okhttp;
    private ProgressBar pb_download_okhttp;
    private TextView txt_download_okhttp;
    private static final OkHttpClient client = new OkHttpClient.Builder()
            //设置超时，不设置可能会报异常
            .connectTimeout(1000, TimeUnit.MINUTES)
            .readTimeout(1000, TimeUnit.MINUTES)
            .writeTimeout(1000, TimeUnit.MINUTES)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_okhttp_download);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "OKHTTP文件下载");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        String result = Long.toString(maxMemory / 1024 / 1024);
        LemonBubble.showRight(OkHttpDownLoadAPK.this, "本程序分配最大内存为：" + result+"MB", 5000);
        btn_download_okhttp = (Button) findViewById(R.id.btn_download_okhttp);
        pb_download_okhttp = (ProgressBar) findViewById(R.id.pb_download_okhttp);
        txt_download_okhttp = (TextView) findViewById(R.id.txt_download_okhttp);

        txt_download_okhttp.setText(result+"MB");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
    }
}
