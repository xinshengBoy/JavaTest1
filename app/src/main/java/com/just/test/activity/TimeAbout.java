package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**时间相关
 * Created by Administrator on 2017/3/1.
 */

public class TimeAbout extends Activity {

    private EditText et_chrometer_start;
    private Button btn_chrometer_start;
    private Chronometer chronometer_test;
    private int inputTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about_time);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "时间相关");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        et_chrometer_start = (EditText)findViewById(R.id.et_chrometer_start);
        btn_chrometer_start = (Button)findViewById(R.id.btn_chrometer_start);
        chronometer_test = (Chronometer)findViewById(R.id.chronometer_test);

        btn_chrometer_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String times = et_chrometer_start.getText().toString();
                if (times != null && !times.equals("")) {
                    inputTime = Integer.parseInt(times);
                    if (inputTime < 0) {
                        Toast.makeText(TimeAbout.this, "请输入", Toast.LENGTH_SHORT).show();
                    } else {
                        chronometer_test.setBase(SystemClock.elapsedRealtime());
                        chronometer_test.start();
                        btn_chrometer_start.setEnabled(false);
                        et_chrometer_start.setEnabled(false);
                    }
                }
            }
        });

        chronometer_test.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (SystemClock.elapsedRealtime() - chronometer_test.getBase() > inputTime*1000*60){
                    chronometer_test.stop();
                    et_chrometer_start.setEnabled(true);
                    btn_chrometer_start.setEnabled(true);
                }
            }
        });
    }
}
