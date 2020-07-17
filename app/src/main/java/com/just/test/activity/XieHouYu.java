package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 歇后语
 * 参考网址：http://avatardata.cn/Docs/Api/610ac3b2-6ee3-4944-b8ac-6918c55b11fc
 * Created by admin on 2017/6/1.
 */

public class XieHouYu extends Activity {

    private RequestQueue mQueue;
    private EditText et_xiehouyu;
    private TextView txt_xiehouyu_result,txt_xiehouyu_random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xiehouyu);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 歇后语");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());

        initView();
    }

    private void initView() {
        et_xiehouyu = (EditText)findViewById(R.id.et_xiehouyu);
        TextView txt_xiehouyu = (TextView) findViewById(R.id.txt_xiehouyu);
        txt_xiehouyu_result = (TextView)findViewById(R.id.txt_xiehouyu_result);

        txt_xiehouyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_xiehouyu.getText().toString();
                txt_xiehouyu_result.setText("");
                if (input.equals("")){
                    LemonBubble.showError(XieHouYu.this,"请输入关键字",1000);
                    return;
                }
                queryData(input);
            }
        });

        txt_xiehouyu_random = (TextView)findViewById(R.id.txt_xiehouyu_random);
        TextView txt_xiehouyu_refresh = (TextView)findViewById(R.id.txt_xiehouyu_refresh);
        txt_xiehouyu_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_xiehouyu_random.setText("");//先清除上一次的东西
                randomQueryData();
            }
        });
        randomQueryData();//进来自动加载
    }

    private void queryData(String keyWord){
        String url = "http://api.avatardata.cn/XieHouYu/Search?key=0ef699daf0a548f0bda7709d9ab47846&keyWord="+keyWord;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        int totalCount = object.getInt("total");
                        JSONArray array = object.getJSONArray("result");
                        String question = null;
                        String answer = null;
                        for (int i=0;i<array.length();i++){
                            JSONObject object1 = array.getJSONObject(i);
                            question = object1.getString("question");
                            answer = object1.getString("answer");
                        }
                        txt_xiehouyu_result.setText("问题：" + question +"\n" + "答案："+answer);
                    }else {
                        LemonBubble.showError(XieHouYu.this, reason, 1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(XieHouYu.this, "请求错误", 1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(XieHouYu.this, volleyError.getMessage(), 1000);
            }
        });
        mQueue.add(request);
    }

    /**
     * 随机获取
     */
    private void randomQueryData(){
        String url = " http://api.avatardata.cn/XieHouYu/Random?key=0ef699daf0a548f0bda7709d9ab47846";

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        JSONObject object1 = object.getJSONObject("result");
                        String question = object1.getString("question");
                        String answer = object1.getString("answer");
                        txt_xiehouyu_random.setText("问题：" + question +"\n" + "答案："+answer);
                    }else {
                        LemonBubble.showError(XieHouYu.this, reason, 1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(XieHouYu.this, "请求错误", 1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(XieHouYu.this, volleyError.getMessage(), 1000);
            }
        });
        mQueue.add(request);
    }
}
