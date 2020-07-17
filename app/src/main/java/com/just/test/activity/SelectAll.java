package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.adapter.SelectAdapter;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;

/**
 * 全选
 */
public class SelectAll extends Activity {
	private TextView txt_num_select;//当前选中了多少
	private Button btn_selectAll;//全选
	private Button btn_selectNative;//反选
	private Button btn_cancleAll;//全部取消
	private ListView listview_select;
	private ArrayList<String> mList = new ArrayList<>();//listview数据集合
	private int checkNum;
	private SelectAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_all_layout);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "全选");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		txt_num_select = (TextView)findViewById(R.id.txt_num_select);
		btn_selectAll = (Button) findViewById(R.id.btn_selectAll);
		btn_selectNative = (Button) findViewById(R.id.btn_selectNative);
		btn_cancleAll = (Button) findViewById(R.id.btn_cancleAll);
		listview_select = (ListView) findViewById(R.id.listview_select);

		for (int i = 1; i <= 50; i++) {
			mList.add("平凡的世界第"+i+"集.rmvb");
		}

		adapter = new SelectAdapter(mList,this);
		listview_select.setAdapter(adapter);
		listview_select.setOverScrollMode(View.OVER_SCROLL_NEVER);//解决listview下拉或上拉时的透明背景阴影的问题

		//全选
		btn_selectAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < mList.size(); i++) {
					SelectAdapter.getIsSelected().put(i, true);
				}
				checkNum = mList.size();
				dataChanged();
			}
		});

		//反选
		btn_selectNative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < mList.size(); i++) {
					if (SelectAdapter.getIsSelected().get(i)) {
						SelectAdapter.getIsSelected().put(i, false);
						checkNum--;
					}else {
						SelectAdapter.getIsSelected().put(i, true);
						checkNum++;
					}
				}
				//通知刷新
				dataChanged();
			}
		});

		//全部取消
		btn_cancleAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < mList.size(); i++) {
					if (SelectAdapter.getIsSelected().get(i)) {
						SelectAdapter.getIsSelected().put(i, false);
						checkNum--;
					}
				}
				dataChanged();
			}
		});

		//各个点击
		listview_select.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				SelectAdapter.ViewHolder holder = (com.just.test.adapter.SelectAdapter.ViewHolder) view.getTag();
				//改变checkbox的状态
				holder.cb.toggle();
				//将选中状况记录下来
				SelectAdapter.getIsSelected().put(position, holder.cb.isChecked());
				//调整选定条目
				if (holder.cb.isChecked()) {
					checkNum++;
				}else {
					checkNum--;
				}

				txt_num_select.setText("已选中"+checkNum+"项");
			}
		});
	}

	//通知和刷新listview
	private void dataChanged() {
		adapter.notifyDataSetChanged();//通知listview刷新
		txt_num_select.setText("已选中"+checkNum+"项");
	}
}
