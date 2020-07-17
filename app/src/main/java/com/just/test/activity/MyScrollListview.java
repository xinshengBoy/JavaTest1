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
import com.just.test.bean.Films;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * listview横向滑动
 * Created by admin on 2017/5/3.
 */

public class MyScrollListview extends Activity {

    private HorizontalScrollView rightHSY;
    private List<String> mList1 = new ArrayList<>();
    private List<Films> mList2 = new ArrayList<>();
    private LinearLayout mainLY,leftLY,rightLY;
    private ListView leftListView,rightListView;
    private static final LinearLayout.LayoutParams FULL = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    public static final LinearLayout.LayoutParams DEFIEDLP = new LinearLayout.LayoutParams(200,500);
    public static final LinearLayout.LayoutParams TEXTLP = new LinearLayout.LayoutParams(200,100);
    private int TEXTSIZE =20;
    private static final String[] TITLES = new String[]{"名称","作者","上映时间","导演","主演","国家","类型"};
    private static final int [] COLORS = new int[]{Color.GREEN,Color.BLUE,Color.YELLOW,Color.RED,Color.BLACK,Color.DKGRAY,Color.GREEN};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainLY = new LinearLayout(this);
        mainLY.setLayoutParams(FULL);
        mainLY.setOrientation(LinearLayout.HORIZONTAL);

        initData();
        initLeft();
        initRight();
        setCaseCading();
        setContentView(mainLY);
    }

    private void initLeft(){
        leftLY = new LinearLayout(this);
        leftLY.setOrientation(LinearLayout.VERTICAL);
        leftLY.setLayoutParams(new LinearLayout.LayoutParams(200,100*(mList1.size()+1)));
        leftListView = new ListView(this);
        leftListView.setLayoutParams(FULL);
        //添加lsitview头部视图
        LinearLayout leftHead = new LinearLayout(this);
        TextView tv = new TextView(this);
        tv.setText("编号");
        tv.setTextSize(TEXTSIZE);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(new LinearLayout.LayoutParams(200,100));
        leftHead.addView(tv);
        leftLY.addView(leftHead);

        leftListView.setAdapter(new TitleAdapter(MyScrollListview.this,mList1,R.layout.item_title_listview));
//        leftListView.setCacheColorHint(Color.TRANSPARENT);
        leftListView.setAlwaysDrawnWithCacheEnabled(true);
        //隐藏滚动条
        leftListView.setVerticalScrollBarEnabled(false);

        leftLY.addView(leftListView);
        leftLY.setBackgroundColor(Color.WHITE);
        mainLY.addView(leftLY);
        getWindow().getDecorView();
    }

    private void initRight(){
        rightHSY = new HorizontalScrollView(this);
        rightHSY.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        rightLY = new LinearLayout(this);
        rightLY.setOrientation(LinearLayout.VERTICAL);
        rightLY.setLayoutParams(FULL);
        //右侧标题
        LinearLayout lly = new LinearLayout(this);
        lly.setLayoutParams(new LinearLayout.LayoutParams(200*TITLES.length,100));
        lly.setOrientation(LinearLayout.HORIZONTAL);

        TextView tv = null;
        LinearLayout ly = null;
        for (int i=0;i<TITLES.length;i++){
            ly = new LinearLayout(this);
            ly.setLayoutParams(TEXTLP);

            tv = new TextView(this);
            tv.setText(TITLES[i]);
            tv.setTextSize(TEXTSIZE);
            tv.setTextColor(COLORS[i]);
            tv.setGravity(Gravity.CENTER);
            ly.addView(tv);
            lly.addView(ly);
        }
        rightLY.addView(lly);

        rightListView = new ListView(this);
        rightListView.setAdapter(new ContentAdapter(MyScrollListview.this,mList2,R.layout.item_content_listview));

        rightLY.addView(rightListView);

        rightHSY.addView(rightLY);
        rightHSY.setPadding(20,0,0,0);

        mainLY.addView(rightHSY);
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
        mList1.add("1");
        mList1.add("2");
        mList1.add("3");
        mList1.add("4");

        Films films1 = new Films("铁道飞虎","王晶","2016年","成龙","房祖名","中国","喜剧");
        Films films2 = new Films("速度与激情8","斯皮尔伯格","2017年","道森","斯坦","美国","动作");
        Films films3 = new Films("2012天后","李琪","2011年","华盛顿","詹姆斯","美国","灾难");
        Films films4 = new Films("人再囧途之泰囧","黄渤","2014年","王宝强","汪涵","中国","喜剧");
        mList2.add(films1);
        mList2.add(films2);
        mList2.add(films3);
        mList2.add(films4);
    }

    private class TitleAdapter extends CommonAdapter<String>{

        public TitleAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            TextView title = helper.getView(R.id.txt_item_titlelistview);
            title.setLayoutParams(TEXTLP);
            title.setText(item);
            title.setTextSize(TEXTSIZE);
            title.setTextColor(Color.RED);
        }
    }

    private class ContentAdapter extends CommonAdapter<Films>{

        public ContentAdapter(Context context, List<Films> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Films item) {
            TextView item_file_name = helper.getView(R.id.item_file_name);
            TextView item_file_author = helper.getView(R.id.item_file_author);
            TextView item_file_time = helper.getView(R.id.item_file_time);
            TextView item_file_dictor = helper.getView(R.id.item_file_dictor);
            TextView item_file_yanyuan = helper.getView(R.id.item_file_yanyuan);
            TextView item_file_country = helper.getView(R.id.item_file_country);
            TextView item_file_type = helper.getView(R.id.item_file_type);

            item_file_name.setLayoutParams(TEXTLP);
            item_file_author.setLayoutParams(TEXTLP);
            item_file_time.setLayoutParams(TEXTLP);
            item_file_dictor.setLayoutParams(TEXTLP);
            item_file_yanyuan.setLayoutParams(TEXTLP);
            item_file_country.setLayoutParams(TEXTLP);
            item_file_type.setLayoutParams(TEXTLP);

            item_file_name.setText(item.getName());
            item_file_author.setText(item.getAuthor());
            item_file_time.setText(item.getTime());
            item_file_dictor.setText(item.getDictor());
            item_file_yanyuan.setText(item.getYanyuan());
            item_file_country.setText(item.getCountry());
            item_file_type.setText(item.getType());
        }
    }
}
