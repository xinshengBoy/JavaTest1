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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 号码吉凶和城市坐标
 * http://avatardata.cn/Docs/Api/8dc6f9ae-b8d8-43a4-97fa-c262af534b61  //号码吉凶
 * http://avatardata.cn/Docs/Api/589efab9-0b1f-46f3-88bc-748bc440446c //城市坐标
 * Created by admin on 2017/6/19.
 */

public class NunberJiXiong extends Activity{

    private EditText et_number_jixiong,et_cityXY;
    private RequestQueue mQueue;
    private TextView yunshi,shuli,hanyi,jianshu,jiye,jiating,jiankang,xiangjie,txt_cityXY;
    private LinearLayout cityXY_layout,numberJiXiong_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_number_jixiong);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "号码吉凶和城市坐标");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
    }

    private void initView() {
        et_number_jixiong = (EditText) findViewById(R.id.et_number_jixiong);
        et_cityXY = (EditText)findViewById(R.id.et_cityXY);
        cityXY_layout = (LinearLayout)findViewById(R.id.cityXY_layout);
        numberJiXiong_layout = (LinearLayout)findViewById(R.id.numberJiXiong_layout);
        Button btn_query = (Button)findViewById(R.id.btn_numberJixiong_query);
        Button btn_cityXY_query = (Button)findViewById(R.id.btn_cityXY_query);
        yunshi = (TextView)findViewById(R.id.txt_numberJixiong_yunshi);
        shuli = (TextView)findViewById(R.id.txt_numberJixiong_shuli);
        hanyi = (TextView)findViewById(R.id.txt_numberJixiong_hanyi);
        jianshu = (TextView)findViewById(R.id.txt_numberJixiong_jianshu);
        jiye = (TextView)findViewById(R.id.txt_numberJixiong_jiye);
        jiating = (TextView)findViewById(R.id.txt_numberJixiong_jiating);
        jiankang = (TextView)findViewById(R.id.txt_numberJixiong_jiankang);
        xiangjie = (TextView)findViewById(R.id.txt_numberJixiong_xiangjie);
        txt_cityXY = (TextView)findViewById(R.id.txt_cityXY);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_number_jixiong.getText().toString();
                if (input.equals("")){
                    LemonBubble.showError(NunberJiXiong.this,"参数不能为空",1000);
                }else {
                    numberJiXiong_layout.setVisibility(View.VISIBLE);
                    cityXY_layout.setVisibility(View.GONE);
                    queryJiXiong(input);
                }
            }
        });

        btn_cityXY_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_cityXY.getText().toString();
                if (input.equals("")){
                    LemonBubble.showError(NunberJiXiong.this,"参数不能为空",1000);
                }else {
                    numberJiXiong_layout.setVisibility(View.GONE);
                    cityXY_layout.setVisibility(View.VISIBLE);
                    queryCityXY(input);
                }
            }
        });
    }

    /**
     * 查询城市坐标
     * @param city 城市名称
     */
    private void queryCityXY(String city){
        EditTextTools.hideSoftInput(NunberJiXiong.this);
        LemonBubble.showRoundProgress(NunberJiXiong.this, "加载中");
        String url = "http://api.avatardata.cn/CityCoordinates/LookUp?key=14a9a3f01c144fcc9e93d53da9ad2ff5&city="+city;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0) {
                        JSONObject object1 = object.getJSONObject("result");
                        String x = object1.getString("x");
                        String y = object1.getString("y");
                        if (x != null && y != null) {
                            txt_cityXY.setText("经度：" + x + ";纬度:" + y);
                        }else {
                            LemonBubble.showError(NunberJiXiong.this,"查询失败",1000);
                        }
                        LemonBubble.hide();
                    }else {
                        LemonBubble.showError(NunberJiXiong.this,reason,1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(NunberJiXiong.this,"参数错误",1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(NunberJiXiong.this,volleyError.getMessage(),1000);
            }
        });
        mQueue.add(request);
    }
    /**
     * 查询号码吉凶
     * @param number 号码
     */
    private void queryJiXiong(String number){
        EditTextTools.hideSoftInput(NunberJiXiong.this);
        LemonBubble.showRoundProgress(NunberJiXiong.this, "加载中");
        String url = "http://v1.avatardata.cn/JiXiong/LookUp?key=f0a9ac199e5941d5b5ab2cce1850e154&keyword="+number;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        JSONObject object1 = object.getJSONObject("result");
                        yunshi.setText("运势："+object1.getString("yunshi"));
                        shuli.setText("梳理："+object1.getString("shuli"));
                        hanyi.setText("含义："+object1.getString("hanyi"));
                        jianshu.setText("简述："+object1.getString("jianshu"));
                        jiye.setText("吉业："+object1.getString("jiye"));
                        jiating.setText("家庭："+object1.getString("jiating"));
                        jiankang.setText("健康："+object1.getString("jiankang"));
                        xiangjie.setText("详解："+object1.getString("xiangjie"));
                        LemonBubble.hide();
                    }else {
                        LemonBubble.showError(NunberJiXiong.this,reason,1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(NunberJiXiong.this,"参数错误",1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(NunberJiXiong.this,volleyError.getMessage(),1000);
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
