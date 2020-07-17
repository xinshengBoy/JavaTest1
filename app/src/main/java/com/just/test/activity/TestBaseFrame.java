package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.just.test.R;

public class TestBaseFrame extends FragmentActivity implements OnClickListener{

	private TabWidget tabwight_baseframe;
	private ViewPager viewpager_baseframe;
	private int [] tabBg = {Color.RED,Color.GREEN,Color.BLUE};
	private static int [] fragmentBgImage = {R.drawable.bg02,R.drawable.bg03,R.drawable.bg04};
	private String [] tabName = {"我的","你的","他的"};
	private TextView [] mTextTabs = new TextView[tabName.length];
	private static String TABNAME = "tabname";
	private static String TABPOSITION = "tanposition";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_baseframe);

		tabwight_baseframe = (TabWidget)findViewById(R.id.tabwight_baseframe);
		viewpager_baseframe = (ViewPager)findViewById(R.id.viewpager_baseframe);

		tabwight_baseframe.setStripEnabled(false);
		//第一个tab
		for (int i = 0; i < tabName.length; i++) {
			mTextTabs[i] = new TextView(this);
			mTextTabs[i].setGravity(Gravity.CENTER);
			mTextTabs[i].setTextSize(22);
			mTextTabs[i].setPadding(0, 5, 0, 5);
			mTextTabs[i].setFocusable(true);
			mTextTabs[i].setText(tabName[i]);
			mTextTabs[i].setBackgroundColor(tabBg[i]);
			tabwight_baseframe.addView(mTextTabs[i]);

			mTextTabs[i].setOnClickListener(this);
		}

		MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
		viewpager_baseframe.setAdapter(adapter);
		viewpager_baseframe.setOnPageChangeListener(mPageChangeListener);
		viewpager_baseframe.setCurrentItem(1);//设置第一个显示的tab
		mTextTabs[1].setBackgroundResource(R.drawable.click);
	}
	@Override
	public void onClick(View v) {
		if (v == mTextTabs[0]) {
			mTextTabs[0].setBackgroundResource(R.drawable.click);
			viewpager_baseframe.setCurrentItem(0);
		}else if (v == mTextTabs[1]) {
			mTextTabs[1].setBackgroundResource(R.drawable.click);
			viewpager_baseframe.setCurrentItem(1);
		}else if (v == mTextTabs[2]) {
			mTextTabs[2].setBackgroundResource(R.drawable.click);
			viewpager_baseframe.setCurrentItem(2);
		}
	}

	/**
	 * viewpager页面改变监听
	 */
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			mTextTabs[position].setBackgroundResource(R.drawable.click);
			tabwight_baseframe.setCurrentTab(position);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	/**
	 * viewpager适配器
	 * @author user
	 *
	 */
	private class MyPagerAdapter extends FragmentStatePagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return MyFragment.create(tabName[position], position);
		}

		@Override
		public int getCount() {
			return tabName.length;
		}

	}

	/**
	 * fragment
	 */
	public static class MyFragment extends Fragment {
		public static MyFragment create(String tabName,int tabPosition) {
			MyFragment f = new MyFragment();
			Bundle b = new Bundle();
			b.putString(TABNAME, tabName);
			b.putInt(TABPOSITION,tabPosition);
			f.setArguments(b);

			return f;
		}

		@Override
		@Nullable
		public View onCreateView(LayoutInflater inflater,
								 @Nullable ViewGroup container,
								 @Nullable Bundle savedInstanceState) {
			Bundle bundle = getArguments();
			String tabName = bundle.getString(TABNAME);
			int position = bundle.getInt(TABPOSITION);
			View view = inflater.inflate(R.layout.layout_baseframe_adapter, null);
			view.setBackgroundResource(fragmentBgImage[position]);

			TextView txt_baseFragment = (TextView) view.findViewById(R.id.txt_baseFragment);
			txt_baseFragment.setText(tabName);
			txt_baseFragment.setTextSize(36);
			return view;
		}
	}
}
