package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 简单的图片浏览器
 * Created by Administrator on 2017/2/28.
 */

public class ImagePictureActivity extends Activity implements View.OnClickListener{

    private ImageView iv_imagePicture;
    private int [] images = new int[]{R.drawable.beautifalgirl,R.drawable.bg01,R.drawable.bg02,R.drawable.bg03,R.drawable.bg04};
    private int currentImg = 0;
    private Button alphaAdd,alphaReduce,next;
    private int currentAlpha = 255;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_imagepicture);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "简单的图片浏览器");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        alphaAdd = (Button)findViewById(R.id.btn_imagePicture_alphaAdd);
        alphaReduce = (Button)findViewById(R.id.btn_imagePicture_alphaReduce);
        next = (Button)findViewById(R.id.btn_imagePicture_next);
        iv_imagePicture = (ImageView)findViewById(R.id.iv_imagePicture);
        iv_imagePicture.setImageResource(images[0]);//显示第一张

        alphaAdd.setOnClickListener(this);
        alphaReduce.setOnClickListener(this);
        next.setOnClickListener(this);
        iv_imagePicture.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == alphaAdd){
            currentAlpha += 20;
            changeAlpha(currentAlpha);
        }else if (view == alphaReduce){
            currentAlpha -= 20;
            changeAlpha(currentAlpha);
        }else if (view == next){
            iv_imagePicture.setImageResource(images[++currentImg % images.length]);
        }else if (view == iv_imagePicture){
            iv_imagePicture.setImageResource(images[++currentImg % images.length]);
        }
    }

    private void changeAlpha(int alpha){
        if (alpha >= 255){
            currentAlpha = 255;
        }else if (alpha <= 0){
            currentAlpha = 0;
        }
        iv_imagePicture.setAlpha(alpha);
    }
}
