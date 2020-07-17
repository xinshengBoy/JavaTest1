package com.just.test.activity;

import java.util.ArrayList;
import java.util.List;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * google刷新
 * @author user
 *
 */
public class SwipeRefresh extends Activity implements SwipeRefreshLayout.OnRefreshListener{

	private ListView list_swipeRefresh;
	private SwipeRefreshLayout swipeLayout;
	private List<Bundle> mList;
	private SwipeAdapter adapter;
	private int max = 20;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_swipe_refresh);

		initDate();
		list_swipeRefresh = (ListView)findViewById(R.id.list_swipeRefresh);
		swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipeLayout);

		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorSchemeResources(R.color.swipe_color_4,
				R.color.swipe_color_2,
				R.color.swipe_color_3,
				R.color.swipe_color_4);

		adapter = new SwipeAdapter(SwipeRefresh.this, mList, R.layout.layout_swiperefresh_item);
		list_swipeRefresh.setAdapter(adapter);
	}

	private void initDate() {
		mList = new ArrayList<Bundle>();
		for (int i = 1; i <= max; i++) {
			Bundle bundle = new Bundle();
			bundle.putString("user_name", "第"+i+"集");

			mList.add(bundle);
		}
	}

	class SwipeAdapter extends CommonAdapter<Bundle>{

		public SwipeAdapter(Context context, List<Bundle> mDatas,int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, Bundle item) {
			TextView txt_swipeRefresh = helper.getView(R.id.txt_swipeRefresh);
			txt_swipeRefresh.setText(item.getString("user_name"));
		}

	}

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				for (int i = max+1; i <= max+2; i++) {//添加数据
					Bundle bundle = new Bundle();
					bundle.putString("user_name", "第"+i+"集");
					mList.add(bundle);
				}
				max += 2;
				swipeLayout.setRefreshing(false);//停止刷新
				adapter.notifyDataSetChanged();
			}
		};
	};
	@Override
	public void onRefresh() {
		handler.sendEmptyMessageDelayed(0, 1000);
	}

}
