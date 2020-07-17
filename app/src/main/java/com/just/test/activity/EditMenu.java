package com.just.test.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.just.test.R;
import com.just.test.widget.DragGridView;
import com.just.test.widget.DragGridView.OnChanageListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class EditMenu extends Activity {
	private List<HashMap<String, Object>> dataSourceList = new ArrayList<HashMap<String, Object>>();
	private DragGridView dragGridView;
	private String[] item = {"首页","公告","动态","日历","行情","数据","监管","投教","新股","通知"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_editmenu);

		dragGridView = (DragGridView)findViewById(R.id.dragGridView);
		for (int i = 0; i < item.length; i++) {
			HashMap<String, Object> itemHashMap = new HashMap<String, Object>();
			itemHashMap.put("item_text", item[i]);
			dataSourceList.add(itemHashMap);
		}
		final SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, dataSourceList,
				R.layout.grid_item, new String[] {"item_text" },
				new int[] {R.id.item_text });
		dragGridView.setAdapter(mSimpleAdapter);

		dragGridView.setOnChangeListener(new OnChanageListener() {
			@Override
			public void onChange(int from, int to) {
				HashMap<String, Object> temp = dataSourceList.get(from);
				//直接交互item
//				dataSourceList.set(from, dataSourceList.get(to));
//				dataSourceList.set(to, temp);
				//这里的处理需要注意下
				if(from < to){
					for(int i=from; i<to; i++){
						Collections.swap(dataSourceList, i, i+1);
					}
				}else if(from > to){
					for(int i=from; i>to; i--){
						Collections.swap(dataSourceList, i, i-1);
					}
				}
				dataSourceList.set(to, temp);
				mSimpleAdapter.notifyDataSetChanged();
			}
		});
	}
}
