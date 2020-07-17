package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import com.just.test.bean.ProvinceDivice;
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
 * 全国行政区域划分
 * 参考网址：http://avatardata.cn/Docs/Api/6d39633a-c340-40e2-ad7b-eb81463b21fd
 * Created by admin on 2017/6/1.
 */

public class ProvinceArea extends Activity{

    private RequestQueue mQueue;
    private ListView area1,area2,area3;
    private List<ProvinceDivice> mList1 = new ArrayList<>();//省级和直辖市数据
    private List<ProvinceDivice> mList2 = new ArrayList<>();//所辖市数据
    private List<ProvinceDivice> mList3 = new ArrayList<>();//所辖区县数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_province_area);
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 全国行政区域划分");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());

        initView();
    }

    private void initView(){
        area1 = (ListView)findViewById(R.id.listview_provinceArea1);
        area2 = (ListView)findViewById(R.id.listview_provinceArea2);
        area3 = (ListView)findViewById(R.id.listview_provinceArea3);

        handler.sendEmptyMessage(0);
    }

    private void queryData(String id, final List<ProvinceDivice> list, final int msg){
        LemonBubble.showRoundProgress(ProvinceArea.this, "加载中");
        String url;
        if (id == null){
            url = " http://api.avatardata.cn/SimpleArea/LookUp?key=0aa7fc75b970429fac81ab67e13095fa";
        }else {
            url = " http://api.avatardata.cn/SimpleArea/LookUp?key=0aa7fc75b970429fac81ab67e13095fa&parentId=" + id;
        }
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray array = object.getJSONArray("result");
                    for (int i=0;i<array.length();i++){
                        JSONObject object1 = array.getJSONObject(i);
                        ProvinceDivice detail = new ProvinceDivice();
                        detail.setArea_id(object1.getString("area_id"));
                        detail.setParent_id(object1.getString("parent_id"));;
                        detail.setName(object1.getString("name"));
                        detail.setLevel(object1.getString("level"));
                        detail.setSeq(object1.getString("seq"));

                        list.add(detail);
                    }
                    handler.sendEmptyMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(ProvinceArea.this, "请求错误", 1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(ProvinceArea.this, volleyError.getMessage(), 1000);
            }
        });
        mQueue.add(request);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                queryData(null,mList1,1);
            }else if (msg.what == 1){
                ProvinceAreaAdapter adapter = new ProvinceAreaAdapter(ProvinceArea.this,mList1,R.layout.item_province_area);
                area1.setAdapter(adapter);
                LemonBubble.hide();
                area1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        adapterView.setBackgroundColor(Color.WHITE);
                        view.setBackgroundColor(Color.RED);
                        if (mList2.size() > 0){//将原先数据清掉
                            mList2.clear();
                        }
                        queryData(mList1.get(i).getArea_id(),mList2,2);
                    }
                });
            }else if (msg.what == 2){
                ProvinceAreaAdapter adapter = new ProvinceAreaAdapter(ProvinceArea.this,mList2,R.layout.item_province_area);
                area2.setAdapter(adapter);
                LemonBubble.hide();
                area2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        adapterView.setBackgroundColor(Color.WHITE);
                        view.setBackgroundColor(Color.RED);
                        if (mList3.size() > 0){//将原先数据清掉
                            mList3.clear();
                        }
                        queryData(mList2.get(i).getArea_id(),mList3,3);
                    }
                });
            }else if (msg.what == 3){
                ProvinceAreaAdapter adapter = new ProvinceAreaAdapter(ProvinceArea.this,mList3,R.layout.item_province_area);
                area3.setAdapter(adapter);
                LemonBubble.hide();
                area3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        adapterView.setBackgroundColor(Color.WHITE);
                        view.setBackgroundColor(Color.RED);
                    }
                });
            }
        }
    };

    private class ProvinceAreaAdapter extends CommonAdapter<ProvinceDivice>{

        public ProvinceAreaAdapter(Context context, List<ProvinceDivice> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, ProvinceDivice item) {
            TextView areaName = helper.getView(R.id.txt_item_province_name);
            areaName.setText(item.getName()+"（代号："+item.getSeq()+"）");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        if (mList1 != null){
            mList1 = null;
        }
        if (mList2 != null){
            mList2 = null;
        }
        if (mList3 != null){
            mList3 = null;
        }
    }
}
