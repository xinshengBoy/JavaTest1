package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.Wave;

/**
 * 加载进度条样式
 * https://github.com/ybq/Android-SpinKit
 * Created by admin on 2017/4/3.
 */

public class SpinKitActivity extends Activity{

    private boolean isVisible = true;
    private DoubleBounce bounce;
    private Wave wave;
    private Circle circle;
    private ThreeBounce threeBounce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_spinkit);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "加载进度条样式");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        final SpinKitView spin_kit = (SpinKitView)findViewById(R.id.spin_kit);
        final Button btn_spinkits = (Button) findViewById(R.id.btn_spinkits);
        ProgressBar pb_spinkit_progress = (ProgressBar) findViewById(R.id.pb_spinkit_progress);
        Button btn_spinkitleft = (Button) findViewById(R.id.btn_spinkitleft);
        TextView txt_spinkit = (TextView) findViewById(R.id.txt_spinkit);
        ImageView iv_spinkit = (ImageView) findViewById(R.id.iv_spinkit);
        btn_spinkits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    spin_kit.setVisibility(View.GONE);
                    isVisible = false;
                    btn_spinkits.setText("显示");
                }else {
                    spin_kit.setVisibility(View.VISIBLE);
                    isVisible = true;
                    btn_spinkits.setText("隐藏");
                }
            }
        });

        bounce = new DoubleBounce();
        bounce.setBounds(0,0,100,100);
        bounce.setColor(Color.GREEN);
        pb_spinkit_progress.setIndeterminateDrawable(bounce);

        wave = new Wave();
        wave.setBounds(0,0,100,100);
        wave.setColor(Color.YELLOW);
        btn_spinkitleft.setCompoundDrawables(wave,null,null,null);//left,top,right,bottom

        circle = new Circle();
        circle.setBounds(0,0,100,100);
        circle.setColor(Color.WHITE);
        txt_spinkit.setCompoundDrawables(null,null,circle,null);//left,top,right,bottom
        txt_spinkit.setBackgroundColor(Color.BLUE);

        threeBounce = new ThreeBounce();
        threeBounce.setColor(Color.WHITE);
        iv_spinkit.setImageDrawable(threeBounce);
        iv_spinkit.setBackgroundColor(Color.GREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();

        bounce.start();
        wave.start();
        circle.start();
        threeBounce.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bounce.stop();
        wave.stop();
        circle.stop();
        threeBounce.stop();
    }
}
