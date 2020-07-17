package com.just.test.activity;

import java.util.ArrayList;

import com.just.test.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
/**
 * 图表  MPAndroidChart
 * @author user
 *
 */
public class MPAndroidChart extends Activity {
	private TextView txt_hellocharts;
	private LineChart mLineChart;//折线图
	private PieChart pieChart;//饼状图
	private BarChart barChart;//条形图
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_hellocharts);

//		txt_hellocharts = (TextView)findViewById(R.id.txt_hellocharts);
		//这里要算出电话号码，首先得到index数组的值，这个值其实就是下标，对应arr数组里面的第几个数值
		//arr数组存储的就是你电话号码所包含的所有数值，但不能重复，
		//通过index数组的下标，来找出在arr数组所对应的数值，得到的就是电话号码了
//		13681658054
//		int [] arr = new int[]{4,5,6,8,3,1,0};
//		int [] index = new int[]{5,4,2,3,5,2,1,3,6,1,0};
//		int [] arr = new int[]{8,2,1,0,3};
//		int [] index = new int[]{2,0,3,2,4,0,1,3,2,3,3};
//		String tel = "";
//		for (int i : index) {
//			tel+=arr[i];
//		}
//		txt_hellocharts.setText("联系方式："+tel);

		//MPAndroidChart
		//折线图
		mLineChart = (LineChart)findViewById(R.id.lineChart);
		LineData mLineData = getLineData(36, 100);
		showChart(mLineChart, mLineData, Color.rgb(114, 188, 223));
		//饼图
		pieChart = (PieChart)findViewById(R.id.pieChart);
		PieData mPieData = getPieData(5, 100);
		showPieChart(pieChart,mPieData);
		//条形图
		barChart = (BarChart)findViewById(R.id.barChart);
		BarData mBarData = getBarData(6, 100);
		showBarChart(barChart,mBarData);
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
		lineChart.setPinchZoom(false);//
		lineChart.setBackgroundColor(color);// 设置背景
		lineChart.setData(lineData); // 设置数据
		Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
		mLegend.setForm(LegendForm.CIRCLE);// 样式
		mLegend.setFormSize(6f);// 字体
		mLegend.setTextColor(Color.WHITE);// 颜色
		//      mLegend.setTypeface(mTf);// 字体

		lineChart.animateX(2500); // 立即执行的动画,x轴
	}

	/**
	 * 折线图生成一个数据
	 * @param count 表示图表中有多少个坐标点
	 * @param range 用来生成range以内的随机数
	 * @return
	 */
	private LineData getLineData(int count, float range) {
		ArrayList<String> xValues = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			// x轴显示的数据，这里默认使用数字下标显示
			xValues.add("" + i);
		}

		// y轴的数据
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		for (int i = 0; i < count; i++) {
			float value = (float) (Math.random() * range) + 3;
			yValues.add(new Entry(value, i));
		}

		// y轴的数据集合
		LineDataSet lineDataSet = new LineDataSet(yValues, "测试折线图" /*显示在比例图上*/);
		// mLineDataSet.setFillAlpha(110);
		// mLineDataSet.setFillColor(Color.RED);

		//用y轴的集合来设置参数
		lineDataSet.setLineWidth(1.75f); // 线宽
		lineDataSet.setCircleSize(3f);// 显示的圆形大小
		lineDataSet.setColor(Color.WHITE);// 显示颜色
		lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色
		lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色

		//-----------------------------
		//加入中间这段代码原来的折线图就变成了填充图了
//	        lineDataSet.setDrawCircles(false);//图表上的数据点不用圆圈显示
//	        lineDataSet.setDrawCubic(true);//设置允许曲线平滑
//	        lineDataSet.setCubicIntensity(0.6f);//设置折现的平滑度
//	        lineDataSet.setDrawFilled(true);//设置允许填充
//	        lineDataSet.setFillColor(Color.rgb(0, 255, 255));//设置填充颜色
		//------------------------------
		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
		lineDataSets.add(lineDataSet); // add the datasets
		LineData lineData = new LineData(xValues, lineDataSets);

		return lineData;
	}

	/**
	 * 饼图数据
	 */
	private PieData getPieData(int count,float range) {
		ArrayList<String> xValues = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			xValues.add("饼"+(i+1));//饼块上显示的文字
		}
		//饼块的实际数据
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		float bing1 = 15;//百分比
		float bing2 = 25;
		float bing3 = 35;
		float bing4 = 5;
		float bing5 = 20;
		yValues.add(new Entry(bing1, 0));//添加数据
		yValues.add(new Entry(bing2, 1));
		yValues.add(new Entry(bing3, 2));
		yValues.add(new Entry(bing4, 3));
		yValues.add(new Entry(bing5, 4));

		//y轴集合
		PieDataSet pieDataSet = new PieDataSet(yValues, "权威统计");
		pieDataSet.setSliceSpace(1);//每个饼状图之间的距离
		//饼图颜色集合
		ArrayList<Integer> colors = new ArrayList<Integer>();
		colors.add(Color.rgb(205, 205, 205));
		colors.add(Color.rgb(114, 188, 223));
		colors.add(Color.rgb(255, 123, 124));
		colors.add(Color.rgb(57, 135, 200));
		colors.add(Color.rgb(255, 56, 167));
		//给饼图设置颜色
		pieDataSet.setColors(colors);

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = 5 * (metrics.densityDpi / 160f);
		pieDataSet.setSelectionShift(px);//选中太多出来的长度

		PieData pieData = new PieData(xValues,pieDataSet);

		return pieData;
	}
	//显示饼状图
	private void showPieChart(PieChart pieCharts, PieData mPieData) {
		pieCharts.setHoleColorTransparent(true);
		pieCharts.setHoleRadius(60f);//半径,0为实心圆
		pieCharts.setTransparentCircleRadius(64f);//半透明圈
		pieCharts.setDescription("测试饼状图");//图表描述
		pieCharts.setDrawCenterText(true);//饼状图中间可添加文字
		pieCharts.setDrawHoleEnabled(true);
		pieCharts.setRotationAngle(90);//初始旋转角度
		pieCharts.setRotationEnabled(true);//是否可以手动旋转
		pieCharts.setUsePercentValues(true);//显示成百分比
		pieCharts.setCenterText("成绩占比");
		pieCharts.setData(mPieData);//设置数据

		Legend mLegend = pieCharts.getLegend();//设置比例图
		mLegend.setPosition(LegendPosition.RIGHT_OF_CHART);//最右边显示
		mLegend.setXEntrySpace(7f);
		mLegend.setYEntrySpace(5f);

		pieCharts.animateXY(1000,1000);//设置动画
	}
	/**
	 * 条形图数据
	 */
	private BarData getBarData(int count,float range) {
		ArrayList<String> xValues = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			xValues.add("第"+(i+1)+"季度");
		}

		ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
		for (int i = 0; i < count; i++) {
			float value = (float) ((Math.random() * range) +3);
			yValues.add(new BarEntry(value, i));
		}

		//y轴数据集合
		BarDataSet barDataSet = new BarDataSet(yValues, "测试条形图");
		barDataSet.setColor(Color.argb(114, 188, 233, 122));

		ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
		barDataSets.add(barDataSet);

		BarData barData = new BarData(xValues,barDataSets);

		return barData;
	}
	/**
	 * 显示条形图
	 */
	private void showBarChart(BarChart barCharts, BarData mBarData) {
		barCharts.setDrawBorders(false);//是否在折线图上添加边框
		barCharts.setDescription("2016年销售情况");//数据描述
		barCharts.setNoDataTextDescription("暂无数据");//无数据时的显示
		barCharts.setDrawGridBackground(false);//是否显示表格颜色
		barCharts.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF);// 表格的的颜色，在这里是是给颜色设置一个透明度
		barCharts.setTouchEnabled(true);//是否可触摸
		barCharts.setDragEnabled(true);//是否可拖拽
		barCharts.setScaleEnabled(true);//是否可缩放
		barCharts.setPinchZoom(false);
		barCharts.setDrawBarShadow(true);
		barCharts.setData(mBarData);//设置数据

		Legend legend = barCharts.getLegend();
		legend.setForm(LegendForm.CIRCLE);//样式
		legend.setFormSize(6f);//字体大小
		legend.setTextColor(Color.BLACK);//字体颜色

		barCharts.animateX(2500);//立即执行动画（x轴）
	}
}
