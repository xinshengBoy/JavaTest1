package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 系统搜索框
 * Created by Administrator on 2017/3/1.
 */

public class SearchTest extends Activity implements SearchView.OnQueryTextListener{

    private SearchView searchview_test;
    private ListView listview_search;
    private String [] results = new String[]{"abc","acb","aaa","bbb","bca","bac","ccc","cba","cab"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_test);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "系统搜索框");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        searchview_test = (SearchView)findViewById(R.id.searchview_test);
        listview_search = (ListView)findViewById(R.id.listview_search);
        ArrayAdapter adapter = new ArrayAdapter(SearchTest.this,android.R.layout.simple_list_item_1,results);
        listview_search.setAdapter(adapter);

        //设置searchview是否自动缩小图标
        searchview_test.setIconifiedByDefault(false);
        //设置是否显示搜索按钮
        searchview_test.setSubmitButtonEnabled(true);
        //默认显示的提示文本，与edittext的hint相似
        searchview_test.setQueryHint("查找字母");
        //设置监听事件
        searchview_test.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Toast.makeText(SearchTest.this,"您选择的是："+s,Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (TextUtils.isEmpty(s)){
            listview_search.clearTextFilter();//清除过滤
        }else {
            listview_search.setFilterText(s);
        }
        return true;
    }
}
