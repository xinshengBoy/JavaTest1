package com.just.test.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.bean.GoodAnswerBean;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 神回复
 * https://www.tianapi.com/#yule  生活类api
 * Created by admin on 2017/6/5.
 */

public class GoodAnswer extends Activity {

    private ListView listview_goodAnswer;
    private List<GoodAnswerBean> mList = new ArrayList<>();
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_good_answer);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "神回复");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());

        listview_goodAnswer = (ListView)findViewById(R.id.listview_goodAnswer);
        mHandler.sendEmptyMessage(0);
        wakeUpAndUnlock(GoodAnswer.this);
    }

    public void onClicks(View view){
        switch (view.getId()){
            case R.id.btn_goodAnswer_answer:
                queryDada("godreply");
                break;
            case R.id.btn_goodAnswer_raokouling:
                queryDada("rkl");
                break;
        }
    }

    private void queryDada(final String type){
        LemonBubble.showRoundProgress(GoodAnswer.this, "加载中");
        if (mList.size() > 0){
            mList.clear();
        }
        String url = "https://api.tianapi.com/txapi/"+type+"/?key=a3f826587cf746a44a0a15100c094810&num=50";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int code = object.getInt("code");
                    String msg = object.getString("msg");
                    if (code == 200){
                        JSONArray array = object.getJSONArray("newslist");
                        for (int i=0;i<array.length();i++){
                            JSONObject object1 = array.getJSONObject(i);
                            GoodAnswerBean detail = new GoodAnswerBean();

                            if (type.equals("rkl")){
                                detail.setTitle("");
                            }else {
                                detail.setTitle(object1.getString("title"));
                            }
                            detail.setContent(object1.getString("content"));

                            mList.add(detail);
                        }
                        mHandler.sendEmptyMessage(1);
                    }else {
                        LemonBubble.showError(GoodAnswer.this,msg,2000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(GoodAnswer.this,"请求失败",2000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mQueue.add(request);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                queryDada("godreply");
            }else if (msg.what == 1){
                GoodAnswerAdapter adapter = new GoodAnswerAdapter(GoodAnswer.this,mList,R.layout.item_goodanswer);
                listview_goodAnswer.setAdapter(adapter);
                LemonBubble.hide();
            }
        }
    };

    private class GoodAnswerAdapter extends CommonAdapter<GoodAnswerBean>{

        public GoodAnswerAdapter(Context context, List<GoodAnswerBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, GoodAnswerBean item) {
            TextView title = helper.getView(R.id.txt_item_goodanswer_title);
            TextView content = helper.getView(R.id.txt_item_goodanswer_content);

            title.setText(item.getTitle());
            content.setText(item.getContent());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        wakeUpAndUnlock(GoodAnswer.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wakeUpAndUnlock(GoodAnswer.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        mQueue.cancelAll(this);
        if (mList != null){
            mList = null;
        }
    }

    /**
     * 唤醒屏幕
     * @param context 上下文
     */
    public static void wakeUpAndUnlock(Context context){
        KeyguardManager manager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock k1 = manager.newKeyguardLock("unlock");
        //解锁
        k1.disableKeyguard();
        //获取电影管理器对象
        PowerManager power = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock w1 = power.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        //点亮屏幕
        w1.acquire();
        //释放
        w1.release();
    }
}
