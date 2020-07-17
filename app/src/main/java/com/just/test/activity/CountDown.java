package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.BadgeView;
import com.just.test.widget.CircleTextProgressBar;
import com.just.test.widget.MyActionBar;

/**
 * 倒计时和圆形进度条
 * @author user
 *
 */
public class CountDown extends Activity {
	private Button btn_countDown;
	private TimeCount timeCount;
	/**
     * 默认类型。
     */
    private CircleTextProgressBar mTvDefault;
    /**
     * 五个字的。
     */
    private CircleTextProgressBar mTvFive;
    /**
     * 圆心点击变色。
     */
    private CircleTextProgressBar mTvCicleColor;

    /**
     * 顺数进度条。
     */
    private CircleTextProgressBar mTvCount;
    /**
     * 宽进度条。
     */
    private CircleTextProgressBar mTvWide;
    /**
     * 窄进度条。
     */
    private CircleTextProgressBar mTvNarrow;
    /**
     * 红色进度条。
     */
    private CircleTextProgressBar mTvRedPro;
    /**
     * 红色边框。
     */
    private CircleTextProgressBar mTvRedFrame;
    /**
     * 红色圆心。
     */
    private CircleTextProgressBar mTvRedCircle;
    /**
     * 跳过。
     */
    private CircleTextProgressBar mTvSkip;
    /**
     * 更新进度条文字。
     */
    private CircleTextProgressBar mTvProgressBar1, mTvProgressBar2;
    private Button btn_restart;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);
        //// TODO: 2016/12/21 actionbar 
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "倒计时和圆形进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        timeCount = new TimeCount(60000, 1000);
        btn_countDown = (Button) findViewById(R.id.btn_countDown);
        btn_countDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	timeCount.start();
            }
        });

        BadgeView badgeView = new BadgeView(CountDown.this);
        badgeView.setTargetView(btn_countDown);
        badgeView.setBadgeCount(5);
        badgeView.setBadgeMargin(1);
        badgeView.setBadgeGravity(Gravity.LEFT);


        btn_restart = (Button) findViewById(R.id.btn_restart);
        mTvDefault = (CircleTextProgressBar) findViewById(R.id.tv_default);
        mTvFive = (CircleTextProgressBar) findViewById(R.id.tv_five_text);
        mTvCicleColor = (CircleTextProgressBar) findViewById(R.id.tv_red_circle_color);

        // 和系统进度条一样，由0-100。
        mTvCount = (CircleTextProgressBar) findViewById(R.id.tv_count);
        mTvCount.setProgressType(CircleTextProgressBar.ProgressType.COUNT);

        // 宽进度条。
        mTvWide = (CircleTextProgressBar) findViewById(R.id.tv_five_wide);
        mTvWide.setProgressLineWidth(30);//写入宽度。
        mTvWide.setTimeMillis(3500);// 把倒计时时间改长一点。

        // 窄进度条。
        mTvNarrow = (CircleTextProgressBar) findViewById(R.id.tv_five_narrow);
        mTvNarrow.setProgressLineWidth(3);// 写入宽度。

        // 红色进度条。
        mTvRedPro = (CircleTextProgressBar) findViewById(R.id.tv_red_progress);
        mTvRedPro.setProgressColor(Color.RED);
        
        // 红色边框进度条。
        mTvRedFrame = (CircleTextProgressBar) findViewById(R.id.tv_red_frame);
        mTvRedFrame.setOutLineColor(Color.RED);

        // 红色圆心进度条。
        mTvRedCircle = (CircleTextProgressBar) findViewById(R.id.tv_red_circle);
        mTvRedCircle.setInCircleColor(Color.RED);

        mTvProgressBar1 = (CircleTextProgressBar) findViewById(R.id.tv_red_progress_text1);
        mTvProgressBar1.setCountdownProgressListener(1, progressListener);
        mTvProgressBar1.setProgressType(CircleTextProgressBar.ProgressType.COUNT);

        mTvProgressBar2 = (CircleTextProgressBar) findViewById(R.id.tv_red_progress_text2);
        mTvProgressBar2.setCountdownProgressListener(2, progressListener);


        // 模拟网易新闻跳过。
        mTvSkip = (CircleTextProgressBar) findViewById(R.id.tv_red_skip);
        mTvSkip.setOutLineColor(Color.TRANSPARENT);
        mTvSkip.setInCircleColor(Color.parseColor("#AAC6C6C6"));
        mTvSkip.setProgressColor(Color.DKGRAY);
        mTvSkip.setProgressLineWidth(3);
        
        btn_restart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTvDefault.reStart();
	            mTvFive.reStart();
	            mTvCicleColor.reStart();
	            mTvCount.reStart();
	            mTvWide.reStart();
	            mTvNarrow.reStart();
	            mTvRedPro.reStart();
	            mTvRedFrame.reStart();
	            mTvRedCircle.reStart();
	            mTvProgressBar1.reStart();
	            mTvProgressBar2.reStart();
	            mTvSkip.reStart();
			}
		});

    }
	/**
	 * 计时器
	 * @author user
	 *
	 */
	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// 总时长，间隔时间
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// 计时过程中所触发
			btn_countDown.setClickable(false);//不可点击
			btn_countDown.setText(millisUntilFinished / 1000 + "秒后重新发送");
		}

		@Override
		public void onFinish() {
			//计时完毕时触发
			btn_countDown.setText("获取验证码");
			btn_countDown.setClickable(true);//可点击
		}
		
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
    }

    private CircleTextProgressBar.OnCountdownProgressListener progressListener = new CircleTextProgressBar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {
            if (what == 1) {
                mTvProgressBar1.setText(progress + "%");
            } else if (what == 2) {
                mTvProgressBar2.setText(progress + "%");
            }
            // 比如在首页，这里可以判断进度，进度到了100或者0的时候，你可以做跳过操作。
        }
    };
}
