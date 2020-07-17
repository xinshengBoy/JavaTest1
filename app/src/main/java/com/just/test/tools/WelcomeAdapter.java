package com.just.test.tools;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class WelcomeAdapter extends PagerAdapter{

	private ArrayList<View> views;

	public WelcomeAdapter(ArrayList<View> views) {
		this.views = views;
	}
	@Override
	public int getCount() {
		return views == null ? 0 : views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	
	@Override
	public Object instantiateItem(View view, int position) {
		((ViewPager)view).addView(views.get(position),0);
		return views.get(position);
	}
	
	@Override
	public void destroyItem(View view, int position, Object object) {
		((ViewPager)view).removeView(views.get(position));
	}

}
