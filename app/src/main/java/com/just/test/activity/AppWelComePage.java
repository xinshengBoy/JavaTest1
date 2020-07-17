package com.just.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.fragment.PageFrameLayout;
import com.just.test.widget.MyActionBar;

/**
 * 欢迎界面
 * Created by admin on 2017/5/8.
 */

public class AppWelComePage extends FragmentActivity {

    private PageFrameLayout contentFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_appwelcomepage);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "欢迎界面");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);


        contentFragment = (PageFrameLayout)findViewById(R.id.contentFragment);
        //设置资源文件和选中圆点
        contentFragment.setUpViews(new int[]{
                R.layout.page_welcome1,
                R.layout.page_welcome2,
                R.layout.page_welcome3,
                R.layout.page_welcome4,
                R.layout.page_welcome5
        },R.drawable.point_check,R.drawable.point_nocheck);

    }
}
