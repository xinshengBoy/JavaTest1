package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 手电筒
 * Created by Administrator on 2017/1/19.
 */

public class FlashLight extends Activity implements View.OnClickListener{

    private ImageView iv_flashlight,iv_flashlight_swich;
    private LinearLayout layout_flashlight;
    private boolean isOpen = false;
    private Camera camera = null;
    private Camera.Parameters parameters = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flashlight);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "手电筒");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        layout_flashlight = (LinearLayout)findViewById(R.id.layout_flashlight);
        iv_flashlight = (ImageView)findViewById(R.id.iv_flashlight);
        iv_flashlight_swich = (ImageView)findViewById(R.id.iv_flashlight_swich);
        iv_flashlight_swich.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == iv_flashlight_swich){
            if (isOpen){
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                isOpen = false;
                camera.release();
                layout_flashlight.setBackgroundColor(Color.BLACK);
                iv_flashlight_swich.setImageResource(R.drawable.switchoff);
            }else {
                camera = Camera.open();
                parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                isOpen = true;
                layout_flashlight.setBackgroundColor(Color.WHITE);
                iv_flashlight_swich.setImageResource(R.drawable.switchon);
            }
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            if (camera != null){
//                camera.release();
//                camera = null;
//            }
//
//        }
//        return false;// 设置成false让back失效 ，true表示 不失效
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null && parameters != null){
            if (isOpen){
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
            }
            camera.release();
            camera = null;
            parameters = null;
            FlashLight.this.finish();
        }
    }
}
