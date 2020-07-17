package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
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
import com.just.test.bean.JokeBean;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 笑话大全和趣图
 * 参考网址：http://avatardata.cn/Docs/Api?id=4b49361c-e858-4a69-845e-0c8c3b24cb5f&detailId=1dd8dfc8-912f-496f-a40a-a01edb1fa75f
 * Created by admin on 2017/6/1.
 */

public class JokeAndPic extends Activity implements View.OnClickListener{

    private RequestQueue mQueue;
    private Button joke,newjoke,pic,newPic;
    private ListView listview_jokeAndPic;
    private List<JokeBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_joke_interest_pic);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 笑话大全和趣图");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
    }

    private void initView() {
        joke = (Button)findViewById(R.id.btn_jokeAndPic_joke);
        newjoke = (Button)findViewById(R.id.btn_jokeAndPic_newjoke);
        pic = (Button)findViewById(R.id.btn_jokeAndPic_pic);
        newPic = (Button)findViewById(R.id.btn_jokeAndPic_newPic);
        listview_jokeAndPic = (ListView)findViewById(R.id.listview_jokeAndPic);

        joke.setOnClickListener(this);
        newjoke.setOnClickListener(this);
        pic.setOnClickListener(this);
        newPic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == joke){
            if (mList.size() > 0){
                mList.clear();
            }
            queryJokeData(1);
        }else if (view == newjoke){
            if (mList.size() > 0){
                mList.clear();
            }
            queryJokeData(2);
        }else if (view == pic){
            if (mList.size() > 0){
                mList.clear();
            }
            queryJokeData(3);
        }else if (view == newPic){
            if (mList.size() > 0){
                mList.clear();
            }
            queryJokeData(4);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                JokeAndPicAdapter adapter = new JokeAndPicAdapter(JokeAndPic.this,mList,R.layout.item_joke_pic);
                listview_jokeAndPic.setAdapter(adapter);
                LemonBubble.hide();
            }
        }
    };

    private void queryJokeData(final int type){
        LemonBubble.showRoundProgress(JokeAndPic.this, "加载中");
        String url = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        Date date = new Date();
        String time = format.format(date);
        if (type == 1){
            url = "http://api.avatardata.cn/Joke/QueryJokeByTime?key=0e10332d126f47e0b1729c80044b2e6d&page=1&rows=50&sort=desc&time="+time;
        }else if (type == 2){
            url = "http://api.avatardata.cn/Joke/NewstJoke?key=0e10332d126f47e0b1729c80044b2e6d&page=1&rows=50";
        }else if (type == 3){
            url = "http://api.avatardata.cn/Joke/QueryImgByTime?key=0e10332d126f47e0b1729c80044b2e6d&page=1&rows=50&sort=desc&time="+time;
        }else if (type == 4){
            url = "http://api.avatardata.cn/Joke/NewstImg?key=0e10332d126f47e0b1729c80044b2e6d&page=1&rows=50";
        }

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0) {
                        JSONArray array = object.getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object1 = array.getJSONObject(i);
                            JokeBean detail = new JokeBean();
                            detail.setContent(object1.getString("content"));
                            detail.setUpdatetime(object1.getString("updatetime"));

                            if (type == 3 || type ==4) {
                                detail.setUrl(object1.getString("url"));
                            }
                            mList.add(detail);
                        }
                        mHandler.sendEmptyMessage(0);
                    }else {
                        LemonBubble.showError(JokeAndPic.this, reason, 1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(JokeAndPic.this, "请求错误", 1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(JokeAndPic.this, volleyError.getMessage(), 1000);
            }
        });
        mQueue.add(request);
    }

    private class JokeAndPicAdapter extends CommonAdapter<JokeBean>{

        private final Context context;

        private JokeAndPicAdapter(Context context, List<JokeBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
            this.context = context;
        }

        @Override
        public void convert(ViewHolder helper, JokeBean item) {
            TextView title = helper.getView(R.id.txt_item_jokepic_content);
            ImageView pic = helper.getView(R.id.iv_item_jokepic);
            TextView updatetime = helper.getView(R.id.txt_item_jokepic_updatetime);

            title.setText(item.getContent());
            updatetime.setText(item.getUpdatetime());

            String url = item.getUrl();
            if (url != null && !url.equals("")){
                Glide.with(context).load(url).into(pic);
            }
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
