package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import q.rorbin.badgeview.QBadgeView;

/**
 * BadgeView 仿QQ未读消息数量
 *https://github.com/qstumn/BadgeView
 * compile 'q.rorbin:badgeview:1.1.0'
 * Created by admin on 2017/4/24.
 */

public class BadgeViews extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_badgeview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "仿QQ未读消息数量");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        TextView txt_badgeview1 = (TextView) findViewById(R.id.txt_badgeview1);
        TextView txt_badgeview2 = (TextView) findViewById(R.id.txt_badgeview2);
        Button btn_badgeview1 = (Button) findViewById(R.id.btn_badgeview1);
        Button btn_badgeview2 = (Button) findViewById(R.id.btn_badgeview2);
        CheckBox cb_badgeview = (CheckBox) findViewById(R.id.cb_badgeview);
        ImageView iv_badgeview = (ImageView) findViewById(R.id.iv_badgeview);

        new QBadgeView(BadgeViews.this).bindTarget(txt_badgeview1).setBadgeGravity(Gravity.START | Gravity.TOP).setBadgeNumber(6);
        new QBadgeView(BadgeViews.this).bindTarget(txt_badgeview2).setBadgeText("新消息");
        new QBadgeView(BadgeViews.this).bindTarget(btn_badgeview1).setBadgeNumber(15).setBadgeBackgroundColor(Color.YELLOW);
        new QBadgeView(BadgeViews.this).bindTarget(btn_badgeview2).setBadgeText("通知");
        new QBadgeView(BadgeViews.this).bindTarget(cb_badgeview).setBadgeNumber(3);
        new QBadgeView(BadgeViews.this).bindTarget(iv_badgeview).setBadgeText("不错").stroke(Color.BLACK,5,true);
    }
}
