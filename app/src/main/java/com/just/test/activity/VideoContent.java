package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.just.test.R;

/**
 * 视频播放
 * 带uri参数
 * @author user
 *
 */
public class VideoContent extends Activity {

	private VideoView video_videoContent;
	private String docVideo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_videocontent);

		Intent intent = getIntent();
		docVideo = intent.getStringExtra("docVideo");
		Uri uri = Uri.parse(docVideo);

		video_videoContent = (VideoView) findViewById(R.id.video_videoContent);
		//加入视频进度控制条
		video_videoContent.setMediaController(new MediaController(VideoContent.this));
		video_videoContent.setVideoURI(uri);
		video_videoContent.requestFocus();
		video_videoContent.start();

		video_videoContent.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();//播放
				mp.setLooping(true);
			}
		});

		video_videoContent.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				Uri uri = Uri.parse(docVideo);
				video_videoContent.setVideoURI(uri);
				video_videoContent.start();

			}
		});
	}

}
