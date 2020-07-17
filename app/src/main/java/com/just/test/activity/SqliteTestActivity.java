package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.bean.MyNoteBookDefault;
import com.just.test.sqlite.NoteBookDBHelper;
import com.just.test.tools.MD5;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 笔记详情
 * Created by user on 2016/12/23.
 */

public class SqliteTestActivity extends Activity implements View.OnClickListener{

    private EditText title,contents;
    private Button submit;
    private String sqlite_title,sqlite_content;
    private int id;
    private static int FORRESULT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sqlite);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "添加笔记");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        //// TODO: 2017/7/17 获取列表页面传过来的id，如果是新建的话id为0，列表的话id不为0
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);
        if (id != 0) {
            sqlite_title = intent.getStringExtra("title");
            sqlite_content = intent.getStringExtra("content");
        }
        initView();
    }

    private void initView(){
        title = (EditText)findViewById(R.id.et_title_sqlite);
        contents = (EditText)findViewById(R.id.et_content_sqlite);
        submit = (Button) findViewById(R.id.btn_submit_sqlite);

        if (sqlite_title != null && !sqlite_title.equals("")){
            title.setText(sqlite_title);
        }
        if (sqlite_content != null && !sqlite_content.equals("")){
            contents.setText(sqlite_content);
        }

        submit.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view == submit){
            boolean isSuccess = SaveData();
            if (isSuccess){
                Intent intent = new Intent(SqliteTestActivity.this,SqliteTestList.class);
                intent.putExtra("state",true);
                setResult(FORRESULT,intent);
                finish();
            }else {
                LemonBubble.showError(SqliteTestActivity.this,"保存失败",1000);
            }
        }
    }

    /**
     * 保存数据
     * @return  返回状态（成功或失败）
     */
    private boolean SaveData(){
        String titles = title.getText().toString().trim();
        String mainContent = contents.getText().toString().trim();

        if (titles.equals("")){
            LemonBubble.showError(SqliteTestActivity.this,"标题不能为空",1000);
            return false;
        }
        if (mainContent.equals("")){
            LemonBubble.showError(SqliteTestActivity.this,"内容不能为空",1000);
            return false;
        }

        NoteBookDBHelper helper = new NoteBookDBHelper(SqliteTestActivity.this);
        MyNoteBookDefault bd = new MyNoteBookDefault();
        bd.setTitle(titles);
        bd.setContent(mainContent);
        bd.setTime(MD5.getCurrentDate());

        //// TODO: 2017/7/17 如果是新增的话直接插入，修改的话先删除掉原来的这条记录
        if (id != 0){
            helper.delete(id);
        }
        helper.insert(bd);
        return true;
    }
}
