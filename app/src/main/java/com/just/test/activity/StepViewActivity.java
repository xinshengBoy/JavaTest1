package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.VerticalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * StepView  指示器
 * https://github.com/baoyachi/StepView
 * compile 'com.github.baoyachi:StepView:1.9'
 * Created by admin on 2017/4/15.
 */

public class StepViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stepview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "指示器");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        HorizontalStepView step_view = (HorizontalStepView) findViewById(R.id.step_view);
        VerticalStepView step_vertical = (VerticalStepView) findViewById(R.id.step_vertical);
        //横向数据
        List<StepBean> mList = new ArrayList<>();
        //完成状态： 1，已完成，0，进行中，-1，未完成
        StepBean s1 = new StepBean("接单",1);
        StepBean s2 = new StepBean("打包",1);
        StepBean s3 = new StepBean("出发",1);
        StepBean s4 = new StepBean("送单",0);
        StepBean s5 = new StepBean("完成",-1);

        mList.add(s1);
        mList.add(s2);
        mList.add(s3);
        mList.add(s4);
        mList.add(s5);

        step_view.setStepViewTexts(mList)//设置数据
                .setTextSize(16)//设置字体大小
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this,android.R.color.holo_red_dark))//已完成的线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this,android.R.color.black))//未完成的线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this,android.R.color.holo_red_dark))//已完成字体的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this,android.R.color.black))//未完成字体的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this,R.drawable.point_check))//已完成的图标
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this,R.drawable.addicon))//默认的图标
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this,R.drawable.point_nocheck));

        //纵向数据
        List<String> mList2 = new ArrayList<>();
        mList2.add("您的订单已打印完毕");
        mList2.add("您的订单已拣货完成");
        mList2.add("扫描员已经扫描");
        mList2.add("打包成功");
        mList2.add("您的订单在京东【滑动外单分拣中心】发货完成，准备送往京东【北京通州分拣中心】");
        mList2.add("您的订单在京东【北京通州分拣中心】分拣完成");
        mList2.add("您的订单在京东【北京通州分拣中心】发货完成，准备送往京东【北京中关村大厦站】");
        mList2.add("您的订单在京东【北京中关村大厦站】验货完成，正在分配配送员");
        mList2.add("配送员【包牙齿】已出发，联系电话【130-0000-0000】，感谢您的耐心等待，才能加评价还能赢取好多礼物哟");
        mList2.add("感谢您在京东购物，欢迎您下次光临");

        step_vertical.setStepsViewIndicatorComplectingPosition(mList2.size()-3) //完成的步数
        .reverseDraw(false)  //默认为true
        .setStepViewTexts(mList2)  //总步骤
        .setLinePaddingProportion(0.85f) //设置指示线与线间距的比例系数
        .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this,android.R.color.holo_blue_dark))  //已完成的连接线
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this,android.R.color.darker_gray))  //未完成的连接线
                .setStepViewComplectedTextColor(ContextCompat.getColor(this,android.R.color.holo_red_dark))  //已完成的字体颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this,android.R.color.black))  //未完成的字体颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this,R.drawable.point_check))  //已完成的图标
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this,R.drawable.addicon))   //未完成的图标
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this,R.drawable.point_nocheck));  //正在进行的图标


    }
}
