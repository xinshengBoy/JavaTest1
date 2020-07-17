package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.adapter.StatusExpandAdapter;
import com.just.test.bean.TimerLineBean;
import com.just.test.bean.TimerLineBean2;
import com.just.test.bean.TimerLineDetailBean;
import com.just.test.listview.ListViewUtility;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;
import java.util.List;

/**
 * 时光轴
 * 参考网址：http://www.itlanbao.com/code/20151113/11253/100643.html
 * Created by admin on 2017/6/9.
 */

public class TimerLine extends Activity {

    private ExpandableListView el_timerLine;
    private List<TimerLineBean> mList = new ArrayList<>();
    private ListView listview_timerLine;
    private List<TimerLineBean2> mList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timer_line);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "时光轴");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initData();
        initView();
    }

    private void initView(){
        el_timerLine = (ExpandableListView)findViewById(R.id.el_timerLine);
        listview_timerLine = (ListView)findViewById(R.id.listview_timerLine);
        StatusExpandAdapter adapter = new StatusExpandAdapter(TimerLine.this,mList);
        el_timerLine.setAdapter(adapter);
        el_timerLine.setGroupIndicator(null);//去掉默认带的箭头
        el_timerLine.setSelection(0);//设置默认选中项
        //遍历所有都展开
        int count = el_timerLine.getCount();
        for (int i=0;i<count;i++){
            el_timerLine.expandGroup(i);
        }

        el_timerLine.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                LemonBubble.showRight(TimerLine.this,mList.get(i).getChild().get(i).getFinishTime(),3000);
                return true;
            }
        });

        TimerLine2Adapter adapter2 = new TimerLine2Adapter(TimerLine.this,mList2,R.layout.item_timer_line2);
        listview_timerLine.setAdapter(adapter2);
        listview_timerLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LemonBubble.showRight(TimerLine.this,"今天是"+mList2.get(i).getTime()+"，我觉得"+mList2.get(i).getContent(),3000);
            }
        });

        ListViewUtility.setListViewHeightBasedOnChildren(el_timerLine);
        ListViewUtility.setListViewHeightBasedOnChildren(listview_timerLine);
    }

    private void initData(){
        String [] timer = new String[]{"6月1号","6月2号","6月3号","6月4号","6月6号","6月7号","6月9号","6月12号","6月13号","6月14号","6月22号","6月23号","6月24号"};
        String [][] content = new String[][]{{"吃饭","睡觉","打豆豆"},{"打球"},{"游泳"},{"骑行","徒步"},{"爬山","漂流"},{"羽毛球"},{"乒乓球"},{"足球"},{"篮球"},{"买康师傅饮料"},{"坐高铁出行"},{"看书"},{"学习"}};

        for (int i=0;i<timer.length;i++){
            TimerLineBean detail = new TimerLineBean();
            detail.setTimer(timer[i]);

            List<TimerLineDetailBean> child = new ArrayList<>();
            for (int j=0;j<content[i].length;j++){
                TimerLineDetailBean details = new TimerLineDetailBean();
                details.setFinishTime(content[i][j]);
                details.setFinished(true);

                child.add(details);
            }
            detail.setChild(child);
            mList.add(detail);
        }

        for (int i=1;i<=12;i++){
            TimerLineBean2 detail = new TimerLineBean2();
            detail.setTime("2017年"+i+"月");
            detail.setContent(i+"月好困");

            mList2.add(detail);
        }
    }

    private class TimerLine2Adapter extends CommonAdapter<TimerLineBean2>{

        private TimerLine2Adapter(Context context, List<TimerLineBean2> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
            this.mDatas = mDatas;
        }

        @Override
        public void convert(ViewHolder helper, TimerLineBean2 item) {
            TextView happenTime = helper.getView(R.id.txt_item_timerLine_happendTime);
            TextView happenThing = helper.getView(R.id.txt_item_timerLine_thing);
            View view_timerLine = helper.getView(R.id.view_timerLine);

            happenTime.setText(item.getTime());
            happenThing.setText(item.getContent());
            if (helper.getPosition() == mDatas.size()-1){
                view_timerLine.setVisibility(View.GONE);
            }
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
