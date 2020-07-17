package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * 滑动监听
 * Created by Administrator on 2017/2/13.
 */

public class TouchEventSimple extends Activity {

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            x1 = event.getX();
            y1 = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP){
            x2 = event.getX();
            y2 = event.getY();

            if (y1 - y2 > 50){
                Toast.makeText(TouchEventSimple.this,"上滑了",Toast.LENGTH_SHORT).show();
            }else if (y2 - y1 > 50){
                Toast.makeText(TouchEventSimple.this,"下滑了",Toast.LENGTH_SHORT).show();
            }else if (x1 - x2 > 50){
                Toast.makeText(TouchEventSimple.this,"左滑了",Toast.LENGTH_SHORT).show();
            }else if (x2 - x1 > 50){
                Toast.makeText(TouchEventSimple.this,"右滑了",Toast.LENGTH_SHORT).show();
            }
        }

        return super.onTouchEvent(event);
    }
}
