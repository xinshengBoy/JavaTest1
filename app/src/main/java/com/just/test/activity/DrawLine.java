package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.github.mikephil.charting.charts.LineChart;

/**
 * 折线图
 * 参考文章：http://blog.csdn.net/qq_37293612/article/details/54959726
 */
public class DrawLine extends Activity {

	private LineChart view_lineChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mpandroid_chart);
//		DrawLineOne drawLineOne = new DrawLineOne(DrawLine.this);
//		setContentView(drawLineOne);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "折线图2");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

//		initView();
	}

//	private void initView() {
//		view_lineChart = (LineChart)findViewById(R.id.view_lineChart);
//
//		view_lineChart.setBackgroundColor(0xffFFDEAB);//图表背景颜色
////		view_lineChart.setNoDataTextDescription("暂无数据");//无数据时显示的内容
//		view_lineChart.setDrawBorders(false);//是否启用回执图表边框
//
//		//// TODO: 2017/7/19 X轴
//		XAxis xAxis = view_lineChart.getXAxis();
//		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴显示在底部
//		xAxis.setDrawGridLines(false);//是否显示竖的表格线
//		xAxis.setDrawLabels(true);//是否绘制标签
//		xAxis.setTextColor(Color.GRAY);//标签字体颜色
//		xAxis.setTextSize(16f);
//		//// TODO: 2017/7/19 Y轴
//		YAxis yAxis = view_lineChart.getAxisLeft();
//		yAxis.setEnabled(false);
//
//		// TODO: 2017/7/19 设置数据
//		List<Entry> entries = new ArrayList<>();
//		for (int i=1;i<15;i++){
//			Random random = new Random();
//			entries.add(new Entry(i,random.nextInt(30)+20));
//		}
//
//		LineDataSet dataSet = new LineDataSet(entries,"");
//		dataSet.setColor(0xffFF6634);
//		dataSet.setValueTextColor(0xffFF6634);
//		dataSet.setValueTextSize(14f);
//
////		LineData lineData = new LineData(dataSet);
////		view_lineChart.setData(lineData);
//		view_lineChart.animateX(500); // 立即执行的动画,x轴
//	}

	/**
	 * 折线图生成一个数据
	 * @return
	 */
//	private LineData getLineData() {
//		ArrayList<String> xValues = new ArrayList<String>();
//		for (int i = 24; i < 31; i++) {
//			// x轴显示的数据，这里默认使用数字下标显示
//			xValues.add("" + i);
//		}
//
//		// y轴的数据
//		ArrayList<Entry> yValues = new ArrayList<Entry>();
//		yValues.add(new Entry(0.051f, 0));
//		yValues.add(new Entry(0.050f, 1));
//		yValues.add(new Entry(0.048f, 2));
//		yValues.add(new Entry(0.049f, 3));
//		yValues.add(new Entry(0.047f, 4));
//		yValues.add(new Entry(0.046f, 5));
//		yValues.add(new Entry(0.047f, 6));

		// y轴的数据集合
//		LineDataSet lineDataSet = new LineDataSet(yValues,"");

		//用y轴的集合来设置参数
//		lineDataSet.setLineWidth(1f); // 线宽
//		lineDataSet.setCircleSize(1f);// 显示的圆形大小
//		lineDataSet.setColor(0xffF89354);// 显示颜色
//		lineDataSet.setCircleColor(0xffF89354);// 圆形的颜色
//		lineDataSet.setHighLightColor(0xffF89354); // 高亮的线的颜色
//		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
//		lineDataSets.add(lineDataSet); // add the datasets

//		LineDataSet lineDataSet = getLineData(15);
//		LineData lineData = new LineData(xValues, lineDataSets);
//
//		return lineData;
//	}

//	private LineDataSet getLineData(int count) {
//		List<Entry> entries = new ArrayList<>();
//		for (int i=1;i<count;i++){
//			Random random = new Random();
//			entries.add(new Entry(i,random.nextInt(30)+20));
//		}
//
//		LineDataSet dataSet = new LineDataSet(entries,"");
//		dataSet.setColor(0xffFF6634);
//		dataSet.setValueTextColor(0xffFF6634);
//		dataSet.setValueTextSize(14f);
//
//		return dataSet;
//		ArrayList<String> xValues = new ArrayList<String>();
//		for (int i = 0; i < count; i++) {
//			// x轴显示的数据，这里默认使用数字下标显示
//			xValues.add("" + i);
//		}
//
//		// y轴的数据
//		ArrayList<Entry> yValues = new ArrayList<Entry>();
//		for (int i = 0; i < count; i++) {
//			float value = (float) (Math.random() * range) + 3;
//			yValues.add(new Entry(value, i));
//		}
//
//		// create a dataset and give it a type
//		// y轴的数据集合
//		LineDataSet lineDataSet = new LineDataSet(yValues, "测试折线图" /*显示在比例图上*/);
//		// mLineDataSet.setFillAlpha(110);
//		// mLineDataSet.setFillColor(Color.RED);
//
//		//用y轴的集合来设置参数
//		lineDataSet.setLineWidth(1.75f); // 线宽
//		lineDataSet.setCircleSize(3f);// 显示的圆形大小
//		lineDataSet.setColor(Color.WHITE);// 显示颜色
//		lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色
//		lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色
//
//		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
//		lineDataSets.add(lineDataSet); // add the datasets
//
//		// create a data object with the datasets
//		LineData lineData = new LineData(xValues, lineDataSets);
//
//		return lineData;
//	}
}
