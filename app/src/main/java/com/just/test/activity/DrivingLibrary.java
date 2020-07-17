package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.just.test.R;
import com.just.test.bean.DrivingAnwser;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 驾考宝典
 * 参考网址：http://avatardata.cn/Docs/Api/1ccd64af-d0d9-4ddd-8bac-660344cf2f21
 * Created by admin on 2017/5/31.
 */

public class DrivingLibrary extends Activity implements View.OnClickListener {

    private Spinner spinner_driving_subject, spinner_driving_card;//考试科目和驾照类型
    private Button btn_driving_random, btn_driving_order, btn_driving_next;//随机测试和顺序测试
    private String[] subjects = new String[]{"科目一", "科目四"};//考试科目类型，一共两种，即科目一和科目四，着两个阶段是理论考试
    private String[] cards = new String[]{"a1", "a2", "b1", "b2", "c1", "c2"};//驾照类型的种类
    private RequestQueue mQueue;
    private ListView listview_driving;
    private List<DrivingAnwser> mList = new ArrayList<>();//总的数据
    private List<DrivingAnwser> showList = new ArrayList<>();//当前显示的数据
    private int errorCount = 0;//回答错误的题目数
    private int rightCount = 0;//回答正确的题目数
    private int showCount = 1;//总共已经显示的题目数,从第一题开始
    private TextView txt_result_count;//用于提示用户当前的答题状况

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_driving_library);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 驾考宝典");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
    }

    private void initView() {
        spinner_driving_subject = (Spinner) findViewById(R.id.spinner_driving_subject);//科目类型：科目一和科目四
        spinner_driving_card = (Spinner) findViewById(R.id.spinner_driving_card);//驾照类型：c1,c2,a1,a2,b1,b2
        btn_driving_random = (Button) findViewById(R.id.btn_driving_random);//随机模式
        btn_driving_order = (Button) findViewById(R.id.btn_driving_order);//顺序模式
        txt_result_count = (TextView) findViewById(R.id.txt_result_count);
        listview_driving = (ListView) findViewById(R.id.listview_driving);
        btn_driving_next = (Button) findViewById(R.id.btn_driving_next);

        //科目类型
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, R.layout.item_spinselect, subjects);
        subjectAdapter.setDropDownViewResource(R.layout.item_dialogspinselect);
        spinner_driving_subject.setAdapter(subjectAdapter);
        //驾照类型
        ArrayAdapter<String> cardAdapter = new ArrayAdapter<>(this, R.layout.item_spinselect, cards);
        cardAdapter.setDropDownViewResource(R.layout.item_dialogspinselect);
        spinner_driving_card.setAdapter(cardAdapter);

        btn_driving_random.setOnClickListener(this);
        btn_driving_order.setOnClickListener(this);
        btn_driving_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_driving_random) {//随机测试
            if (mList.size() > 0){//每次切换的时候要清空原来的数据
                mList.clear();
            }
            if (showList.size() > 0){//清空当前要显示的数据
                showList.clear();
            }
            showCount = 0;//将已经显示的总数归零
            rightCount = 0;//将答对的数量归零
            errorCount = 0;//将打错的数量归零
            queryData("rand");
        } else if (view == btn_driving_order) {//顺序测试
            if (mList.size() > 0){//每次切换的时候要清空原来的数据
                mList.clear();
            }
            if (showList.size() > 0){//清空当前要显示的数据
                showList.clear();
            }
            showCount = 0;//将已经显示的总数归零
            rightCount = 0;//将答对的数量归零
            errorCount = 0;//将打错的数量归零
            queryData("order");
        } else if (view == btn_driving_next) {//下一题
            if (showCount == mList.size()){
                showUpdateDialog(DrivingLibrary.this,"答题结果","恭喜您测试完毕，正确"+rightCount+"题，错误"+errorCount+"题，再接再励哟");
            }else {
                showCount += 1;//将要显示数目+1
                mHandler.sendEmptyMessage(0);//赋值和显示
            }
        }
    }

    private void queryData(final String type) {
        LemonBubble.showRoundProgress(DrivingLibrary.this, "加载中");
        String subject = spinner_driving_subject.getSelectedItem().toString();
        final String card = spinner_driving_card.getSelectedItem().toString();

        if (subject.equals("") || card.equals("")) {
            return;
        }
        String result_subject = "";
        if (subject.equals(subjects[0])) {
            result_subject = "1";
        } else if (subject.equals(subjects[1])) {
            result_subject = "4";
        }
        String url = "http://api.avatardata.cn/Jztk/Query?key=856161f09d0e4b4b97d05e0bdf323e07&subject=" + result_subject + "&model=" + card;
        final String finalResult_subject = result_subject;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray array = object.getJSONArray("result");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        DrivingAnwser detail = new DrivingAnwser();
                        detail.setId(object1.getString("id"));
                        detail.setQuestion(object1.getString("question"));
                        detail.setAnswer(object1.getString("answer"));
                        detail.setItem1(object1.getString("item1"));
                        detail.setItem2(object1.getString("item2"));
                        detail.setItem3(object1.getString("item3"));
                        detail.setItem4(object1.getString("item4"));
                        detail.setExplains(object1.getString("explains"));
                        detail.setUrl(object1.getString("url"));

                        mList.add(detail);
                    }
                    mHandler.sendEmptyMessage(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(DrivingLibrary.this, "请求错误", 1000);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(DrivingLibrary.this, volleyError.getMessage(), 1000);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key", "856161f09d0e4b4b97d05e0bdf323e07");
                map.put("subject", finalResult_subject);
                map.put("model", card);
                map.put("testType", type);
                return map;
            }
        };
        mQueue.add(request);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                showList.clear();
                showList.add(mList.get(showCount));
                DrivingAnwserAdapter adapter = new DrivingAnwserAdapter(DrivingLibrary.this, showList, R.layout.item_driving_anwser);
                listview_driving.setAdapter(adapter);
                LemonBubble.hide();
            } else if (msg.what == 1) {
                txt_result_count.setText("正确：" + rightCount + " / 错误：" + errorCount);
            }
        }
    };

    /**
     * 更新对话框
     * @param context 上下文
     * @param updateContent 更新的内容
     */
    private void showUpdateDialog(final Context context,String title,String updateContent) {
        LemonHelloInfo info = new LemonHelloInfo()
                .setTitle(title)
                .setTitleColor(Color.RED)
                .setContent(updateContent)
                .setContentFontSize(15)
                .addAction(new LemonHelloAction("确定", Color.BLUE, new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        lemonHelloView.hide();
                    }
                }));
        info.show(context);
    }

    private class DrivingAnwserAdapter extends CommonAdapter<DrivingAnwser> {

        private final Context context;
        private TextView txt_driving_explains;

        public DrivingAnwserAdapter(Context context, List<DrivingAnwser> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
            this.context = context;
        }

        @Override
        public void convert(ViewHolder helper, DrivingAnwser item) {
            TextView txt_driving_question = helper.getView(R.id.txt_driving_question);
            txt_driving_explains = helper.getView(R.id.txt_driving_explains);
            ImageView iv_driving = helper.getView(R.id.iv_driving);
            TextView txt_item_driving1 = helper.getView(R.id.txt_item_driving1);
            TextView txt_item_driving2 = helper.getView(R.id.txt_item_driving2);
            TextView txt_item_driving3 = helper.getView(R.id.txt_item_driving3);
            TextView txt_item_driving4 = helper.getView(R.id.txt_item_driving4);

            txt_driving_question.setText("（" + showCount + 1 + "）" + item.getQuestion());
            txt_driving_explains.setText(item.getExplains());
            txt_item_driving1.setText("（1）" + item.getItem1());
            txt_item_driving2.setText("（2）" + item.getItem2());
            txt_item_driving3.setText("（3）" + item.getItem3());
            txt_item_driving4.setText("（4）" + item.getItem4());

            if (!item.getUrl().equals("")) {
                Glide.with(context).load(item.getUrl()).into(iv_driving);
            }

            txt_driving_question.setClickable(false);
            txt_driving_explains.setClickable(false);
            iv_driving.setClickable(false);

            txt_driving_explains.setVisibility(View.GONE);

            final int answer = Integer.parseInt(item.getAnswer());
            txt_item_driving1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (1 == answer) {
                        LemonBubble.showRight(context, "回答正确", 1000);
                        rightCount += 1;
                    } else {
                        LemonBubble.showError(context, "回答错误", 1000);
                        errorCount += 1;
                    }
                    txt_driving_explains.setVisibility(View.VISIBLE);
                    mHandler.sendEmptyMessage(1);
                }
            });
            txt_item_driving2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (2 == answer) {
                        LemonBubble.showRight(context, "回答正确", 1000);
                        rightCount += 1;
                    } else {
                        LemonBubble.showError(context, "回答错误", 1000);
                        errorCount += 1;
                    }
                    txt_driving_explains.setVisibility(View.VISIBLE);
                    mHandler.sendEmptyMessage(1);
                }
            });
            txt_item_driving3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (3 == answer) {
                        LemonBubble.showRight(context, "回答正确", 1000);
                        rightCount += 1;
                    } else {
                        LemonBubble.showError(context, "回答错误", 1000);
                        errorCount += 1;
                    }
                    txt_driving_explains.setVisibility(View.VISIBLE);
                    mHandler.sendEmptyMessage(1);
                }
            });
            txt_item_driving4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (4 == answer) {
                        LemonBubble.showRight(context, "回答正确", 1000);
                        rightCount += 1;
                    } else {
                        LemonBubble.showError(context, "回答错误", 1000);
                        errorCount += 1;
                    }
                    txt_driving_explains.setVisibility(View.VISIBLE);
                    mHandler.sendEmptyMessage(1);
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        mQueue.stop();
        if (mList != null) {
            mList.clear();
            mList = null;
        }
        if (showList != null) {
            showList = null;
        }
        errorCount = 0;
        rightCount = 0;
        showCount = 0;
    }
}
