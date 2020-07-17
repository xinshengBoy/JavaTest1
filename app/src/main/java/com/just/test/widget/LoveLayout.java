package com.just.test.widget;

import java.util.Random;
import com.just.test.R;
import com.just.test.tools.MyEvaluator;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class LoveLayout extends RelativeLayout{

	Drawable [] drawable = new Drawable[6];
	Random random = new Random();
	private int dWidth;
	private int dHeight;
	private LayoutParams layoutParams;
	private int mWidth;
	private int mHeight;

	public LoveLayout(Context context) {
		super(context);
		init();
	}

	public LoveLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}


	private void init() {
		drawable[0] = getResources().getDrawable(R.drawable.ic_tabbar_course_normal);
		drawable[1] = getResources().getDrawable(R.drawable.ic_tabbar_course_pressed);
		drawable[2] = getResources().getDrawable(R.drawable.ic_tabbar_found_normal);
		drawable[3] = getResources().getDrawable(R.drawable.ic_tabbar_found_pressed);
		drawable[4] = getResources().getDrawable(R.drawable.ic_tabbar_settings_normal);
		drawable[5] = getResources().getDrawable(R.drawable.ic_tabbar_settings_pressed);

		dWidth = drawable[0].getIntrinsicWidth();//图片长度
		dHeight = drawable[0].getIntrinsicHeight();//图片高度
		layoutParams = new LayoutParams(dWidth,dHeight);//图片尺寸
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
	}

	public void addLove() {
		ImageView imageView = new ImageView(getContext());
		int index = random.nextInt(drawable.length);//随机选择图片的位置
		imageView.setImageDrawable(drawable[index]);
		layoutParams.addRule(CENTER_HORIZONTAL);
		layoutParams.addRule(ALIGN_PARENT_BOTTOM);

		addView(imageView,layoutParams);

		//动画
		AnimatorSet anim = getAnimator(imageView);
		anim.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});
		anim.start();
	}

	private AnimatorSet getAnimator(ImageView iv) {
		//贝塞尔动画（evaluator估值器）
		ValueAnimator bezer = getValueAnimator(iv);
		AnimatorSet sets = new AnimatorSet();
		//按顺序执行
		sets.playSequentially(bezer);
		sets.setTarget(iv);
		return sets;
	}

	private ValueAnimator getValueAnimator(final ImageView iv) {
		//贝塞尔曲线的四个关键点(起始点p0,拐点P1,拐点P2，终点p3)
		PointF pointF0 = new PointF((mWidth - dWidth) / 2,mHeight);
		PointF pointF1 = getPoint(1);
		PointF pointF2 = getPoint(2);
		PointF pointF3 = new PointF(random.nextInt(mWidth),0);
		//属性动画不仅可以改变view的属性；还可以改变自定义的属性；
		MyEvaluator evaluator = new MyEvaluator(pointF1,pointF2);
		ValueAnimator animator = ValueAnimator.ofObject(evaluator, pointF0,pointF3);
		animator.setDuration(3000);//持续时间
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				PointF point = (PointF) animation.getAnimatedValue();
				//控制属性变化
				iv.setX(point.x);
				iv.setY(point.y);
				iv.setAlpha(1 - animation.getAnimatedFraction());
			}
		});

		return animator;
	}

	private PointF getPoint(int type) {
		PointF pointF = new PointF();
		pointF.x = random.nextInt(mWidth);//下一个出现的点
		if (type == 1) {
			pointF.y = random.nextInt(mHeight / 2) + mHeight / 2 ;
		}else {
			pointF.y = random.nextInt(mHeight / 2);
		}

		return pointF;
	}
}
