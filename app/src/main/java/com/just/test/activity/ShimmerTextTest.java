package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.Titanic;
import com.just.test.custom.TitanicTextView;
import com.just.test.widget.MyActionBar;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerButton;
import com.romainpiel.shimmer.ShimmerTextView;

/**
 * shimmerTextView  淡淡发光的文字，类似于提示滑动解锁的样式
 * compile 'com.romainpiel.shimmer:library:1.4.0@aar'
 * https://github.com/RomainPiel/Shimmer-android
 * Created by admin on 2017/5/15.
 */

public class ShimmerTextTest extends Activity {

    private Shimmer shimmer;
    private Shimmer customShimmer;
    private Titanic titanic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shimmer_textview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "淡淡发光的文字");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        ShimmerTextView txt_shimmer1 = (ShimmerTextView) findViewById(R.id.txt_shimmer1);
        ShimmerTextView txt_shimmer2 = (ShimmerTextView) findViewById(R.id.txt_shimmer2);
        ShimmerTextView txt_shimmer3 = (ShimmerTextView) findViewById(R.id.txt_shimmer3);
        ShimmerButton btn_shimmer4 = (ShimmerButton) findViewById(R.id.txt_shimmer4);
        ShimmerButton btn_shimmer5 = (ShimmerButton) findViewById(R.id.txt_shimmer5);
        shimmer = new Shimmer();
        shimmer.start(txt_shimmer1);//开始动画
        shimmer.start(txt_shimmer2);
        shimmer.start(btn_shimmer4);
        shimmer.start(btn_shimmer5);

        customShimmer = new Shimmer();
        customShimmer.setRepeatCount(100)//持续的次数
                .setDuration(5500)//持续时间
                .setStartDelay(300)//延迟时间
                .setDirection(Shimmer.ANIMATION_DIRECTION_RTL);//设置方向
        customShimmer.start(txt_shimmer3);

        /**水波效果**/
        TitanicTextView view = (TitanicTextView) findViewById(R.id.txt_titanic);
        titanic = new Titanic();
        titanic.start(view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        shimmer.cancel();
        titanic.cancel();
    }
}
