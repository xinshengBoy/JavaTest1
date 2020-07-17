package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 对坐标系的监听
 * 参考网址：http://www.apkbus.com/blog-896840-64733.html
 * Created by admin on 2017/5/31.
 */

public class ViewCoordinate extends Activity {

    private TextView txt_coordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_coordinate);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "对坐标系的监听");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        txt_coordinate = (TextView)findViewById(R.id.txt_coordinate);
        txt_coordinate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        float coorX = event.getRawX();
                        float coorY = event.getRawY();
                        txt_coordinate.setText("坐标点：X = " + coorX + ",Y = " + coorY);
                        break;
                }

                return true;
            }
        });
    }
}
