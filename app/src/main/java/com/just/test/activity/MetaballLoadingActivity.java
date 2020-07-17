package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;

import com.just.test.custom.MetaballView;

/**
 * 一个有贝塞尔曲线动画的加载提示框
 * https://github.com/dodola/MetaballLoading
 * Created by admin on 2017/4/13.
 */

public class MetaballLoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MetaballView view = new MetaballView(this);
        setContentView(view);

    }
}
