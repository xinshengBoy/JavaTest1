package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dingmouren.videowallpaper.VideoWallpaper;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * VideoWallpaper
 * 可以将本地mp4文件设置成手机桌面，手机桌面原来可以如此炫酷
 * compile 'com.dingmouren.videowallpaper:videowallpaper:1.0.1'
 * 参考网址：https://github.com/DingMouRen/VideoWallpaper
 *
 视频文件必须是mp4格式
 mp4文件必须是本地文件

 * Created by admin on 2017/7/13.
 */

public class VideoWallpaperTest extends Activity implements View.OnClickListener{

    private Button startSetting,silence,normal,exitSetting;
    private VideoWallpaper paper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video_wallpaper);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 桌面播放视频");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        paper = new VideoWallpaper();
        initView();
    }

    private void initView() {
        startSetting = (Button)findViewById(R.id.btn_videoWallpaper_setting);
        silence = (Button)findViewById(R.id.btn_videoWallpaper_silence);
        normal = (Button)findViewById(R.id.btn_videoWallpaper_normal);
        exitSetting = (Button)findViewById(R.id.btn_videoWallpaper_exit);

        startSetting.setOnClickListener(this);
        silence.setOnClickListener(this);
        normal.setOnClickListener(this);
        exitSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == startSetting){
            paper.setToWallPaper(VideoWallpaperTest.this,"/sdcard/zzh/video1.mp4");
        }else if (view == silence){
            VideoWallpaper.setVoiceSilence(VideoWallpaperTest.this);
        }else if (view == normal){
            VideoWallpaper.setVoiceNormal(VideoWallpaperTest.this);
        }else if (view == exitSetting){
            finish();
        }
    }
}
