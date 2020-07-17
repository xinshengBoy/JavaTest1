package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.sortlistview.CharacterParser;
import com.just.test.sortlistview.ClearEditText;
import com.just.test.sortlistview.PinyinComparator;
import com.just.test.sortlistview.SideBar;
import com.just.test.sortlistview.SortAdapter;
import com.just.test.sortlistview.SortModel;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 列表字母排序
 * https://github.com/leerduo/SortListView/blob/master/res/layout/activity_main.xml
 * Created by admin on 2017/5/13.
 */

public class SortListTest extends Activity {

    private ClearEditText et_clearEditText;
    private ListView listview_sortList;
    private TextView txt_sortListView;
    private SideBar sideBar;
    private CharacterParser parser;
    private List<SortModel> sortList = new ArrayList<>();//初始数据
    private List<SortModel> filterList = new ArrayList<>();//过滤后的数据
    private String [] array = new String[]{"阿里云","阿爸","布吉","不会","长江","厂房","大国","打架","E","English","负责","辅助"
            ,"国家","过河","黄河","慌张","INT","IT","结果","吉祥","渴望","课后","落后","罗湖","妈妈","马上","宁静","难过","呕心沥血","呕吐"
            ,"平安","匹配","去年","取经","然后","仍然","四海为家","死神","太热了","唐朝","UFO","UI","VIVO","vichy","王朝","往事"
            ,"幸福","训练","因为爱","引用","郑和","争取"};
    private PinyinComparator pinyinComparator;
    private SortAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sort_listview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "列表字母排序");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);


        //实例化汉字拼音转换类
        parser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        //根据a-z进行排序源数据
        Collections.sort(sortList, pinyinComparator);
        initData();

        et_clearEditText = (ClearEditText)findViewById(R.id.et_clearEditText);
        listview_sortList = (ListView)findViewById(R.id.listview_sortList);
        txt_sortListView = (TextView)findViewById(R.id.txt_sortListView);
        sideBar = (SideBar)findViewById(R.id.sidebar_sortListView);
        sideBar.setTextView(txt_sortListView);


        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                txt_sortListView.setVisibility(View.VISIBLE);
                txt_sortListView.setText(s);
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1){
                    listview_sortList.setSelection(position);
                }
            }
        });


        adapter = new SortAdapter(SortListTest.this,sortList);
        listview_sortList.setAdapter(adapter);
        listview_sortList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SortListTest.this,sortList.get(i).getName(),Toast.LENGTH_SHORT).show();
            }
        });

        et_clearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData(){
        for (int i=0;i<array.length;i++){
            SortModel model = new SortModel();
            model.setName(array[i]);
            //汉子转换成拼音
            String pinyin = parser.getSelling(array[i]);
            String sortString = pinyin.substring(0,1).toUpperCase();//提取第一个字母变大写
            //正则表达式判断首字母是否为英文字母
            if (sortString.matches("[A-Z]")){
                model.setSortLetters(sortString);
            }else {
                model.setSortLetters("#");
            }
            sortList.add(model);
        }
    }

    /**
     * 根据输入框中的值来过滤数据并更新列表
     * @param str  输入的值
     */
    private void filterData(String str){
        if (TextUtils.isEmpty(str)){
            filterList = sortList;
        }else {
            filterList.clear();
            for (SortModel model : sortList){
                String name = model.getName();
                if (name.contains(str)){
                    filterList.add(model);
                }
            }
        }
        //根据a-z排序
        Collections.sort(filterList,pinyinComparator);
        adapter.updateListView(filterList);
    }
}
