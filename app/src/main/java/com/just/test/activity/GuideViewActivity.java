package com.just.test.activity;

import com.just.test.R;
import com.just.test.widget.GuideView;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GuideViewActivity extends Activity {

	private Object guideView;
	private TextView txt_guideview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_guideview);

		txt_guideview = (TextView)findViewById(R.id.txt_guideview);
//		setGuideView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setGuideView();
	}

	private void setGuideView() {
		// 使用图片
		final ImageView iv = new ImageView(this);
		iv.setImageResource(R.drawable.mainmenu);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		iv.setLayoutParams(params);

		guideView = GuideView.Builder
				.newInstance(this)
				.setTargetView(txt_guideview)//设置目标
				.setCustomGuideView(iv)
				.setDirction(GuideView.Direction.LEFT_BOTTOM)
				.setShape(GuideView.MyShape.RECTANGULAR)   // 设置圆形显示区域，
				.setBgColor(getResources().getColor(R.color.shadow))
				.setOnclickListener(new GuideView.OnClickCallback() {
					@Override
					public void onClickedGuideView() {
						((GuideView) guideView).hide();
					}
				})
				.build();
		((GuideView) guideView).show();
	}
}
