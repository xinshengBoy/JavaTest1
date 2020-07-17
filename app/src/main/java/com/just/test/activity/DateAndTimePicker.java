package com.just.test.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.util.Locale;

/**
 * 日期时间选择器
 * Created by Administrator on 2017/2/13.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class DateAndTimePicker extends Activity implements View.OnClickListener{

    private Button date,time;
    DateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat fmtTime = new SimpleDateFormat("HH:mm:ss");
    Calendar calendar = Calendar.getInstance(Locale.CHINA);
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            upDateDate();
        }
    };



    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);

            upDateTime();
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_date_time_picker);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "日期时间选择器");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        date = (Button)findViewById(R.id.btn_dateAndTimePicker_Date);
        time = (Button)findViewById(R.id.btn_dateAndTimePicker_Time);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == date){
            DatePickerDialog dialog = new DatePickerDialog(DateAndTimePicker.this,d,calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if (view == time){
            TimePickerDialog dialog = new TimePickerDialog(DateAndTimePicker.this,t,
                    calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
            dialog.show();
        }
    }

    private void upDateDate() {
        date.setText(fmtDate.format(calendar.getTime()));
    }

    private void upDateTime() {
        time.setText(fmtTime.format(calendar.getTime()));
    }

}
