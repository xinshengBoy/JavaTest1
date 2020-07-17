package com.just.test.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.just.test.fragment.FragmentOne;
import com.just.test.fragment.FragmentTwo;
import com.just.test.fragment.FragmentZero;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告栏效果适配器
 */
public class FragmentAdapter extends FragmentPagerAdapter {

	List<Fragment> fragments;
	/**
	 *  Fragment实际只有Zero、One、Two这三张
	 */
	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		fragments = new ArrayList<>();
		fragments.add(new FragmentTwo());
		fragments.add(new FragmentZero());
		fragments.add(new FragmentOne());
		fragments.add(new FragmentTwo());
		fragments.add(new FragmentZero());
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
}