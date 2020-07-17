package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.just.test.R;

/**
 * 文字展开收起效果
 * @author user
 *
 */
public class TextExpand extends Activity {
	private TextView txt_textExpand;
	private ImageView iv_expand;
	private int maxLine = 3;//最大默认显示行数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_expand_layout);
		txt_textExpand = (TextView)findViewById(R.id.txt_textExpand);
		iv_expand = (ImageView)findViewById(R.id.iv_expand);

		//先设置文字
		txt_textExpand.setText(getText(R.string.text_expand_text));
		//设置文字高度
		txt_textExpand.setHeight(txt_textExpand.getLineHeight()*maxLine);
		txt_textExpand.post(new Runnable() {

			@Override
			public void run() {
				//判断展开按钮要不要显示
				iv_expand.setVisibility(txt_textExpand.getLineCount() > maxLine ? View.VISIBLE : View.GONE);
			}
		});
		//展开按钮点击事件
		iv_expand.setOnClickListener(new OnClickListener() {
			boolean isExpand;//是否折叠
			@Override
			public void onClick(View v) {
				isExpand = !isExpand;//默认未展开
				txt_textExpand.clearAnimation();//清除文字动画效果
				final int deltaValue;//默认高度
				//起始高度
				final int startValue = txt_textExpand.getHeight();
				int durationMills = 500;//动画持续时间
				if (isExpand) {
					iv_expand.setImageResource(R.drawable.sign_up);
					//折叠动画，从实际高度缩回起始高度
					deltaValue = txt_textExpand.getLineHeight() * txt_textExpand.getLineCount() - startValue;
					RotateAnimation animation = new RotateAnimation(180, 0,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
					animation.setDuration(durationMills);
					animation.setFillAfter(true);
					iv_expand.startAnimation(animation);
				}else {
					iv_expand.setImageResource(R.drawable.sign_down);
					//展开动画，从起始高度增长至实际高度
					deltaValue = txt_textExpand.getLineHeight() * maxLine - startValue;
					RotateAnimation animation = new RotateAnimation(180, 0,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
					animation.setDuration(durationMills);
					animation.setFillAfter(true);
					iv_expand.startAnimation(animation);
				}

				Animation animation = new Animation(){
					protected void applyTransformation(float interpolatedTime, android.view.animation.Transformation t) {
						txt_textExpand.setHeight((int)(startValue + deltaValue * interpolatedTime));
					}
				};
				animation.setDuration(durationMills);
				txt_textExpand.startAnimation(animation);
			}
		});
	}

}
