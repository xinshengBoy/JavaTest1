package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.listview.WaterDropListView;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 水滴列表  是一个可以直接用哪个来做下拉上拉动作的刷新控件
 * https://github.com/THEONE10211024/WaterDropListView
 * Created by admin on 2017/5/10.
 */

public class WaterDropListviewTest extends Activity implements WaterDropListView.IWaterDropListViewListener{

    private WaterDropListView listview_waterdrop;
    private List<String> mList = new ArrayList<>();
    private int count = 30;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_waterdrop_listview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "水滴列表");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initData();

        listview_waterdrop = (WaterDropListView)findViewById(R.id.listview_waterdrop);
        adapter = new ArrayAdapter(WaterDropListviewTest.this,android.R.layout.simple_list_item_1,mList);
        listview_waterdrop.setAdapter(adapter);
        listview_waterdrop.setWaterDropListViewListener(this);
        listview_waterdrop.setPullLoadEnable(true);//可下拉刷新和上拉加载更多
        listview_waterdrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LemonBubble.showRight(WaterDropListviewTest.this,"正在播放"+mList.get(i),2000);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    count = 30;
                    mList.clear();
                    initData();
                    listview_waterdrop.stopRefresh();
                    adapter.notifyDataSetChanged();
                    LemonBubble.showRight(WaterDropListviewTest.this,"刷新成功",2000);
                    break;
                case 1:
                    count += 2;
                    mList.clear();
                    initData();
                    listview_waterdrop.stopLoadMore();
                    adapter.notifyDataSetChanged();
                    LemonBubble.showRight(WaterDropListviewTest.this,"加载成功",2000);
                    break;
            }
        }
    };
    private void initData(){
        for (int i=1;i<count;i++){
            mList.add("向往的生活网络版第"+i+"期");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        if (mList != null){
            mList = null;
        }
    }

    @Override
    public void onRefresh() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onLoadMore() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
