package com.just.test.activity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.tools.TextShow;
import com.just.test.widget.MyActionBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 歌词同步
 * 参考网址：http://www.itlanbao.com/code/20151214/10000/100695.html
 * Created by admin on 2017/6/7.
 */

public class LyricsSync extends Activity {

    private TextView txt_lyricsSync;
    private Button btn_lyricsSync_start;
    private MediaPlayer mPlayer;
    private Map<String,String> showMap = new LinkedHashMap<>();//当前显示的歌词
    private Map<String,String> totalMap = new HashMap<>();//全部的歌词
    private List<String> mList = new ArrayList<>();//每句歌词的时间
    private TextShow textShow;
    private int i;
    private String s = "";
    private boolean flag = false;//用来停止和判断线程
    private boolean change = false;
    private int count = 5;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lyrics_sync);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "歌词同步");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView(){
        btn_lyricsSync_start = (Button) findViewById(R.id.btn_lyricsSync_start);
        txt_lyricsSync = (TextView)findViewById(R.id.txt_lyricsSync);
        mPlayer = new MediaPlayer();

        btn_lyricsSync_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying){
                    if (mList.size() > 0){
                        mList.clear();
                    }
                    if (showMap.size() > 0){
                        showMap.clear();
                    }
                    if (totalMap.size() > 0){
                        totalMap.clear();
                    }
                    mPlayer.stop();
                    mPlayer.release();
                    playMusic();
                    isPlaying = false;
                    btn_lyricsSync_start.setText("播放");
                    txt_lyricsSync.setText("");
                }else {
                    playMusic();
                    isPlaying = true;
                    btn_lyricsSync_start.setText("暂停");
                }
            }
        });

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mPlayer.stop();
                txt_lyricsSync.setText("");
            }
        });
    }

    private void playMusic(){
//        AssetManager manager = getAssets();
//        AssetFileDescriptor afd;
        //设置播放的音频类型
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
//            afd = manager.openFd("littleApple.mp3");
//            FileDescriptor fd = afd.getFileDescriptor();
            File file = new File("/sdcard/zzh/littleApple.mp3");
            FileInputStream fs = new FileInputStream(file);
            mPlayer.setDataSource(fs.getFD());
//            mPlayer.setDataSource(fd);
            mPlayer.prepare();//准备
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开始播放
        mPlayer.start();
        //加载歌词
        readLyrics();
        //设置显示界面刚开始显示5行
        for (int i=0;i<5;i++){
            showMap.put(mList.get(i),totalMap.get(mList.get(i)));
        }

        textShow = new TextShow(showMap);

        new Thread(){
            @Override
            public void run() {
                while (!flag) {
                    try {
                        sleep(500);
                        mHandler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * 读取歌词
     */
    private void readLyrics(){
        try {
            InputStream is = getAssets().open("littleApple.lrc");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg;
            while ((msg=br.readLine()) != null){
                if (msg.length() != 0){
                    //保存歌词的时间和歌词内容
                    totalMap.put(msg.substring(1,6),msg.substring(msg.lastIndexOf("]")+1)+"\n");
                    //保存每一行歌词的时间
                    mList.add(msg.substring(1,6));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 歌词开始同步
     */
    private void changeLyricsText(){
        //给正在播放的歌词列表添加歌词
        if (showMap != null && showMap.get(conTime(mPlayer.getCurrentPosition())) != null){
            String s1;
            txt_lyricsSync.setText(textShow.Add(conTime(mPlayer.getCurrentPosition()).toString()));
            s1 = conTime(mPlayer.getCurrentPosition()).toString();
            //判断在相同的时间段内，此方法是否执行了两次
            if (!s.equals(s1)){
                change = true;
                s = s1;
            }else{
                change = false;
            }

            if (change){
                //设置正在播放的歌词的上方的行数
                if (i > 5){
                    showMap.remove(mList.get(i-6));//移除顶部的一条
                }

                //增加一行在底部
                if (count < totalMap.size()){
                    showMap.put(mList.get(count),totalMap.get(mList.get(count)));
                }
                i++;
                count++;
            }
        }
    }

    /**
     * 将音频的播放时间转换成00:00的格式
     * @param duration 位置
     * @return 转换完成的格式
     */
    private CharSequence conTime(int duration){
        int second = duration / 1000;//秒
        int minute = second / 60;//分钟

        second = second % 60;
        String minute1 = String.valueOf(minute);
        String second1 = String.valueOf(second);

        if (minute < 10){
            minute1 = "0"+minute1;
        }
        if (second < 10){
            second1 = "0"+second1;
        }
        return minute1 + ":" +second1;
    }


    Looper looper=Looper.getMainLooper();
    Handler mHandler = new Handler(looper){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0 && !flag){
                //判断歌词是不是全部显示了
                if (i == totalMap.size()){
                    flag = true;//已经全部歌词都显示了
                    return;
                }
                //开始同步歌词
                changeLyricsText();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        if (totalMap != null){
            totalMap = null;
        }
        if (showMap != null){
            showMap = null;
        }
        flag = true;
    }
}
