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
import com.just.test.listview.PullSeparateListView;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 列表item之间拉伸
 * PullSeparateListView
 * https://github.com/chiemy/PullSeparateListView
 * Created by admin on 2017/5/11.
 */

public class PullSepareteListviewTest extends Activity {

    private PullSeparateListView listview_pullSperete;
    private List<String> mList = new ArrayList<>();
    private PromptDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pullseparete_listview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "列表item之间拉伸");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initData();
        dialog = new PromptDialog(PullSepareteListviewTest.this);
        dialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(3000);
        listview_pullSperete = (PullSeparateListView)findViewById(R.id.listview_pullSperete);
        listview_pullSperete.setSeparateAll(true);//所有item都会被拉伸   //lv.setShowDownAnim(true);  手指触屏以上的会被拉伸
        PullSepareteAdapter adapter = new PullSepareteAdapter(PullSepareteListviewTest.this,mList,R.layout.item_intenttext);
        listview_pullSperete.setAdapter(adapter);
        listview_pullSperete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    private class PullSepareteAdapter extends CommonAdapter<String>{

        public PullSepareteAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            TextView txt_intentcontent = helper.getView(R.id.txt_intentcontent);
            txt_intentcontent.setBackgroundColor(Color.BLACK);
            txt_intentcontent.setTextColor(Color.WHITE);
            txt_intentcontent.setText(item);
        }
    }
}
