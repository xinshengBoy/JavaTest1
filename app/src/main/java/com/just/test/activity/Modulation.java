package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 仿视频播放页面上下滑动调节音量大小和页面亮度
 * 参考网址：http://www.2cto.com/kf/201609/551320.html
 * 亮度参考：http://www.jb51.net/article/106638.htm
 * Created by admin on 2017/6/23.
 */

public class Modulation extends Activity {

    private RelativeLayout modulation_layout;
    private TextView txt_modulation_result;
    private GestureDetector mGestureDetector;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_modulation);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "亮度和音量调节");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView() {
        modulation_layout = (RelativeLayout) findViewById(R.id.modulation_layout);
        txt_modulation_result = (TextView) findViewById(R.id.txt_modulation_result);

        setGestureDetector();
    }

    /**
     * 设置亮度
     *
     * @param number 亮度值  0为最暗，1为最亮
     */
    private void setBrightness(float number) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //屏幕最大亮度为255
        lp.screenBrightness = lp.screenBrightness + number / 255f;
        if (lp.screenBrightness > 1) {
            lp.screenBrightness = 1;
        } else if (lp.screenBrightness < 0.1) {
            lp.screenBrightness = (float) 0.1;
        }
        getWindow().setAttributes(lp);

        float bright = lp.screenBrightness;
        txt_modulation_result.setText((int) Math.ceil(bright * 100) + "%");
    }

    /**
     * 设置声音
     *
     * @param volume 声音大小
     */
    private void setVolumeness(float volume) {
        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);

        if (currentVolume < 0) {
            currentVolume = 0;
        }
        currentVolume += volume;

        if (currentVolume < 0){
            currentVolume = 0;
        }else if (currentVolume > maxVolume){
            currentVolume = maxVolume;
        }

        manager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, AudioManager.FLAG_PLAY_SOUND);
        double percent = currentVolume / maxVolume;
        percent = (double) (Math.round(percent*100)/100);//这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000
        txt_modulation_result.setText(percent * 100 + "%");
//        txt_modulation_result.setText((int) Math.ceil((currentVolume / maxVolume) * 100) + "%");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public void setGestureDetector(){
        mGestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                int screenWidth = modulation_layout.getWidth();
                if (e1.getY() - e2.getY() > 0.5 && Math.abs(distanceY) > 0.5) {//上滑
                    if (e1.getX() > screenWidth / 2) {
                        //声音加大
                        setVolumeness(1);
                    } else {
                        //亮度加亮
                        setBrightness(2);
                    }
                }

                if (e1.getY() - e2.getY() < 0.5 && Math.abs(distanceY) > 0.5) {//下滑
                    if (e1.getX() > screenWidth / 2) {
                        //声音减小
                        setVolumeness(-1);
                    } else {
                        //亮度减小
                        setBrightness(-2);
                    }
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            File file = new File("/sdcard/zzh/littleApple.mp3");
            FileInputStream fs = new FileInputStream(file);
            mPlayer.setDataSource(fs.getFD());
            mPlayer.prepare();//准备
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开始播放
        mPlayer.start();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mPlayer.start();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}
