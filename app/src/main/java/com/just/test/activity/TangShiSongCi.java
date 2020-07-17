package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
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
 * 唐诗宋词
 * http://avatardata.cn/Docs/Api/6db8f77f-37b3-409f-afb4-890be7930b46
 * Created by admin on 2017/6/19.
 */

public class TangShiSongCi extends Activity {

    private RequestQueue mQueue;
    private TextView biaoti,jieshao,zuozhe,neirong;
    private List<Bundle> mList = new ArrayList<>();
    private LinearLayout detail_layout;
    private ListView listview_tangshisongci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tangshisongci);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "唐诗宋词");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
    }

    private void initView() {
        AutoCompleteTextView act_tangshisongci = (AutoCompleteTextView) findViewById(R.id.act_tangshisongci);
        Button btn_tangshisongci_query = (Button) findViewById(R.id.btn_tangshisongci_query);
        Button btn_tangshi_random = (Button) findViewById(R.id.btn_tangshi_random);
        detail_layout = (LinearLayout)findViewById(R.id.detail_layout);
        biaoti = (TextView)findViewById(R.id.txt_tangshisongci_biaoti);
        jieshao = (TextView)findViewById(R.id.txt_tangshisongci_jieshao);
        zuozhe = (TextView)findViewById(R.id.txt_tangshisongci_zuozhe);
        neirong = (TextView)findViewById(R.id.txt_tangshisongci_neirong);
        listview_tangshisongci = (ListView)findViewById(R.id.listview_tangshisongci);

        btn_tangshisongci_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listview_tangshisongci.setVisibility(View.VISIBLE);
                detail_layout.setVisibility(View.GONE);
            }
        });

        btn_tangshi_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryTangshiDetail(null);
            }
        });
        act_tangshisongci.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();
                if (input.length() > 0) {
                    queryKeyWord(charSequence.toString());
                }else {
                    mList.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 通过关键字查询获取id和name
     * @param keyword 关键字
     */
    private void queryKeyWord(String keyword){
        LemonBubble.showRoundProgress(TangShiSongCi.this, "加载中");
        String url = "http://api.avatardata.cn/TangShiSongCi/Search?key=fedf745df30e4dc49491ebbd718628d6&keyWord="+keyword;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0) {
                        JSONArray array = object.getJSONArray("result");
                        for (int i=0;i<array.length();i++){
                            JSONObject object1 = array.getJSONObject(i);
                            String id = object1.getString("id");
                            String name = object1.getString("name");

                            Bundle bundle = new Bundle();
                            bundle.putString("id",id);
                            bundle.putString("name",name);

                            mList.add(bundle);
                        }
                        mHandler.sendEmptyMessage(0);
                    }else {
                        LemonBubble.showError(TangShiSongCi.this,reason,1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(TangShiSongCi.this,"参数错误",1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(TangShiSongCi.this,volleyError.getMessage(),1000);
            }
        });
        mQueue.add(request);
    }

    /**
     * 查询具体的诗句
     * @param id 诗句的id
     */
    private void queryTangshiDetail(String id){
        LemonBubble.showRoundProgress(TangShiSongCi.this, "加载中");
        String url;
        if (id != null) {
            url = "http://api.avatardata.cn/TangShiSongCi/LookUp?key=fedf745df30e4dc49491ebbd718628d6&id=" + id;
        }else {
            url = "http://api.avatardata.cn/TangShiSongCi/Random?key=fedf745df30e4dc49491ebbd718628d6";
        }
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0) {
                        JSONObject object1 = object.getJSONObject("result");
                        biaoti.setText(object1.getString("biaoti"));
                        jieshao.setText(object1.getString("jieshao"));
                        zuozhe.setText(object1.getString("zuozhe"));
                        neirong.setText(object1.getString("neirong"));

                        mHandler.sendEmptyMessage(1);
                    }else {
                        LemonBubble.showError(TangShiSongCi.this,reason,1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(TangShiSongCi.this,"参数错误",1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(TangShiSongCi.this,volleyError.getMessage(),1000);
            }
        });
        mQueue.add(request);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                TangshisongciAdapter adapter = new TangshisongciAdapter(TangShiSongCi.this,mList,R.layout.item_tangshisongci);
                listview_tangshisongci.setAdapter(adapter);
                listview_tangshisongci.setVisibility(View.VISIBLE);
                detail_layout.setVisibility(View.GONE);
                LemonBubble.hide();
                listview_tangshisongci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String select = mList.get(i).getString("id");
                        queryTangshiDetail(select);
                    }
                });
            }else if (msg.what == 1){
                listview_tangshisongci.setVisibility(View.GONE);
                detail_layout.setVisibility(View.VISIBLE);
                LemonBubble.hide();
            }
        }
    };

    private class TangshisongciAdapter extends CommonAdapter<Bundle>{

        public TangshisongciAdapter(Context context, List<Bundle> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Bundle item) {
            TextView name = helper.getView(R.id.txt_item_tangshi_name);
            TextView id = helper.getView(R.id.txt_item_tangshi_id);

            name.setText(item.getString("name"));
            id.setText(item.getString("id"));
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
