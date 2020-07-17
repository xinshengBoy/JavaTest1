package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 仿微信视频播放页面
 * Created by admin on 2017/4/25.
 */

public class WeixinVideoSuccess extends Activity implements View.OnClickListener{

    private VideoView weixin_videoview;
    private Button weixin_play,weixin_pause,weixin_rePlay;
    private TextView weixin_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weixinvideo_success);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "小视频详情");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        weixin_videoview = (VideoView)findViewById(R.id.weixin_videoview);
        weixin_play = (Button)findViewById(R.id.weixin_play);
        weixin_pause = (Button)findViewById(R.id.weixin_pause);
        weixin_rePlay = (Button)findViewById(R.id.weixin_rePlay);
        weixin_time = (TextView)findViewById(R.id.weixin_time);

        weixin_play.setOnClickListener(this);
        weixin_pause.setOnClickListener(this);
        weixin_rePlay.setOnClickListener(this);

        Intent intent = getIntent();
        String path = intent.getStringExtra("filePath");
        weixin_videoview.setVideoPath(path);
        weixin_time.setText("视频长度"+(weixin_videoview.getDuration() / 1024) + "M");
    }

    @Override
    public void onClick(View view) {
        if (view == weixin_play){
            weixin_videoview.start();
        }else if (view == weixin_pause){
            weixin_videoview.pause();
        }else if (view == weixin_rePlay){
            weixin_videoview.resume();
            weixin_videoview.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (weixin_videoview != null){
            weixin_videoview.resume();
            weixin_videoview = null;
        }
    }
}
