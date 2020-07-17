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
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
/**
 * listview上拉加载更多
 * @author user
 *
 */
public class ListViewRefresh extends Activity implements OnScrollListener{

	private ListView listview_loadMore;
	private View loadMore;
	private List<Bundle> mList = new ArrayList<Bundle>();
	private ListViewRefreshAdapter adapter;
	private int count;
	private int lastItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_listview_refresh);

		listview_loadMore = (ListView)findViewById(R.id.listview_loadMore);
		loadMore = getLayoutInflater().inflate(R.layout.layout_listview_refresh_loadmore, null);

		initData();
		count = mList.size();
		adapter = new ListViewRefreshAdapter(ListViewRefresh.this, mList, R.layout.layout_listview_refresh_item);
		listview_loadMore.addFooterView(loadMore);////添加底部view，一定要在setAdapter之前添加，否则会报错。
		listview_loadMore.setAdapter(adapter);
		listview_loadMore.setOnScrollListener(this);
	}
	/**
	 * 初始化数据并赋值
	 */
	private void initData() {
		for (int i = 1; i <= 20; i++) {
			Bundle bundle = new Bundle();
			bundle.putString("test", "第"+i+"集");
			mList.add(bundle);
		}
	}
	/**
	 * 加载更多数据
	 */
	private void LoadMoreData() {
		count = adapter.getCount();
		for (int i = count; i < count+5; i++) {
			Bundle bundle = new Bundle();
			bundle.putString("test", "第"+i+"集");
			mList.add(bundle);
		}
		count = mList.size();
	}
	/**
	 * adapter
	 * @author user
	 *
	 */
	private class ListViewRefreshAdapter extends CommonAdapter<Bundle> {

		public ListViewRefreshAdapter(Context context, List<Bundle> mDatas,
									  int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, Bundle item) {
			TextView txt_loadMoreItem = helper.getView(R.id.txt_loadMoreItem);
			txt_loadMoreItem.setText(item.getString("test"));
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//下拉到空闲是，且最后一个item的数等于数据的总数时，进行更新
		if (lastItem == count && scrollState == this.SCROLL_STATE_IDLE) {
			loadMore.setVisibility(View.VISIBLE);
			handler.sendEmptyMessageDelayed(0, 3000);
		}
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
						 int visibleItemCount, int totalItemCount) {
		//减1是因为上面加了个addFooterView
		lastItem = firstVisibleItem + visibleItemCount - 1;
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0){
				LoadMoreData();
				adapter.notifyDataSetChanged();
				loadMore.setVisibility(View.GONE);
				if (count > 50) {
					listview_loadMore.removeFooterView(loadMore);
				}
			}
		};
	};
}
