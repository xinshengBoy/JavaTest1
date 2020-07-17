package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.tools.EditTextTools;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 今日油价
 * http://avatardata.cn/Docs/Api/a77e2154-2789-43a2-a30e-aa34d95df26d
 * Created by admin on 2017/6/19.
 */

public class OilPrice extends Activity {

    private RequestQueue mQueue;
    private EditText et_oilprice;
    private TextView txt_oilprice_createTime,txt_oilprice_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_oilprice);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "今日油价");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
    }

    private void initView() {
        et_oilprice = (EditText)findViewById(R.id.et_oilprice);
        Button btn_oilPrice_query = (Button) findViewById(R.id.btn_oilPrice_query);
        txt_oilprice_createTime = (TextView)findViewById(R.id.txt_oilprice_createTime);
        txt_oilprice_detail = (TextView)findViewById(R.id.txt_oilprice_detail);

        btn_oilPrice_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_oilprice.getText().toString();
                if (input.equals("")){
                    LemonBubble.showError(OilPrice.this,"参数不能为空",1000);
                }else {
                    queryOilPrice(input);
                }
            }
        });
    }

    /**
     * 查询相关省份的今日油价
     * @param province 省份
     */
    private void queryOilPrice(String province){
        LemonBubble.showRoundProgress(OilPrice.this, "加载中");
        EditTextTools.hideSoftInput(OilPrice.this);
        String url = "http://api.avatardata.cn/OilPrice/LookUp?key=711fea7f215e48a8b6b4222e41d89849&prov="+province;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        JSONObject object1 = object.getJSONObject("result");
                        if (object1 == null){
                            LemonBubble.showError(OilPrice.this,"数据为空",1000);
                            return;
                        }
                        JSONArray array = object1.getJSONArray("list");
                        for (int i=0;i<array.length();i++){
                            JSONObject object2 = array.getJSONObject(i);
                            txt_oilprice_createTime.setText(object2.getString("ct"));
                            txt_oilprice_detail.setText("0号汽油："+object2.getString("po")+"元"+"\n"
                                +"90号汽油："+object2.getString("p90")+"元"+"\n"
                                +"93号汽油："+object2.getString("p93")+"元"+"\n"
                                +"97号汽油："+object2.getString("p97")+"元");
                        }
                        LemonBubble.hide();
                    }else {
                        LemonBubble.showError(OilPrice.this,reason,1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(OilPrice.this,"请求失败"+e.getMessage(),1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(OilPrice.this,volleyError.getMessage(),1000);
            }
        });
        mQueue.add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        mQueue.stop();
    }
}
