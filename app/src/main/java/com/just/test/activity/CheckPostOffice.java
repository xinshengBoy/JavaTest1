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
import com.just.test.bean.PostOfficeBean;
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
import java.util.regex.Pattern;

/**
 * 全国邮编查询
 * 参考网址：http://avatardata.cn/Docs/Api/b3d25cbd-449d-41c3-8765-21649658789e
 * Created by admin on 2017/6/1.
 */

public class CheckPostOffice extends Activity implements View.OnClickListener{

    private RequestQueue mQueue;
    private EditText et_postOffice;
    private TextView txt_postOffice;
    private ListView listview_postOffice;
    private List<PostOfficeBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_check_postoffice);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 全国邮编查询");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());

        initView();
    }

    private void initView() {
        et_postOffice = (EditText)findViewById(R.id.et_postOffice);
        txt_postOffice = (TextView)findViewById(R.id.txt_postOffice);
        listview_postOffice = (ListView)findViewById(R.id.listview_postOffice);

        txt_postOffice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == txt_postOffice){
            EditTextTools.hideSoftInput(CheckPostOffice.this);//隐藏软键盘
            if (mList.size() > 0){
                mList.clear();
            }
            queryData();
        }
    }

    private void queryData(){
        String input = et_postOffice.getText().toString();
        if (input.equals("")){
            return;
        }

        LemonBubble.showRoundProgress(CheckPostOffice.this, "加载中");
        String url;
        //判断是否输入的是数字
        String reg = "^-?\\d+$";
        boolean isNumber = Pattern.compile(reg).matcher(input).find();
        if (isNumber) {
            url = " http://api.avatardata.cn/PostNumber/QueryPostnumber?key=70315040049e40d29bd48d3c0dc61b6c&postnumber="+input+"&page=1&rows=50";
        }else {
            url = " http://api.avatardata.cn/PostNumber/QueryAddress?key=70315040049e40d29bd48d3c0dc61b6c&address="+input+"&page=1&rows=50";
        }

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
                            PostOfficeBean detail = new PostOfficeBean();
                            detail.setPostnumber(object1.getString("postnumber"));
                            detail.setAddress(object1.getString("jd")+object1.getString("address"));

                            mList.add(detail);
                        }
                        mHandler.sendEmptyMessage(0);
                    }else {
                        LemonBubble.showError(CheckPostOffice.this, reason, 1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(CheckPostOffice.this, "请求错误", 1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(CheckPostOffice.this, volleyError.getMessage(), 1000);
            }
        });
        mQueue.add(request);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                PostOfficeAdapter adapter = new PostOfficeAdapter(CheckPostOffice.this,mList,R.layout.item_history_today);
                listview_postOffice.setAdapter(adapter);
                LemonBubble.hide();
            }
        }
    };

    private class PostOfficeAdapter extends CommonAdapter<PostOfficeBean> {

        public PostOfficeAdapter(Context context, List<PostOfficeBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, PostOfficeBean item) {
            TextView address = helper.getView(R.id.item_historyToday_title);
            TextView postNumber = helper.getView(R.id.item_historyToday_date);

            address.setText(item.getAddress());
            postNumber.setText("邮编："+item.getPostnumber());
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
