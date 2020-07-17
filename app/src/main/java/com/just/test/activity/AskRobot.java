package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
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
import com.just.test.bean.AskRobotBean;
import com.just.test.tools.ACache;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 问答机器人
 * http://avatardata.cn/Docs/Api/4068ce58-df2d-4836-a4af-86abc1974d27
 * Created by admin on 2017/6/19.
 * 参考缓存文章：http://blog.csdn.net/a1681681238/article/details/50393468    缓存问题
 * http://blog.csdn.net/freesonhp/article/details/26603033  背景与软键盘冲突问题，焦点和数据的问题
 */

public class AskRobot extends Activity implements View.OnLayoutChangeListener {

    private RequestQueue mQueue;
    private EditText et_askrobot;
    private ArrayList<AskRobotBean> mList = new ArrayList<>();
    private AskRobotAdapter adapter;
    private ListView listview_askrobot;
    private int keyHeight;
    private ACache acache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_askrobot);
        //设置背景图片，这个背景图片不要在xml文件里面设置，不然会被软键盘顶起来变形
        getWindow().setBackgroundDrawableResource(R.drawable.bg_chat);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "问答机器人");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 4;
        mQueue = Volley.newRequestQueue(getApplicationContext());
        acache = ACache.get(AskRobot.this);
        initView();
    }

    private void initView() {
        listview_askrobot = (ListView) findViewById(R.id.listview_askrobot);
        et_askrobot = (EditText) findViewById(R.id.et_askrobot);
        Button btn_askRobot_query = (Button) findViewById(R.id.btn_askRobot_query);

        btn_askRobot_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_askrobot.getText().toString();
                if (input.equals("")) {
                    LemonBubble.showError(AskRobot.this, "问题不能为空哟", 1000);
                } else {
                    queryAskResult(input);
                    et_askrobot.setText("");
                }
            }
        });
        //监听edittext的点击事件
        et_askrobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_askrobot.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mList != null) {
                            listview_askrobot.setSelection(mList.size() - 1);
                        }
                    }
                },100);
            }
        });
        //监听edittext的焦点事件,有焦点时键盘弹起让数据到最新一条
        et_askrobot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    et_askrobot.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mList != null) {
                                listview_askrobot.setSelection(mList.size() - 1);
                            }
                        }
                    },100);
                }
            }
        });

        mList = (ArrayList<AskRobotBean>) acache.getAsObject("ask");//获取聊天记录
//        mList = DataInfoCache.loadListCache(AskRobot.this,"AskRobot");
        if (mList == null){
            mList = new ArrayList<>();
        }else {
            mHandler.sendEmptyMessage(0);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        listview_askrobot.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom) > keyHeight) {
            if (mList != null) {
                listview_askrobot.setSelection(mList.size());
            }
        }
    }

    private void queryAskResult(final String input) {
        String url = "http://api.avatardata.cn/Tuling/Ask?key=3bb1366d7a324b57b42bb74d6fc10b0c&info=" + input;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0) {
                        JSONObject object1 = object.getJSONObject("result");
                        String text = object1.getString("text");

                        AskRobotBean bean = new AskRobotBean();
                        bean.setAskDetail(input);
                        bean.setAnswerDetail(text);
                        mList.add(bean);

                        mHandler.sendEmptyMessage(0);
                    } else {
                        LemonBubble.showError(AskRobot.this, reason, 1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(AskRobot.this, "请求失败", 1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(AskRobot.this, volleyError.getMessage(), 1000);
            }
        });
        mQueue.add(request);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (mList.size() > 1) {
                    if (adapter == null){
                        adapter = new AskRobotAdapter(AskRobot.this, mList, R.layout.item_askrobot);
                        listview_askrobot.setAdapter(adapter);
                    }
                    adapter.notifyData(mList);
                    listview_askrobot.setSelection(mList.size());//解决列表显示块超过键盘以上的部分不会把最新的消息顶上去的问题
                } else {
                    adapter = new AskRobotAdapter(AskRobot.this, mList, R.layout.item_askrobot);
                    listview_askrobot.setAdapter(adapter);
                }
            }
        }
    };


    private class AskRobotAdapter extends CommonAdapter<AskRobotBean> {

        private AskRobotAdapter(Context context, List<AskRobotBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        void notifyData(List<AskRobotBean> data) {
            mDatas = data;
            notifyDataSetChanged();
        }

        @Override
        public void convert(ViewHolder helper, AskRobotBean item) {
            TextView ask = helper.getView(R.id.txt_item_askrobot_ask);
            TextView answer = helper.getView(R.id.txt_item_askrobot_answer);

            ask.setText(item.getAskDetail());
            answer.setText(item.getAnswerDetail());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        mQueue.stop();
        acache.put("ask",mList,60*60*24*30);//保存聊天记录,缓存一个月
//        DataInfoCache.saveListCache(AskRobot.this,mList,"AskRobot");//保存聊天记录
        if (mList != null) {
            mList = null;
        }
    }
}
