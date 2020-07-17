package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.custom.SeekCircle;
import com.just.test.widget.MyActionBar;

/**
 * 仪表盘进度条
 * https://github.com/Necat0r/SeekCircle
 * Created by admin on 2017/6/14.
 */

public class SeekCircleTest extends Activity {

    private SeekCircle view_seekcircle;
    private TextView txt_seekcircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_seek_circle);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "仪表盘进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        view_seekcircle = (SeekCircle)findViewById(R.id.view_seekcircle);
        txt_seekcircle = (TextView)findViewById(R.id.txt_seekcircle);

        view_seekcircle.setOnSeekCircleChangeListener(new SeekCircle.OnSeekCircleChangeListener() {
            @Override
            public void onProgressChanged(SeekCircle seekCircle, int progress, boolean fromUser) {
                txt_seekcircle.setText(progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekCircle seekCircle) {

            }

            @Override
            public void onStopTrackingTouch(SeekCircle seekCircle) {

            }
        });
    }
}
