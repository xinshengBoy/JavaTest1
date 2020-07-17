package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 垂直翻页公告(MarqueeView)
 * https://github.com/sfsheng0322/MarqueeView
 * compile 'com.sunfusheng:marqueeview:1.2.0'
 * Created by admin on 2017/4/3.
 */

public class MarqueeViewActivity extends Activity {

    private MarqueeView marqueeview1,marqueeview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_marqueeview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "垂直翻页公告");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        //列表
        marqueeview1 = (MarqueeView)findViewById(R.id.marqueeview1);
        List<String> info = new ArrayList<>();
        info.add("1,素胚勾勒出青花笔锋浓转淡");
        info.add("2,瓶身描绘的牡丹一如你初妆");
        info.add("3,冉冉檀香透过窗心事我了然");
        info.add("4,宣纸上走笔至此搁一半");
        info.add("5,釉色渲染仕女图韵味被私藏");
        info.add("6,而你嫣然的一笑如含苞待放");
        info.add("7,你的美一缕飘散");
        info.add("8,去到我去不了的地方");
        info.add("9,天青色等烟雨");
        info.add("10,而我在等你");
        info.add("11,炊烟袅袅升起");
        info.add("12,隔江千万里");
        marqueeview1.startWithList(info);
        //设置监听
        marqueeview1.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(getApplicationContext(), String.valueOf(marqueeview1.getPosition()) + ". " + textView.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        //字符串
        marqueeview2 = (MarqueeView)findViewById(R.id.marqueeview2);
        String notice = "在瓶底书汉隶仿前朝的飘逸,就当我为遇见你伏笔,天青色等烟雨,而我在等你,月色被打捞起,晕开了结局,如传世的青花瓷自顾自美丽,你眼带笑意";
        marqueeview2.startWithText(notice);

    }

    @Override
    protected void onStart() {
        super.onStart();
        marqueeview1.startFlipping();
        marqueeview2.startFlipping();
    }

    @Override
    protected void onStop() {
        super.onStop();
        marqueeview1.stopFlipping();
        marqueeview2.stopFlipping();
    }
}
