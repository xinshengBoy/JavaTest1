package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.MessageEvent;
import com.just.test.widget.MyActionBar;

import org.greenrobot.eventbus.EventBus;

/**
 * eventbus测试
 * http://blog.csdn.net/itachi85/article/details/52205464
 * Created by admin on 2017/5/8.
 */

public class EventBusTest extends Activity {

    private TextView txt_eventBus_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_eventbus);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "倒计时和圆形进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        Button btn_eventBus_test = (Button) findViewById(R.id.btn_eventBus_test);
        txt_eventBus_test = (TextView)findViewById(R.id.txt_eventBus_test);

        btn_eventBus_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent(1,"测试成功"));
            }
        });
        //注册事件
        EventBus.getDefault().register(EventBusTest.this);
    }


}
