package com.just.test.custom;

import com.just.test.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class CustonDeleteListView extends ListView implements OnGestureListener{
	//删除监听
	public interface OnDeleteListener {
		void onDelete(int index);
	}
	private OnDeleteListener mDeleteLister;// 删除事件监听器
	private GestureDetector mGD;// 手势动作探测器
	private boolean isDeleteBtnShow;//删除按钮是否显示
	private int mSelectedItem;//所选item条目
	private Button mDeleteButton;//删除按钮
	private ViewGroup mItemLayout;//列表布局项
	public CustonDeleteListView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mGD = new GestureDetector(getContext(), this);
//		setOnTouchListener(this);
	}

	public void setOnDeleteLister(OnDeleteListener listener) {
		mDeleteLister = listener;
	}

	// 触摸监听事件
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if (isDeleteBtnShow) {
//            hideDelete();
//            return false;
//        } else {
//            return mGD.onTouchEvent(event);
//        }
//    }



	public void hideDelete() {
		mItemLayout.removeView(mDeleteButton);
		mDeleteButton = null;
		isDeleteBtnShow = false;
	}

	public boolean isDeleteBtnShow() {
		return isDeleteBtnShow;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		if (!isDeleteBtnShow) {
			mSelectedItem = pointToPosition((int)e.getX(), (int)e.getY());
		}
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						   float velocityY) {
		if (!isDeleteBtnShow && Math.abs(velocityX)>Math.abs(velocityY)) {
			mDeleteButton = (Button) LayoutInflater.from(getContext()).inflate(R.layout.custom_deletebutton,null);

			mDeleteButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mItemLayout.removeView(mDeleteButton);//移除按钮
					mDeleteButton = null;//置空
					isDeleteBtnShow = false;
					mDeleteLister.onDelete(mSelectedItem);
				}
			});

			mItemLayout = (ViewGroup) getChildAt(mSelectedItem - getFirstVisiblePosition());
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
					(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);

			mItemLayout.addView(mDeleteButton,params);
			isDeleteBtnShow = true;
		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {}

	/**
	 * 解决scrollview与listview的高度的冲突
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
