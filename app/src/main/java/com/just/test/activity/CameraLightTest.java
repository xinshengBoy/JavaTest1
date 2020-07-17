package com.just.test.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 闪光灯测试
 * 参考路径：http://blog.csdn.net/cy524563/article/details/41545387
 * Created by admin on 2017/10/28.
 */

public class CameraLightTest extends Activity implements View.OnClickListener{

    private Button open,close;
    private Camera mCamera;
    private Camera.Parameters mParameters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_camera_light);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "闪光灯测试");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView() {
        open = (Button)findViewById(R.id.btn_cameraLight_open);
        close = (Button)findViewById(R.id.btn_cameraLight_close);

        open.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == open){
            boolean isOk = checkFlashlight();
            if (isOk){
                openFlashlight();
            }
        }else if (view == close){
            closeFlashlight();
        }
    }

    // 检测当前设备是否配置闪光灯
    private boolean checkFlashlight() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Toast.makeText(this, "当前设备没有闪光灯", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    // 打开闪光灯
    private void openFlashlight() {

        try {
            mCamera = Camera.open();
            int textureId = 0;
            mCamera.setPreviewTexture(new SurfaceTexture(textureId));
            mCamera.startPreview();

            mParameters = mCamera.getParameters();

            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParameters);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // 关闭闪光灯
    private void closeFlashlight() {

        if (mCamera != null) {
            mParameters = mCamera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mParameters);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}
