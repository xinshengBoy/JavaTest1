package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.cooltechworks.views.ScratchImageView;
import com.cooltechworks.views.ScratchTextView;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 刮刮乐
 * compile 'com.github.cooltechworks:ScratchView:v1.1'
 * https://github.com/sharish/ScratchView
 * Created by admin on 2017/6/7.
 */

public class ScratchViewTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scratchview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "刮刮乐");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        ScratchImageView iv_scratchview = (ScratchImageView) findViewById(R.id.iv_scratchview);
        iv_scratchview.setRevealListener(new ScratchImageView.IRevealListener() {
            @Override
            public void onRevealed(ScratchImageView scratchImageView) {
                LemonBubble.showRight(ScratchViewTest.this,"再接再厉",2000);
                scratchImageView.setBackgroundColor(getResources().getColor(R.color.gray));
            }

            @Override
            public void onRevealPercentChangedListener(ScratchImageView scratchImageView, float v) {

            }
        });

        ScratchTextView txt_scratchview = (ScratchTextView) findViewById(R.id.txt_scratchview);
        txt_scratchview.setRevealListener(new ScratchTextView.IRevealListener() {
            @Override
            public void onRevealed(ScratchTextView scratchTextView) {
                LemonBubble.showRight(ScratchViewTest.this,scratchTextView.getText().toString(),2000);
            }

            @Override
            public void onRevealPercentChangedListener(ScratchTextView scratchTextView, float v) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
    }
}
