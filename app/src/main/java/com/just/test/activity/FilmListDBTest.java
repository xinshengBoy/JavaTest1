package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.FilmDetail;
import com.just.test.sqlite.FilmListDBHelper;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.ImageBtn;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 电影列表的数据库
 * Created by admin on 2017/5/23.
 */

public class FilmListDBTest extends Activity implements View.OnClickListener{


    private ListView listview_filmList;
    private List<FilmDetail> mList = new ArrayList<>();
    private List<String> searchList = new ArrayList<>();
    private ProgressBar pb_filmlist;
    private TextView txt_filmlist_notice;
    private ImageBtn btn_filmlist_create,txt_filmlist_search,txt_filmlist_deleteAll;
    private FilmListDBHelper db;
    private AutoCompleteTextView auto_filmlist_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filmlist);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "电影列表");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        txt_filmlist_deleteAll = (ImageBtn)findViewById(R.id.txt_filmlist_deleteAll);
        txt_filmlist_search = (ImageBtn)findViewById(R.id.txt_filmlist_search);
        btn_filmlist_create = (ImageBtn)findViewById(R.id.btn_filmlist_create);

        txt_filmlist_deleteAll.setImageResource(R.drawable.icon_delete);
        txt_filmlist_deleteAll.setTextView("删除");
        txt_filmlist_search.setImageResource(R.drawable.icon_search);
        txt_filmlist_search.setTextView("搜索");
        btn_filmlist_create.setImageResource(R.drawable.icon_create);
        btn_filmlist_create.setTextView("新建");
        txt_filmlist_deleteAll.setOnClickListener(this);
        txt_filmlist_search.setOnClickListener(this);
        btn_filmlist_create.setOnClickListener(this);

        auto_filmlist_search = (AutoCompleteTextView) findViewById(R.id.auto_filmlist_search);
        listview_filmList = (ListView)findViewById(R.id.listview_filmList);
        pb_filmlist = (ProgressBar)findViewById(R.id.pb_filmlist);
        txt_filmlist_notice = (TextView)findViewById(R.id.txt_filmlist_notice);

        db = new FilmListDBHelper(FilmListDBTest.this);
        handler.sendEmptyMessage(0);

        auto_filmlist_search.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchList));//设置自动提示框的内容
        //对输入的监听
        auto_filmlist_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.equals("")){
                    auto_filmlist_search.setText("");
                    listview_filmList.setVisibility(View.VISIBLE);//输入没值时显示
                }else {
                    listview_filmList.setVisibility(View.GONE);//输入时listview隐藏
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (auto_filmlist_search.getText().toString().equals("")){
                    listview_filmList.setVisibility(View.VISIBLE);//输入没值时显示
                }
            }
        });
        auto_filmlist_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String result = auto_filmlist_search.getText().toString();
                for (int j=0;j<mList.size();j++){
                    if (result.equals(mList.get(j).getFilmname())){

                        Intent intent = new Intent(FilmListDBTest.this,FilmDetailTest.class);
                        intent.putExtra("type","1");
                        Bundle bundle = new Bundle();
                        FilmDetail detail = mList.get(j);
                        bundle.putSerializable("datas",detail);
                        intent.putExtras(bundle);
                        startActivityForResult(intent,0);

                        auto_filmlist_search.setVisibility(View.GONE);
                        listview_filmList.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                pb_filmlist.setVisibility(View.VISIBLE);
                txt_filmlist_notice.setVisibility(View.GONE);
                listview_filmList.setVisibility(View.GONE);
                quaryFilmData();
            }else if (msg.what == 1){
                pb_filmlist.setVisibility(View.GONE);
                if (mList.size() == 0){
                    txt_filmlist_notice.setVisibility(View.VISIBLE);
                    listview_filmList.setVisibility(View.GONE);
                }else {
                    FilmListAdapter adapter = new FilmListAdapter(FilmListDBTest.this,mList,R.layout.item_filmlist);
                    listview_filmList.setAdapter(adapter);
                    listview_filmList.setVisibility(View.VISIBLE);
                    txt_filmlist_notice.setVisibility(View.GONE);
                    listview_filmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(FilmListDBTest.this,FilmDetailTest.class);
                            intent.putExtra("type","1");
                            Bundle bundle = new Bundle();
                            FilmDetail detail = mList.get(i);
                            bundle.putSerializable("datas",detail);
                            intent.putExtras(bundle);
                            startActivityForResult(intent,0);
                        }
                    });
                    listview_filmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            showUpdateDialog(FilmListDBTest.this,"提示","确定要删除这条数据么",mList.get(i).getId());
                            return true;
                        }
                    });
                }
            }else if (msg.what == 2){
                LemonBubble.showRight(FilmListDBTest.this,"删除成功",2000);
                mList.clear();
                quaryFilmData();
            }
        }
    };
    private void quaryFilmData(){
        mList = db.getFilmAllInfo();
        for (int i=0;i<mList.size();i++){
            searchList.add(mList.get(i).getFilmname());
        }
        handler.sendEmptyMessage(1);
    }

    /**
     * 更新对话框
     * @param context 上下文
     * @param updateContent 更新的内容
     */
    private void showUpdateDialog(final Context context, String title, String updateContent, final String id) {
        LemonHelloInfo info = new LemonHelloInfo()
                .setTitle(title)
                .setTitleColor(Color.RED)
                .setContent(updateContent)
                .setContentFontSize(15)
                .addAction(new LemonHelloAction("确定", Color.BLUE, new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        lemonHelloView.hide();
                        if (id == null){
                            db.deleteAll();
                        }else {
                            db.delete(id);
                        }
                        handler.sendEmptyMessage(2);
                    }
                }))
                .addAction(new LemonHelloAction("取消", Color.GRAY, new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        lemonHelloView.hide();
                    }
                }));
        info.show(context);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_filmlist_create){
            Intent intent = new Intent(FilmListDBTest.this,FilmDetailTest.class);
            intent.putExtra("type","0");//0代表新建，1代表修改
            startActivityForResult(intent,0);
        }else if (view == txt_filmlist_search){
            auto_filmlist_search.setVisibility(View.VISIBLE);
        }else if (view == txt_filmlist_deleteAll){
            showUpdateDialog(FilmListDBTest.this,"提示","确定删除所有",null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0){
            mList.clear();
            quaryFilmData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class FilmListAdapter extends CommonAdapter<FilmDetail>{

        public FilmListAdapter(Context context, List<FilmDetail> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, FilmDetail item) {
            TextView name = helper.getView(R.id.item_filmlist_name);
            TextView date = helper.getView(R.id.item_filmlist_date);

            name.setText(item.getFilmname());
            date.setText(item.getFilmdate());
        }
    }
}
