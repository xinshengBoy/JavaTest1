package com.just.test.tools;

import android.app.Dialog;
import android.content.Context;

public class CustomDialog1 extends Dialog{

	public CustomDialog1(Context context) {
		super(context);
	}
	
	protected CustomDialog1(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	
	public CustomDialog1(Context context, int themeResId) {
		super(context, themeResId);
	}



}
