package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.just.test.R;
import com.just.test.bean.InternationalNewsBean;
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
 * 国际新闻
 * 参考网址：https://www.tianapi.com/#news
 * http://avatardata.cn/Docs/Api/8c3bd19e-2a06-45de-839a-baa72f872ec9
 * Created by admin on 2017/6/1.
 */

public class InternationalNews extends Activity{

    private RequestQueue mQueue;
    private List<InternationalNewsBean> mList = new ArrayList<>();
    private TextView txt_international_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_international_news);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "国际新闻");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        txt_international_refresh = (TextView)findViewById(R.id.txt_international_refresh);
        txt_international_refresh.setVisibility(View.GONE);
        mHandler.sendEmptyMessage(0);
    }

    public void onClicks(View view){
        switch (view.getId()){
            case R.id.btn_internationalNews_guoji:
                queryData("world");
                break;
            case R.id.btn_internationalNews_guonei:
                queryData("guonei");
                break;
            case R.id.btn_internationalNews_shehui:
                queryData("social");
                break;
            case R.id.btn_internationalNews_yule:
                queryData("huabian");
                break;
            case R.id.btn_internationalNews_tiyu:
                queryData("tiyu");
                break;
            case R.id.btn_internationalNews_nba:
                queryData("nba");
                break;
            case R.id.btn_internationalNews_zuqiu:
                queryData("football");
                break;
            case R.id.btn_internationalNews_keji:
                queryData("keji");
                break;
            case R.id.btn_internationalNews_chuangye:
                queryData("startup");
                break;
            case R.id.btn_internationalNews_pingguo:
                queryData("apple");
                break;
            case R.id.btn_internationalNews_junshi:
                queryData("military");
                break;
            case R.id.btn_internationalNews_yidong:
                queryData("mobile");
                break;
            case R.id.btn_internationalNews_lvyou:
                queryData("travel");
                break;
            case R.id.btn_internationalNews_jiankang:
                queryData("health");
                break;
            case R.id.btn_internationalNews_qiwen:
                queryData("qiwen");
                break;
            case R.id.btn_internationalNews_meinv:
                queryData("meinv");
                break;
            case R.id.btn_internationalNews_vr:
                queryData("vr");
                break;
            case R.id.btn_internationalNews_it:
                queryData("it");
                break;
            case R.id.btn_internationalNews_weixin:
                queryData("wxnew");
                break;
        }
    }
    private void queryData(String name){
        LemonBubble.showRoundProgress(InternationalNews.this, "加载中");
        if (mList.size() > 0){
            mList.clear();
        }
//        String url = "http://api.avatardata.cn/WorldNews/Query?key=5c8a0d1661b94e7fabdd55837829dbf7&page=1&rows=10";
        String url = "https://api.tianapi.com/"+name+"/?key=a3f826587cf746a44a0a15100c094810&num=50";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    String reason = object.getString("msg");
                    int error_code = object.getInt("code");
                    if (error_code == 200) {
                        JSONArray array = object.getJSONArray("newslist");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object1 = array.getJSONObject(i);
                            InternationalNewsBean detail = new InternationalNewsBean();
                            detail.setCtime(object1.getString("ctime"));
                            detail.setTitle(object1.getString("title"));
                            detail.setDescription(object1.getString("description"));
                            detail.setPicUrl(object1.getString("picUrl"));
                            detail.setUrl(object1.getString("url"));

                            mList.add(detail);
                        }
                        mHandler.sendEmptyMessage(1);
                    }else {
                        LemonBubble.showError(InternationalNews.this, reason, 1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(InternationalNews.this, "请求错误", 1000);
                    txt_international_refresh.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(InternationalNews.this, volleyError.getMessage(), 1000);
                txt_international_refresh.setVisibility(View.VISIBLE);
            }
        });
        mQueue.add(request);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                queryData("world");//第一次进入默认加载国际新闻
            }else if (msg.what == 1){
                txt_international_refresh.setVisibility(View.GONE);
                InternationalNewsAdapter adapter = new InternationalNewsAdapter(InternationalNews.this,mList,R.layout.item_international_news);
                ListView listview_internationalNews = (ListView)findViewById(R.id.listview_internationalNews);
                listview_internationalNews.setAdapter(adapter);
                LemonBubble.hide();
                listview_internationalNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(InternationalNews.this,InternationalNewsDetail.class);
                        intent.putExtra("title",mList.get(i).getTitle());
                        intent.putExtra("url",mList.get(i).getUrl());
                        startActivity(intent);
                    }
                });
            }
        }
    };


    private class InternationalNewsAdapter extends CommonAdapter<InternationalNewsBean>{

        private final Context context;

        private InternationalNewsAdapter(Context context, List<InternationalNewsBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
            this.context = context;
        }

        @Override
        public void convert(ViewHolder helper, InternationalNewsBean item) {
            ImageView pic = helper.getView(R.id.iv_item_international);
            TextView title = helper.getView(R.id.txt_item_international_title);
            TextView descripyion = helper.getView(R.id.txt_item_international_descripyion);
            TextView ctime = helper.getView(R.id.txt_item_international_ctime);

            title.setText(item.getTitle());
            descripyion.setText(item.getDescription());
            ctime.setText(item.getCtime());

            if (!item.getPicUrl().equals("")){
                Glide.with(context).load(item.getPicUrl()).into(pic);
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
        mQueue.stop();
    }
}
