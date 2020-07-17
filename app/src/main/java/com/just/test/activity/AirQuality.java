package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.bean.AirQualityBean;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 全国空气质量查询
 * 参考网址：http://avatardata.cn/Docs/Api?id=6258d582-7779-48f5-acc2-aaa64b14e3c7&detailId=06f8bd7f-c363-4bdf-8026-261f8b9e41ed
 * Created by admin on 2017/6/1.
 */

public class AirQuality extends Activity {

    private RequestQueue mQueue;
    private ListView listview_airquality_city,listview_airquality_detail;
    private List<String> cityList = new ArrayList<>();
    private List<AirQualityBean> airList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_airquality);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 全国空气质量查询");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());

        initView();
    }

    private void initView() {
        listview_airquality_city = (ListView)findViewById(R.id.listview_airquality_city);
        listview_airquality_detail = (ListView)findViewById(R.id.listview_airquality_detail);
        mHandler.sendEmptyMessage(0);
    }

    private void queryCityList(){
        LemonBubble.showRoundProgress(AirQuality.this, "加载中");
        String url = "http://api.avatardata.cn/Aqi/CityList?key=74f24db4b5f04047a6ce70856ac44d1f";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        String result = object.getString("result");
                        String [] results = result.split(",");
                        for (int i=0;i<results.length;i++){
                            cityList.add(results[i]);
                        }
                        mHandler.sendEmptyMessage(1);
                    }else {
                        LemonBubble.showError(AirQuality.this, reason, 1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(AirQuality.this, "请求失败", 1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(AirQuality.this, volleyError.getMessage(), 1000);
            }
        });
        mQueue.add(request);
    }

    private void queryDetail(String city){
        LemonBubble.showRoundProgress(AirQuality.this, "加载中");
        String url = " http://api.avatardata.cn/Aqi/LookUp?key=74f24db4b5f04047a6ce70856ac44d1f&city="+city;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        JSONObject object1 = object.getJSONObject("result");
                        AirQualityBean detail = new AirQualityBean();
                        detail.setCity(object1.getString("city"));
                        detail.setInputTime(object1.getString("time"));
                        detail.setAqi(object.getString("aqi"));
                        detail.setLevel(object.getString("level"));
                        detail.setCore(object.getString("core"));

                        airList.add(detail);
                        mHandler.sendEmptyMessage(2);
                    }else {
                        LemonBubble.showError(AirQuality.this, reason, 1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(AirQuality.this, "请求失败", 1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(AirQuality.this, volleyError.getMessage(), 1000);
            }
        });
        mQueue.add(request);
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                queryCityList();
            }else if (msg.what == 1){
                AirQualityCityAdapter cityAdapter = new AirQualityCityAdapter(AirQuality.this,cityList,R.layout.item_province_area);
                listview_airquality_city.setAdapter(cityAdapter);
                LemonBubble.hide();
                listview_airquality_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (airList.size() > 0){
                            airList.clear();
                        }
                        queryDetail(cityList.get(i));
                    }
                });
            }else if (msg.what == 2){
                AirQualityDetailAdapter detailAdapter = new AirQualityDetailAdapter(AirQuality.this,airList,R.layout.item_airquality_detail);
                listview_airquality_detail.setAdapter(detailAdapter);
                LemonBubble.hide();
            }
        }
    };

    private class AirQualityCityAdapter extends CommonAdapter<String>{

        private AirQualityCityAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            TextView cityName = helper.getView(R.id.txt_item_province_name);
            cityName.setText(item);
        }
    }

    private class AirQualityDetailAdapter extends CommonAdapter<AirQualityBean>{

        private AirQualityDetailAdapter(Context context, List<AirQualityBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, AirQualityBean item) {
            TextView city = helper.getView(R.id.txt_item_airquality_cityName);
            TextView aqi = helper.getView(R.id.txt_item_airquality_aqi);
            TextView level = helper.getView(R.id.txt_item_airquality_level);
            TextView core = helper.getView(R.id.txt_item_airquality_core);
            TextView time = helper.getView(R.id.txt_item_airquality_time);

            city.setText(item.getCity());
            aqi.setText(item.getAqi());
            level.setText(item.getLevel());
            core.setText(item.getCore());
            time.setText(item.getInputTime());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        mQueue.stop();
        if (cityList != null){
            cityList = null;
        }
        if (airList != null){
            airList = null;
        }
    }
}
