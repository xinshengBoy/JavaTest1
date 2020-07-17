package com.just.test.activity;

import java.util.ArrayList;
import java.util.List;

import com.just.test.R;
import com.just.test.activity.MainActivity;
import com.just.test.adapter.MoveAndTopAdapter;
import com.just.test.adapter.TopAndMoveListView;
import com.just.test.bean.OptionalBean;

import android.app.Activity;
import android.os.Bundle;

public class MoveAndTopActivity extends Activity {
	private TopAndMoveListView dragListView;
	private MoveAndTopAdapter adapter = null;
	public static MainActivity ma;
	List<OptionalBean> data = new ArrayList<OptionalBean>();
	public int checkNum; // 记录选中的条目数量
	String Cnames[] = { "阿里巴巴", "百度", "微博", "京东" };
	String Enames[] = { "BABA", "BIDU", "WB", "JD" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_moveandtop);
		data = initDate();
		initView();
	}

	private void initView() {
		dragListView = (TopAndMoveListView) findViewById(R.id.other_drag_list);
		adapter = new MoveAndTopAdapter(this, data);
		dragListView.setAdapter(adapter);
	}
	// 初始化数据
	private List<OptionalBean> initDate() {

		List<OptionalBean> stocks = new ArrayList<OptionalBean>();
		for (int i = 0; i < Cnames.length; i++) {
			OptionalBean bean = new OptionalBean();
			bean.setCH_Name(Cnames[i]);
			bean.setEN_Name(Enames[i]);
			stocks.add(bean);
		}
		return stocks;
	}
}
