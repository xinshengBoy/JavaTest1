package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

/**
 * 城市公交查询
 * http://avatardata.cn/Docs/Api/9b387513-1f95-4810-81db-951023d96b27
 * Created by admin on 2017/6/16.
 */

public class CityBusTest extends Activity implements View.OnClickListener{

    private EditText et_citybus_city,et_citybus_station,et_citybus_number,et_citybus_startAddr,et_citybus_endAddr;
    private Button btn_citybus_query;
    private TextView txt_citybus_position,txt_citybus_lines,txt_citybus_Transfer;
    private ListView listview_citybus;
    private int style = 0;
    private RequestQueue mQueue;
    private List<Map<String,String>> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_citybus);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 城市公交查询");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
    }

    private void initView(){
        txt_citybus_position = (TextView)findViewById(R.id.txt_citybus_position);//站点查询
        txt_citybus_lines = (TextView)findViewById(R.id.txt_citybus_lines);//线路查询
        txt_citybus_Transfer = (TextView)findViewById(R.id.txt_citybus_Transfer);//换乘查询
        et_citybus_city = (EditText)findViewById(R.id.et_citybus_city);//城市
        et_citybus_station = (EditText)findViewById(R.id.et_citybus_station);//站点
        et_citybus_number = (EditText)findViewById(R.id.et_citybus_number);//车号，多少多少路
        et_citybus_startAddr = (EditText)findViewById(R.id.et_citybus_startAddr);//出发位置
        et_citybus_endAddr = (EditText)findViewById(R.id.et_citybus_endAddr);//到达位置
        btn_citybus_query = (Button)findViewById(R.id.btn_citybus_query);//查询
        listview_citybus = (ListView)findViewById(R.id.listview_citybus);//显示数据

        txt_citybus_position.setOnClickListener(this);
        txt_citybus_lines.setOnClickListener(this);
        txt_citybus_Transfer.setOnClickListener(this);
        btn_citybus_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Drawable nav_down = getResources().getDrawable(R.drawable.sign_down);
        nav_down.setBounds(0,0,nav_down.getMinimumWidth(),nav_down.getMinimumHeight());
        Drawable nav_up = getResources().getDrawable(R.drawable.sign_up);
        nav_up.setBounds(0,0,nav_up.getMinimumWidth(),nav_up.getMinimumHeight());
        if (view == txt_citybus_position){
            et_citybus_station.setVisibility(View.VISIBLE);
            et_citybus_number.setVisibility(View.GONE);
            et_citybus_startAddr.setVisibility(View.GONE);
            et_citybus_endAddr.setVisibility(View.GONE);
            txt_citybus_position.setCompoundDrawables(null,null,nav_down,null);
            txt_citybus_lines.setCompoundDrawables(null,null,nav_up,null);
            txt_citybus_Transfer.setCompoundDrawables(null,null,nav_up,null);
            style = 0;
        }else if (view == txt_citybus_lines){
            et_citybus_number.setVisibility(View.VISIBLE);
            et_citybus_station.setVisibility(View.GONE);
            et_citybus_startAddr.setVisibility(View.GONE);
            et_citybus_endAddr.setVisibility(View.GONE);
            txt_citybus_lines.setCompoundDrawables(null,null,nav_down,null);
            txt_citybus_position.setCompoundDrawables(null,null,nav_up,null);
            txt_citybus_Transfer.setCompoundDrawables(null,null,nav_up,null);
            style = 1;
        }else if (view == txt_citybus_Transfer){
            et_citybus_startAddr.setVisibility(View.VISIBLE);
            et_citybus_endAddr.setVisibility(View.VISIBLE);
            et_citybus_station.setVisibility(View.GONE);
            et_citybus_number.setVisibility(View.GONE);
            txt_citybus_Transfer.setCompoundDrawables(null,null,nav_down,null);
            txt_citybus_position.setCompoundDrawables(null,null,nav_up,null);
            txt_citybus_lines.setCompoundDrawables(null,null,nav_up,null);
            style = 2;
        }else if (view == btn_citybus_query){
            EditTextTools.hideSoftInput(CityBusTest.this);//隐藏软键盘
            String inputCity = et_citybus_city.getText().toString();
            String inputCondition1;
            String inputCondition2;
            if (style == 0){
                inputCondition1 = et_citybus_station.getText().toString();
                if (inputCity.equals("") || inputCondition1.equals("")){
                    LemonBubble.showError(CityBusTest.this,"参数不能为空",1000);
                    return;
                }
                queryData(style,inputCity,inputCondition1,null);
            }else if (style == 1){
                inputCondition1 = et_citybus_number.getText().toString();
                if (inputCity.equals("") || inputCondition1.equals("")){
                    LemonBubble.showError(CityBusTest.this,"参数不能为空",1000);
                    return;
                }
                queryData(style,inputCity,inputCondition1,null);
            }else if (style == 2){
                inputCondition1 = et_citybus_startAddr.getText().toString();
                inputCondition2 = et_citybus_endAddr.getText().toString();
                if (inputCity.equals("") || inputCondition1.equals("") || inputCondition2.equals("")){
                    LemonBubble.showError(CityBusTest.this,"参数不能为空",1000);
                    return;
                }
                queryData(style,inputCity,inputCondition1,inputCondition2);
            }
        }
    }

    private void queryData(final int type, String city, String condition1, String condition2){
        LemonBubble.showRoundProgress(CityBusTest.this, "加载中");
        if (mList.size() > 0){
            mList.clear();
        }
        String url = null;
        if (type == 0) {
            url = " http://api.avatardata.cn/CityBus/LookUp?key=e03fff0c35f840c188f4f449e027bd83&city="+city+"&station="+condition1;
        }else if (type == 1){
            url = "http://api.avatardata.cn/CityBus/Query?key=e03fff0c35f840c188f4f449e027bd83&city="+city+"&busNo="+condition1;
        }else if (type == 2){
            url = "http://api.avatardata.cn/CityBus/GetInfo?key=e03fff0c35f840c188f4f449e027bd83&city="+city+"&start_addr="+condition1+"&end_addr="+condition2;
        }

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        JSONObject object1 = object.getJSONObject("result");
                        String result = object1.getString("result");
                        XmlToJson toJson = new XmlToJson.Builder(result).build();
                        JSONObject object2 = toJson.toJson();
                        assert object2 != null;
                        JSONObject object5 = object2.getJSONObject("root");
                        if (type == 0) {
                            JSONObject object3 = object5.getJSONObject("stats");
                            JSONArray array = object3.getJSONArray("stat");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object4 = array.getJSONObject(i);
                                String name = object4.getString("name");
                                String line_names = object4.getString("line_names");
                                line_names = line_names.replaceAll(";", "\n");

                                Map<String, String> map = new HashMap<>();
                                map.put("name", name);
                                map.put("line_names", line_names);
                                mList.add(map);
                            }
                        }else if (type == 1){
                            JSONObject object3 = object5.getJSONObject("lines");
                            JSONArray array = object3.getJSONArray("line");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object4 = array.getJSONObject(i);
                                String name = object4.getString("name");
                                String line_names = object4.getString("stats");
                                line_names = line_names.replaceAll(";", "->");
                                String info = object4.getString("info");
                                Map<String, String> map = new HashMap<>();
                                map.put("name", name);
                                map.put("line_names", line_names);
                                map.put("info",info);
                                mList.add(map);
                            }
                        }else if (type == 2){
                            JSONObject object3 = object5.getJSONObject("buses");
                            JSONArray array = object3.getJSONArray("bus");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object4 = array.getJSONObject(i);
                                JSONObject object6 = object4.getJSONObject("segments");
                                JSONObject object7 = object6.getJSONObject("segment");
                                String start_stat = object7.getString("start_stat");
                                String end_stat = object7.getString("end_stat");
                                String line_names = object7.getString("line_name");
                                String stats = object7.getString("stats");
                                stats = stats.replaceAll(";", "->");
                                Map<String, String> map = new HashMap<>();
                                map.put("name", start_stat+"-->"+end_stat);
                                map.put("line_names", line_names);
                                map.put("info",stats);
                                mList.add(map);
                            }
                        }
                        mHandler.sendEmptyMessage(0);
                    }else {
                        LemonBubble.showError(CityBusTest.this,reason,1500);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(CityBusTest.this,"请求失败",1500);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(CityBusTest.this,volleyError.getMessage(),1500);
            }
        });
        mQueue.add(request);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                CityBusAdapter adapter = new CityBusAdapter(CityBusTest.this,mList,R.layout.item_citybus);
                listview_citybus.setAdapter(adapter);
                LemonBubble.hide();
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        mQueue.stop();
        style = 0;
        if (mList != null){
            mList = null;
        }
    }

    private class CityBusAdapter extends CommonAdapter<Map<String,String>>{

        private CityBusAdapter(Context context, List<Map<String, String>> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Map<String, String> item) {
            TextView name = helper.getView(R.id.txt_item_citybus_name);
            TextView lineNames = helper.getView(R.id.txt_item_citybus_lineNames);
            TextView info = helper.getView(R.id.txt_item_citybus_info);

            name.setText(item.get("name"));
            lineNames.setText(item.get("line_names"));

            String infos = item.get("info");
            if (infos != null && !infos.equals("")){
                info.setText(infos);
            }
        }
    }
}
