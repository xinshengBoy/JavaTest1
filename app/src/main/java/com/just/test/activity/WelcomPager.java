package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.tools.WelcomeAdapter;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;

/**
 * 欢迎界面
 */
public class WelcomPager extends Activity implements OnPageChangeListener,OnClickListener{

	private ViewPager viewpager;
	private ArrayList<View> views = new ArrayList<>();
	private static final int [] pic = {R.drawable.img_hp1,R.drawable.img_hp2,R.drawable.img_hp3,R.drawable.img_hp4,R.drawable.img_hp5,R.drawable.img_hp6,R.drawable.img_hp7,R.drawable.img_hp8};
	private WelcomeAdapter adapter;
	private Button btn_callback;
	private ImageView [] points;
	private int currentIndex;
	private LinearLayout linearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "欢迎界面");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);


		viewpager = (ViewPager) findViewById(R.id.viewpager);
		adapter = new WelcomeAdapter(views);
		btn_callback = (Button)findViewById(R.id.btn_callback);
		btn_callback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WelcomPager.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		initData();
	}

	private void initData() {
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		//初始化引导图片列表
		for (int i = 0; i < pic.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(mParams);
			imageView.setImageResource(pic[i]);
			imageView.setScaleType(ImageView.ScaleType.FIT_START);//图片适应类型，这样图片就会紧贴顶部
			views.add(imageView);
		}
		
		viewpager.setAdapter(adapter);
		viewpager.setOnPageChangeListener(this);
		initPoint();
	}
	
	private void initPoint(){
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		points = new ImageView[pic.length];
		for (int i = 0; i < pic.length; i++) {
			//得到linearLayout下面的每一个子元素
			points[i] = (ImageView) linearLayout.getChildAt(i);
			//默认都设置为灰色
			points[i].setEnabled(true);
			points[i].setBackgroundResource(R.drawable.point_unclick);
			//给每个小点设置监听
			points[i].setOnClickListener(this);
			//设置位置tag。方便取出与当前位置对应
			points[i].setTag(i);
		}
		currentIndex = 0;
		//设置默认位置
		points[currentIndex].setEnabled(false);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		if (position == 7) {//最后一张显示返回按钮
			btn_callback.setVisibility(View.VISIBLE);
			linearLayout.setVisibility(View.GONE);
		}else{
			btn_callback.setVisibility(View.GONE);
			linearLayout.setVisibility(View.VISIBLE);
			setCurDot(position);
		}
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}
	
	private void setCurDot(int position){
		if (position < 0 || position > pic.length-1 || currentIndex == position) {
			return;
		}

		points[position].setEnabled(false);
		points[currentIndex].setEnabled(true);
		currentIndex = position;
	}
	/**
	 * 设置当前页的位置
	 * @param position
	 */
	private void setCurView(int position){
		if (position < 0 || position >= pic.length) {
			return;
		}
		viewpager.setCurrentItem(position);
	}
}
