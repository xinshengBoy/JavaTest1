package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * 模仿iPhone手机滑动解锁文字的样式
 * http://facebook.github.io/shimmer-android/
 * compile 'com.facebook.shimmer:shimmer:0.1.0@aar'
 * 开源的主要优势是：可以使图片也有滑动的效果，目前自定义的是继承textview，所以不能对图片有作用
 * @author user
 */
public class IPhoneText extends Activity {

	private ShimmerFrameLayout shimmerframe_layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iphone_text_layout);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "IPhone解锁文字");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		shimmerframe_layout = (ShimmerFrameLayout) findViewById(R.id.shimmerframe_layout);
		shimmerframe_layout.setDuration(2000);//设置每次运行的时间长短
		shimmerframe_layout.setBaseAlpha(0.2f);//设置基本的透明度
		shimmerframe_layout.setTilt(0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		shimmerframe_layout.startShimmerAnimation();
	}

	@Override
	protected void onPause() {
		super.onPause();
		shimmerframe_layout.stopShimmerAnimation();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		shimmerframe_layout.stopShimmerAnimation();
	}
}
