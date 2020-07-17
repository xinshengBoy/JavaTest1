package com.just.test.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

/**
 * 上证指数走势图
 * @author user
 *
 */
public class MPChart2 extends Activity {
	private LineChart lineChart2;
	private RequestQueue mQueue;
	private String volleyUrl = "http://222.73.229.180:8080/v1/sh1/line/000001?select=price,time";
	List<Bundle> mList = new ArrayList<Bundle>();
	private YAxis yAxis;         //Y坐标轴

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mpchart2);

		lineChart2 = (LineChart) findViewById(R.id.lineChart2);
		yAxis = lineChart2.getAxisLeft();
		lineChart2.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
		lineChart2.getXAxis().setPosition(XAxisPosition.BOTTOM); // 让x轴在下面
		mQueue = Volley.newRequestQueue(getApplicationContext());
		loadGetStr(volleyUrl);
	}

	private void loadGetStr(String url) {
		StringRequest request = new StringRequest(url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						try {
							JSONObject object = new JSONObject(response);
							JSONArray array = object.getJSONArray("line");
							for (int i = 0; i < array.length(); i++) {
								Bundle bundle = new Bundle();
								String result = array.getString(i);
								result = result.substring(1,
										result.length() - 1);
								String[] results = result.split(",");
								bundle.putString("price", results[0]);
								// 时间做处理
								String time = results[1];
								if (time.length() < 6) {
									time = "0" + time;
								}
								time = time.substring(0, 2) + ":"+ time.substring(2, 4);
								bundle.putString("time", time);

								mList.add(bundle);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						handler.sendEmptyMessage(0);
					}

				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});

		mQueue.add(request);
	}
	/**
	 * 折线图生成一个数据
	 * @param count 表示图表中有多少个坐标点
	 * @return
	 */
	private LineData getLineData(int count) {
		ArrayList<String> xValues = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			// x轴显示的数据，这里默认使用数字下标显示
			xValues.add(mList.get(i).getString("time"));
		}

		// y轴的数据
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		float max = 0;
		float min = 0;
		for (int i = 0; i < count; i++) {
			String price = mList.get(i).getString("price");
			float prices = Float.parseFloat(price);
			if (max >= prices) {
				max = prices;
			}
			if (min <= prices) {
				min = prices;
			}
			yValues.add(new Entry(prices,i));
		}

		int priceMax = (int)max + 10;
		int priceMin = (int)min + 10;
		// y轴的数据集合
		LineDataSet lineDataSet = new LineDataSet(yValues, "上证指数");

		yAxis.setStartAtZero(false);    //设置Y轴坐标是否从0开始
		yAxis.setAxisMaxValue(priceMax);    //设置Y轴坐标最大为多少
		yAxis.resetAxisMaxValue();    //重新设置Y轴坐标最大为多少，自动调整
		yAxis.setAxisMinValue(priceMin);    //设置Y轴坐标最小为多少
		yAxis.resetAxisMinValue();    //重新设置Y轴坐标，自动调整

		//用y轴的集合来设置参数
		lineDataSet.setLineWidth(0.5f); // 线宽
		lineDataSet.setCircleSize(1f);// 显示的圆形大小
		lineDataSet.setColor(Color.WHITE);// 显示颜色
		lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色
		lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色
		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
		lineDataSets.add(lineDataSet); // add the datasets
		LineData lineData = new LineData(xValues, lineDataSets);

		return lineData;
	}

	// 折线图设置显示的样式
	private void showChart(LineChart lineChart, LineData lineData, int color) {
		lineChart.setDrawBorders(false);  //是否在折线图上添加边框

		lineChart.setDescription("");// 数据描述
		// 如果没有数据的时候，会显示这个，类似listview的emtpyview
		lineChart.setNoDataTextDescription("You need to provide data for the chart.");
		lineChart.setDrawGridBackground(false); // 是否显示表格颜色
		lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
		lineChart.setTouchEnabled(true); // 设置是否可以触摸
		lineChart.setDragEnabled(true);// 是否可以拖拽
		lineChart.setScaleEnabled(true);// 是否可以缩放
		lineChart.setPinchZoom(true);// x,y轴是否可同时缩放
		lineChart.setDragDecelerationEnabled(true);//
		lineChart.setBackgroundColor(color);// 设置背景
		lineChart.setData(lineData); // 设置数据
		Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
		mLegend.setForm(LegendForm.CIRCLE);// 样式
		mLegend.setFormSize(6f);// 字体
		mLegend.setTextColor(Color.WHITE);// 颜色
		//      mLegend.setTypeface(mTf);// 字体

		lineChart.animateX(2500); // 立即执行的动画,x轴
	}

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				LineData mLineData = getLineData(mList.size());
				showChart(lineChart2, mLineData, Color.rgb(114, 188, 223));
			}
		};
	};
}
