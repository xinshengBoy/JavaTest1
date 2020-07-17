package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import static com.just.test.R.id.txt_scroller;

/**
 * 可滑动的文字和菜单
 * 系统自带的滑动或滚动  Scorller
 * 参考文章：https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&amp;mid=2247484893&amp;idx=1&amp;sn=5874130932d4533064e40045055d0185&amp;chksm=96cda490a1ba2d86491a65f34513e50b80a5d0ccbedae644225bc0a3d262505d43381b603310&amp;scene=21#wechat_redirect
 * Created by admin on 2017/6/26.
 * Created by Administrator on 2017/1/12.
 */

public class MyScrollText extends Activity {

    private LinearLayout scroller_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scrolltext);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "可滑动的文字和菜单");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView() {
        scroller_layout = (LinearLayout) findViewById(R.id.scroller_layout);
        Button btn_scroller_reset = (Button) findViewById(R.id.btn_scroller_reset);
        Button btn_scroller_start = (Button) findViewById(R.id.btn_scroller_start);

        btn_scroller_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroller_layout.scrollTo(0, 0);
            }
        });

        btn_scroller_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当点击startScrollby的时，让LinearLayout里面的textview向右滚动100px，这里为什么是-100呢，按照坐标轴来说100才是向右移动才对啊！
                //当时我也是一脸懵逼的，突然一想，不对，移动的并不是textview，而是linearlayout的可视区域，视觉上的textview向右滚，其实是linearlayout的可视区域向左移动，所以是-100；当点击startScrollto的时候，我们让linearlayout的可视区域回到原点。
                scroller_layout.scrollBy(-100, 0);
            }
        });
    }
}
