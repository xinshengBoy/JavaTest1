package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.just.test.service.HomeService;

/**
 * HOME键监听
 * Created by Administrator on 2017/2/16.
 */

public class HomeMoniter extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(HomeMoniter.this, HomeService.class);
        startService(intent);
    }
}
