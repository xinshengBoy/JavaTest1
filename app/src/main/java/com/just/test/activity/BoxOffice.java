package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.BoxOfficeNetBean;
import com.just.test.bean.BoxOfficeNewFilmBean;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 最新票房榜和网络票房榜
 * 参考网址：http://avatardata.cn/Docs/Api/093351ef-8b55-42b2-8406-13752e9cef1c
 * Created by admin on 2017/6/2.
 */

public class BoxOffice extends Activity implements View.OnClickListener{

    private Context mContext = BoxOffice.this;
    private Activity mActivity = BoxOffice.this;
    private Button btn_boxOffice_newFilm,btn_boxOffice_net;
    private ListView listview_boxOffice;
    private List<BoxOfficeNewFilmBean> newFilmList = new ArrayList<>();
    private List<BoxOfficeNetBean> netList = new ArrayList<>();
    private LinearLayout boxoffice_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_box_office);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 最新票房榜");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView() {
        btn_boxOffice_newFilm = (Button)findViewById(R.id.btn_boxOffice_newFilm);
        btn_boxOffice_net = (Button)findViewById(R.id.btn_boxOffice_net);
        listview_boxOffice = (ListView)findViewById(R.id.listview_boxOffice);
        boxoffice_layout = (LinearLayout)findViewById(R.id.boxoffice_layout);

        btn_boxOffice_newFilm.setOnClickListener(this);
        btn_boxOffice_net.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_boxOffice_newFilm){
            queryNewFilm();
        }else if (view == btn_boxOffice_net){
            queryNet();
        }
    }

    private void queryNewFilm(){
        LemonBubble.showRoundProgress(BoxOffice.this, "加载中");
        //中国票房
        String url = " http://api.avatardata.cn/BoxOffice/Newest?key=bc18aa1edbe7499cb22b394a8b8ccf37&area=CN";

        OkHttpUtils.get().url(url).tag(this).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LemonBubble.showError(mContext,"请求失败",1000);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        JSONArray array = object.getJSONArray("result");
                        for (int i=0;i<array.length();i++){
                            JSONObject object1 = array.getJSONObject(i);
                            BoxOfficeNewFilmBean detail = new BoxOfficeNewFilmBean();
                            detail.setRid(object1.getString("rid"));
                            detail.setName(object1.getString("name"));
                            detail.setWk(object1.getString("wk"));
                            detail.setWboxoffice(object1.getString("wboxoffice"));
                            detail.setTboxoffice(object1.getString("tboxoffice"));

                            newFilmList.add(detail);
                        }
                        mHandler.sendEmptyMessage(0);
                    }else {
                        LemonBubble.showError(BoxOffice.this,reason,1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(mContext,"请求失败",1000);
                }
            }
        });
    }

    private void queryNet(){
        LemonBubble.showRoundProgress(BoxOffice.this, "加载中");
        String url = "http://api.avatardata.cn/BoxOffice/Internet?key=bc18aa1edbe7499cb22b394a8b8ccf37";
        OkHttpUtils.get().url(url).tag(this).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LemonBubble.showError(mContext,"请求错误",1000);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    int error_code = object.getInt("error_code");
                    String reason = object.getString("reason");
                    if (error_code == 0){
                        JSONArray array = object.getJSONArray("result");
                        for (int i=0;i<array.length();i++){
                            JSONObject object1 = array.getJSONObject(i);
                            BoxOfficeNetBean detail = new BoxOfficeNetBean();
                            detail.setName(object1.getString("name"));
                            detail.setTotals(object1.getString("totals"));
                            detail.setStatistics(object1.getString("statistics"));
                            detail.setAveraging(object1.getString("averaging"));
                            detail.setAttendance(object1.getString("attendance"));
                            detail.setPeople(object1.getString("people"));
                            detail.setFare(object1.getString("fare"));
                            detail.setBoxoffice(object1.getString("boxoffice"));

                            netList.add(detail);
                        }
                        mHandler.sendEmptyMessage(1);
                    }else {
                        LemonBubble.showError(mContext,reason,1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonBubble.showError(mContext,"请求错误",1000);
                }
            }
        });
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                BoxOfficeNewFilmAdapter newAdapter = new BoxOfficeNewFilmAdapter(BoxOffice.this,newFilmList,R.layout.item_boxoffice_newfilm);
                listview_boxOffice.setAdapter(newAdapter);
                boxoffice_layout.setVisibility(View.GONE);
                LemonBubble.hide();
            }else if (msg.what == 1){
                BoxOfficeNetAdater netAdater = new BoxOfficeNetAdater(BoxOffice.this,netList,R.layout.item_boxoffice_net);
                listview_boxOffice.setAdapter(netAdater);
                boxoffice_layout.setVisibility(View.VISIBLE);
                LemonBubble.hide();
            }
        }
    };

    private class BoxOfficeNewFilmAdapter extends CommonAdapter<BoxOfficeNewFilmBean>{

        BoxOfficeNewFilmAdapter(Context context, List<BoxOfficeNewFilmBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, BoxOfficeNewFilmBean item) {
            TextView name = helper.getView(R.id.txt_item_boxoffice_name);
            TextView wk = helper.getView(R.id.txt_item_boxoffice_wk);
            TextView wboxoffice = helper.getView(R.id.txt_item_boxoffice_wboxoffice);
            TextView tboxoffice = helper.getView(R.id.txt_item_boxoffice_tboxoffice);

            name.setText(item.getRid() + ":" + item.getName());
            wk.setText(item.getWk());
            wboxoffice.setText("周票房（万元）："+item.getWboxoffice());
            tboxoffice.setText("总票房（万元）："+item.getTboxoffice());
        }
    }

    private class BoxOfficeNetAdater extends CommonAdapter<BoxOfficeNetBean>{

        public BoxOfficeNetAdater(Context context, List<BoxOfficeNetBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, BoxOfficeNetBean item) {
            TextView name = helper.getView(R.id.txt_item_boxoffice_netName);
            TextView boxoffice = helper.getView(R.id.txt_item_boxoffice_netBoxoffice);
            TextView total = helper.getView(R.id.txt_item_boxoffice_netTotal);
            TextView statistics = helper.getView(R.id.txt_item_boxoffice_netStatistics);
            TextView averaging = helper.getView(R.id.txt_item_boxoffice_netAveraging);
            TextView attendance = helper.getView(R.id.txt_item_boxoffice_netAttendance);
            TextView people = helper.getView(R.id.txt_item_boxoffice_netPeople);
            TextView fare = helper.getView(R.id.txt_item_boxoffice_netFare);

            name.setText(item.getName());
            boxoffice.setText(item.getBoxoffice());
            total.setText(item.getTotals());
            statistics.setText(item.getStatistics());
            averaging.setText(item.getAveraging());
            attendance.setText(item.getAttendance());
            people.setText(item.getPeople());
            fare.setText(item.getFare());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        OkHttpUtils.getInstance().cancelTag(this);
        if (newFilmList != null){
            newFilmList.clear();
            newFilmList = null;
        }
        if (netList != null){
            netList.clear();
            netList = null;
        }
    }
}
