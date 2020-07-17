package com.just.test.fragment;

import com.just.test.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		
		View settingFragment = inflater.inflate(R.layout.fragment_setting, container ,false);
		return settingFragment;
	}

	public static SettingFragment newInstance() {
		SettingFragment fragment2 = new SettingFragment();
		return fragment2;
	}
}
