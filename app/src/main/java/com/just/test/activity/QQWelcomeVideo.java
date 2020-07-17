package com.just.test.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.custom.MyQQVideoView;
import com.just.test.custom.QQFormView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 仿QQ，uber登录界面播放视频的效果
 * https://github.com/KobeGong/UberSplash
 * http://www.itlanbao.com/code/20150806/10033/100153.html
 * Created by admin on 2017/6/13.
 */

public class QQWelcomeVideo extends Activity implements View.OnClickListener,View.OnLayoutChangeListener{

    private MyQQVideoView view_qqvideo;
    private QQFormView view_formview;
    private Button login,regster;
    private String VIDEO_NAME = "welcome_video.mp4";
    private TextView appName;
    private InputType inputType = InputType.NONE;
    private View view_empty_qq;
    private RelativeLayout view_qqcontainer;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;
        setContentView(R.layout.layout_qq_welcome_video);

        initView();
    }

    private void initView(){
        view_qqcontainer = (RelativeLayout)findViewById(R.id.view_qqcontainer);
        view_qqvideo = (MyQQVideoView)findViewById(R.id.view_qqvideo);
        view_formview = (QQFormView)findViewById(R.id.view_formview);
        login = (Button)findViewById(R.id.btn_qqwelcome_login);
        regster = (Button)findViewById(R.id.btn_qqwelcome_regster);
        appName = (TextView)findViewById(R.id.txt_qqwelcome_appname);
        view_empty_qq = findViewById(R.id.view_empty_qq);

        view_formview.post(new Runnable() {
            @Override
            public void run() {
                int delta = view_formview.getTop()+view_formview.getHeight();
                view_formview.setTranslationY(-1 * delta);
            }
        });

        File videoFile = getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists()){
            videoFile = copyVideoFile();
        }

        playVideo(videoFile);//播放视频
        playAnim();//开启动画

        login.setOnClickListener(this);
        regster.setOnClickListener(this);
//        view_formview.focusChanges(new QQFormView.focusChangeListener() {
//            @Override
//            public void focusChange(boolean focus) {
//                if (focus){
//                    view_empty_qq.setVisibility(View.GONE);
//                }else {
//                    view_empty_qq.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        view_qqcontainer.addOnLayoutChangeListener(this);
    }



    private void playVideo(File videoFile) {
        view_qqvideo.setVideoPath(videoFile.getPath());
        view_qqvideo.setLayoutParams(new RelativeLayout.LayoutParams(-1,-1));
        view_qqvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);//循环播放
                mediaPlayer.start();
            }
        });
    }

    private void playAnim(){
        ObjectAnimator anim = ObjectAnimator.ofFloat(appName,"alpha",0,1);
        anim.setDuration(3000);//每次动画持续4秒钟
        anim.setRepeatCount(1);//重复次数
        anim.setRepeatMode(ObjectAnimator.REVERSE);//重放模式
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                appName.setVisibility(View.GONE);
                view_formview.setVisibility(View.VISIBLE);
                view_formview.setAlpha(1);
                view_formview.setTranslationY(200);
                view_formview.setFocusable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    private File copyVideoFile() {
        File videoFile;
        try {
            FileOutputStream fos = openFileOutput(VIDEO_NAME, MODE_PRIVATE);
            InputStream in = getResources().openRawResource(R.raw.welcome_video);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = in.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoFile = getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists())
            throw new RuntimeException("video file has problem, are you sure you have welcome_video.mp4 in res/raw folder?");
        return videoFile;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view_qqvideo.stopPlayback();
    }

    @Override
    public void onClick(View view) {
        int delta = view_formview.getTop() + view_formview.getHeight();
        switch (inputType){
            case NONE:
                view_formview.animate().translationY(0).alpha(1).setDuration(500).start();
                if (view == login){
                    inputType = InputType.LOGIN;
                }else if (view == regster){
                    inputType = InputType.SIGN_UP;
                }
                login.setText("登录");
                regster.setText("注册");
                break;
            case LOGIN:
                view_formview.animate().translationY(-1 * delta).alpha(0).setDuration(500).start();
                inputType = InputType.NONE;
                login.setText("登录");
                regster.setText("注册");
                break;
            case SIGN_UP:
                view_formview.animate().translationY(-1 * delta).alpha(0).setDuration(500).start();
                inputType = InputType.NONE;
                login.setText("登录");
                regster.setText("注册");
                break;
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){
            view_empty_qq.setVisibility(View.GONE);
        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){
            view_empty_qq.setVisibility(View.VISIBLE);
        }
    }

    enum InputType {
        NONE, LOGIN, SIGN_UP
    }
}
