package com.just.test.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.fragment.FindFragment;
import com.just.test.fragment.ScheduleFragment;
import com.just.test.fragment.SettingFragment;
import com.just.test.widget.MyActionBar;

/**
 * 底部菜单框架
 */
public class FragmentTabHost extends FragmentActivity implements OnClickListener{

	private FragmentManager manager;//定义fragment对象
	private RelativeLayout schedule_layout,find_layout,setting_layout;
	private ImageView schedule_image,find_image,setting_image;
	private TextView schedule_text,find_text,setting_text;
	private ScheduleFragment scheduleFragment;
	private FindFragment findFragment;
	private SettingFragment settingFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost);
		manager = getSupportFragmentManager();

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "底部菜单框架");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		initViews();
	}
	//初始化视图
	private void initViews() {
		//日程
		schedule_layout = (RelativeLayout) findViewById(R.id.schedule_layout);
		schedule_layout.setOnClickListener(this);
		schedule_image = (ImageView) findViewById(R.id.schedule_image);
		schedule_text = (TextView) findViewById(R.id.schedule_text);
		//发现
		find_layout = (RelativeLayout) findViewById(R.id.find_layout);
		find_layout.setOnClickListener(this);
		find_image = (ImageView) findViewById(R.id.find_image);
		find_text = (TextView) findViewById(R.id.find_text);
		//设置
		setting_layout = (RelativeLayout) findViewById(R.id.setting_layout);
		setting_layout.setOnClickListener(this);
		setting_image = (ImageView) findViewById(R.id.setting_image);
		setting_text = (TextView) findViewById(R.id.setting_text);

		setChioceItem(0);
	}
	@Override
	public void onClick(View v) {
		if (v == schedule_layout) {
			setChioceItem(0);
		} else if (v == find_layout) {
			setChioceItem(1);
		} else if (v == setting_layout) {
			setChioceItem(2);
		}
	}

	/**
	 * 重置选项  隐藏其他fragment
	 * @param positin 要显示的item
     */
	private void setChioceItem(int positin) {
		FragmentTransaction transaction = manager.beginTransaction();
		clearChioce();
		hideFragment(transaction);
		int blue = 0xFF0AB2FB;
		switch (positin) {
			case 0:
				schedule_image.setImageResource(R.drawable.ic_tabbar_course_pressed);
				schedule_text.setTextColor(blue);
				schedule_layout.setBackgroundColor(Color.GREEN);
				if (scheduleFragment == null) {
					scheduleFragment = new ScheduleFragment();
					transaction.add(R.id.content, scheduleFragment);
				}else {
					transaction.show(scheduleFragment);
				}
				break;
			case 1:
				find_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
				find_text.setTextColor(blue);
				find_layout.setBackgroundColor(Color.BLUE);
				if (findFragment == null) {
					findFragment = new FindFragment();
					transaction.add(R.id.content, findFragment);
				}else {
					transaction.show(findFragment);
				}
				break;
			case 2:
				setting_image.setImageResource(R.drawable.ic_tabbar_settings_pressed);
				setting_text.setTextColor(blue);
				setting_layout.setBackgroundColor(Color.RED);
				if (settingFragment == null) {
					settingFragment = new SettingFragment();
					transaction.add(R.id.content, settingFragment);
				}else {
					transaction.show(settingFragment);
				}
				break;
			default:
				break;
		}

		transaction.commit();
	}
	/**
	 * 隐藏fragment的方法
	 * @param transaction fragment
	 */
	private void hideFragment(FragmentTransaction transaction) {
		if (scheduleFragment != null) {
			transaction.hide(scheduleFragment);
		}

		if (findFragment !=null) {
			transaction.hide(findFragment);
		}

		if (settingFragment != null) {
			transaction.hide(settingFragment);
		}
	}

	/**
	 * 定义一个重置所有选项的方法
	 */
	private void clearChioce() {
		//日程
		schedule_image.setImageResource(R.drawable.ic_tabbar_course_normal);
		int gray = 0xFF7597B3;
		schedule_text.setTextColor(gray);
		int white = 0xFFFFFFFF;
		schedule_layout.setBackgroundColor(white);
		//发现
		find_image.setImageResource(R.drawable.ic_tabbar_found_normal);
		find_text.setTextColor(gray);
		find_layout.setBackgroundColor(white);
		//设置
		setting_image.setImageResource(R.drawable.ic_tabbar_settings_normal);
		setting_text.setTextColor(gray);
		setting_layout.setBackgroundColor(white);
	}
}
