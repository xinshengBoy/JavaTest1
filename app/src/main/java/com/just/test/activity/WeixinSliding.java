package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 仿微信网页下拉有顶部背景样式
 * https://github.com/HomHomLin/SlidingLayout
 * compile 'homhomlin.lib:sldinglayout:0.9.0'
 * Created by admin on 2017/5/12.
 */

public class WeixinSliding extends Activity {

    private ListView listview_slidinglayout;
    private List<String> mList = new ArrayList<>();
    private PromptDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weixin_slidinglayout);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "微信下拉背景");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initData();

        dialog = new PromptDialog(WeixinSliding.this);
        dialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(3000);

        listview_slidinglayout = (ListView)findViewById(R.id.listview_slidinglayout);
        PullSepareteAdapter adapter = new PullSepareteAdapter(WeixinSliding.this,mList,R.layout.item_intenttext);
        listview_slidinglayout.setAdapter(adapter);
        listview_slidinglayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.showInfo("即将播放"+mList.get(i));
            }
        });
    }

    private void initData(){
        for (int i=1;i<55;i++){
            mList.add("人民的名义网络版第"+i+"集");
        }
    }
    private class PullSepareteAdapter extends CommonAdapter<String> {

        public PullSepareteAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            TextView txt_intentcontent = helper.getView(R.id.txt_intentcontent);
            txt_intentcontent.setBackgroundColor(0xffEAA07E);
            txt_intentcontent.setTextColor(Color.WHITE);
            txt_intentcontent.setText(item);
        }
    }
}
