package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;
import com.just.test.widget.ZoomImageView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**笑话大全
 * Created by Administrator on 2017/3/16.
 */

public class JokeAllActivity extends Activity implements View.OnClickListener {

    private ListView listview_jokeall;
    private RequestQueue mQueue;
    private List<Bundle> mTextList = new ArrayList<>();
    private List<Bundle> mImgList = new ArrayList<>();
    private String CONTENT = "content";
    private String UPDATETIME = "updatetime";
    private String URL = "url";
    private Button btn_jokeall_more,btn_joke_text,btn_joke_img;
    private int textPage = 1;
    private int imgPage = 1;
    private ZoomImageView iv_jokeall_img;
    private LinearLayout headerLayout,layout_joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jokeall);

        //// TODO: 2016/12/21 actionbar
        headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "笑话大全");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        layout_joke = (LinearLayout) findViewById(R.id.layout_joke);
        btn_joke_text = (Button)findViewById(R.id.btn_joke_text);
        btn_joke_img = (Button)findViewById(R.id.btn_joke_img);
        listview_jokeall = (ListView)findViewById(R.id.listview_jokeall);
        btn_jokeall_more = (Button)findViewById(R.id.btn_jokeall_more);
        iv_jokeall_img = (ZoomImageView)findViewById(R.id.iv_jokeall_img);
        btn_joke_text.setOnClickListener(this);
        btn_joke_img.setOnClickListener(this);
        btn_jokeall_more.setOnClickListener(this);
        iv_jokeall_img.setOnClickListener(this);
        handler.sendEmptyMessage(0);

    }

    private void getJokeTextData(){
        String url = "http://japi.juhe.cn/joke/content/list.from?key=ae7abb2fda4f1b9a72fdec4d416124d3&page="+textPage+"&pagesize=20&sort=asc&time=1418745237";
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray array;
                        try {
                            JSONObject object = response.getJSONObject("result");
                            array = object.getJSONArray("data");
                            for(int i=0;i<array.length();i++){
                                Bundle bundle = new Bundle();
                                JSONObject object1 = array.getJSONObject(i);
                                String content = object1.getString(CONTENT);
                                String time = object1.getString(UPDATETIME);

                                bundle.putString(CONTENT, content);
                                bundle.putString(UPDATETIME, time);

                                mTextList.add(bundle);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        handler.sendEmptyMessage(1);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mQueue.add(request);
    }

    private void getJokeImgData(){
        String url = "http://japi.juhe.cn/joke/img/text.from?key=ae7abb2fda4f1b9a72fdec4d416124d3&page="+imgPage+"&pagesize=10";
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray array;
                        try {
                            JSONObject object = response.getJSONObject("result");
                            array = object.getJSONArray("data");
                            for(int i=0;i<array.length();i++){
                                Bundle bundle = new Bundle();
                                JSONObject object1 = array.getJSONObject(i);
                                String content = object1.getString(CONTENT);
                                String time = object1.getString(UPDATETIME);
                                String url = object1.getString(URL);

                                bundle.putString(CONTENT, content);
                                bundle.putString(UPDATETIME, time);
                                bundle.putString(URL,url);

                                mImgList.add(bundle);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        handler.sendEmptyMessage(3);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mQueue.add(request);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                getJokeTextData();
            }else if (msg.what == 1){
                JokeAllAdapter adapter = new JokeAllAdapter(JokeAllActivity.this,mTextList,R.layout.item_jokeall);
                listview_jokeall.setAdapter(adapter);
            }else if (msg.what == 2){
                getJokeImgData();
            }else if (msg.what == 3){
                JokeAllAdapter adapter = new JokeAllAdapter(JokeAllActivity.this,mImgList,R.layout.item_jokeall);
                listview_jokeall.setAdapter(adapter);
                listview_jokeall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Picasso.with(JokeAllActivity.this).load(mImgList.get(i).getString(URL)).into(iv_jokeall_img);
                        iv_jokeall_img.setVisibility(View.VISIBLE);
                        listview_jokeall.setVisibility(View.GONE);
                        btn_jokeall_more.setVisibility(View.GONE);
                        headerLayout.setVisibility(View.GONE);
                        layout_joke.setVisibility(View.GONE);
                    }
                });
            }
        }
    };

    @Override
    public void onClick(View view) {
        if (view == btn_jokeall_more){
            textPage +=1;
            handler.sendEmptyMessage(0);
        }else if (view == btn_joke_text){
            textPage =1;
            handler.sendEmptyMessage(0);
        }else if (view == btn_joke_img){
            imgPage = 1;
            handler.sendEmptyMessage(2);
        }else if (view == iv_jokeall_img){
            iv_jokeall_img.setVisibility(View.GONE);
            listview_jokeall.setVisibility(View.VISIBLE);
            btn_jokeall_more.setVisibility(View.VISIBLE);
            headerLayout.setVisibility(View.VISIBLE);
            layout_joke.setVisibility(View.VISIBLE);
        }
    }

    private class JokeAllAdapter extends CommonAdapter<Bundle>{

        public JokeAllAdapter(Context context, List<Bundle> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Bundle item) {
            ImageView imageView = helper.getView(R.id.iv_jokeall_url);
            TextView content = helper.getView(R.id.txt_jokeall_content);
            TextView time = helper.getView(R.id.txt_jokeall_time);

            String img = item.getString(URL);
            if (img == null || img.equals("")){
                imageView.setVisibility(View.GONE);
            }else {
                Picasso.with(JokeAllActivity.this).load(img).into(imageView);
                imageView.setVisibility(View.VISIBLE);
            }
            content.setText(item.getString(CONTENT));
            time.setText(item.getString(UPDATETIME));
        }
    }
}
