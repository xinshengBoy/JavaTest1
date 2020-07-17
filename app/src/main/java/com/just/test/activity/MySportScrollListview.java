package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.NBASport;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * NBA球赛技术统计表
 * Created by admin on 2017/5/20.
 */

public class MySportScrollListview extends Activity {

    private LinearLayout sportCount_layout,leftLY,rightLY;
    private ListView leftListView,rightListView;
    private HorizontalScrollView rightHSY;
    private List<String> mList1 = new ArrayList<>();
    private List<NBASport> mList2 = new ArrayList<>();
    private String [] playerName = new String[]{"詹姆斯","欧文","乐福","史密斯","汤普森","杰弗森","科沃尔","弗莱","香珀特","德里克-威廉姆斯","德隆-威廉姆斯","琼斯","D.琼斯"};
    private static final LinearLayout.LayoutParams FULL = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    private static final String[] TITLES = new String[]{"位置","出场时间","投篮","三分","罚球","前篮板","后篮板","总篮板","助攻","抢断","盖帽","失误","犯规","+/-","得分"};
    private static final LinearLayout.LayoutParams TEXTLP = new LinearLayout.LayoutParams(200,100);
    private int TEXTSIZE =16;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nbacount_scrollliestview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "NBA球赛技术统计表");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        sportCount_layout = (LinearLayout)findViewById(R.id.sportCount_layout);
        initData();
        initLeft();
        initRight();
        setCaseCading();
    }

    private void initLeft(){
        leftLY = new LinearLayout(this);
        leftLY.setBackgroundColor(0xffD6EBFD);
        leftLY.setOrientation(LinearLayout.VERTICAL);
        leftLY.setLayoutParams(new LinearLayout.LayoutParams(200,100*(mList1.size()+1)));
        leftListView = new ListView(this);
        leftListView.setLayoutParams(FULL);
        //添加lsitview头部视图
        LinearLayout leftHead = new LinearLayout(this);
        TextView tv = new TextView(this);
        tv.setText("球员");
        tv.setTextSize(TEXTSIZE);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(new LinearLayout.LayoutParams(200,100));
        leftHead.addView(tv);
        leftLY.addView(leftHead);

        leftListView.setAdapter(new PlayingNameAdapter(MySportScrollListview.this,mList1,R.layout.item_title_listview));
//        leftListView.setCacheColorHint(Color.TRANSPARENT);
        leftListView.setAlwaysDrawnWithCacheEnabled(true);
        //隐藏滚动条
        leftListView.setVerticalScrollBarEnabled(false);

        leftLY.addView(leftListView);
        leftLY.setBackgroundColor(Color.WHITE);
        sportCount_layout.addView(leftLY);
        getWindow().getDecorView();
    }

    private void initRight(){
        rightHSY = new HorizontalScrollView(this);
        rightHSY.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        rightLY = new LinearLayout(this);
        rightLY.setBackgroundColor(0xffF5FAFE);
        rightLY.setOrientation(LinearLayout.VERTICAL);
        rightLY.setLayoutParams(FULL);
        //右侧标题
        LinearLayout lly = new LinearLayout(this);
        lly.setLayoutParams(new LinearLayout.LayoutParams(200*TITLES.length,100));
        lly.setOrientation(LinearLayout.HORIZONTAL);

        TextView tv;
        LinearLayout ly;
        for (int i=0;i<TITLES.length;i++){
            ly = new LinearLayout(this);
            ly.setBackgroundColor(0xff57AFF3);
            ly.setLayoutParams(TEXTLP);

            tv = new TextView(this);
            tv.setText(TITLES[i]);
            tv.setTextSize(TEXTSIZE);
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);
            ly.addView(tv);
            lly.addView(ly);
        }
        rightLY.addView(lly);

        rightListView = new ListView(this);
        rightListView.setHorizontalScrollBarEnabled(false);
        rightListView.setAdapter(new PlayingScoreAdapter(MySportScrollListview.this,mList2,R.layout.item_score));
        rightListView.setAlwaysDrawnWithCacheEnabled(true);
        //隐藏滚动条
        rightListView.setHorizontalScrollBarEnabled(false);

        rightLY.addView(rightListView);

        rightHSY.addView(rightLY);
        rightHSY.setPadding(20,0,0,0);

        sportCount_layout.addView(rightHSY);
    }

    private void setCaseCading(){
        rightListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                leftListView.dispatchTouchEvent(motionEvent);
                return false;
            }
        });
        leftListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (leftListView.getChildAt(0) != null){
                    Rect r = new Rect();
                    leftListView.getChildVisibleRect(leftListView.getChildAt(0),r,null);
                    rightListView.setSelectionFromTop(leftListView.getFirstVisiblePosition(),r.top);
                }
            }
        });
    }

    private void initData(){
        for (int i=0;i<playerName.length;i++){
            mList1.add(playerName[i]);
        }
        NBASport sport1 = new NBASport("前锋","32","12-18","4-6","2-4","0","4","4","7","4","3","1","1","45","30");
        NBASport sport2 = new NBASport("后卫","29","8-11","3-6","4-5","0","1","1","3","1","1","4","2","36","23");
        NBASport sport3 = new NBASport("前锋","26","7-14","4-9","3-4","3","9","12","2","1","0","1","2","34","21");
        NBASport sport4 = new NBASport("后卫","32","4-5","1-1","0-0","1","6","7","0","0","0","0","0","27","9");
        NBASport sport5 = new NBASport("中锋","25","3-5","0-0","1-2","1","1","2","1","0","0","2","2","18","7");
        NBASport sport6 = new NBASport("前锋","19","3-7","2-4","2-4","2","3","5","1","0","2","1","0","6","10");
        NBASport sport7 = new NBASport("后卫","12","2-5","2-5","2-2","0","1","1","0","0","0","0","1","16","8");
        NBASport sport8 = new NBASport("前锋","13","3-6","1-2","0-0","0","1","1","3","0","0","1","2","-2","7");
        NBASport sport9 = new NBASport("后卫","32","2-2","2-2","1-2","1","4","5","3","2","0","1","2","20","7");
        NBASport sport10 = new NBASport("前锋","9","2-4","0-1","0-0","1","0","1","1","0","0","0","0","-4","4");
        NBASport sport11 = new NBASport("后卫","16","2-4","0-2","0-0","0","3","3","6","2","0","0","2","20","4");
        NBASport sport12 = new NBASport("后卫-前锋","9","0-2","0-1","0-0","0","1","1","0","0","0","1","3","-4","0");
        NBASport sport13 = new NBASport("--","5","0-2","0-0","0-0","-","-","2","0","0","0","0","0","  ","0");

        mList2.add(sport1);
        mList2.add(sport2);
        mList2.add(sport3);
        mList2.add(sport4);
        mList2.add(sport5);
        mList2.add(sport6);
        mList2.add(sport7);
        mList2.add(sport8);
        mList2.add(sport9);
        mList2.add(sport10);
        mList2.add(sport11);
        mList2.add(sport12);
        mList2.add(sport13);
   }

    /**
     * 球员名称
     */
    private class PlayingNameAdapter extends CommonAdapter<String> {

        public PlayingNameAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            TextView title = helper.getView(R.id.txt_item_titlelistview);
            title.setLayoutParams(TEXTLP);
            title.setText(item);
            title.setTextSize(TEXTSIZE);
            title.setTextColor(Color.BLACK);
        }
    }

    /**
     * 球员得分情况
     */
    private class PlayingScoreAdapter extends CommonAdapter<NBASport>{

        public PlayingScoreAdapter(Context context, List<NBASport> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, NBASport item) {
            TextView item_nba_position = helper.getView(R.id.item_nba_position);
            TextView item_nba_playingTime = helper.getView(R.id.item_nba_playingTime);
            TextView item_nba_shoot = helper.getView(R.id.item_nba_shoot);
            TextView item_nba_threePoints = helper.getView(R.id.item_nba_threePoints);
            TextView item_nba_freeThrow = helper.getView(R.id.item_nba_freeThrow);
            TextView item_nba_frontBackboard = helper.getView(R.id.item_nba_frontBackboard);
            TextView item_nba_backboard = helper.getView(R.id.item_nba_backboard);
            TextView item_nba_totalBackboard = helper.getView(R.id.item_nba_totalBackboard);
            TextView item_nba_assists = helper.getView(R.id.item_nba_assists);
            TextView item_nba_steals = helper.getView(R.id.item_nba_steals);
            TextView item_nba_blocks = helper.getView(R.id.item_nba_blocks);
            TextView item_nba_errors = helper.getView(R.id.item_nba_errors);
            TextView item_nba_foul = helper.getView(R.id.item_nba_foul);
            TextView item_nba_efficiency = helper.getView(R.id.item_nba_efficiency);
            TextView item_nba_score = helper.getView(R.id.item_nba_score);

            item_nba_position.setLayoutParams(TEXTLP);
            item_nba_playingTime.setLayoutParams(TEXTLP);
            item_nba_shoot.setLayoutParams(TEXTLP);
            item_nba_threePoints.setLayoutParams(TEXTLP);
            item_nba_freeThrow.setLayoutParams(TEXTLP);
            item_nba_frontBackboard.setLayoutParams(TEXTLP);
            item_nba_backboard.setLayoutParams(TEXTLP);
            item_nba_totalBackboard.setLayoutParams(TEXTLP);
            item_nba_assists.setLayoutParams(TEXTLP);
            item_nba_steals.setLayoutParams(TEXTLP);
            item_nba_blocks.setLayoutParams(TEXTLP);
            item_nba_errors.setLayoutParams(TEXTLP);
            item_nba_foul.setLayoutParams(TEXTLP);
            item_nba_efficiency.setLayoutParams(TEXTLP);
            item_nba_score.setLayoutParams(TEXTLP);

            item_nba_position.setText(item.getPosition());
            item_nba_playingTime.setText(item.getPlayingTime());
            item_nba_shoot.setText(item.getShoot());
            item_nba_threePoints.setText(item.getThreePoints());
            item_nba_freeThrow.setText(item.getFreeThrow());
            item_nba_frontBackboard.setText(item.getFrontBackboard());
            item_nba_backboard.setText(item.getBackboard());
            item_nba_totalBackboard.setText(item.getTotalBackboard());
            item_nba_assists.setText(item.getAssists());
            item_nba_steals.setText(item.getSteals());
            item_nba_blocks.setText(item.getBlocks());
            item_nba_errors.setText(item.getErrors());
            item_nba_foul.setText(item.getFoul());
            item_nba_efficiency.setText(item.getEfficiency());
            item_nba_score.setText(item.getScore());
        }
    }
}
