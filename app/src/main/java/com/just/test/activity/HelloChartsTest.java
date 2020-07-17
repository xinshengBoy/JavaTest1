package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class HelloChartsTest extends Activity {
	private LineChartView lineChartView;
	private LineChart lineChart_test;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_hellocharts_test);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "折线图");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		lineChartView = (LineChartView)findViewById(R.id.lineChartView);
		lineChart_test = (LineChart)findViewById(R.id.lineChart_test);

		List<PointValue> mPointValues = new ArrayList<PointValue>();
		List<AxisValue> mAxisValues = new ArrayList<AxisValue>();
		for (int i = 0; i < 20; i++) {
			mPointValues.add(new PointValue(i, new Random().nextInt(10)));
			mAxisValues.add(new AxisValue(i).setLabel(i+""));//为每个对应的i设置相应的label(显示在X轴)
		}
		Line line = new Line(mPointValues).setColor(Color.GREEN).setCubic(true);
		line.setStrokeWidth(1);//线条的宽度
		List<Line> lines = new ArrayList<Line>();
		lines.add(line);
		LineChartData data = new LineChartData();
		data.setLines(lines);
		//坐标轴
		Axis axisX = new Axis();//x轴
		axisX.setHasTiltedLabels(true);
		axisX.setTextColor(Color.BLACK);
		axisX.setName("采集时间");
		axisX.setMaxLabelChars(10);
		axisX.setValues(mAxisValues);
		data.setAxisXBottom(axisX);

		Axis axisY = new Axis();//x轴
		axisY.setHasTiltedLabels(true);
		axisY.setTextColor(Color.BLACK);
		axisY.setName("采集量");
		axisY.setMaxLabelChars(7);//默认是3，只能看最后三个数字
		data.setAxisYLeft(axisY);

		//设置行为属性，支持缩放、滑动及平移等
		lineChartView.setInteractive(true);
		lineChartView.setZoomType(ZoomType.HORIZONTAL);
		lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
		lineChartView.setLineChartData(data);
		lineChartView.setVisibility(View.VISIBLE);

		LineData mLineData = getLineData();
		showChart(lineChart_test, mLineData, 0xffFFDEAB);
	}

	/**
	 * 折线图生成一个数据
	 * @return
	 */
	private LineData getLineData() {
		ArrayList<String> xValues = new ArrayList<String>();
		for (int i = 24; i < 31; i++) {
			// x轴显示的数据，这里默认使用数字下标显示
			xValues.add("" + i);
		}

		// y轴的数据
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		yValues.add(new Entry(0.051f, 0));
		yValues.add(new Entry(0.050f, 1));
		yValues.add(new Entry(0.048f, 2));
		yValues.add(new Entry(0.049f, 3));
		yValues.add(new Entry(0.047f, 4));
		yValues.add(new Entry(0.046f, 5));
		yValues.add(new Entry(0.047f, 6));
//		for (int i = 0; i < count; i++) {
//			float value = (float) (Math.random() * range) + 3;
//			yValues.add(new Entry(value, i));
//		}

		// y轴的数据集合
		LineDataSet lineDataSet = new LineDataSet(yValues,"");
		// mLineDataSet.setFillAlpha(110);
		// mLineDataSet.setFillColor(Color.RED);

		//用y轴的集合来设置参数
		lineDataSet.setLineWidth(1f); // 线宽
		lineDataSet.setCircleSize(1f);// 显示的圆形大小
		lineDataSet.setColor(0xffF89354);// 显示颜色
		lineDataSet.setCircleColor(0xffF89354);// 圆形的颜色
		lineDataSet.setHighLightColor(0xffF89354); // 高亮的线的颜色

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

	// 折线图设置显示的样式
	private void showChart(LineChart lineChart, LineData lineData, int color) {
		lineChart.setDrawBorders(false);  //是否在折线图上添加边框

		lineChart.setDescription("");// 数据描述
		// 如果没有数据的时候，会显示这个，类似listview的emtpyview
		lineChart.setNoDataTextDescription("You need to provide data for the chart.");
		lineChart.setDrawGridBackground(false); // 是否显示表格颜色
		lineChart.setGridBackgroundColor(color & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
		lineChart.setTouchEnabled(true); // 设置是否可以触摸
		lineChart.setDragEnabled(true);// 是否可以拖拽
		lineChart.setScaleEnabled(false);// 是否可以缩放
		lineChart.setPinchZoom(false);//
		lineChart.setBackgroundColor(color);// 设置背景
		lineChart.setDrawBorders(false);//是否设置里面的网格
		lineChart.setData(lineData); // 设置数据
		Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
		mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
		mLegend.setFormSize(6f);// 字体
		mLegend.setTextColor(Color.WHITE);// 颜色

		lineChart.animateX(500); // 立即执行的动画,x轴
	}
}
