package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

import pl.droidsonroids.gif.GifImageView;

/**
 * gif动画显示
 * 因为imageview不是直接显示gif动画，所以用gifview。jar包来做就好了
 * @author user
 *https://github.com/koral--/android-gif-drawable
 * compile 'pl.droidsonroids.gif:android-gif-drawable:1.0.+'
 */
public class GIFActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_gif);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "gif动画显示");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		GifView custom_gif1= (GifView) findViewById(R.id.custom_gif1);
		custom_gif1.setGifImage(R.drawable.animation);//设置图片资源
//		custom_gif.setShowDimension(300, 300);//设置图片大小
		custom_gif1.setGifImageType(GifImageType.COVER);//设置图片加载方式

		GifView custom_gif2= (GifView) findViewById(R.id.custom_gif2);
		custom_gif2.setGifImage(R.drawable.animation1);//设置图片资源
//		custom_gif.setShowDimension(300, 300);//设置图片大小
		custom_gif2.setGifImageType(GifImageType.COVER);//设置图片加载方式

		GifImageView custom_gif3= (GifImageView) findViewById(R.id.custom_gif3);
		custom_gif3.setImageResource(R.drawable.animation1);//设置图片资源
	}

}
