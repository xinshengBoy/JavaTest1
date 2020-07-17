package com.just.test.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.tools.TimeCount;

/**
 * 程序开启引导页
 * Created by user on 2016/12/20.
 */

public class TotualActivity extends Activity {

    private TextView time;
    private long delayTime = 5000;
    private long secondTime = 1000;
    private TimeCount timeCount;
    //危险权限（运行时权限）
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int PERMISSION_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_totualview);
    }

    private void initView(){
        time = (TextView)findViewById(R.id.txt_totual_time);
        timeCount = new TimeCount(delayTime, secondTime,time,"跳过");
        timeCount.start();
        handler.postDelayed(runnable,delayTime);
//        time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                handler.sendEmptyMessage(0);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //// TODO: 2016/11/28 权限检测，只有在全部权限都同意的情况下才能进入程序，有一个不同意的话，则继续弹出这个权限的对话框
        PackageManager pkm = this.getPackageManager();//包管理器
        String pkName = this.getPackageName();//应用包名
        int len = PERMISSIONS.length;
        //所有权限是否全部允许
        boolean[] permissions = new boolean[len];
        for (int i = 0; i < len; i++){
            permissions[i] =   (PackageManager.PERMISSION_GRANTED
                    == pkm.checkPermission(PERMISSIONS[i], pkName));
        }
        boolean isAllPermissionAllowed = true;
        int index = 0;
        String[] tempArray = new String[len];
        for (int j = 0 ; j < len ; j++){
            //将不允许的权限放入这个临时的数组里面
            if (!permissions[j]){
                tempArray[index] = PERMISSIONS[j];
                index ++;
                isAllPermissionAllowed = false;
            }
        }
        //得到所有未允许的权限，再次请求
        String[] array = new String[index];
        for (int k = 0 ; k < index ; k++){
            array[k] = tempArray[k];
        }
        if (isAllPermissionAllowed) {// 这里才开始真的干活的
            initView();
        } else {
            ActivityCompat.requestPermissions(this, array, PERMISSION_REQUEST_CODE);
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                Intent i = new Intent(TotualActivity.this, MainActivity.class);
                startActivity(i);

                //启动主Activity后销毁自身
                finish();
                timeCount.cancel();
            }
        }
    };
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(TotualActivity.this, MainActivity.class);
            startActivity(i);

            //启动主Activity后销毁自身
            finish();
        }
    };
}
