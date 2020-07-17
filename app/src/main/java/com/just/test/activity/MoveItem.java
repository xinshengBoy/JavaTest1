package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.just.test.widget.TouchInterceptorListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表item拖动交换顺序
 */
public class MoveItem extends Activity {

	private EditAdapter adapter;
	private TouchInterceptorListView list;
	private TextView txt_resultMove;
	private List<Bundle> arrayText = new ArrayList<>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_moveitem);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "列表item拖动交换顺序");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		list = (TouchInterceptorListView)findViewById(R.id.list_move);//(TouchInterceptor)findViewById(android.R.id.list);
		Button btn_finishMove = (Button) findViewById(R.id.btn_finishMove);
		txt_resultMove = (TextView) findViewById(R.id.txt_resultMove);
		getText();

		adapter = new EditAdapter(MoveItem.this,arrayText);
		list.setAdapter(adapter);
		list.setDropListener(mDropListener);

		btn_finishMove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				arrayText = adapter.list;
				if (arrayText != null) {
					txt_resultMove.setText(arrayText.toString());
				}
			}
		});
	}
	public void getText(){
		for (int i = 1; i <= 10; i++) {
			Bundle bundle = new Bundle();
			bundle.putBoolean("isSelected", true);
			bundle.putString("navName", "传奇"+i);

			arrayText.add(bundle);
		}
	}

	//交换listview的数据
	private TouchInterceptorListView.DropListener mDropListener =
			new TouchInterceptorListView.DropListener() {
				public void drop(int from, int to) {
					if (from >=2 && to >=2) {
						Bundle item = arrayText.get(from);
						arrayText.remove(item);//.remove(from);
						arrayText.add(to, item);
						adapter.setDate(arrayText);
						adapter.notifyDataSetChanged();
					}
				}
			};


	class EditAdapter extends BaseAdapter {

		private final Context context;
		private List<Bundle> list;

		public EditAdapter(Context context, List<Bundle> list){
			this.context = context;
			this.list = list;
		}

		public void setDate(List<Bundle> mList){
			list = mList;
			notifyDataSetChanged();
		}
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.move_item, parent, false);

				holder.cb_checkEdit = (CheckBox) convertView.findViewById(R.id.cb_checkEdit);
				holder.txt_menuTitle = (TextView) convertView.findViewById(R.id.txt_menuTitle);
				holder.iv_moveMenu = (ImageView) convertView.findViewById(R.id.iv_moveMenu);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.txt_menuTitle.setText(list.get(position).getString("navName"));
			//checkbox设置了setOnCheckedChangeListener,  listview滑动后checkbox选中的状态错乱.可能出现的原因是checkbox先设置了选中状态如checkbox.setChecked(true,后设置的OnCheckedChange监听事件,
			// 此时这个checkbox状态已经改变了,但是没有被监听到.当下一个item中的checkbox执行setChecked时,会触发上一个checkbox的OnCheckedChange事件,造成选中状态错乱或者其他问题.
			//草靠网址：http://blog.csdn.net/u011510784/article/details/50497180
			holder.cb_checkEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					Bundle bundle = new Bundle();
					bundle.putString("navName",holder.txt_menuTitle.getText().toString());
					bundle.putBoolean("isSelected",isChecked);
					list.set(position,bundle);//改变原来索引的值
					notifyDataSetChanged();
				}
			});
			holder.cb_checkEdit.setChecked(list.get(position).getBoolean("isSelected"));
			if (position<2){//前两栏不可点击
				holder.cb_checkEdit.setEnabled(false);
				holder.iv_moveMenu.setVisibility(View.INVISIBLE);
			}
			return convertView;
		}

		private class ViewHolder {
			CheckBox cb_checkEdit;
			TextView txt_menuTitle;
			ImageView iv_moveMenu;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (arrayText != null){
			arrayText = null;
		}
	}
}
