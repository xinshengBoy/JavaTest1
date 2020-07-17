package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 日历列表
 * https://github.com/traex/CalendarListview
 * compile 'com.github.traex.calendarlistview:library:1.2.3'
 * Created by admin on 2017/5/10.
 */

public class CalendarListviewTest extends Activity implements DatePickerController{

    private DayPickerView listview_calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calendar_listview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "日历列表");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        listview_calendar = (DayPickerView)findViewById(R.id.listview_calendar);
        listview_calendar.setController(this);
    }

    @Override
    public int getMaxYear() {
        return 2020;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        LemonBubble.showRight(CalendarListviewTest.this,"当前选择的是："+year+"年"+(month+1)+"月"+day+"日",2000);
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
        LemonBubble.showRight(CalendarListviewTest.this,"当前选择的是："+selectedDays.getFirst().toString()+"------"+selectedDays.getLast().toString(),2000);
    }
}
