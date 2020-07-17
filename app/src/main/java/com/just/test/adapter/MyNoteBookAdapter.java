package com.just.test.adapter;

import java.util.ArrayList;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.JSONDateKey;
import com.just.test.tools.ViewHolder;


import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;


/*
 * 用来填充ListView,这次成功把这个类从主页面分离，只要传过来两个值，inflater ,和数据ArrayList<>;
 */
public class MyNoteBookAdapter extends CommonAdapter<Bundle>{

	public MyNoteBookAdapter(Context context, ArrayList<Bundle> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Bundle item) {
		TextView txt_notebook_title = helper.getView(R.id.txt_notebook_title);
		TextView txt_notebook_time = helper.getView(R.id.txt_notebook_time);
		
		txt_notebook_title.setText(item.getString(JSONDateKey.NOTEBOOKTITLE));
		txt_notebook_time.setText(item.getString(JSONDateKey.NOTEBOOKTIME));
	}
	
}
