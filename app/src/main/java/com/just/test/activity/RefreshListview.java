package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.JSONDateKey;
import com.just.test.tools.PullToRefreshListView;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import java.util.LinkedList;
import java.util.List;

/**
 * 上拉下拉刷新
 */
public class RefreshListview extends Activity {

	private LinkedList<Bundle> mItemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pulltorefresh);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "上拉下拉刷新");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		initData();
		PullToRefreshListView pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

		PullAdapter adapter = new PullAdapter(RefreshListview.this, mItemList, R.layout.item_pull);
		pull_refresh_list.setAdapter(adapter);
		pull_refresh_list.setOverScrollMode(View.OVER_SCROLL_NEVER);//解决listview下拉或上拉时的透明背景阴影的问题
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(mItemList.get(position).getString(JSONDateKey.DOCURL)));
				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
				startActivity(intent);
			}
		});
	}

	private void initData() {
		mItemList = new LinkedList<>();
		for (int i = 0; i < 50; i++) {
			Bundle bundle = new Bundle();
			bundle.putString(JSONDateKey.DOCTITLE, "今天星期"+i);
			bundle.putString(JSONDateKey.CREATETIME, "2016-03-"+i);
			bundle.putString(JSONDateKey.DOCURL, "www.baidu.com");
			mItemList.add(bundle);
		}
	}

	class PullAdapter extends CommonAdapter<Bundle>{

		public PullAdapter(Context context, List<Bundle> mDatas,
						   int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, Bundle item) {
			TextView pull_title = helper.getView(R.id.pull_title);
			TextView pull_docUrl = helper.getView(R.id.pull_docUrl);
			TextView pull_createTime = helper.getView(R.id.pull_createTime);

			pull_title.setText(item.getString(JSONDateKey.DOCTITLE));
			pull_docUrl.setText(item.getString(JSONDateKey.DOCURL));
			pull_createTime.setText(item.getString(JSONDateKey.CREATETIME));
		}

	}

}
