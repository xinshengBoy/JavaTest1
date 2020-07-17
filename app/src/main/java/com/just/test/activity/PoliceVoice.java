package com.just.test.activity;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.just.test.R;

/**
 * 警车音效
 * @author user
 *
 */
public class PoliceVoice extends Activity implements OnClickListener{

	private Button btn_police1Start,btn_police1Stop,btn_police2Start,btn_police2Stop,btn_police3Start,btn_police3Stop,
			btn_police4Start,btn_police4Stop,btn_police5Start,btn_police5Stop,btn_loop,btn_noloop;
	private SoundPool sp;
	private HashMap<Integer, Integer> map;
	private int number = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_police_voice);
		initSound();
		initView();

	}

	private void initSound() {
		sp = new SoundPool(10, 0, 5);//音频流的容量，质量等
		map = new HashMap<Integer, Integer>();
		map.put(1, sp.load(this, R.raw.jingche1,1));
		map.put(2, sp.load(this, R.raw.jingche2,1));
		map.put(3, sp.load(this, R.raw.jingche3,1));
		map.put(4, sp.load(this, R.raw.jiuhuche,1));
		map.put(5, sp.load(this, R.raw.wife,1));
		map.put(6, sp.load(this, R.raw.xindian,1));
		map.put(7, sp.load(this, R.raw.xindian2,1));
		map.put(8, sp.load(this, R.raw.jingche4,1));
	}

	private void initView() {
		btn_police1Start = (Button)findViewById(R.id.btn_police1Start);
		btn_police1Stop = (Button)findViewById(R.id.btn_police1Stop);

		btn_police2Start = (Button)findViewById(R.id.btn_police2Start);
		btn_police2Stop = (Button)findViewById(R.id.btn_police2Stop);

		btn_police3Start = (Button)findViewById(R.id.btn_police3Start);
		btn_police3Stop = (Button)findViewById(R.id.btn_police3Stop);

		btn_police4Start = (Button)findViewById(R.id.btn_police4Start);
		btn_police4Stop = (Button)findViewById(R.id.btn_police4Stop);

		btn_police5Start = (Button)findViewById(R.id.btn_police5Start);
		btn_police5Stop = (Button)findViewById(R.id.btn_police5Stop);

		btn_loop = (Button)findViewById(R.id.btn_loop);
		btn_noloop = (Button)findViewById(R.id.btn_noloop);

		btn_police1Start.setOnClickListener(this);
		btn_police1Stop.setOnClickListener(this);
		btn_police2Start.setOnClickListener(this);
		btn_police2Stop.setOnClickListener(this);
		btn_police3Start.setOnClickListener(this);
		btn_police3Stop.setOnClickListener(this);
		btn_police4Start.setOnClickListener(this);
		btn_police4Stop.setOnClickListener(this);
		btn_police5Start.setOnClickListener(this);
		btn_police5Stop.setOnClickListener(this);
		btn_loop.setOnClickListener(this);
		btn_noloop.setOnClickListener(this);
	}

	private void PlaySound(int sound) {
		AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		float maxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float currentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float ratioVolumn = currentVolumn / maxVolumn;
		//该方法的第一个参数指定播放哪个声音；leftVolume、rightVolume指定左、右的音量：
		//priority指定播放声音的优先级，数值越大，优先级越高；loop指定是否循环，0为不循环，-1为循环；
		//rate指定播放的比率，数值可从0.5到2， 1为正常比率。
		sp.play(map.get(sound), ratioVolumn, ratioVolumn, 1, number, 1f);
	}
	@Override
	public void onClick(View v) {
		if (v == btn_police1Start) {
			PlaySound(1);
		}else if (v == btn_police1Stop) {
			sp.pause(map.get(1));
		}else if (v == btn_police2Start) {
			PlaySound(2);
		}else if (v == btn_police2Stop) {
			sp.pause(map.get(2));
		}else if (v == btn_police3Start) {
			PlaySound(3);
		}else if (v == btn_police3Stop) {
			sp.pause(map.get(3));
		}else if (v == btn_police4Start) {
			PlaySound(4);
		}else if (v == btn_police4Stop) {
			sp.pause(map.get(4));
		}else if (v == btn_police5Start) {
			PlaySound(5);
		}else if (v == btn_police5Stop) {
			sp.pause(map.get(5));
		}else if (v == btn_loop) {
			number = -1;//循环
		}else if (v == btn_noloop) {
			number = 0;//不循环
		}
	}
}
