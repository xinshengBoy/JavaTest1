package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.PullToZoomListView;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;
import java.util.List;

/**
 * 顶部带图片的下拉列表，图片能放大
 * https://github.com/matrixxun/PullToZoomInListView
 * Created by admin on 2017/5/10.
 */

public class PullToZoomListviewTest extends Activity {

    private PullToZoomListView listview_pulltozoom;
    private List<String> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pulltozoom_listview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "顶部带图片的下拉列表，图片能放大");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initData();

        listview_pulltozoom = (PullToZoomListView)findViewById(R.id.listview_pulltozoom);
        listview_pulltozoom.setAdapter(new ArrayAdapter<>(PullToZoomListviewTest.this,android.R.layout.simple_list_item_1,mList));
        //添加头部图片
        listview_pulltozoom.getHeaderView().setImageResource(R.drawable.friend);
        //设置模式
        listview_pulltozoom.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);

        listview_pulltozoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LemonBubble.showRight(PullToZoomListviewTest.this,"即将播放"+mList.get(i),2000);
            }
        });
    }

    private void initData(){
        for (int i=1;i<30;i++){
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
}
