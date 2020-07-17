package com.just.test.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.bean.OptionalBean;

/***
 * 自定义适配器
 *
 * @author zhangjia
 *
 */
@SuppressLint("UseSparseArrays")
public class MoveAndTopAdapter extends BaseAdapter {
	private static HashMap<Integer, Boolean> isSelected;
	private Context context;
	public boolean isHidden;
	public List<OptionalBean> items;

	public MoveAndTopAdapter(Context context, List<OptionalBean> items) {
		this.context = context;
		isSelected = new HashMap<Integer, Boolean>();
		this.items = items;
	}

	public void showDropItem(boolean showOptionalBean) {
		this.ShowOptionalBean = showOptionalBean;
	}

	public void setInvisiblePosition(int position) {
		invisilePosition = position;
	}

	@SuppressLint({ "InflateParams", "ViewHolder" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.drag_list_item, null);
		TextView te_ch = (TextView) convertView.findViewById(R.id.item_chName);
		TextView te_en = (TextView) convertView.findViewById(R.id.item_enName);
		ImageView imagetop = (ImageView) convertView.findViewById(R.id.item_top_icon);
		ImageView imagedelete = (ImageView) convertView.findViewById(R.id.item_cb);
		te_ch.setText(items.get(position).getCH_Name());
		te_en.setText(items.get(position).getEN_Name());
		imagedelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				items.remove(position);
				MoveAndTopAdapter.this.notifyDataSetChanged();
			}
		});
		imagetop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDetailInfo(position);
				items.add(0, items.get(position));
				items.remove(position + 1);
				MoveAndTopAdapter.this.notifyDataSetChanged();
			}
		});
		if (isChanged) {
			if (position == invisilePosition) {
				if (!ShowOptionalBean) {
					convertView.findViewById(R.id.item_chName).setVisibility(View.INVISIBLE);
					convertView.findViewById(R.id.item_enName).setVisibility(View.INVISIBLE);
					convertView.findViewById(R.id.item_drag_icon).setVisibility(View.INVISIBLE);
				}
			}
			if (lastFlag != -1) {
				if (lastFlag == 1) {
					if (position > invisilePosition) {
						Animation animation;
						animation = getFromSelfAnimation(0, -height);
						convertView.startAnimation(animation);
					}
				} else if (lastFlag == 0) {
					if (position < invisilePosition) {
						Animation animation;
						animation = getFromSelfAnimation(0, height);
						convertView.startAnimation(animation);
					}
				}
			}
		}
		return convertView;
	}

	static class ViewHolder {
		TextView tv_CH;
		TextView tv_EN;
		ImageView iv_TOP;
		ImageView iv_Delete;
	}

	/***
	 * 动态修改ListVIiw的方位.
	 *
	 * @param start
	 *            点击移动的position
	 * @param down
	 *            松开时候的position
	 */
	private int invisilePosition = -1;
	private boolean isChanged = true;
	private boolean ShowOptionalBean = false;

	public void exchange(int startPosition, int endPosition) {
		Object startObject = getItem(startPosition);
		if (startPosition < endPosition) {
			items.add(endPosition + 1, (OptionalBean) startObject);
			items.remove(startPosition);
		} else {
			items.add(endPosition, (OptionalBean) startObject);
			items.remove(startPosition + 1);
		}
		isChanged = true;
		notifyDataSetChanged();
	}

	public void exchangeCopy(int startPosition, int endPosition) {
		Object startObject = getCopyOptionalBean(startPosition);
		if (startPosition < endPosition) {
			mCopyList.add(endPosition + 1, (OptionalBean) startObject);
			mCopyList.remove(startPosition);
		} else {
			mCopyList.add(endPosition, (OptionalBean) startObject);
			mCopyList.remove(startPosition + 1);
		}
		isChanged = true;
		notifyDataSetChanged();
	}

	public Object getCopyOptionalBean(int position) {
		return mCopyList.get(position);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addDragOptionalBean(int start, OptionalBean obj) {
		items.remove(start);// 删除该项
		items.add(start, (OptionalBean) obj);// 添加删除项
	}

	private ArrayList<OptionalBean> mCopyList = new ArrayList<OptionalBean>();

	public void copyList() {
		mCopyList.clear();
		for (OptionalBean str : items) {
			mCopyList.add(str);
		}
	}

	public void pastList() {
		items.clear();
		for (OptionalBean str : mCopyList) {
			items.add(str);
		}
	}

	private int lastFlag = -1;
	private int height;
	public void setIsSameDragDirection(boolean value) {
	}

	public void setLastFlag(int flag) {
		lastFlag = flag;
	}

	public void setHeight(int value) {
		height = value;
	}

	public void setCurrentDragPosition(int position) {
	}

	public Animation getFromSelfAnimation(int x, int y) {
		TranslateAnimation go = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, x,
				Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, y);
		go.setInterpolator(new AccelerateDecelerateInterpolator());
		go.setFillAfter(true);
		go.setDuration(100);
		go.setInterpolator(new AccelerateInterpolator());
		return go;
	}

	public Animation getToSelfAnimation(int x, int y) {
		TranslateAnimation go = new TranslateAnimation(Animation.ABSOLUTE, x, Animation.RELATIVE_TO_SELF, 0,
				Animation.ABSOLUTE, y, Animation.RELATIVE_TO_SELF, 0);
		go.setInterpolator(new AccelerateDecelerateInterpolator());
		go.setFillAfter(true);
		go.setDuration(100);
		go.setInterpolator(new AccelerateInterpolator());
		return go;
	}
	/**
	 *  提示置顶成功
	 * @param clickID
	 */
	private void showDetailInfo(int clickID) {
		Toast.makeText(context, "置顶成功", Toast.LENGTH_SHORT).show();
	}

}