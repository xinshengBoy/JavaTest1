package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
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
import com.just.test.bean.FamousPeopleSayBean;
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
 * 名人名言
 * 参考网址：http://avatardata.cn/Docs/Api/5bc6f2a4-927c-415f-80ae-8772b76c8c73
 * Created by admin on 2017/6/1.
 */

public class FamousPeopleSay extends Activity implements View.OnClickListener{

    private RequestQueue mQueue;
    private List<FamousPeopleSayBean> mList = new ArrayList<>();
    private EditText et_famous_peoplesay;
    private TextView txt_famous_peoplesay;
    private ListView listview_famous_peoplesay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_famous_peoplesay);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 名人名言");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());

        initView();
    }

    private void initView(){
        et_famous_peoplesay = (EditText)findViewById(R.id.et_famous_peoplesay);
        txt_famous_peoplesay = (TextView)findViewById(R.id.txt_famous_peoplesay);
        listview_famous_peoplesay = (ListView)findViewById(R.id.listview_famous_peoplesay);

        txt_famous_peoplesay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == txt_famous_peoplesay){
            EditTextTools.hideSoftInput(FamousPeopleSay.this);//隐藏软键盘
            if (mList.size() > 0){
                mList.clear();
            }
            queryData();
        }
    }

    private void queryData(){
        String input = et_famous_peoplesay.getText().toString();
        if (input.equals("")){
            return;
        }
        LemonBubble.showRoundProgress(FamousPeopleSay.this, "加载中");
        String url = " http://api.avatardata.cn/MingRenMingYan/LookUp?key=3ee5dace01e447219b6915291ce3c4fd&keyword="+input+"&page=1&rows=50";
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

                            FamousPeopleSayBean detail = new FamousPeopleSayBean();
                            detail.setFamous_name(object1.getString("famous_name"));
                            detail.setFamous_saying(object1.getString("famous_saying"));

                            mList.add(detail);
                        }
                        mHandler.sendEmptyMessage(0);
                    }else {
                        LemonBubble.showError(FamousPeopleSay.this, reason, 1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(FamousPeopleSay.this, "请求错误", 1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(FamousPeopleSay.this, volleyError.getMessage(), 1000);
            }
        });
        mQueue.add(request);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                FamousPeopleSayAdapter adapter = new FamousPeopleSayAdapter(FamousPeopleSay.this,mList,R.layout.item_history_today);
                listview_famous_peoplesay.setAdapter(adapter);
                LemonBubble.hide();
            }
        }
    };

    private class FamousPeopleSayAdapter extends CommonAdapter<FamousPeopleSayBean>{

        public FamousPeopleSayAdapter(Context context, List<FamousPeopleSayBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, FamousPeopleSayBean item) {
            TextView saying = helper.getView(R.id.item_historyToday_title);
            TextView name = helper.getView(R.id.item_historyToday_date);

            saying.setText(item.getFamous_saying());
            name.setText(item.getFamous_name());
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
