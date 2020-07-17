package com.just.test.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acker.simplezxing.activity.CaptureActivity;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.util.HashMap;
import java.util.Map;

/**
 * 快速实现二维码扫描
 * 参考网址：http://www.tuicool.com/articles/jMzUZz
 * compile 'com.acker:simplezxing:1.5'
 * 注意：集成了这个的话其他地方是不能导入zxing版本比3.5版本低的
 * Created by admin on 2017/7/1.
 */

public class SimpleZxing extends Activity{

    private TextView txt_scanResult;
    private static final int REQ_CODE_PERMISSION = 0x1111;
    private TextView txt_pass;
    // 多选提示框
    private AlertDialog alertDialog3;
    private String [] mList = {"苏州邮局","苏州快递","杭州邮局","杭州快递","杭州邮政"};
    private Map<Integer,String> mCheckList = new HashMap<>();
    private String result = mList[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_simple_zxing);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "快速实现二维码扫描");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView() {
        Button btn_startScan = (Button) findViewById(R.id.btn_startSimpleZxingScan);
        txt_scanResult = (TextView)findViewById(R.id.txt_scanResult);

        btn_startScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SimpleZxing.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(SimpleZxing.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
                } else {
                    // Have gotten the permission
                    startCaptureActivityForResult();
                }
            }
        });

        txt_pass = (TextView)findViewById(R.id.txt_pass);
        txt_pass.setText(result);
        txt_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMutilAlertDialog(view);
            }
        });
    }

    private void startCaptureActivityForResult(){
        Intent intent = new Intent(SimpleZxing.this, CaptureActivity.class);
        //设置具体参数设置
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // User agree the permission
                    startCaptureActivityForResult();
                } else {
                    // User disagree the permission
                    Toast.makeText(this, "You must agree the camera permission request before you use the code scan function", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CaptureActivity.REQ_CODE:
                switch (resultCode){
                    case RESULT_OK:
                        String result = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        txt_scanResult.setText("RESULT_OK:"+result);
                        break;
                    case RESULT_CANCELED:
                        if (data != null){
                            String results = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                            txt_scanResult.setText("RESULT_CANCELED:"+results);
                        }
                        break;
                }
                break;
        }
    }

    public void showMutilAlertDialog(View view){
        // 创建一个AlertDialog建造者
        AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);
        // 设置标题
        alertDialogBuilder.setTitle("java EE 常用框架");
        // 参数介绍
        // 第一个参数：弹出框的信息集合，一般为字符串集合
        // 第二个参数：被默认选中的，一个布尔类型的数组
        // 第三个参数：勾选事件监听
        alertDialogBuilder.setMultiChoiceItems(mList, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // dialog：不常使用，弹出框接口
                // which：勾选或取消的是第几个
                // isChecked：是否勾选
                if (isChecked) {
                    // 选中
                    Toast.makeText(SimpleZxing.this, "选中"+mList[which], Toast.LENGTH_SHORT).show();
                    mCheckList.put(which,mList[which]);
                }else {
                    // 取消选中
                    Toast.makeText(SimpleZxing.this, "取消选中"+mList[which], Toast.LENGTH_SHORT).show();
                    mCheckList.remove(mList[which]);
                }

            }
        });
        alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //TODO 业务逻辑代码
                if (mCheckList.size() != 0){
                    result = "";
                }
                for (int i=0;i<mCheckList.size();i++){
                    result += mCheckList.get(i) + "\n";
                }
                txt_pass.setText(result);
                // 关闭提示框
                alertDialog3.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO 业务逻辑代码

                // 关闭提示框
                alertDialog3.dismiss();
            }
        });
        alertDialog3 = alertDialogBuilder.create();
        alertDialog3.show();
    }
}
