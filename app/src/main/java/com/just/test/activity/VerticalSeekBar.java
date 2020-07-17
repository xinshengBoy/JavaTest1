package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.custom.PopVerticalSeekbarView;
import com.just.test.widget.MyActionBar;

/**
 * 垂直拖动条
 * http://www.itlanbao.com/code/20150918/10000/100538.html
 * Created by admin on 2017/6/14.
 */

public class VerticalSeekBar extends Activity {

    private PopVerticalSeekbarView seekBar_vertical;
    private TextView txt_vertical_seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vertical_seekbar);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "垂直拖动条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        seekBar_vertical = (PopVerticalSeekbarView) findViewById(R.id.seekBar_vertical);
        txt_vertical_seekbar = (TextView)findViewById(R.id.txt_vertical_seekbar);
        seekBar_vertical.setOnSeekBarChangeListener(new PopVerticalSeekbarView.SeekBarListener() {
            @Override
            public void onChange(int progress) {
                txt_vertical_seekbar.setText(progress+"%");
            }
        });
    }
}
