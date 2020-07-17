package com.just.test.activity;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.widget.TextView;

import com.just.test.tools.FileHelper;

/**
 * 本程序的流量统计
 * Created by user on 2016/12/8.
 */

public class AppFlowTotal extends Activity {

    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo("com.just.test",PackageManager.GET_ACTIVITIES);
            uid = info.uid;
            long rxData = TrafficStats.getUidRxBytes(uid);
            long txData = TrafficStats.getUidTxBytes(uid);
            long totalData = rxData + txData;
            String total = FileHelper.FormetFileSize(totalData);
            TextView textView = new TextView(this);
            textView.setText("本程序总共使用了"+total+"流量");
            textView.setTextSize(30f);
            setContentView(textView);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
