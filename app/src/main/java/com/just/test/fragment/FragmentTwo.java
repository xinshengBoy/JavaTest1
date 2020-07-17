package com.just.test.fragment;

import com.just.test.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentTwo extends Fragment{

	ImageView imageview;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// 第一个参数是这个Fragment将要显示的界面布局,第二个参数是这个Fragment所属的Activity,第三个参数是决定此fragment是否附属于Activity
    	View view=inflater.inflate(R.layout.layout_guanggao_item, container, false);
    	imageview = (ImageView) view.findViewById(R.id.iv_guanggaolan);
    	imageview.setImageResource(R.drawable.friend);
    	view.setBackgroundColor(Color.YELLOW);
        return view;
    }

	public static FragmentTwo newInstance() {
		FragmentTwo fragment2 = new FragmentTwo();
		return fragment2;
	}
}
