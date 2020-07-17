package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.animotion.DoubleNumberView;
import com.just.test.animotion.SecondsNumberView;
import com.just.test.widget.MyActionBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 炫酷的计时器
 * Created by admin on 2017/5/13.
 */

public class NumberSwichTest extends Activity {

    private DoubleNumberView hour_numberview, minite_numberview;
    private SecondsNumberView second_numberview;
    private EditText et_numberview;
    private int number = 0, hourNumber = 0, miniteNumber = 0, secondNumber = 0;
    private TimerTask task;
    private boolean isStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_number_swich);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "炫酷的计时器");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        hour_numberview = (DoubleNumberView) findViewById(R.id.hour_numberview);
        minite_numberview = (DoubleNumberView) findViewById(R.id.minite_numberview);
        second_numberview = (SecondsNumberView) findViewById(R.id.second_numberview);

        et_numberview = (EditText) findViewById(R.id.et_numberview);
        final Button btn_numberview = (Button) findViewById(R.id.btn_numberview);
        btn_numberview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStart) {
                    task.cancel();
                    btn_numberview.setText("启动");
                    isStart = false;
                    number = 0;
                    hourNumber = 0;
                    miniteNumber = 0;
                    secondNumber = 0;
                    hour_numberview.setText(hourNumber);
                    minite_numberview.setText(miniteNumber);
                    second_numberview.setText(secondNumber);
                } else {
//                    task.cancel();
                    btn_numberview.setText("停止");
                    isStart = true;

                    String result = et_numberview.getText().toString();
                    final int input = Integer.parseInt(result);//分钟
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            int inputTotal = input * 60;
                            int nowToatl = secondNumber + miniteNumber * 60 + hourNumber * 60 * 60;
                            if (nowToatl - inputTotal <= 0) {
                                number++;
                                if (number < 60) {
                                    secondNumber = number;//时间小于一分钟
                                } else if (number == 60) {
                                    number = 0;
                                    secondNumber = number;
                                    if (miniteNumber < 60) {
                                        miniteNumber += 1;
                                    } else if (miniteNumber == 60) {
                                        miniteNumber = 0;
                                        hourNumber += 1;
                                    }
                                }
                                handler.obtainMessage().sendToTarget();
                            } else {
                                task.cancel();
                            }
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, 1000, 1000);
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hour_numberview.setText(hourNumber);
            minite_numberview.setText(miniteNumber);
            second_numberview.setText(secondNumber);
        }
    };
}
