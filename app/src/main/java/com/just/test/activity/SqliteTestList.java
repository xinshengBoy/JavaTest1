package com.just.test.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.bean.MyNoteBookDefault;
import com.just.test.sqlite.NoteBookDBHelper;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLITE数据库测试列表
 * Created by user on 2016/12/23.
 */

public class SqliteTestList extends Activity implements View.OnClickListener{

    private ImageView iv_sqlite_add;
    private ListView listview;
    private LinearLayout layout_sqlite_nocontent;
    private List<MyNoteBookDefault> mList = new ArrayList<>();
    private static int FORRESULT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sqlite_list);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "SQLITE列表");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);


        iv_sqlite_add = (ImageView) findViewById(R.id.iv_sqlite_add);
        listview = (ListView)findViewById(R.id.list_sqlite);
        layout_sqlite_nocontent = (LinearLayout) findViewById(R.id.layout_sqlite_nocontent);

        iv_sqlite_add.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onClick(View view) {
        if (view == iv_sqlite_add){
            //跳转新增页面
            Intent intent = new Intent(SqliteTestList.this,SqliteTestActivity.class);
            intent.putExtra("id",0);
            startActivityForResult(intent,FORRESULT);
        }
    }
    //查询数据
    private void initData(){
        NoteBookDBHelper helper = new NoteBookDBHelper(SqliteTestList.this);
        mList = helper.getAllInfo();
        handler.sendEmptyMessage(1);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                initData();
            }else if (msg.what == 1){
                if (mList == null || mList.size() == 0){
                    layout_sqlite_nocontent.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.GONE);
                }else {
                    SqliteListAdapter adapter = new SqliteListAdapter(SqliteTestList.this, mList, R.layout.layout_sqlite_item);
                    listview.setAdapter(adapter);

                    layout_sqlite_nocontent.setVisibility(View.GONE);
                    listview.setVisibility(View.VISIBLE);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //跳转修改页面
                            Intent intent = new Intent(SqliteTestList.this,SqliteTestActivity.class);
                            intent.putExtra("id",mList.get(i).getId());
                            intent.putExtra("title",mList.get(i).getTitle());
                            intent.putExtra("content",mList.get(i).getContent());
                            startActivityForResult(intent,FORRESULT);
                        }
                    });
                }
            }
        }
    };

    /**
     * 获取返回标志，重新查询数据库，刷新数据
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data  返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FORRESULT){
            boolean state = data.getBooleanExtra("state",false);
            if (state) {
                LemonBubble.showRight(SqliteTestList.this, "保存成功", 1000);
            }
            if (mList.size() > 0){
                mList.clear();
            }
            handler.sendEmptyMessage(0);
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
    }

    public class SqliteListAdapter extends CommonAdapter<MyNoteBookDefault>{

        private SqliteListAdapter(Context context, List<MyNoteBookDefault> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, final MyNoteBookDefault item) {
            LinearLayout layout_sqlite_item = helper.getView(R.id.layout_sqlite_item);
            TextView title = helper.getView(R.id.txt_sqlite_title);
            TextView time = helper.getView(R.id.txt_sqlite_time);

            title.setText(item.getTitle());
            time.setText(item.getTime());

            layout_sqlite_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SqliteTestList.this,SqliteTestActivity.class);
                    intent.putExtra("id",item.getId());
                    intent.putExtra("title",item.getTitle());
                    intent.putExtra("content",item.getContent());
                    startActivity(intent);
                }
            });

            layout_sqlite_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(SqliteTestList.this);
                    dialog.setIcon(R.drawable.aar_ic_clear);
                    dialog.setTitle("删除");
                    dialog.setMessage("确定删除");
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NoteBookDBHelper helpers = new NoteBookDBHelper(SqliteTestList.this);
                            helpers.delete(item.getId());
                            dialogInterface.dismiss();
                            Toast.makeText(SqliteTestList.this,"删除成功",Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessage(0);
                        }
                    });
                    dialog.create().show();
                    return false;
                }
            });
        }
    }
}
