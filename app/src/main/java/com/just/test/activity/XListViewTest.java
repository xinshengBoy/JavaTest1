package com.just.test.activity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.tools.XListView;
import com.just.test.tools.XListView.IXListViewListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class XListViewTest extends Activity implements IXListViewListener{

	private XListView xlistview_test;
	private List<Bundle> mList = new ArrayList<Bundle>();
	private XListviewAdapter adapter;
	private int size = 40;
	private boolean isRefreshing = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_xlistview);

		initData();
		xlistview_test = (XListView)findViewById(R.id.xlistview_test);
		xlistview_test.setPullRefreshEnable(true);//下拉刷新
		xlistview_test.setPullLoadEnable(true);//上拉刷新
		adapter = new XListviewAdapter(XListViewTest.this, mList, R.layout.layout_xlistview_item);
		xlistview_test.setAdapter(adapter);

		xlistview_test.setXListViewListener(this);
		xlistview_test.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(XListViewTest.this, "点击了第"+position+"个", Toast.LENGTH_LONG).show();
			}
		});
	}

	private void initData() {
		for (int i = 1; i <= size; i++) {
			Bundle bundle = new Bundle();
			bundle.putString("key", "我是第"+i+"名");
			mList.add(bundle);
		}
	}

	private class XListviewAdapter extends CommonAdapter<Bundle> {

		public XListviewAdapter(Context context, List<Bundle> mDatas,
								int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, Bundle item) {
			TextView txt_xlistview_item = helper.getView(R.id.txt_xlistview_item);
			txt_xlistview_item.setText(item.getString("key"));
		}

		private void update(List<Bundle> list) {//刷新视图的方法
			mDatas = list;
			notifyDataSetChanged();
		}

	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mList.clear();//先清除数据
				size = size - 1;//重新给list赋值
				initData();
				adapter.update(mList);//更新adapter
				isRefreshing = true;
				onLoad(isRefreshing);//停止加载刷新
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mList.clear();//先清除数据
				size = size + 1;//重新给list赋值
				initData();
				adapter.update(mList);//更新adapter
				isRefreshing = false;
				onLoad(isRefreshing);//停止加载刷新
			}
		}, 2000);
	}
	/**
	 * 获得数据后调用此方法，不然刷新会一直进行下去，根本停不下来
	 */
	private void onLoad(boolean isRefreshing) {
		xlistview_test.stopRefresh();//停止刷新
		xlistview_test.stopLoadMore();//停止加载更多

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		String curTime = format.format(curDate);
		if (isRefreshing) {
			xlistview_test.setRefreshTime(curTime);//设置刷新时间
		}else {
			xlistview_test.setLoadTime(curTime);//设置刷新时间
		}
	}
	private Handler mHandler = new Handler(){

	};
}
