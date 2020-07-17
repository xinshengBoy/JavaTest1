package com.just.test.custom;

import com.just.test.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 自定义标题头，可通用
 * @author user
 *
 */
public class TitleView extends RelativeLayout{

	private ImageView iv_custom_actionbar_back;
	private TextView txt_custom_actionbar_title;
	private ImageView iv_custom_actionbar_share;

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//加载布局
		LayoutInflater.from(context).inflate(R.layout.custom_actionbar, this);
		//获取控件
		iv_custom_actionbar_back = (ImageView)findViewById(R.id.iv_custom_actionbar_back);
		txt_custom_actionbar_title = (TextView)findViewById(R.id.txt_custom_actionbar_title);
		iv_custom_actionbar_share = (ImageView)findViewById(R.id.iv_custom_actionbar_share);
	}

	/**
	 * 设置标题
	 */
	public void setTitleText(String title) {
		txt_custom_actionbar_title.setText(title);
	}

	/**
	 * 设置左侧图片的点击事件
	 */
	public void setLeftButtonOnClicker(OnClickListener listener) {
		iv_custom_actionbar_back.setOnClickListener(listener);
	}

	/**
	 * 设置右侧图片的点击事件
	 */
	public void setRightButtonOnClicker(OnClickListener listener) {
		iv_custom_actionbar_share.setOnClickListener(listener);
	}

}
