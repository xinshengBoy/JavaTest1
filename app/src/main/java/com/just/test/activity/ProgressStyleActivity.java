package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.just.test.R;
import com.just.test.widget.CBProgressBar;
import com.just.test.widget.MyActionBar;

/**
 * 进度条显示样式
 */
public class ProgressStyleActivity extends Activity {
	private ProgressBar progress_style1, progress_style2;
	private CBProgressBar my_progress1, my_progress2, my_progress3;
	private Button btn_startProgress, btn_download;
	private static final int NO_FINISH = 0;
	private static final int FINISH = 1;
	private static final int UPDATE_PROGRESS = 2;
	private int count = 0;
	boolean isDownloading;
	boolean stop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress_style_layout);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "进度条显示样式");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		progress_style1 = (ProgressBar) findViewById(R.id.progress_style1);
		progress_style2 = (ProgressBar) findViewById(R.id.progress_style2);
		btn_startProgress = (Button) findViewById(R.id.btn_startProgress);

		my_progress1 = (CBProgressBar) findViewById(R.id.my_progress1);
		my_progress2 = (CBProgressBar) findViewById(R.id.my_progress2);
		my_progress3 = (CBProgressBar) findViewById(R.id.my_progress3);
		btn_download = (Button) findViewById(R.id.btn_download);

		my_progress1.setMax(100);
		my_progress2.setMax(100);
		my_progress3.setMax(100);
		btn_download.setText("下载");
		btn_download.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (!isDownloading) {
					stop = false;
					isDownloading = true;
					btn_download.setText("停止");
					downloading(my_progress1);
					downloading(my_progress2);
					downloading(my_progress3);
				} else {
					isDownloading = false;
					stop = true;
					btn_download.setText("下载");
				}
			}
		});
		// 设置进度条是否自动运转
		progress_style1.setIndeterminate(false);
		progress_style2.setIndeterminate(false);

		btn_startProgress.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				progress_style1.setVisibility(View.VISIBLE);
				progress_style2.setVisibility(View.VISIBLE);
				// 设置最大值
				progress_style1.setMax(100);
				progress_style2.setMax(100);
				// 设置当前值
				progress_style1.setProgress(0);
				progress_style2.setProgress(0);
				// 通过线程来改变progressbar的值
				new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < 10; i++) {
							try {
								count = (i + 1) * 10;
								Thread.sleep(1000);

								if (i == 9) {
									Message msg = new Message();
									msg.what = FINISH;
									handler.sendMessage(msg);
									break;
								} else {
									Message msg = new Message();
									msg.what = NO_FINISH;
									handler.sendMessage(msg);
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		});

	}

	private void downloading(CBProgressBar cbProgress) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				int progress = 0;
				while (!stop) {
					if (progress >= 100) {
						break;
					}
					Message msg = handler.obtainMessage();
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					progress += 1;
					msg.what = UPDATE_PROGRESS;
					msg.arg1 = progress;
					msg.sendToTarget();
				}
				progress = 0;
			}
		}).start();

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case FINISH:
					progress_style1.setVisibility(View.GONE);
					progress_style2.setVisibility(View.GONE);
					count = 0;
					break;
				case NO_FINISH:
					if (!Thread.currentThread().isInterrupted()) {
						progress_style1.setProgress(count);
						progress_style2.setProgress(count);
						// 设置标题栏中的进度条的值
						setProgress(count * 100);
						setSecondaryProgress(count * 100);
					}
					break;
				case UPDATE_PROGRESS:
					my_progress1.setProgress(msg.arg1);
					my_progress2.setProgress(msg.arg1);
					my_progress3.setProgress(msg.arg1);
					if (msg.arg1 == 100) {
						isDownloading = false;
						btn_download.setText("下载");
					}
					break;
			}

			super.handleMessage(msg);

		}
	};
}
