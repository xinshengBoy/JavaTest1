package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.RoundImageView;
import com.just.test.widget.MyActionBar;

/**
 * 圆形图片
 * http://blog.csdn.net/u011192530/article/details/53836546
 * Created by admin on 2017/5/5.
 */

public class RoundImageViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_roundimageview);
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "圆形图片");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        RoundImageView iv_roundimageview = (RoundImageView)findViewById(R.id.iv_roundimageview);
        iv_roundimageview.setPadding(100,0,0,0);
    }
}
