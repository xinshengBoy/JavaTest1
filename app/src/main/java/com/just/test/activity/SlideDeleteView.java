package com.just.test.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.tools.ListViewCompat;
import com.just.test.tools.SlideView;
import com.just.test.widget.MyActionBar;

public class SlideDeleteView extends Activity implements OnItemClickListener , SlideView.OnSlideListener, android.view.View.OnClickListener{
	private ListViewCompat list_slide;
	private List<MessageItem> mMessageItems = new ArrayList<SlideDeleteView.MessageItem>();
	private SlideView mLastSlideViewWithStatusOn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidedetele_view);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "侧滑删除");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		initView();
	}


	private void initView() {
		list_slide = (ListViewCompat) findViewById(R.id.list_slide);

		for (int i = 0; i < 30; i++) {
			MessageItem item = new MessageItem();
			if (i % 2 ==0) {
				item.iconRes = R.drawable.default_qq_avatar;
				item.title = "腾讯新闻";
				item.msg = "天津大爆炸，大量工厂被毁";
				item.time = "2016-3-8";

			}else {
				item.iconRes = R.drawable.wechat_icon;
				item.title = "微信公众号";
				item.msg = "欢迎使用腾讯微信";
				item.time = "15:10 pm";
			}
			mMessageItems.add(item);
		}

		list_slide.setAdapter(new SlideAdapter());
		list_slide.setOnItemClickListener(this);
		list_slide.setOverScrollMode(View.OVER_SCROLL_NEVER);//解决listview下拉或上拉时的透明背景阴影的问题

	}

	private class SlideAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public SlideAdapter() {
			super();
			mInflater = getLayoutInflater();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mMessageItems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mMessageItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			SlideView slideView = (SlideView) convertView;
			if (slideView == null) {
				View itemView = mInflater.inflate(R.layout.slide_item, null);

				slideView = new SlideView(SlideDeleteView.this);
				slideView.setContentView(itemView);

				holder = new ViewHolder(slideView);
				slideView.setOnSlideListener(SlideDeleteView.this);
				slideView.setTag(holder);
			} else {
				holder = (ViewHolder) slideView.getTag();
			}
			MessageItem item = mMessageItems.get(position);
			item.slideView = slideView;
			item.slideView.shrink();

			holder.icon.setImageResource(item.iconRes);
			holder.title.setText(item.title);
			holder.msg.setText(item.msg);
			holder.time.setText(item.time);
			holder.deleteHolder.setOnClickListener(SlideDeleteView.this);

			return slideView;
		}

	}

	public class MessageItem {
		public int iconRes;
		public String title;
		public String msg;
		public String time;
		public SlideView slideView;
	}

	private static class ViewHolder {
		public ImageView icon;
		public TextView title;
		public TextView msg;
		public TextView time;
		public ViewGroup deleteHolder;

		ViewHolder(View view) {
			icon = (ImageView) view.findViewById(R.id.icon);
			title = (TextView) view.findViewById(R.id.title);
			msg = (TextView) view.findViewById(R.id.msg);
			time = (TextView) view.findViewById(R.id.time);
			deleteHolder = (ViewGroup)view.findViewById(R.id.holder);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onSlide(View view, int status) {
		if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
			mLastSlideViewWithStatusOn.shrink();
		}

		if (status == SLIDE_STATUS_ON) {
			mLastSlideViewWithStatusOn = (SlideView) view;
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}


}
