package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * 姓氏起源
 * 参考网址：http://avatardata.cn/Docs/Api?id=7fd9b74b-f051-4751-9f9e-26da8bcc674e&detailId=36d3a146-0676-4e55-aa47-4ad16dfb6d0d
 * Created by admin on 2017/6/2.
 */

public class NameOrigin extends Activity {

    private EditText et_nameOrigin;
    private TextView txt_nameOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_name_origin);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 姓氏起源");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView(){
        et_nameOrigin = (EditText)findViewById(R.id.et_nameOrigin);
        Button btn_nameOrigin_check = (Button)findViewById(R.id.btn_nameOrigin_check);
        Button btn_nameOrigin_random = (Button)findViewById(R.id.btn_nameOrigin_random);
        txt_nameOrigin = (TextView)findViewById(R.id.txt_nameOrigin);
        btn_nameOrigin_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_nameOrigin.getText().toString();
                if (input.equals("")){
                    LemonBubble.showError(NameOrigin.this,"请输入您的姓",2000);
                    return;
                }
                queryName(input);
            }
        });
        btn_nameOrigin_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryName(null);
            }
        });
    }

    private void queryName(String name){
        LemonBubble.showRoundProgress(NameOrigin.this, "加载中");
        String url;
        if (name == null){
            url = "http://api.avatardata.cn/XingShiQiYuan/Random?key=1c56165057724837913e03f0e2a33c91";
        }else {
            url = "http://api.avatardata.cn/XingShiQiYuan/LookUp?key=1c56165057724837913e03f0e2a33c91&xingshi=" + name;
        }

        OkHttpUtils.get().url(url).tag(this).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LemonBubble.showError(NameOrigin.this,"请求错误",1000);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        JSONObject object1 = object.getJSONObject("result");
                        String xing = object1.getString("xing");
                        String intro = object1.getString("intro");

                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("xing",xing);
                        bundle.putString("intro",intro);
                        message.what = 0;
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    }else {
                        LemonBubble.showError(NameOrigin.this,reason,1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(NameOrigin.this,"请求错误",1000);
                }
            }
        });
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                Bundle bundle = msg.getData();
                txt_nameOrigin.setText(bundle.getString("xing")+"\n"+ bundle.getString("intro"));
                LemonBubble.hide();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        mHandler.removeCallbacksAndMessages(null);
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
