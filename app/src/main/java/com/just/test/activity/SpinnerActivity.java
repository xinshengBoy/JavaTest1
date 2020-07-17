package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * spinner下拉框
 * Created by user on 2016/12/8.
 */

public class SpinnerActivity extends Activity {
    private TextView txt_spinner_result;
    private Spinner spinner_test1,spinner_test2;
    private List<String> mList1 = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_spinner_test);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "spinner下拉框");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initDate1();
        initDate2();
        init();
    }
    private void init(){
        spinner_test1 = (Spinner)findViewById(R.id.spinner_test1);
        spinner_test2 = (Spinner)findViewById(R.id.spinner_test2);
        Button btn_spinner = (Button) findViewById(R.id.btn_spinner);
        txt_spinner_result = (TextView) findViewById(R.id.txt_spinner_result);
        //创建适配器
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,mList1);
        //设置样式
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_test1.setAdapter(adapter1);

        //创建适配器
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,mList2);
        //设置样式
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner_test2.setAdapter(adapter2);
        btn_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_spinner_result.setText("结果：武器-->"+spinner_test1.getSelectedItem()+",兵种-->"+spinner_test2.getSelectedItem());
            }
        });
        //系统提供的样式如下
        //simple_spinner_dropdown_item(列表-间距较高比较好看)
        //simple_spinner_item(列表-间距紧凑不好看)
        //simple_list_item_checked（复选框-选中的有绿沟）
        //simple_list_item_single_choice (单选按钮)
    }
    private void initDate1(){
        mList1.add("飞机");
        mList1.add("大炮");
        mList1.add("潜艇");
        mList1.add("导弹");
        mList1.add("无人机");
        mList1.add("手枪");
    }
    private void initDate2(){
        mList2.add("陆军");
        mList2.add("海军");
        mList2.add("空军");
        mList2.add("火箭军");
        mList2.add("特种兵");
        mList2.add("后勤");
    }
}
