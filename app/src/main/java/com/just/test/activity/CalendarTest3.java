package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.maning.calendarlibrary.MNCalendar;
import com.maning.calendarlibrary.listeners.OnCalendarChangeListener;
import com.maning.calendarlibrary.listeners.OnCalendarItemClickListener;
import com.maning.calendarlibrary.model.Lunar;
import com.maning.calendarlibrary.model.MNCalendarConfig;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日历控件3 可显示阴历，可自定义,除了横向的日历，还有垂直的日历
 * 参考网址：https://github.com/maning0303/MNCalendar
 * compile 'com.github.maning0303:MNCalendar:V1.0.2'
 * Created by admin on 2017/7/14.
 */

public class CalendarTest3 extends Activity {

    private MNCalendar view_mncalendar1,view_mncalendar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calendar3);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "日历控件3");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView() {
        view_mncalendar1 = (MNCalendar)findViewById(R.id.view_mncalendar1);
        view_mncalendar2 = (MNCalendar)findViewById(R.id.view_mncalendar2);

        //// TODO: 2017/7/14 系统默认样式
        final SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        view_mncalendar1.setOnCalendarItemClickListener(new OnCalendarItemClickListener() {
            @Override
            public void onClick(Date date, Lunar lunar) {
                LemonBubble.showRight(CalendarTest3.this,"阳历："+format.format(date)+"\n"+"农历:"+lunar.lunarYear+"年"+lunar.lunarMonth+"月"+lunar.lunarDay+"日",2000);
            }

            @Override
            public void onLongClick(Date date) {
                LemonBubble.showRight(CalendarTest3.this,format.format(date),1000);
            }
        });
        view_mncalendar1.setOnCalendarChangeListener(new OnCalendarChangeListener() {
            @Override
            public void lastMonth() {
                LemonBubble.showRight(CalendarTest3.this,format.format(view_mncalendar1.getCurrentDate()),1000);
            }

            @Override
            public void nextMonth() {
                LemonBubble.showRight(CalendarTest3.this,format.format(view_mncalendar1.getCurrentDate()),1000);
            }
        });
        //// TODO: 2017/7/14 自定义样式
        MNCalendarConfig config = new MNCalendarConfig.Builder()
                .setMnCalendar_colorWeek("#ff00ff00")    //星期栏的文字颜色
                .setMnCalendar_colorLunar("#ff0000")    //阴历的文字颜色
                .setMnCalendar_colorSolar("#9BCCAF")    //阳历的文字颜色
                .setMnCalendar_colorTodayBg("#FF0000")  //今天的圆形背景颜色
                .setMnCalendar_colorTodayText("#000000")//今天文字的颜色
                .setMnCalendar_colorOtherMonth("#F1EDBD")//不是本月的文字颜色
                .setMnCalendar_colorTitle("#FF0000")    //标题的颜色（文字和箭头）
                .setMnCalendar_showLunar(true)          //是否显示阴历
                .setMnCalendar_showWeek(true)           //是否显示星期栏
                .setMnCalendar_showTitle(true)          //是否显示标题栏
                .build();
        view_mncalendar2.setConfig(config);
        view_mncalendar2.setOnCalendarItemClickListener(new OnCalendarItemClickListener() {
            @Override
            public void onClick(Date date,Lunar lunar) {
                LemonBubble.showRight(CalendarTest3.this,"阳历："+format.format(date)+"\n"+"农历:"+lunar.lunarYear+"年"+lunar.lunarMonth+"月"+lunar.lunarDay+"日",2000);
            }

            @Override
            public void onLongClick(Date date) {
                LemonBubble.showRight(CalendarTest3.this,format.format(date),1000);
            }
        });
        view_mncalendar2.setOnCalendarChangeListener(new OnCalendarChangeListener() {
            @Override
            public void lastMonth() {
                LemonBubble.showRight(CalendarTest3.this,format.format(view_mncalendar2.getCurrentDate()),1000);
            }

            @Override
            public void nextMonth() {
                LemonBubble.showRight(CalendarTest3.this,format.format(view_mncalendar2.getCurrentDate()),1000);
            }
        });
    }
}
