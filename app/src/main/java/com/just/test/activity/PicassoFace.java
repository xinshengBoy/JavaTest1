package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.rohitarya.picasso.facedetection.transformation.FaceCenterCrop;
import com.rohitarya.picasso.facedetection.transformation.core.PicassoFaceDetector;
import com.squareup.picasso.Picasso;

/**
 * Picasso face detection transformation   picasso加载图片和图片裁剪，自动定位到头部
 * https://github.com/aryarohit07/PicassoFaceDetectionTransformation
 * Created by admin on 2017/4/7.
 */

public class PicassoFace extends Activity {

    private ImageView iv_picassoFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_picasso_face);

        PicassoFaceDetector.initialize(PicassoFace.this);
        initView();
    }

    private void initView(){
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "倒计时和圆形进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        Button btn_picassoFaceTest = (Button) findViewById(R.id.btn_picassoFaceTest);
        iv_picassoFace = (ImageView) findViewById(R.id.iv_picassoFace);
        btn_picassoFaceTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                loadPicassoImage();
            }
        }
    };

    private void loadPicassoImage(){
        String url = "http://img.woyaogexing.com/2017/01/26/1c6e0d2d4e1e3611!200x200.jpg";
        Picasso.with(PicassoFace.this)
                .load(url)
                .fit()
                .centerInside()
                .transform(new FaceCenterCrop(100,100))
                .into(iv_picassoFace);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PicassoFaceDetector.releaseDetector();
    }
}
