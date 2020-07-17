package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 音量设置
 * Created by Administrator on 2017/2/19.
 */

public class VoiceSetting extends Activity implements SeekBar.OnSeekBarChangeListener{

    private SeekBar seekBar_speak,seekBar_setting,seekBar_ling,seekBar_music,seekBar_message;
    private AudioManager manager;
    private int speak_current,setting_current,ring_current,music_current,alerm_current;
    private TextView txt_voice_speak,txt_voice_setting,txt_voice_ring,txt_voice_music,txt_voice_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_voiceseting);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "音量设置");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
        getVoice();
    }

    private void initView(){
        seekBar_speak = (SeekBar)findViewById(R.id.seekBar_speak);
        seekBar_setting = (SeekBar)findViewById(R.id.seekBar_setting);
        seekBar_ling = (SeekBar)findViewById(R.id.seekBar_ling);
        seekBar_music = (SeekBar)findViewById(R.id.seekBar_music);
        seekBar_message = (SeekBar)findViewById(R.id.seekBar_message);

        txt_voice_speak = (TextView)findViewById(R.id.txt_voice_speak);
        txt_voice_setting = (TextView)findViewById(R.id.txt_voice_setting);
        txt_voice_ring = (TextView)findViewById(R.id.txt_voice_ring);
        txt_voice_music = (TextView)findViewById(R.id.txt_voice_music);
        txt_voice_message = (TextView)findViewById(R.id.txt_voice_message);

        seekBar_speak.setOnSeekBarChangeListener(this);
        seekBar_setting.setOnSeekBarChangeListener(this);
        seekBar_ling.setOnSeekBarChangeListener(this);
        seekBar_music.setOnSeekBarChangeListener(this);
        seekBar_message.setOnSeekBarChangeListener(this);
    }

    private void getVoice(){
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //通话
        int speak_max = manager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
        speak_current = manager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        seekBar_speak.setMax(speak_max);
        seekBar_speak.setProgress(speak_current);
        txt_voice_speak.setText("通话音量："+speak_current);
        //系统音量
        int setting_max = manager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        setting_current = manager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        seekBar_setting.setMax(setting_max);
        seekBar_setting.setProgress(setting_current);
        txt_voice_setting.setText("系统音量："+setting_current);
        //铃声音量
        int ring_max = manager.getStreamMaxVolume(AudioManager.STREAM_RING);
        ring_current = manager.getStreamVolume(AudioManager.STREAM_RING);
        seekBar_ling.setMax(ring_max);
        seekBar_ling.setProgress(ring_current);
        txt_voice_ring.setText("铃声音量："+ring_current);
        //媒体音量
        int music_max = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        music_current = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar_music.setMax(music_max);
        seekBar_music.setProgress(music_current);
        txt_voice_music.setText("音乐音量："+music_current);
        //媒体音量
        int alarm_max = manager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        alerm_current = manager.getStreamVolume(AudioManager.STREAM_ALARM);
        seekBar_message.setMax(alarm_max);
        seekBar_message.setProgress(alerm_current);
        txt_voice_message.setText("提示音量："+alerm_current);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        //第三个flags是一些附加参数,只介绍两个常用的
        //FLAG_PLAY_SOUND 调整音量时播放声音
        //FLAG_SHOW_UI 调整时显示音量条,就是按音量键出现的那个
        //0表示什么也没有
        if (seekBar == seekBar_speak){
            manager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,i,AudioManager.FLAG_PLAY_SOUND);
            txt_voice_speak.setText("通话音量 ："+i);
        }else if (seekBar == seekBar_setting){
            manager.setStreamVolume(AudioManager.STREAM_SYSTEM,i,AudioManager.FLAG_PLAY_SOUND);
            txt_voice_setting.setText("系统音量："+i);
        }else if (seekBar == seekBar_ling){
            manager.setStreamVolume(AudioManager.STREAM_RING,i,AudioManager.FLAG_PLAY_SOUND);
            txt_voice_ring.setText("铃声音量："+i);
        }else if (seekBar == seekBar_music){
            manager.setStreamVolume(AudioManager.STREAM_MUSIC,i,AudioManager.FLAG_PLAY_SOUND);
            txt_voice_music.setText("音乐音量："+i);
        }else if (seekBar == seekBar_message){
            manager.setStreamVolume(AudioManager.STREAM_ALARM,i,AudioManager.FLAG_PLAY_SOUND);
            txt_voice_message.setText("提示音量："+i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null){
            manager = null;
        }
    }
}
