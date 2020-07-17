package com.just.test.activity;

import java.util.ArrayList;
import java.util.List;

import com.just.test.R;
import com.just.test.bean.Dish;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PicassoImage extends Activity {

	private GridView list_picassoImage;
	private ImageView iv_picasso_detail;
	private static final String BASE_URL = "http://img1.3lian.com/img2011/w1/106/85/";
	ArrayList<Dish> dishList = new ArrayList<Dish>();
	private long firstClick;
	private long lastClick;
	// 计算点击的次数
	private int count;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_picasso_image);
		initData();
		list_picassoImage = (GridView) findViewById(R.id.list_picassoImage);
		PicassoAdapter adapter = new PicassoAdapter(PicassoImage.this, dishList, R.layout.layout_picasso_item);
		list_picassoImage.setAdapter(adapter);
		iv_picasso_detail = (ImageView)findViewById(R.id.iv_picasso_detail);

		list_picassoImage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Picasso.with(PicassoImage.this).load(dishList.get(position).getImageUrl()).into(iv_picasso_detail);
				iv_picasso_detail.setVisibility(View.VISIBLE);
				list_picassoImage.setVisibility(View.GONE);

				AlertDialog dialog = new AlertDialog.Builder(PicassoImage.this)
						.setTitle(dishList.get(position).getName())
						.setMessage(dishList.get(position).getPrice())
						.create();
				Window window = dialog.getWindow();
				window.setGravity(Gravity.BOTTOM);
				dialog.show();
			}
		});

		iv_picasso_detail.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						// 如果第二次点击 距离第一次点击时间过长 那么将第二次点击看为第一次点击
						if (firstClick != 0 && System.currentTimeMillis() - firstClick > 300) {
							count = 0;
						}
						count++;
						if (count == 1) {
							firstClick = System.currentTimeMillis();
						} else if (count == 2) {
							lastClick = System.currentTimeMillis();
							// 两次点击小于300ms 也就是连续点击
							if (lastClick - firstClick < 500) {// 判断是否是执行了双击事件
								iv_picasso_detail.setVisibility(View.GONE);
								list_picassoImage.setVisibility(View.VISIBLE);
							}
						}
						break;
				}
				return true;
			}
		});
	}

	private void initData() {
		dishList.add(new Dish(BASE_URL+"43.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"42.jpg","小炒肉","18.00"));
		dishList.add(new Dish(BASE_URL+"41.jpg","清炒时蔬","15.00"));
		dishList.add(new Dish(BASE_URL+"40.jpg","金牌烤鸭","36.00"));
		dishList.add(new Dish(BASE_URL+"39.jpg","鱼香肉丝","22.00"));
		dishList.add(new Dish(BASE_URL+"38.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"37.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"36.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"35.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"34.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"33.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"32.jpg","小炒肉","18.00"));
		dishList.add(new Dish(BASE_URL+"31.jpg","清炒时蔬","15.00"));
		dishList.add(new Dish(BASE_URL+"30.jpg","金牌烤鸭","36.00"));
		dishList.add(new Dish(BASE_URL+"29.jpg","鱼香肉丝","22.00"));
		dishList.add(new Dish(BASE_URL+"28.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"27.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"26.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"25.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"24.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"23.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"22.jpg","小炒肉","18.00"));
		dishList.add(new Dish(BASE_URL+"21.jpg","清炒时蔬","15.00"));
		dishList.add(new Dish(BASE_URL+"20.jpg","金牌烤鸭","36.00"));
		dishList.add(new Dish(BASE_URL+"49.jpg","鱼香肉丝","22.00"));
		dishList.add(new Dish(BASE_URL+"50.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"17.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"16.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"15.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"14.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"13.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"12.jpg","小炒肉","18.00"));
		dishList.add(new Dish(BASE_URL+"11.jpg","清炒时蔬","15.00"));
		dishList.add(new Dish(BASE_URL+"10.jpg","金牌烤鸭","36.00"));
		dishList.add(new Dish(BASE_URL+"43.jpg","鱼香肉丝","22.00"));
		dishList.add(new Dish(BASE_URL+"44.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"45.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"46.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"47.jpg","水煮鱼片","38.00"));
		dishList.add(new Dish(BASE_URL+"48.jpg","水煮鱼片","38.00"));
	}

	private class PicassoAdapter extends CommonAdapter<Dish>{

		public PicassoAdapter(Context context, List<Dish> mDatas,
							  int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, Dish item) {
			ImageView iv_picasso = helper.getView(R.id.iv_picasso);
			TextView txt_picasso_name = helper.getView(R.id.txt_picasso_name);
			TextView txt_picasso_price = helper.getView(R.id.txt_picasso_price);

			txt_picasso_name.setText(item.getName());
			txt_picasso_price.setText(item.getPrice());
			Picasso.with(PicassoImage.this).load(item.getImageUrl()).into(iv_picasso);
		}
	}
}
