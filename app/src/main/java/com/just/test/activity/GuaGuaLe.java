package com.just.test.activity;

import com.just.test.R;
import com.just.test.tools.ErinieShow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
/**
 * 刮奖效果
 * @author user
 *
 */
public class GuaGuaLe extends Activity {
	private Button erniebtn;//开始抽奖
	private RelativeLayout container;//显示刮奖区
	private ErinieShow erinieShow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_guaguale);

		erniebtn = (Button) findViewById(R.id.erniebtn);
		container = (RelativeLayout) findViewById(R.id.container);

		erniebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showErnie();
			}
		});
	}

	private void showErnie(){
		container.removeAllViews();//先移除所有视图

		int level = getLevel();//获得奖数
		erinieShow=new ErinieShow(this, level);

		container.addView(erinieShow,new LayoutParams(-2,-2));
	}

	/**
	 * 随机获得几等奖
	 * @return
	 */
	public int getLevel(){
		double d=Math.random()*100;
		if(d<50){
			return 0;
		}
		if(d<80){
			return 3;
		}
		if(d<95){
			return 2;
		}
		return 1;

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
