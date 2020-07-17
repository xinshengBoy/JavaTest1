package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;

/**
 * kenburnsview  实现 Ken Burns effect 效果，达到身临其境效果的 ImageView
 * compile 'com.flaviofaria:kenburnsview:1.0.7'
 * https://github.com/flavioarfaria/KenBurnsView
 * Created by admin on 2017/5/15.
 */

public class KenburnsViewTest extends Activity {

    private KenBurnsView view_kenburns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_kenburns_view);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "倒计时和圆形进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        view_kenburns = (KenBurnsView) findViewById(R.id.view_kenburns);
        view_kenburns.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                Toast.makeText(KenburnsViewTest.this,"动画开始",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Toast.makeText(KenburnsViewTest.this,"动画结束",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        view_kenburns.resume();//开始动画
    }

    @Override
    protected void onPause() {
        super.onPause();
        view_kenburns.pause();//暂停动画
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        view_kenburns.restart();
    }
}
