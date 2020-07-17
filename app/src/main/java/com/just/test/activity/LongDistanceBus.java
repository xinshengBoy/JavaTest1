package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.EditTextTools;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 长途汽车站及时间表等
 * http://avatardata.cn/Docs/Api?id=8e757dfa-3148-49c8-a265-13fda958bb8f&detailId=abda5544-8987-4422-a85f-430567ab4c4a
 * Created by admin on 2017/6/19.
 */

public class LongDistanceBus extends Activity {

    private RequestQueue mQueue;
    private EditText et_longBus_position,et_longBus_from,et_longBus_to;
    private ListView listview_longBus;
    private List<Bundle> mPositionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_longdistance_bus);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "长途汽车");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
    }

    private void initView() {
        et_longBus_position = (EditText)findViewById(R.id.et_longBus_position);
        et_longBus_from = (EditText)findViewById(R.id.et_longBus_from);
        et_longBus_to = (EditText)findViewById(R.id.et_longBus_to);
        Button btn_longBus_queryPosition = (Button) findViewById(R.id.btn_longBus_queryPosition);
        Button btn_longBus_query = (Button) findViewById(R.id.btn_longBus_query);
        listview_longBus = (ListView)findViewById(R.id.listview_longBus);

        btn_longBus_queryPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String position = et_longBus_position.getText().toString();
                if (position.equals("")){
                    LemonBubble.showError(LongDistanceBus.this,"请输入站点",1000);
                }else {
                    if (mPositionList.size() > 0){
                        mPositionList.clear();
                    }
                    queryPosition(position);
                }
            }
        });

        btn_longBus_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = et_longBus_from.getText().toString();
                String to = et_longBus_to.getText().toString();
                if (from.equals("") || to.equals("")){
                    LemonBubble.showError(LongDistanceBus.this,"请输入起点或终点城市名称",1000);
                }else {
                    if (mPositionList.size() > 0){
                        mPositionList.clear();
                    }
                    queryLongBusLine(from,to);
                }
            }
        });
    }

    /**
     * 查询城市长途汽车站点
     * @param city 城市名称
     */
    private void queryPosition(String city){
        LemonBubble.showRoundProgress(LongDistanceBus.this, "加载中");
        EditTextTools.hideSoftInput(LongDistanceBus.this);
        String url = "http://api.avatardata.cn/Bus/Query?key=a387086acbf64d638f0692da5b4c48bc&station="+city;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        JSONObject object1 = object.getJSONObject("result");
                        JSONArray array = object1.getJSONArray("list");
                        for (int i=0;i<array.length();i++){
                            JSONObject object2 = array.getJSONObject(i);

                            Bundle bundle = new Bundle();
                            bundle.putString("nameOrStart",object2.getString("name"));
                            bundle.putString("telOrDate",object2.getString("tel"));
                            bundle.putString("addsOrPrice",object2.getString("adds"));

                            mPositionList.add(bundle);
                        }
                        mHandler.sendEmptyMessage(0);
                    }else {
                        LemonBubble.showError(LongDistanceBus.this,reason,1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(LongDistanceBus.this,"请求失败",1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LemonBubble.showError(LongDistanceBus.this,error.getMessage(),1000);
            }
        });
        mQueue.add(request);
    }

    /**
     * 查询到某个城市的时间，车站及票价
     * @param from 出发车站
     * @param to 到达车站
     */
    private void queryLongBusLine(String from,String to){
        LemonBubble.showRoundProgress(LongDistanceBus.this, "加载中");
        EditTextTools.hideSoftInput(LongDistanceBus.this);
        String url = " http://api.avatardata.cn/Bus/QueryByFromTo?key=a387086acbf64d638f0692da5b4c48bc&from="+from+"&to="+to;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        JSONObject object1 = object.getJSONObject("result");
                        JSONArray array = object1.getJSONArray("list");
                        for (int i=0;i<array.length();i++){
                            JSONObject object2 = array.getJSONObject(i);
                            Bundle bundle = new Bundle();
                            bundle.putString("nameOrStart",object2.getString("start"));
                            bundle.putString("arrive",object2.getString("arrive"));
                            bundle.putString("telOrDate",object2.getString("date"));
                            bundle.putString("addsOrPrice",object2.getString("price"));

                            mPositionList.add(bundle);
                        }
                        mHandler.sendEmptyMessage(0);
                    }else {
                        LemonBubble.showError(LongDistanceBus.this,reason,1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(LongDistanceBus.this,"请求失败",1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LemonBubble.showError(LongDistanceBus.this,error.getMessage(),1000);
            }
        });
        mQueue.add(request);
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                LongDistanceBusAdapter adapter = new LongDistanceBusAdapter(LongDistanceBus.this,mPositionList,R.layout.item_longdistance_bus);
                listview_longBus.setAdapter(adapter);
                LemonBubble.hide();
            }
        }
    };

    private class LongDistanceBusAdapter extends CommonAdapter<Bundle>{

        private LongDistanceBusAdapter(Context context, List<Bundle> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Bundle item) {
            TextView position = helper.getView(R.id.txt_item_longBus_name);
            TextView arrive = helper.getView(R.id.txt_item_longBus_arrive);
            TextView telOrDate = helper.getView(R.id.txt_item_longBus_telOrDate);
            TextView addsOrPrice = helper.getView(R.id.txt_item_longBus_addsOrPrice);

            position.setText(item.getString("nameOrStart"));
            arrive.setText(item.getString("arrive"));
            telOrDate.setText(item.getString("telOrDate"));
            addsOrPrice.setText(item.getString("addsOrPrice"));
        }
    }
}
