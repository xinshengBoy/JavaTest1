package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.SpinnerBean;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * spinner联动
 * Created by admin on 2017/4/19.
 */

public class SpinnerLianDong extends Activity {

    private Spinner spinner1, spinner2;
    private List<String> shengList = new ArrayList<>();
    private List<SpinnerBean> shiList = new ArrayList<>();
    private List<String> shi = new ArrayList<>();
    private ArrayAdapter<String> adapter2;
    private String sheng, city;
    private TextView txt_liandong2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_liandong2);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "spinner联动");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initData();

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        txt_liandong2 = (TextView)findViewById(R.id.txt_liandong2);

        //创建适配器
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.item_spinselect, shengList);
        //设置样式
        adapter1.setDropDownViewResource(R.layout.item_dialogspinselect);
        spinner1.setAdapter(adapter1);
        sheng = (String) spinner1.getSelectedItem();

        for (int i = 0; i < shiList.size(); i++) {
            if (sheng.equals(shiList.get(i).getSheng())) {
                shi.add(shiList.get(i).getShi());
            }
        }
        //创建适配器

        adapter2 = new ArrayAdapter<>(this, R.layout.item_spinselect, shi);
        //设置样式
        adapter2.setDropDownViewResource(R.layout.item_dialogspinselect);
        spinner2.setAdapter(adapter2);
        city = (String) spinner2.getSelectedItem();

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                sheng = shengList.get(position);
                shi.clear();
                for (int i = 0; i < shiList.size(); i++) {
                    if (sheng.equals(shiList.get(i).getSheng())) {
                        shi.add(shiList.get(i).getShi());
                    }
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = shi.get(position);
                txt_liandong2.setText("您的选择是:"+sheng+"省"+city+",对嘛？");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initData() {
        String[] list = new String[]{"湖南", "江西", "湖北", "广东"};
        String[][] list3 = new String[][]{{"长沙市", "株洲市", "湘潭市", "郴州市", "岳阳市", "衡阳市"}, {"南昌市", "赣州市", "九江市", "吉安市", "鹰潭市", "景德镇市"},
                {"武汉市", "宜昌市", "赤壁市", "南阳市", "黄冈市", "汉口市"}, {"广州市", "深圳市", "珠海市", "韶关市", "汕头市", "潮州市"}};
        for (int i = 0; i < list.length; i++) {
            shengList.add(list[i]);
            for (int j = 0; j < list3[i].length; j++) {
                SpinnerBean bean = new SpinnerBean();
                bean.setSheng(list[i]);
                bean.setShi(list3[i][j]);
                shiList.add(bean);
            }
        }
    }
}
