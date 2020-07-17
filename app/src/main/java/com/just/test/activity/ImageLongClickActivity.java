package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 图片长按事件
 * @author user
 */
public class ImageLongClickActivity extends Activity {
    private ImageView show_image, hide_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_longclick_layout);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "图片长按");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        show_image = (ImageView) findViewById(R.id.show_image);
        hide_image = (ImageView) findViewById(R.id.hide_image);

        show_image.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    isSee(false);
                } else if ((event.getAction() == MotionEvent.ACTION_DOWN)) {
                    isSee(true);
                }
                return true;
            }
        });
    }

    /**
     * 判断图片是否可见
     * @param isSee 是否可见
     */
    private void isSee(boolean isSee) {
        if (isSee) {
            hide_image.setVisibility(View.VISIBLE);
            show_image.setVisibility(View.GONE);
        } else {
            show_image.setVisibility(View.VISIBLE);
            hide_image.setVisibility(View.GONE);
        }
    }
}
