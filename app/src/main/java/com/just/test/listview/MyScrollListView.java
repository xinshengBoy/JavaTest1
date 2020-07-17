package com.just.test.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 解决listview与scrollview的高度冲突问题
 * Created by admin on 2017/5/17.
 */


public class MyScrollListView extends ListView {

    public MyScrollListView(Context context) {
        super(context);
    }

    public MyScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
