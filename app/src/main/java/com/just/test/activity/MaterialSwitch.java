package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cooltechworks.views.ScratchImageView;
import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.github.glomadrian.materialanimatedswitch.MaterialAnimatedSwitch;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 开关
 * https://github.com/glomadrian/material-animated-switch
 * compile 'com.github.glomadrian:MaterialAnimatedSwitch:1.1@aar'
 * Created by admin on 2017/6/12.
 */

public class MaterialSwitch extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_material_switch);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "开关和文字刮刮乐");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        ScratchImageView iv_scratchview = (ScratchImageView) findViewById(R.id.iv_scratchview);
        iv_scratchview.setRevealListener(new ScratchImageView.IRevealListener() {
            @Override
            public void onRevealed(ScratchImageView scratchImageView) {
                boolean isFinish = scratchImageView.isRevealed();
                if (isFinish){
                    Toast.makeText(MaterialSwitch.this,"当前：",Toast.LENGTH_SHORT).show();
                    scratchImageView.reveal();
                }
            }

            @Override
            public void onRevealPercentChangedListener(ScratchImageView scratchImageView, float v) {
                if (v > 0.5){//超过一半自动把蒙层全部去掉
                    scratchImageView.clear();
                }
            }
        });

        MaterialAnimatedSwitch toggle_material = (MaterialAnimatedSwitch) findViewById(R.id.toggle_material);
        Toast.makeText(MaterialSwitch.this,"当前状态："+toggle_material.isChecked(),Toast.LENGTH_SHORT).show();
        toggle_material.setOnCheckedChangeListener(new MaterialAnimatedSwitch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean b) {
                if (b) {
                    LemonBubble.showRight(MaterialSwitch.this,"启用了本功能",3000);
                }else {
                    LemonBubble.showError(MaterialSwitch.this,"弃用了本功能",3000);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
    }
}
