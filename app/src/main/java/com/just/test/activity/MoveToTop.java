package com.just.test.activity;

import java.util.LinkedList;
import java.util.List;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.JSONDateKey;
import com.just.test.tools.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MoveToTop extends Activity {

	private ListView listview_moveToTop;
	private LinkedList<Bundle> mItemList;
	private int listPos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.move_to_top_layout);

		initDate();
		listview_moveToTop = (ListView) findViewById(R.id.listview_moveToTop);
		listview_moveToTop.setOverScrollMode(View.OVER_SCROLL_NEVER);//解决listview下拉或上拉时的透明背景阴影的问题
		listview_moveToTop.setCacheColorHint(Color.TRANSPARENT);//防止上下拖动时背景会变成黑色的问题
		MoveTopAdapter adapter = new MoveTopAdapter(MoveToTop.this, mItemList, R.layout.movetop_item);
		listview_moveToTop.setAdapter(adapter);
		listview_moveToTop.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(mItemList.get(position).getString(JSONDateKey.DOCURL)));
				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
				startActivity(intent);
			}
		});
		listview_moveToTop.setSelection(listPos);//每次返回时可以回到原始位置
	}

	private void initDate() {
		mItemList = new LinkedList<Bundle>();
		for (int i = 1; i < 30; i++) {
			Bundle bundle = new Bundle();
			bundle.putString(JSONDateKey.TOPNAME, "第"+i+"天跑了"+i+"公里");
			bundle.putString(JSONDateKey.TOPTIME, "2016-04-"+i);
			bundle.putString(JSONDateKey.DOCURL, "www.baidu.com");
			mItemList.add(bundle);
		}
	}

	private OnScrollListener ScrollLis = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				listPos = listview_moveToTop.getFirstVisiblePosition();
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
							 int visibleItemCount, int totalItemCount) {
		}
	};

	public class MoveTopAdapter extends CommonAdapter<Bundle>{

		public MoveTopAdapter(Context context, List<Bundle> mDatas,
							  int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, Bundle item) {
			TextView txt_moveToTopName = helper.getView(R.id.txt_moveToTopName);
			TextView txt_moveToTopTime = helper.getView(R.id.txt_moveToTopTime);

			txt_moveToTopName.setText(item.getString(JSONDateKey.TOPNAME));
			txt_moveToTopTime.setText(item.getString(JSONDateKey.TOPTIME));
		}

	}
}
