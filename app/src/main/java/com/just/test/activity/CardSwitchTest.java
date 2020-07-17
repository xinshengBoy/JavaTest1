package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bakerj.infinitecards.InfiniteCardView;
import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡片切换，可作为欢迎界面使用
 * 参考网址：https://github.com/BakerJQ/Android-InfiniteCards
 * compile 'com.bakerj:infinite-cards:1.0.2'
 * Created by admin on 2017/7/14.
 */

public class CardSwitchTest extends Activity {

    private int [] images = new int[]{R.drawable.bg01,R.drawable.bg02,R.drawable.bg03,R.drawable.bg04};
    private List<Integer> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_card_switch);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 卡片切换");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initData();
        initView();
    }

    private void initView() {
        InfiniteCardView view_cardView = (InfiniteCardView) findViewById(R.id.view_cardView);
        CardSwitchAdapter adapter = new CardSwitchAdapter(CardSwitchTest.this,mList,R.layout.item_cardswitch);
        view_cardView.setAdapter(adapter);
    }

    private void initData(){
        for (int image : images) {
            mList.add(image);
        }
    }

    private class CardSwitchAdapter extends CommonAdapter<Integer>{

        private CardSwitchAdapter(Context context, List<Integer> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Integer item) {
            ImageView card = helper.getView(R.id.iv_item_cardswitch);
            card.setImageResource(item);
        }
    }
}
