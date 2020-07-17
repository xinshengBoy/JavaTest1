package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.PieChartView;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义饼状图
 * https://github.com/wuseal/PieChartView
 * Created by admin on 2017/5/15.
 */

public class PieChartViewTest extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_piechartview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "自定义饼状图");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        PieChartView iv_piechartview = (PieChartView) findViewById(R.id.iv_piechartview);
        //添加数据
        List<PieChartView.PieceDataHolder> mList = new ArrayList<>();
        mList.add(new PieChartView.PieceDataHolder(30, Color.RED,"火车"));
        mList.add(new PieChartView.PieceDataHolder(30, Color.BLUE,"汽车"));
        mList.add(new PieChartView.PieceDataHolder(10, Color.GREEN,"飞机"));
        mList.add(new PieChartView.PieceDataHolder(5, Color.YELLOW,"轮船"));
        mList.add(new PieChartView.PieceDataHolder(20, Color.GRAY,"高铁"));
        mList.add(new PieChartView.PieceDataHolder(5, Color.BLACK,"11路"));

        iv_piechartview.setData(mList);
    }
}
