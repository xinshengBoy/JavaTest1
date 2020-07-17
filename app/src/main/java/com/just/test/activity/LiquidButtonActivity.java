package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.gospelware.liquidbutton.LiquidButton;

/**
 * 一个动态加载的控件样式
 * https://github.com/yoruriko/LiquidButton
 * compile 'com.gospelware.liquidbutton:liquidButtonLib:1.1.5'
 * Created by admin on 2017/4/12.
 */

public class LiquidButtonActivity extends Activity implements View.OnClickListener{

    private Button updateProgress_btn,finish_btn;
    private TextView progress_tv;
    private LiquidButton lb_liquid;
    private float progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_liquidbutton);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "液体加载控件");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        lb_liquid = (LiquidButton) findViewById(R.id.lb_liquid);
        Switch fillAfter_switch = (Switch) findViewById(R.id.fillAfter_switch);
        Switch autoPlay_switch = (Switch) findViewById(R.id.autoPlay_switch);
        updateProgress_btn = (Button) findViewById(R.id.updateProgress_btn);
        progress_tv = (TextView) findViewById(R.id.progress_tv);
        finish_btn = (Button) findViewById(R.id.finish_btn);

        fillAfter_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lb_liquid.setFillAfter(isChecked);
            }
        });
        autoPlay_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lb_liquid.setAutoPlay(isChecked);
            }
        });
        lb_liquid.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                progress_tv.setText("Finish");
            }

            @Override
            public void onProgressUpdate(float progress) {
                progress_tv.setText(String.format("%.2f",progress*100) + "%");
            }
        });

        updateProgress_btn.setOnClickListener(this);
        finish_btn.setOnClickListener(this);
        lb_liquid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == updateProgress_btn){
            progress += 0.1f;
            lb_liquid.changeProgress(progress);
        }else if (v == finish_btn){
            progress = 0;
            lb_liquid.finishPour();
        }else if (v == lb_liquid){
            lb_liquid.startPour();
        }
    }
}
