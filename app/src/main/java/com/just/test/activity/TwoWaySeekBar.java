package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.custom.MaterialRangeSlider;
import com.just.test.widget.MyActionBar;

/**
 * 双向滑动条
 * Created by admin on 2017/6/14.
 */

public class TwoWaySeekBar extends Activity implements MaterialRangeSlider.RangeSliderListener{

    private MaterialRangeSlider view_twoWaySeekBar;
    private TextView txt_seekbar_min,txt_seekbar_max;
    private int currentMin = 30;
    private int currentMax = 66;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_twoway_seekbar);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "双向滑动条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        txt_seekbar_min = (TextView)findViewById(R.id.txt_seekbar_min);
        txt_seekbar_max = (TextView)findViewById(R.id.txt_seekbar_max);
        view_twoWaySeekBar = (MaterialRangeSlider)findViewById(R.id.view_twoWaySeekBar);
        view_twoWaySeekBar.setMin(0);
        view_twoWaySeekBar.setMax(100);
        view_twoWaySeekBar.setStartingMinMax(currentMin,currentMax);
        view_twoWaySeekBar.setRangeSliderListener(this);

        txt_seekbar_min.setText(currentMin+"%");
        txt_seekbar_max.setText(currentMax+"%");
    }

    @Override
    public void onMaxChanged(int newValue) {
        txt_seekbar_max.setText(newValue+"%");
    }

    @Override
    public void onMinChanged(int newValue) {
        txt_seekbar_min.setText(newValue+"%");
    }
}
