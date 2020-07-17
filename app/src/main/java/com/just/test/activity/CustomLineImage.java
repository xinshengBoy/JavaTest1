package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;

import com.just.test.widget.DrawImg;

/**
 * 自定义折线图
 * Created by user on 2016/12/8.
 */

public class CustomLineImage extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawImg img = new DrawImg(this);
        setContentView(img);
    }
}
