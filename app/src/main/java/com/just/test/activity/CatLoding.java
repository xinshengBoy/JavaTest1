package com.just.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.roger.catloadinglibrary.CatLoadingView;

/**
 * 小猫进度条
 * 参考网址：https://github.com/Rogero0o/CatLoadingView
 * compile 'com.roger.catloadinglibrary:catloadinglibrary:1.0.1'
 * Created by admin on 2017/6/7.
 */

public class CatLoding extends AppCompatActivity {

    private CatLoadingView catLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_catloading);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "小猫进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        catLoadingView = new CatLoadingView();
        Button btn_catloading_start = (Button)findViewById(R.id.btn_catloading_start);
        btn_catloading_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catLoadingView.show(getSupportFragmentManager(), "");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        catLoadingView.dismiss();
    }
}
