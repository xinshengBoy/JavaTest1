package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

/**
 * timesqure 日历控件
 *compile 'com.squareup:android-times-square:1.6.5@aar'
 * https://github.com/square/android-times-square
 * Created by admin on 2017/4/25.
 */

public class TimeSqure extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timesqure);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "日历控件");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        CalendarPickerView calender_view = (CalendarPickerView) findViewById(R.id.calender_view);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);

        Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR,-1);

        calender_view.init(lastYear.getTime(),nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(new Date());
    }
}
