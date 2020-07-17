package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import qiu.niorgai.StatusBarCompat;

/**
 * 状态栏沉浸
 * Created by admin on 2017/5/5.
 */

public class StateColume extends Activity {

    private int [] COLORS = new int[]{Color.RED,Color.WHITE,Color.BLACK,Color.BLUE,Color.GREEN,Color.GRAY,Color.YELLOW,Color.TRANSPARENT};
    private int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LinearLayout layout = new LinearLayout(this);

        final TextView textView = new TextView(this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(param);
        textView.setText("状态栏沉浸");
        textView.setTextSize(50);
        textView.setGravity(Gravity.CENTER);
        layout.addView(textView);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.setBackgroundColor(COLORS[position]);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setTextColor(COLORS[position]);
                position += 1;
                if (COLORS.length <= position ){
                    position = 0;
                }
                layout.setBackgroundColor(COLORS[position]);

            }
        });
        setContentView(layout);
        StatusBarCompat.translucentStatusBar(StateColume.this);
    }
}
