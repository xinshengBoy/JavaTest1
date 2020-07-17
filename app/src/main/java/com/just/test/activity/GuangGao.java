package com.just.test.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.adapter.FragmentAdapter;
import com.just.test.widget.MyActionBar;

import java.util.Timer;
import java.util.TimerTask;
/**
 * 广告展示栏效果
 * ViewPager无线循环和手动左右滑动，类似于广告栏效果
 * @author user
 */
public class GuangGao extends FragmentActivity {
	private FragmentAdapter mAdapter;
	private ViewPager viewpager_guanggao;
	private Timer mTimer = new Timer();
	private TimerTask mTask ;
	private int pageIndex = 1;
	private boolean isTaskRun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_guanggao);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "广告展示栏效果");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		// 设置ViewPager
		viewpager_guanggao = (ViewPager) findViewById(R.id.viewpager_guanggao);
		mAdapter = new FragmentAdapter(getSupportFragmentManager());
		viewpager_guanggao.setAdapter(mAdapter);
		viewpager_guanggao.setOnPageChangeListener(new OnPageChangeListener() {

			/* 更新手动滑动时的位置 */
			@Override
			public void onPageSelected(int position) {
				pageIndex = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			/* state: 0空闲，1是滑行中，2加载完毕 */
			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == 0 && !isTaskRun) {
					setCurrentItem();
					startTask();
				} else if (state == 1 && isTaskRun)
					stopTask();
			}
		});
	}

	/**
	 * 开启定时任务
	 */
	private void startTask() {
		isTaskRun = true;
		mTask = new TimerTask() {
			@Override
			public void run() {
				pageIndex++;
				mHandler.sendEmptyMessage(0);
			}
		};
		mTimer.schedule(mTask, 2 * 1000, 2 * 1000);// 这里设置自动切换的时间，单位是毫秒，2*1000表示2秒
	}

	// 处理EmptyMessage(0)
	@SuppressLint("HandlerLeak")
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			setCurrentItem();
		}
	};

	/**
	 * 处理Page的切换逻辑
	 */
	private void setCurrentItem() {
		if (pageIndex == 0) {
			pageIndex = 3;
		} else if (pageIndex == 4) {
			pageIndex = 1;
		}
		viewpager_guanggao.setCurrentItem(pageIndex, false);// 取消动画
	}

	/**
	 * 停止定时任务
	 */
	private void stopTask() {
		isTaskRun = false;
		mTimer.cancel();
	}

	public void onResume() {
		super.onResume();
		setCurrentItem();
		startTask();
	}

	@Override
	public void onPause() {
		super.onPause();
		stopTask();
	}
}
