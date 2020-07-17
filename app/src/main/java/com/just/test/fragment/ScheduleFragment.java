package com.just.test.fragment;

import com.just.test.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScheduleFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		
		View scheduleFragment = inflater.inflate(R.layout.fragment_schedule, container ,false);
		return scheduleFragment;
	}

	public static ScheduleFragment newInstance() {
		ScheduleFragment fragment2 = new ScheduleFragment();
		return fragment2;
	}
}
