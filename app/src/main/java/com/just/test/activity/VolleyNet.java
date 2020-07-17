package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.tools.CommonAdapter;
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

/**
 * volly网络请求
 * volley stringrequest（get），jsonrequest
 */
public class VolleyNet extends Activity implements OnClickListener {
    private Button btn_getNetWork, btn_postNetWork;
    private ListView list_volley;
    private ListView list_jsonVolley;
    private LinearLayout layout_volleyHeader;
    private RequestQueue mQueue;
    private final String volleyUrl = "http://222.73.229.180:8080/v1/sh1/line/000001?select=price,avg_price,volume,time";
    private final String url = "http://mobile.sseinfo.com/options/course/optioneasy/c/4109810.json";
    private final String jsonUrl = "http://ordercente1.kokoerp.com/api/index.php?c=api_getApi_PDA&a=baseInfo";//物流渠道数据
    private List<Bundle> mList = new ArrayList<>();
    private List<String> mJsonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_network_layout);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "volly网络请求");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
    }

    private void initView() {
        btn_getNetWork = (Button) findViewById(R.id.btn_getNetWork);
        btn_postNetWork = (Button) findViewById(R.id.btn_postNetWork);
        layout_volleyHeader = (LinearLayout) findViewById(R.id.layout_volleyHeader);
        list_volley = (ListView) findViewById(R.id.list_getVolley);
        list_jsonVolley = (ListView) findViewById(R.id.list_jsonVolley);

        btn_getNetWork.setOnClickListener(this);
        btn_postNetWork.setOnClickListener(this);
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_getNetWork) {
            loadGetStr(volleyUrl);
        } else if (v == btn_postNetWork) {
            loadJsonStr(jsonUrl);
        }
    }

    /**
     * get请求
     *
     * @param url 请求的路径
     */
    private void loadGetStr(String url) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("line");
                    for (int i = 0; i < array.length(); i++) {
                        Bundle bundle = new Bundle();
                        String result = array.getString(i);
                        result = result.substring(1, result.length() - 1);
                        String[] results = result.split(",");
                        bundle.putString("price", results[0]);
                        bundle.putString("avgPrice", results[1]);
                        bundle.putString("volume", results[2]);
                        //时间做处理
                        String time = results[3];
                        if (time.length() < 6) {
                            time = "0" + time;
                        }
                        time = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
                        bundle.putString("time", time);

                        mList.add(bundle);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LemonBubble.showError(VolleyNet.this, error.getMessage(), 3000);
            }
        });
        mQueue.add(request);
    }

    /**
     * post请求
     * @param jsonUrl 请求的路径
     */
    private void loadJsonStr(String jsonUrl) {
        StringRequest request = new StringRequest(Request.Method.POST, jsonUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("allFreight");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        mJsonList.add(object1.getString("freight_name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LemonBubble.showError(VolleyNet.this, error.getMessage(), 3000);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key", "Rds345ESwdgdsss671SSD");
                return map;
            }
        };
        mQueue.add(request);
    }

    /**
     * get请求适配器
     */
    private class VolleyAdapter extends CommonAdapter<Bundle> {
        private VolleyAdapter(Context context, List<Bundle> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Bundle item) {
            TextView txt_time = helper.getView(R.id.txt_time);
            TextView txt_price = helper.getView(R.id.txt_price);
            TextView txt_avgPrice = helper.getView(R.id.txt_avgPrice);
            TextView txt_volume = helper.getView(R.id.txt_volume);

            txt_time.setText(item.getString("time"));
            txt_price.setText(item.getString("price"));
            txt_avgPrice.setText(item.getString("avgPrice"));
            txt_volume.setText(item.getString("volume"));
        }
    }

    /**
     * post请求
     */
    private class VolleyJsonAdapter extends CommonAdapter<String> {
        private VolleyJsonAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            TextView txt_jsonTitle = helper.getView(R.id.txt_jsonTitle);
//            TextView txt_jsonTime = helper.getView(R.id.txt_jsonTime);

            txt_jsonTitle.setText(item);
//            txt_jsonTime.setText(item.getString("time"));
        }
    }

    private final Handler handler = new Handler() {
        private VolleyJsonAdapter jsonAdapter;

        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                loadGetStr(volleyUrl);
            } else if (msg.what == 1) {
                list_volley.setVisibility(View.VISIBLE);
                layout_volleyHeader.setVisibility(View.VISIBLE);
                list_jsonVolley.setVisibility(View.GONE);
                VolleyAdapter adapter = new VolleyAdapter(VolleyNet.this, mList, R.layout.layout_volley_item);
                list_volley.setAdapter(adapter);
            } else if (msg.what == 2) {
                list_volley.setVisibility(View.GONE);
                layout_volleyHeader.setVisibility(View.GONE);
                list_jsonVolley.setVisibility(View.VISIBLE);
                jsonAdapter = new VolleyJsonAdapter(VolleyNet.this, mJsonList, R.layout.layout_jsonvolley_item);
                list_jsonVolley.setAdapter(jsonAdapter);
//                list_jsonVolley.setOnItemClickListener(new OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mJsonList.get(position).getString("url")));
//                        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//                        startActivity(intent);
//                    }
//                });
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        if (mList != null) {
            mList = null;
        }
        if (mJsonList != null) {
            mJsonList = null;
        }
    }
}
