package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.RulerView;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 自定义直尺
 * 参考网址：http://www.itlanbao.com/code/100709.html
 * Created by admin on 2017/6/7.
 */

public class RulerViewTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rulerview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "自定义直尺");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        RulerView view_rulerview = (RulerView) findViewById(R.id.view_rulerview);
        view_rulerview.setOnScaleListener(new RulerView.OnScaleListener() {
            @Override
            public void onScaleChanged(int scale) {
                LemonBubble.showRight(RulerViewTest.this,scale+"厘米",3000);
            }
        });

//        view_rulerview.setOnClickedListener(new RulerView.onClickLister() {
//            @Override
//            public void onClickChanged(int scale) {
//                LemonBubble.showRight(RulerViewTest.this,scale+"厘米",3000);
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
    }
}
