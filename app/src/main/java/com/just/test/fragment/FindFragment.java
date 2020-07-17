package com.just.test.fragment;

import com.just.test.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FindFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		
		View findFragment = inflater.inflate(R.layout.fragment_find, container ,false);
		return findFragment;
	}

	public static FindFragment newInstance() {
		FindFragment fragment2 = new FindFragment();
		return fragment2;
	}
}
