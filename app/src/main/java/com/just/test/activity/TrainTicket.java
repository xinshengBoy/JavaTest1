package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.just.test.bean.RailwayStationsBean;
import com.just.test.bean.TrainTicketDetailBean;
import com.just.test.tools.ACache;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 火车票查询
 * http://avatardata.cn/Docs/Api/a33d5bb5-4bbc-47b5-b7b7-3765fd37b014
 * Created by admin on 2017/6/17.
 */

public class TrainTicket extends Activity implements View.OnClickListener{

    private AutoCompleteTextView startStation,endStation;
    private Button btn_query;
    private ListView listview_trainTicket;
    private List<RailwayStationsBean> mList = new ArrayList<>();
    private List<TrainTicketDetailBean> mDetailList = new ArrayList<>();
    private List<String> stationData = new ArrayList<>();
    private RequestQueue mQueue;
    private String resultStart,resultEnd;
    private ACache acache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trainticket);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 火车票查询");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        acache = ACache.get(TrainTicket.this);
        queryRailwayStations();
        initView();
    }

    private void initView() {
        startStation = (AutoCompleteTextView) findViewById(R.id.act_trainticket_startAdd);
        endStation = (AutoCompleteTextView) findViewById(R.id.act_trainticket_endAdd);
        btn_query = (Button)findViewById(R.id.btn_trainticket_query);
        listview_trainTicket = (ListView)findViewById(R.id.listview_trainTicket);

        btn_query.setOnClickListener(this);
        startStation.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stationData));//设置自动提示框的内容
        endStation.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stationData));//设置自动提示框的内容
        startStation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String result = startStation.getText().toString();
                for (int j=0;j<mList.size();j++){
                    if (result.equals(mList.get(j).getName())){
                        resultStart = mList.get(j).getCode();
                        break;
                    }
                }
            }
        });
        endStation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String result = endStation.getText().toString();
                for (int j=0;j<mList.size();j++){
                    if (result.equals(mList.get(j).getName())){
                        resultEnd = mList.get(j).getCode();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btn_query){
            if (resultStart != null && !resultStart.equals("") && resultEnd != null && !resultEnd.equals("")){
                queryTrainTicket(resultStart,resultEnd);
            }else {
                LemonBubble.showError(TrainTicket.this,"参数不能为空",2000);
            }
        }
    }

    /**
     * 查询火车票
     */
    private void queryTrainTicket(String startAdd,String endAdd){
        LemonBubble.showRoundProgress(TrainTicket.this, "加载中");

        String url = "http://api.avatardata.cn/TrainTicket/Lookup?key=55bbd59a68344ff5bffa9619e37c7ef5&from="+startAdd+"&to="+endAdd;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        JSONArray array = object.getJSONArray("result");
                        for (int i=0;i<array.length();i++){
                            JSONObject object1 = array.getJSONObject(i);
                            TrainTicketDetailBean detailBean = new TrainTicketDetailBean();
                            detailBean.setTrain(object1.getString("train"));
                            detailBean.setFrom(object1.getString("from"));
                            detailBean.setTo(object1.getString("to"));
                            detailBean.setStart(object1.getString("start"));
                            detailBean.setEnd(object1.getString("end"));
                            detailBean.setLeave(object1.getString("leave"));
                            detailBean.setArrive(object1.getString("arrive"));
                            detailBean.setTime(object1.getString("time"));

                            JSONObject object2 = object1.getJSONObject("tickets");
                            TrainTicketDetailBean.Tickets tickets = new TrainTicketDetailBean.Tickets();
                            tickets.setRz(object2.getString("rz"));
                            tickets.setYz(object2.getString("yz"));
                            tickets.setWz(object2.getString("wz"));
                            tickets.setRw(object2.getString("rw"));
                            tickets.setYw(object2.getString("yw"));
                            tickets.setSwz(object2.getString("swz"));
                            tickets.setTz(object2.getString("tz"));
                            tickets.setZy(object2.getString("zy"));
                            tickets.setZe(object2.getString("ze"));
                            tickets.setGr(object2.getString("gr"));
                            tickets.setQt(object2.getString("qt"));

                            detailBean.setTickets(tickets);

                            mDetailList.add(detailBean);
                        }
                        mHandler.sendEmptyMessage(1);
                    }else {
                        LemonBubble.showError(TrainTicket.this,reason,2000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(TrainTicket.this,"请求失败",2000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(TrainTicket.this,volleyError.getMessage(),2000);
            }
        });
        mQueue.add(request);
    }

    /**
     * 查询全国火车站列表
     */
    private void queryRailwayStations(){
        LemonBubble.showRoundProgress(TrainTicket.this, "加载中");
        JSONArray arrays = acache.getAsJSONArray("result");
        if (arrays == null){
            String url = "http://api.avatardata.cn/TrainTicket/Station?key=55bbd59a68344ff5bffa9619e37c7ef5";
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try {
                        JSONObject object = new JSONObject(s);
                        int error_code = object.getInt("error_code");
                        String reason = object.getString("reason");
                        if (error_code == 0) {
                            JSONArray array = object.getJSONArray("result");
                            acache.put("result",array,60*60*24*30);//加入缓存,缓存一个月,一个月后自动失效
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object1 = array.getJSONObject(i);
                                RailwayStationsBean detail = new RailwayStationsBean();
                                detail.setCode(object1.getString("code"));
                                detail.setName(object1.getString("name"));
                                detail.setPy(object1.getString("py"));
                                detail.setPinyin(object1.getString("pinyin"));

                                mList.add(detail);
                            }
                            mHandler.sendEmptyMessage(0);
                        } else {
                            LemonBubble.showError(TrainTicket.this, reason, 2000);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        LemonBubble.showError(TrainTicket.this, "请求失败", 2000);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LemonBubble.showError(TrainTicket.this, volleyError.getMessage(), 2000);
                }
            });
            mQueue.add(request);
        }else if (arrays.length() > 0){
            try {
                for (int i=0;i<arrays.length();i++){
                    JSONObject object1 = arrays.getJSONObject(i);
                    RailwayStationsBean detail = new RailwayStationsBean();
                    detail.setCode(object1.getString("code"));
                    detail.setName(object1.getString("name"));
                    detail.setPy(object1.getString("py"));
                    detail.setPinyin(object1.getString("pinyin"));

                    mList.add(detail);
                }
                mHandler.sendEmptyMessage(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class TrainTicketAdapter extends CommonAdapter<TrainTicketDetailBean>{

        public TrainTicketAdapter(Context context, List<TrainTicketDetailBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, TrainTicketDetailBean item) {
            TextView train = helper.getView(R.id.txt_train_number);
            TextView from = helper.getView(R.id.txt_train_from);
            TextView to = helper.getView(R.id.txt_train_to);
            TextView start = helper.getView(R.id.txt_train_start);
            TextView end = helper.getView(R.id.txt_train_end);
            TextView leave = helper.getView(R.id.txt_train_leave);
            TextView arrive = helper.getView(R.id.txt_train_arrive);
            TextView time = helper.getView(R.id.txt_train_time);
            TextView rz = helper.getView(R.id.txt_train_rz);
            TextView yz = helper.getView(R.id.txt_train_yz);
            TextView wz = helper.getView(R.id.txt_train_wz);
            TextView rw = helper.getView(R.id.txt_train_rw);
            TextView yw = helper.getView(R.id.txt_train_yw);
            TextView gr = helper.getView(R.id.txt_train_gr);
            TextView tz = helper.getView(R.id.txt_train_tz);
            TextView zy = helper.getView(R.id.txt_train_zy);
            TextView ze = helper.getView(R.id.txt_train_ze);
            TextView swz = helper.getView(R.id.txt_train_swz);
            TextView qt = helper.getView(R.id.txt_train_qt);

            train.setText(item.getTrain());
            from.setText(item.getFrom());
            to.setText(item.getTo());
            start.setText(item.getStart());
            end.setText(item.getEnd());
            leave.setText(item.getLeave());
            arrive.setText(item.getArrive());
            time.setText(item.getTime());

            TrainTicketDetailBean.Tickets tickets = item.getTickets();
            rz.setText("软座(张):"+tickets.getRz());
            yz.setText("硬座(张):"+tickets.getYz());
            wz.setText("无座(张):"+tickets.getWz());
            rw.setText("软卧(张):"+tickets.getRw());
            yw.setText("硬卧(张):"+tickets.getYw());
            gr.setText("高级软卧(张):"+tickets.getGr());
            tz.setText("特等座(张):"+tickets.getTz());
            zy.setText("一等座(张):"+tickets.getZy());
            ze.setText("二等座(张):"+tickets.getZe());
            swz.setText("商务座(张):"+tickets.getSwz());
            qt.setText("其他(张):"+tickets.getQt());
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                for (int i=0;i<mList.size();i++){
                    stationData.add(mList.get(i).getName());
                }
                LemonBubble.hide();
            }else if (msg.what == 1){
                TrainTicketAdapter adapter = new TrainTicketAdapter(TrainTicket.this,mDetailList,R.layout.item_traintickets);
                listview_trainTicket.setAdapter(adapter);
                LemonBubble.hide();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        mQueue.stop();
        if (mList != null){
            mList = null;
        }
        if (startStation != null){
            startStation = null;
        }
        resultStart = null;
        resultEnd = null;
    }
}
