package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.YourMood;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.MD5;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * MNXUtilsDB 是把 xUtils3 数据库模块单独抽取出来，方便使用！当前使用的是xUtils3的V3.5.0版本中的数据库模块。
 * https://github.com/maning0303/MNXUtilsDB
 * compile 'com.github.maning0303:MNXUtilsDB:V1.0.0'
 * Created by admin on 2017/8/31.
 */

public class MNXUtilsDBTest extends Activity implements View.OnClickListener{

    private EditText et_xutilsdb;
    private Button btn_xutilsDB;
    private ListView listview_xutilsDB;
    private List<YourMood> mList = new ArrayList<>();
    private DbManager dm;
    private TextView txt_xutils_nocontent,btn_xutils_update;
    private ProgressBar pb_xutils;
    private PromptDialog dialog;
    private YourMoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xutils_db);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "xUtils3数据库");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        dm = MyApp.getDbManager();
        initView();
    }

    private void initView() {
        et_xutilsdb = (EditText)findViewById(R.id.et_xutilsdb);
        btn_xutilsDB = (Button)findViewById(R.id.btn_xutilsDB);
        btn_xutils_update = (TextView)findViewById(R.id.btn_xutils_update);
        listview_xutilsDB = (ListView)findViewById(R.id.listview_xutilsDB);
        txt_xutils_nocontent = (TextView)findViewById(R.id.txt_xutils_nocontent);
        txt_xutils_nocontent.setVisibility(View.VISIBLE);
        pb_xutils = (ProgressBar)findViewById(R.id.pb_xutils);

        btn_xutilsDB.setOnClickListener(this);
        btn_xutils_update.setOnClickListener(this);

        handler.sendEmptyMessage(6);//自动加载数据
    }

    @Override
    public void onClick(View view) {
        if (view == btn_xutilsDB) {
            String input = et_xutilsdb.getText().toString();
            if (!input.equals("")) {
                String time = MD5.getCurrentDate();
                Bundle bundle = new Bundle();
                bundle.putString("mood", input);
                bundle.putString("time", time);

                Message message = new Message();
                message.what = 0;
                message.setData(bundle);
                handler.sendMessage(message);
            } else {
                LemonBubble.showError(MNXUtilsDBTest.this, "内容不能为空", 1000);
            }
        }else if (view == btn_xutils_update){
            handler.sendEmptyMessage(6);//自动加载数据
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                Bundle bundle = msg.getData();
                saveMood(bundle.getString("mood"),bundle.getString("time"),0);
            }else if (msg.what == 1){
                showToast("保存成功",true);
                et_xutilsdb.setText("");//清空内容
                searchMood();
            }else if (msg.what == 2){
                showToast("保存失败，请重试",false);
            }else if (msg.what == 3){
                //显示列表
                adapter = new YourMoodAdapter(MNXUtilsDBTest.this,mList,R.layout.item_json);
                listview_xutilsDB.setAdapter(adapter);
                listview_xutilsDB.setVisibility(View.VISIBLE);
                txt_xutils_nocontent.setVisibility(View.GONE);
                pb_xutils.setVisibility(View.GONE);

                listview_xutilsDB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        et_xutilsdb.setText(mList.get(i).getMood());
                    }
                });
                listview_xutilsDB.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        final YourMood yourMood = mList.get(i);
                        dialog = new PromptDialog(MNXUtilsDBTest.this);
                        dialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(3000);
                        final PromptButton button = new PromptButton("确定", new PromptButtonListener() {
                            @Override
                            public void onClick(PromptButton promptButton) {
                                try {
                                    dm.delete(yourMood);
                                    mList.remove(i);
                                    handler.sendEmptyMessage(7);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                            }
                        });
                        button.setTextColor(Color.GREEN);
                        button.setFocusBacColor(Color.RED);

                        dialog.showWarnAlert("你确定要删除这条数据么？",new PromptButton("取消", new PromptButtonListener() {
                            @Override
                            public void onClick(PromptButton promptButton) {
                                dialog.dismiss();
                            }
                        }),button);
                        return false;
                    }
                });
            }else if (msg.what == 4){
                showToast("暂无数据，快去新增一条吧",false);
                txt_xutils_nocontent.setVisibility(View.VISIBLE);
                listview_xutilsDB.setVisibility(View.GONE);
                pb_xutils.setVisibility(View.GONE);
            }else if (msg.what == 5){
                showToast("查询失败，请重试",false);
                pb_xutils.setVisibility(View.GONE);
            }else if (msg.what == 6){
                //一进来自动查询数据
                pb_xutils.setVisibility(View.VISIBLE);
                searchMood();
            }else if (msg.what == 7){
                showToast("删除成功",true);
                if (mList.size() == 0){
                    txt_xutils_nocontent.setVisibility(View.VISIBLE);
                    listview_xutilsDB.setVisibility(View.GONE);
                }else {
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };

    /**
     * 保存输入的心情
     * @param mood 心情
     * @param time 时间
     * @param type 类型：0，新增；1，更新
     */
    private void saveMood(String mood,String time,int type){
        if (type == 0) {
            YourMood yourMood = new YourMood();
            yourMood.setMood(mood);
            yourMood.setTime(time);

            try {
                dm.save(yourMood);
                handler.sendEmptyMessage(1);
            } catch (DbException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(2);
            }
        }else if (type == 1){

        }
    }

    /**
     * 查询所有
     */
    private void searchMood(){
        if (mList.size() != 0){
            mList.clear();
        }
        try {
            mList = dm.selector(YourMood.class).findAll();
            if (mList != null && mList.size() > 0){
                handler.sendEmptyMessage(3);//显示列表
            }else {
                handler.sendEmptyMessage(4);//暂无数据
            }
        } catch (DbException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(5);//查询失败
        }
    }

    /**
     * 显示提示信息
     * @param msg 提示的内容
     * @param isRight 正确信息还是错误信息
     */
    private void showToast(String msg,boolean isRight){
        if (isRight) {
            LemonBubble.showRight(MNXUtilsDBTest.this, msg, 1000);
        }else {
            LemonBubble.showError(MNXUtilsDBTest.this, msg, 1000);
        }
    }
    private class YourMoodAdapter extends CommonAdapter<YourMood>{

        private YourMoodAdapter(Context context, List<YourMood> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, YourMood item) {
            TextView mood = helper.getView(R.id.txt_jsonContent);
            TextView time = helper.getView(R.id.txt_jsonTime);

            mood.setText(item.getMood());
            time.setText(item.getTime());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        if (mList != null){
            mList.clear();
            mList = null;
        }

        try {
            dm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
