package com.just.test.activity;

import java.util.ArrayList;
import java.util.List;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 滑动删除
 */
public class SlipDelete extends Activity {

	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slipdelete);
		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "滑动删除");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		listView = (ListView) findViewById(R.id.list);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("选项"+i);
		}
		MyAdapter adapter = new MyAdapter(SlipDelete.this, list);
		listView.setAdapter(adapter);
		listView.setOverScrollMode(View.OVER_SCROLL_NEVER);//解决listview下拉或上拉时的透明背景阴影的问题
	}


}

class MyAdapter extends BaseAdapter{

	private List<String> mList;
	private Context mContext;
	private float downX;//点下时的坐标点
	private float upX;//抬起时的坐标点
	private Button button;//执行删除时的button
	private View view;
	private Animation animation;//删除时的动画
	public MyAdapter(Context context,ArrayList<String> list) {
		this.mContext = context;
		this.mList = list;
		animation = AnimationUtils.loadAnimation(mContext, R.anim.push_out);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.delete_item, null);
			holder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
			holder.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//为每个view设置触控监听
		convertView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final ViewHolder holder = (ViewHolder) v.getTag();
				//按下时的处理
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						downX = event.getX();
						//判断删除按钮是否显示
						if (button != null) {
							button.setVisibility(View.GONE);
						}
						break;
					case MotionEvent.ACTION_UP:
						upX = event.getX();
						break;
				}

				if (holder.btn_delete != null) {
					if (Math.abs(downX-upX) > 280) {//绝对值如果大于35，就认为是左右滑动
						holder.btn_delete.setVisibility(View.VISIBLE);
						button = holder.btn_delete;//赋值给全局的button
						view = v;//得到ItemView,在上面加动画
						return true;//终止时间
					}
				}
				return false;//释放时间，可点击状态
			}
		});
		//删除按钮点击
		holder.btn_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (button != null) {
					button.setVisibility(View.GONE);
					deleteItem(view, position);
				}
			}
		});
		holder.txt_title.setText(mList.get(position));
		return convertView;
	}

	final static class ViewHolder {

		public Button btn_delete;
		public TextView txt_title;

	}

	/**
	 * 删除条目的方法
	 * @param view
	 * @param position
	 */
	public void deleteItem(View view,final int position){
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mList.remove(position);//移除数据
				notifyDataSetChanged();//更新列表
			}
		});
	}


}
