package com.just.test.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.just.test.activity.SlideDeleteView;

/**
 * 侧滑删除
 */
public class ListViewCompat extends ListView{
	
	private SlideView mFoucusedItemView;

	public ListViewCompat(Context context) {
		super(context);
	}
	
	public ListViewCompat(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewCompat(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	public void shrinkListItem(int position) {
		View item = getChildAt(position);
		if (item != null) {
			try {
				((SlideView) item).shrink();
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) event.getX();
			int y = (int) event.getY();
			int position = pointToPosition(x, y);
			if (position != INVALID_POSITION) {
				SlideDeleteView.MessageItem data = (SlideDeleteView.MessageItem) getItemAtPosition(position);
				mFoucusedItemView = data.slideView;
			}
			break;

		default:
			break;
		}
		
		if (mFoucusedItemView != null) {
			mFoucusedItemView.onRequireTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}

}
