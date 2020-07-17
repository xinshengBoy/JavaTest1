package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;

import com.just.test.R;

public class TopStopListViewActivity extends Activity {

	private LinearLayout invis;
	private ListView listview_topstop;
	private String [] strs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_topstop);

		invis = (LinearLayout) findViewById(R.id.invis);
		listview_topstop = (ListView) findViewById(R.id.listview_topstop);

		strs = new String[20];
		for (int i = 0; i < strs.length; i++) {
			strs[i] = "data-----" + i;
		}

		//第一个头部
		TextView header1 = new TextView(this);
		header1.setText("这是头部内容哟");
		header1.setTextSize(20);
		header1.setTextColor(Color.BLACK);
		header1.setGravity(Gravity.CENTER);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 300);
		header1.setLayoutParams(params);
		listview_topstop.addHeaderView(header1);//添加头部
		//第二个头部
		TextView header2 = new TextView(this);
		header2.setText("悬浮部分");
		header2.setTextSize(20);
		header2.setTextColor(Color.BLACK);
		header2.setGravity(Gravity.CENTER);
		header2.setBackgroundColor(0xFFccedc7);

		LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT, 150);
		header2.setLayoutParams(params2);
		listview_topstop.addHeaderView(header2);//ListView条目中的悬浮部分 添加到头部

		listview_topstop.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strs));
		listview_topstop.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= 1) {
					invis.setVisibility(View.VISIBLE);
				} else {

					invis.setVisibility(View.GONE);
				}
			}
		});
	}
}
