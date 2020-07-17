package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.just.test.R;

public class AndroidTransitions extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_androidtransitions);
		
		final ViewGroup layout_transition = (ViewGroup)findViewById(R.id.layout_transition);
		final ImageView txt_transition = (ImageView) layout_transition.findViewById(R.id.txt_transition);
		final Button btn_transition = (Button) layout_transition.findViewById(R.id.btn_transition);

		btn_transition.setOnClickListener(new OnClickListener() {
			boolean isVisible;
			@Override
			public void onClick(View v) {
				TransitionManager.beginDelayedTransition(layout_transition);
				isVisible = !isVisible;
				txt_transition.setVisibility(isVisible ? View.VISIBLE : View.GONE);
				
			}
		});
	}
}
