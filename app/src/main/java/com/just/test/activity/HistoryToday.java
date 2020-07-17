package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.bean.HistoryTodayBean;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 历史上的今天
 * 参考网址：http://avatardata.cn/Docs/Api/4b396fc5-22f5-4c21-86d1-b5f5777e6744
 * Created by admin on 2017/5/31.
 */

public class HistoryToday extends Activity implements View.OnClickListener{

    private Button btn_historyToday_big,btn_historyToday_people;
    private ListView listview_history;
    private RequestQueue mQueue;
    private List<HistoryTodayBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_history_today);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 历史上的今天");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
    }

    private void initView(){
        btn_historyToday_big = (Button)findViewById(R.id.btn_historyToday_big);
        btn_historyToday_people = (Button)findViewById(R.id.btn_historyToday_people);
        listview_history = (ListView)findViewById(R.id.listview_historyTodays);

        btn_historyToday_big.setOnClickListener(this);
        btn_historyToday_people.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_historyToday_big){
            if (mList.size() > 0){
                mList.clear();
            }
            queryData(1);//国内国际大事件
        }else if (view == btn_historyToday_people){
            if (mList.size() > 0){
                mList.clear();
            }
            queryData(2);//民间大事件
        }
    }

    /**
     * 查询数据
     * @param type 类型
     */
    private void queryData(int type){
        LemonBubble.showRoundProgress(HistoryToday.this, "加载中");
        //获取今日的月份和时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String dateResult = format.format(date);
        String [] result = dateResult.split("-");
        String month = result[1];//月份
        String day = result[2];//日期
        String url = " http://api.avatardata.cn/HistoryToday/LookUp?key=85489d65a46e4b1f8000cab36e6ecb3c&yue="+month+"&ri="+day+"&type="+type+"&page=1&rows=50";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray array = object.getJSONArray("result");
                    for (int i=0;i<array.length();i++){
                        JSONObject object1 = array.getJSONObject(i);
                        HistoryTodayBean detail = new HistoryTodayBean();
                        detail.setDate(object1.getInt("year")+"年"+object1.getInt("month")+"月"+object1.getInt("day")+"日");
                        detail.setTitle(object1.getString("title"));

                        mList.add(detail);
                    }
                    mHandler.sendEmptyMessage(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(HistoryToday.this, "请求错误", 1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(HistoryToday.this, volleyError.getMessage(), 2000);
            }
        });
        mQueue.add(request);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                HistoryTodayAdapter bigAdapter = new HistoryTodayAdapter(HistoryToday.this,mList,R.layout.item_history_today);
                listview_history.setAdapter(bigAdapter);
                LemonBubble.hide();
            }
        }
    };

    private class HistoryTodayAdapter extends CommonAdapter<HistoryTodayBean>{

        private HistoryTodayAdapter(Context context, List<HistoryTodayBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, HistoryTodayBean item) {
            TextView title = helper.getView(R.id.item_historyToday_title);
            TextView date = helper.getView(R.id.item_historyToday_date);

            title.setText(item.getTitle());
            date.setText(item.getDate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        mQueue.stop();
        if (mList != null){
            mList = null;
        }
    }
}
