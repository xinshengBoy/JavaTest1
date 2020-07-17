package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.bean.TranslateStyle;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在线翻译
 * 参考网址:http://avatardata.cn/Docs/Api/3c83a8d0-97e6-408d-b7a3-37d24a04ab6c
 * Created by admin on 2017/5/31.
 */

public class TranslateLine extends Activity implements View.OnClickListener{

    private EditText et_translate_line;
    private Spinner spinner_translate_from,spinner_translate_to;
    private TextView txt_translate_from,txt_translate_to;
    private Button btn_translates;
    private ArrayList<String> fromList = new ArrayList<>();
    private List<TranslateStyle> mList = new ArrayList<>();
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_translate_line);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "在线翻译");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        initData();
        initView();
    }

    private void initView(){
        et_translate_line = (EditText)findViewById(R.id.et_translate_line);
        spinner_translate_from = (Spinner)findViewById(R.id.spinner_translate_from);
        spinner_translate_to = (Spinner)findViewById(R.id.spinner_translate_to);
        btn_translates = (Button)findViewById(R.id.btn_translates);
        txt_translate_from = (TextView)findViewById(R.id.txt_translate_from);
        txt_translate_to = (TextView)findViewById(R.id.txt_translate_to);

        //当前语言
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(this, R.layout.item_spinselect, fromList);
        fromAdapter.setDropDownViewResource(R.layout.item_dialogspinselect);
        spinner_translate_from.setAdapter(fromAdapter);
        //要翻译成的语言
        ArrayAdapter<String> toAdapter = new ArrayAdapter<>(this, R.layout.item_spinselect, fromList);
        toAdapter.setDropDownViewResource(R.layout.item_dialogspinselect);
        spinner_translate_to.setAdapter(toAdapter);

        btn_translates.setOnClickListener(this);
    }

    private void initData(){
        String [] froms = new String[]{"中文","英语","日语","韩语","西班牙语","法语","泰语","阿拉伯语","俄语","葡萄牙语","德语","意大利语"};
        String [] abbs = new String[]{"cn","en","ja","ko","es","fr","th","ar","ru","pt","de","it"};

        for (int i=0;i<froms.length;i++){
            TranslateStyle detail = new TranslateStyle();
            detail.setLanguages(froms[i]);
            detail.setAbbreviation(abbs[i]);
            mList.add(detail);
            fromList.add(froms[i]);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_translates){
            String input = et_translate_line.getText().toString();
            String fromSelect = spinner_translate_from.getSelectedItem().toString();
            String toSelect = spinner_translate_to.getSelectedItem().toString();
            if (input.equals("")){
                return;
            }
            if (fromSelect.equals(toSelect)){
                return;
            }

            String abbFrom = "";
            String abbTo = "";
            for (int j=0;j<mList.size();j++){
                if (fromSelect.equals(mList.get(j).getLanguages())){
                    abbFrom = mList.get(j).getAbbreviation();
                }
                if (toSelect.equals(mList.get(j).getLanguages())){
                    abbTo = mList.get(j).getAbbreviation();
                }
            }
            if (abbFrom.equals("") || abbTo.equals("")){
                return;
            }
            String url = "http://api.avatardata.cn/Dictionary/Translate?key=82dd7be9d646498f8a2c98806ab08dae&query="+input+"&from="+abbFrom+"&to="+abbTo;
            final String finalAbbFrom = abbFrom;
            final String finalAbbTo = abbTo;
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String fromResult = "";
                    String toResult = "";
                    try {
                        JSONObject object = new JSONObject(s);
                        String reason = object.getString("reason");
                        String error_code = object.getString("error_code");
                        if (error_code.equals("0")) {
                            JSONArray array = object.getJSONArray("tran_result");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object2 = array.getJSONObject(i);
                                fromResult = object2.getString("src");
                                toResult = object2.getString("dst");
                            }

                            Bundle bundle = new Bundle();
                            bundle.putString("reason",reason);
                            bundle.putString("from",fromResult);
                            bundle.putString("to",toResult);
                            Message message = new Message();
                            message.what = 0;
                            message.setData(bundle);
                            mHandler.sendMessage(message);
                        }else {
                            Bundle bundle = new Bundle();
                            bundle.putString("reason",reason);
                            Message message = new Message();
                            message.what = 1;
                            message.setData(bundle);
                            mHandler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        LemonBubble.showError(TranslateLine.this,"请求错误",1000);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LemonBubble.showError(TranslateLine.this,volleyError.getMessage(),1000);
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<>();
                    map.put("key","82dd7be9d646498f8a2c98806ab08dae");
                    map.put("query","utf-8");
                    map.put("from", finalAbbFrom);
                    map.put("to", finalAbbTo);
                    map.put("dtype","JSON");
                    return map;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> map = new HashMap<>();
                    map.put("key","82dd7be9d646498f8a2c98806ab08dae");
                    map.put("query","utf-8");
                    map.put("from", finalAbbFrom);
                    map.put("to", finalAbbTo);
                    map.put("dtype","JSON");
                    return map;
                }
            };
            mQueue.add(request);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                //成功
                Bundle bundle = msg.getData();
                txt_translate_from.setText(bundle.getString("from"));
                txt_translate_to.setText(bundle.getString("to"));
                LemonBubble.showRight(TranslateLine.this,bundle.getString("reason"),1000);
            }else if (msg.what == 1){
                //失败
                Bundle bundle = msg.getData();
                LemonBubble.showError(TranslateLine.this,bundle.getString("reason"),1000);
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        if (fromList != null){
            fromList = null;
        }
        if (mList != null){
            mList = null;
        }
    }
}
