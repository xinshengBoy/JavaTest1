package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.io.File;
import java.util.Random;

import id.zelory.compressor.Compressor;

/**
 * compressor图片压缩
 * https://github.com/zetbaitsu/Compressor
 * Created by admin on 2017/4/7.
 */

public class CompressorImage extends Activity {

    private ImageView actual_image,compressed_image;
    private TextView actual_size,compressed_size;
    private int PICKPHOTO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_compressor);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "compressor图片压缩");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        actual_image = (ImageView)findViewById(R.id.actual_image);
        compressed_image = (ImageView)findViewById(R.id.compressed_image);
        actual_size = (TextView)findViewById(R.id.actual_size);
        compressed_size = (TextView)findViewById(R.id.compressed_size);

        actual_image.setBackgroundColor(getRandomColor());
        clearImage();
//        getResources().getString(com.android.internal.R.string.home_page)
    }

    /**
     * 选择图片
     * @param view
     */
    public void chooseImage(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,PICKPHOTO);
    }

    public void compressImage(View view){
        if (actual_image == null){
            showError("请选择图片");
        }else {
//            Compressor.getDefault(getApplicationContext())
//                    .compressToFileAsObservable(actual_image)
        }
    }

    public void customCompressImage(View view){

    }

    private void clearImage(){
        actual_image.setBackgroundColor(getRandomColor());
        compressed_image.setImageDrawable(null);
        compressed_image.setBackgroundColor(getRandomColor());
        compressed_size.setText("Size : -");
    }

    /**
     * 获取随机颜色
     * @return
     */
    private int getRandomColor(){
        Random random = new Random();
        return Color.argb(100,random.nextInt(256),random.nextInt(256),random.nextInt(256));
    }

    /**
     * 提示信息
     * @param message
     */
    private void showError(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
