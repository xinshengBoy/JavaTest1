package com.just.test.activity;

import android.app.Activity;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.just.test.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 手机拍照
 * Created by Administrator on 2017/2/7.
 */

public class TakeCamera2 extends Activity implements View.OnClickListener{

    private SurfaceView sv_takeCamera;
    private Button btn_takePhotoPicture;
    private Button btn_takeAutoFocus;
    private android.hardware.Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.layout_takecamera2);

        sv_takeCamera = (SurfaceView)findViewById(R.id.sv_takeCamera);
        btn_takePhotoPicture = (Button)findViewById(R.id.btn_takePhotoPicture);
        btn_takeAutoFocus = (Button)findViewById(R.id.btn_takeAutoFocus);
        //将获取的摄像头填满整个窗口
        sv_takeCamera.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //设置窗口分辨率
        sv_takeCamera.getHolder().setFixedSize(176,144);
        //保持屏幕常亮
        sv_takeCamera.getHolder().setKeepScreenOn(true);
        //监听摄像头
        sv_takeCamera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    //打开摄像头
                    camera = android.hardware.Camera.open();
                    //获取摄像头参数对象
                    android.hardware.Camera.Parameters parameters = camera.getParameters();
                    //设置摄像头分辨率
                    parameters.setPreviewSize(800,480);
                    //设置摄像头捕获画面的频路
                    parameters.setPreviewFrameRate(3);
                    //设置拍摄照片的大小
                    parameters.setPictureSize(1024,768);
                    //设置捕捉图像的画质
                    parameters.setJpegQuality(80);
                    //把参数返回给摄像头
                    camera.setParameters(parameters);
                    //显示摄像头捕获的画面
                    camera.setPreviewDisplay(surfaceHolder);
                    //开始预览摄像头
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (camera != null){
                    camera.release();
                    camera = null;//释放摄像头
                }
            }
        });

        btn_takePhotoPicture.setOnClickListener(this);
        btn_takeAutoFocus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (camera != null) {
            if (view == btn_takePhotoPicture) {
                camera.takePicture(null, null, new android.hardware.Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, android.hardware.Camera camera) {
                        try {
                            File jpgFile = new File(Environment.getExternalStorageDirectory(),getTime()+".jpg");
                            FileOutputStream os = new FileOutputStream(jpgFile);
                            os.write(bytes);
                            os.close();
                            camera.startPreview();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else if (view == btn_takeAutoFocus) {
                camera.autoFocus(null);
            }
        }
    }

    /**
     * 获取当前时间
     * @return
     */
    private String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String currentTimeString = format.format(date);
        return currentTimeString;
    }
}
