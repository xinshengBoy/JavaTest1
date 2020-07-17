package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.custom.CircularAnim;
import com.just.test.widget.MyActionBar;
import com.xyz.xruler.XyzRuler;
import com.xyz.xswitch.XyzSwitch;

/**
 * 状态和身高选择
 * 自定义switch和直尺的使用
 * https://github.com/zhangxuyang321/XyzInfo
 *compile 'com.xyz.xswitch:xswitch:1.0.1'
 * compile 'com.xyz.xruler:xruler:1.0.4'
 * Created by admin on 2017/6/22.
 */

public class XyzInfoTest extends Activity implements View.OnClickListener{

    private TextView txt_ruler_weight,txt_ruler_birth;
    private ImageView iv_circularAnim;
    private Button btn_circularAnim1,btn_circularAnim2;
    private boolean isBtnOpen = true;
    private boolean isImgOpen = true;
    private LinearLayout circularAnim_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xyzinfo);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "状态和身高选择");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView() {
        XyzSwitch view_xyzSwitch = (XyzSwitch)findViewById(R.id.view_xyzSwitch);
        view_xyzSwitch.setOnXyzSwitchChangeListener(new XyzSwitch.XyzSwitchChange() {
            @Override
            public void changed(boolean isRight) {
                Toast.makeText(XyzInfoTest.this,isRight ? "女" : "男",Toast.LENGTH_SHORT).show();
            }
        });

        txt_ruler_weight = (TextView)findViewById(R.id.txt_ruler_weight);
        txt_ruler_birth = (TextView)findViewById(R.id.txt_ruler_birth);

        XyzRuler view_ruler_weight = (XyzRuler) findViewById(R.id.view_ruler_weight);
        XyzRuler view_ruler_birth = (XyzRuler) findViewById(R.id.view_ruler_birth);

        view_ruler_weight.setOnRulerValueChangeListener(new XyzRuler.RulerValue() {
            @Override
            public void value(int value) {
                txt_ruler_weight.setText(value+"");
            }
        });

        view_ruler_birth.setOnRulerValueChangeListener(new XyzRuler.RulerValue() {
            @Override
            public void value(int value) {
                txt_ruler_birth.setText(value+"");
            }
        });
        circularAnim_layout = (LinearLayout)findViewById(R.id.circularAnim_layout);
        iv_circularAnim = (ImageView) findViewById(R.id.iv_circularAnim);
        btn_circularAnim1 = (Button) findViewById(R.id.btn_circularAnim1);
        btn_circularAnim2 = (Button) findViewById(R.id.btn_circularAnim2);

        iv_circularAnim.setOnClickListener(this);
        btn_circularAnim1.setOnClickListener(this);
        btn_circularAnim2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == iv_circularAnim){
            if (isImgOpen){
                CircularAnim.hide(circularAnim_layout).triggerView(iv_circularAnim).go();
                isImgOpen = false;
            }else {
                CircularAnim.show(circularAnim_layout).triggerView(iv_circularAnim).go();
            }
        }else if (view == btn_circularAnim1){
            if (isBtnOpen) {
                CircularAnim.hide(btn_circularAnim1).go();
                isBtnOpen = false;
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(2000);
//                            btn_circularAnim1.setVisibility(View.VISIBLE);
                            CircularAnim.show(btn_circularAnim1).go();
                            isBtnOpen = true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }else {
                CircularAnim.show(btn_circularAnim1).go();
                isBtnOpen = true;
            }
        }else if (view == btn_circularAnim2){
            CircularAnim.fullActivity(XyzInfoTest.this,btn_circularAnim2)
                    .colorOrImageRes(R.color.green)
                    .go(new CircularAnim.OnAnimationEndListener() {
                        @Override
                        public void onAnimationEnd() {
                            startActivity(new Intent(XyzInfoTest.this,MainActivity.class));
                        }
                    });
        }
    }
}
