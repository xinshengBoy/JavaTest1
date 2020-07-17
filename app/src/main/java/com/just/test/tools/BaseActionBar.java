package com.just.test.tools;

import android.app.ActionBar;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.just.test.R;

/**
 * 通用actionbar
 * @author user
 *
 */
public class BaseActionBar {

	public static void actionBar(final Activity activity) {
		final ActionBar actionBar = activity.getActionBar();
//		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.layout_actionbar);// 自定义ActionBar布局
		// 监听事件
		actionBar.getCustomView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				switch (arg0.getId()) {
					case R.id.iv_ActionBar_Back:
						activity.finish();
						break;
					default:
						break;
				}
			}
		});

	}

}
