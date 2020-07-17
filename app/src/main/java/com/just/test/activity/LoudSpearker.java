package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 扬声器
 * Created by Administrator on 2017/2/19.
 */

public class LoudSpearker extends Activity implements View.OnClickListener{

    private Button btn_loud_open;
    private boolean isLoud = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loudspearker);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "扬声器");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_loud_open = (Button)findViewById(R.id.btn_loud_open);
        btn_loud_open.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_loud_open){
            AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            isLoud = manager.isSpeakerphoneOn();

            if (isLoud){
                manager.setSpeakerphoneOn(false);
                manager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                        manager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL ),
                        AudioManager.STREAM_VOICE_CALL);
                btn_loud_open.setText("打开扬声器");
            }else {
                manager.setSpeakerphoneOn(true);
                manager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                        manager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL ),
                        AudioManager.STREAM_VOICE_CALL);
                btn_loud_open.setText("关闭扬声器");
            }
        }
    }
}
