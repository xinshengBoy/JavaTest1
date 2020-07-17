package com.just.test.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.maning.mnvideoplayerlibrary.player.MNViderPlayer;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * SurfaceView + MediaPlayer 实现的视频播放器，支持横竖屏切换，手势快进快退、调节音量，亮度等。
 * https://github.com/maning0303/MNVideoPlayer
 * compile 'com.github.maning0303:MNVideoPlayer:V1.0.3'
 * Created by admin on 2017/9/1.
 */

public class MNVideoPlayerTest extends Activity {

    private MNViderPlayer view_mnvideoplayer;
    private String url = "/sdcard/zzh/video2.mp4";
    private Button btn_mnvideo_play;
    private LinearLayout headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mnvideo_player);

        //// TODO: 2016/12/21 actionbar
        headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "视频播放器");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
        requestPermission();
    }

    private void initView() {
        btn_mnvideo_play = (Button)findViewById(R.id.btn_mnvideo_play);
        view_mnvideoplayer = (MNViderPlayer)findViewById(R.id.view_mnvideoplayer);
        view_mnvideoplayer.setWidthAndHeightProportion(16,9);//设置宽高比
        view_mnvideoplayer.setIsNeedBatteryListen(true);//设置电量监听
        view_mnvideoplayer.setIsNeedNetChangeListen(true);//设置网络监听

        view_mnvideoplayer.setDataSource(url,"舞蹈");//设置播放路径和标题
        view_mnvideoplayer.setOnCompletionListener(new MNViderPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                LemonBubble.showRight(MNVideoPlayerTest.this,"播放完成",1000);
            }
        });
        view_mnvideoplayer.setOnNetChangeListener(new MNViderPlayer.OnNetChangeListener() {
            @Override
            public void onWifi(MediaPlayer mediaPlayer) {
//                LemonBubble.showRight(MNVideoPlayerTest.this,"WIFI下播放",1000);
            }

            @Override
            public void onMobile(MediaPlayer mediaPlayer) {
                LemonBubble.showRight(MNVideoPlayerTest.this,"使用数据流量播放",1000);
            }

            @Override
            public void onNoAvailable(MediaPlayer mediaPlayer) {
                LemonBubble.showRight(MNVideoPlayerTest.this,"网络不可用",1000);
            }
        });

        btn_mnvideo_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_mnvideoplayer.playVideo(url,"舞蹈");//播放调用
                btn_mnvideo_play.setText("暂停");
            }
        });

        if (view_mnvideoplayer.isFullScreen()){
            btn_mnvideo_play.setVisibility(View.GONE);
            headerLayout.setVisibility(View.GONE);
        }else {
            btn_mnvideo_play.setVisibility(View.VISIBLE);
            headerLayout.setVisibility(View.VISIBLE);
        }

//        MediaPlaye

    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("视频播放调节亮度需要申请权限");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                Uri.parse("package:" + getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(intent, 100);
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        view_mnvideoplayer.pauseVideo();
    }

    @Override
    public void onBackPressed() {
        if (view_mnvideoplayer.isFullScreen()){
            //全屏情况下
            view_mnvideoplayer.setOrientationPortrait();//返回竖屏
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        LemonBubble.forceHide();
        if (view_mnvideoplayer != null){//退出或离开页面销毁播放器
            view_mnvideoplayer.destroyVideo();
            view_mnvideoplayer = null;
        }
        super.onDestroy();
    }
}
