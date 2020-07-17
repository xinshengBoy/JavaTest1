package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * android图片裁剪
 * compile 'com.soundcloud.android:android-crop:1.0.1@aar'
 * https://github.com/jdamcd/android-crop
 * Created by admin on 2017/5/16.
 */

public class AndroidCrop extends Activity {

    private ImageView iv_androidCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_android_crop);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "图片裁剪");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        iv_androidCrop = (ImageView) findViewById(R.id.iv_androidCrop);
        Button btn_crop_picture = (Button) findViewById(R.id.btn_crop_picture);
        btn_crop_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_androidCrop.setImageDrawable(null);
                Crop.pickImage(AndroidCrop.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK){
            beginCrop(data.getData());
        }else if (requestCode == Crop.REQUEST_CROP){
            handleCrop(resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 开始裁剪  图片暂存路径
     * @param source
     */
    private void beginCrop(Uri source){
        Uri sources = Uri.fromFile(new File(getCacheDir(),"cropped"));
        Crop.of(source,sources).asSquare().start(AndroidCrop.this);
    }

    /**
     * 展示裁剪返回结果
     * @param resultCode  状态码
     * @param result 结果
     */
    private void handleCrop(int resultCode,Intent result){
        if (result != null) {
            if (resultCode == RESULT_OK) {
                iv_androidCrop.setImageURI(Crop.getOutput(result));
            } else {
                Toast.makeText(AndroidCrop.this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
