package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.custom.SnailBar;
import com.just.test.widget.MyActionBar;

/**
 * 蜗牛进度条
 * 参考网址：https://github.com/android-cjj/SnailBar
 * Created by admin on 2017/6/7.
 */

public class SnailBarTest extends Activity {

    private SnailBar bar_snailbar;
    private TextView txt_snailbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_snailbar);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "蜗牛进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        Button btn_snailbar_start = (Button) findViewById(R.id.btn_snailbar_start);
        bar_snailbar = (SnailBar)findViewById(R.id.bar_snailbar);
        txt_snailbar = (TextView)findViewById(R.id.txt_snailbar);

        btn_snailbar_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar_snailbar.setVisibility(View.VISIBLE);
            }
        });

        bar_snailbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                txt_snailbar.setText("进度："+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
