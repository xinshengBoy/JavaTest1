package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.adapter.MyCardStackViewAdapter;
import com.loopeer.cardstack.CardStackView;

import java.sql.Array;
import java.util.Arrays;

/**
 * https://github.com/loopeer/CardStackView
 * CardStackView 卡片交互
 * compile 'com.loopeer.library:cardstack:1.0.2'
 * Created by admin on 2017/4/24.
 */

public class CardStackActivity extends Activity implements CardStackView.ItemExpendListener,View.OnClickListener{

    public static Integer [] TEST_COLOR = new Integer[]{
            R.color.color_1,R.color.color_2,R.color.color_3,R.color.color_4,R.color.color_5,
            R.color.color_6,R.color.color_7,R.color.color_8,R.color.color_9,R.color.color_10,
            R.color.color_11,R.color.color_12,R.color.color_13,R.color.color_14,R.color.color_15,
            R.color.color_16,R.color.color_17,R.color.color_18,R.color.color_19,R.color.color_20,
            R.color.color_21,R.color.color_22,R.color.color_23,R.color.color_24,R.color.color_25,R.color.color_26
    };
    private CardStackView cardstack_main;
    private LinearLayout cardstack_layout;
    private Button btn_cardsatck_pre,btn_cardsatck_next;
    private MyCardStackViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cardstack);

        cardstack_main = (CardStackView)findViewById(R.id.cardstack_main);
        cardstack_layout = (LinearLayout)findViewById(R.id.cardstack_layout);
        btn_cardsatck_pre = (Button)findViewById(R.id.btn_cardsatck_pre);
        btn_cardsatck_next = (Button)findViewById(R.id.btn_cardsatck_next);

        btn_cardsatck_pre.setOnClickListener(this);
        btn_cardsatck_next.setOnClickListener(this);

        cardstack_main.setItemExpendListener(this);
        adapter = new MyCardStackViewAdapter(CardStackActivity.this);
        cardstack_main.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.updateData(Arrays.asList(TEST_COLOR));
            }
        },200);
    }

    @Override
    public void onItemExpend(boolean expend) {
        cardstack_layout.setVisibility(expend ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_cardsatck_pre){
            cardstack_main.pre();
        }else if (view == btn_cardsatck_next){
            cardstack_main.next();
        }
    }
}
