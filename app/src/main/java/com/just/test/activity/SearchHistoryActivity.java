package com.just.test.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.bean.SearchHistory;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索历史
 * Created by Administrator on 2017/2/24.
 */

public class SearchHistoryActivity extends Activity implements View.OnClickListener{

    private EditText et_searchHistory;
    private Button btn_searchs,btn_cheackAll,btn_deleteTable,btn_deleteSqlite;
    private ListView listview_searchHistory;
    private DbManager db;
    private List<SearchHistory> mList = new ArrayList<>();
    private int positions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_history);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "搜索历史");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);
        initDB();
        initView();
    }

    private void initView(){
        et_searchHistory = (EditText)findViewById(R.id.et_searchHistory);
        btn_searchs = (Button)findViewById(R.id.btn_searchs);
        btn_cheackAll = (Button)findViewById(R.id.btn_cheackAll);
        btn_deleteTable = (Button)findViewById(R.id.btn_deleteTable);
        btn_deleteSqlite = (Button)findViewById(R.id.btn_deleteSqlite);
        listview_searchHistory = (ListView)findViewById(R.id.listview_searchHistory);

        btn_searchs.setOnClickListener(this);
        btn_cheackAll.setOnClickListener(this);
        btn_deleteTable.setOnClickListener(this);
        btn_deleteSqlite.setOnClickListener(this);
    }

    private void initDB(){
        MyApp myApp = new MyApp();
        db = x.getDb(myApp.getDaoConfig());
    }
    @Override
    public void onClick(View view) {
        if (view == btn_searchs){
            String inputHistory = et_searchHistory.getText().toString();
            if (inputHistory.equals("") || inputHistory == null){
                Toast.makeText(SearchHistoryActivity.this,"请输入关键字",Toast.LENGTH_SHORT).show();
            }else {
                try {
                    if (db.getTable(SearchHistory.class) == null) {
                        initDB();
                    }
                        SearchHistory SH = new SearchHistory(inputHistory);
                        db.save(SH);
                        Toast.makeText(SearchHistoryActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        handler.sendEmptyMessage(0);
                        et_searchHistory.setText("");

                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }else if (view == btn_cheackAll){
            handler.sendEmptyMessage(0);
        }else if (view == btn_deleteTable){
            try {
                db.dropTable(SearchHistory.class);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }else if (view == btn_deleteSqlite){
            try {
                db.dropDb();
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    class SearchHistoryAdapter extends CommonAdapter<SearchHistory>{

        public SearchHistoryAdapter(Context context, List<SearchHistory> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, SearchHistory item) {
            TextView txt_item_search = helper.getView(R.id.txt_item_search);
            txt_item_search.setText(item.getHistory());
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                try {
                    if (db.getTable(SearchHistory.class) == null) {
                        initDB();
                    }
                    mList = db.findAll(SearchHistory.class);
                    if (mList == null || mList.size() == 0){
                        Toast.makeText(SearchHistoryActivity.this,"暂无数据",Toast.LENGTH_SHORT).show();
                    }else {
                        SearchHistoryAdapter adapter = new SearchHistoryAdapter(getApplicationContext(), mList, R.layout.item_search_history);
                        listview_searchHistory.setAdapter(adapter);
                        listview_searchHistory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                                positions = position;
                                AlertDialog.Builder builder = new AlertDialog.Builder(SearchHistoryActivity.this)
                                        .setMessage("删除？")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                try {
                                                    WhereBuilder b = WhereBuilder.b();
                                                    b.and("id","=",mList.get(positions).getId());
                                                    db.delete(SearchHistory.class,b);
                                                    handler.sendEmptyMessage(0);
                                                    dialogInterface.dismiss();
                                                } catch (DbException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                builder.create().show();
                                return false;
                            }
                        });

                        listview_searchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                                LayoutInflater inflater = LayoutInflater.from(SearchHistoryActivity.this);
                                View views = inflater.inflate(R.layout.item_history_update,null);
                                final TextView txt_history_old = (TextView) views.findViewById(R.id.txt_history_old);
                                final EditText et_history_new = (EditText) views.findViewById(R.id.et_history_new);
                                txt_history_old.setText(mList.get(position).getHistory());
                                AlertDialog.Builder builder = new AlertDialog.Builder(SearchHistoryActivity.this)
                                        .setTitle("修改")
                                        .setView(views)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                try {
//                                                    SearchHistory history = db.findFirst(SearchHistory.class);
                                                    WhereBuilder b = WhereBuilder.b();
                                                    b.and("id","=",mList.get(position).getId());
                                                    KeyValue value = new KeyValue("history",et_history_new.getText().toString());
                                                    db.update(SearchHistory.class,b,value);
//                                                    history.setHistory(et_history_new.getText().toString());
//
//                                                    db.update(history,"history");
                                                    handler.sendEmptyMessage(0);
                                                    dialogInterface.dismiss();
                                                } catch (DbException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                builder.create().show();
                            }
                        });
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
