package com.just.test.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.Fruits;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * LitePal数据库
 * compile 'org.litepal.android:core:1.3.0'
 * http://www.jianshu.com/p/557682e0a9f0
 * Created by admin on 2017/5/23.
 */

public class LitePalTest extends Activity {

    private ListView listview_litepal;
    private List<Fruits> list = new ArrayList<>();
    private List<Fruits> mList = new ArrayList<>();
    private FruitsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_litepal);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "LitePal数据库");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        checkAllData();
        listview_litepal = (ListView)findViewById(R.id.listview_litepal);
        adapter = new FruitsAdapter(LitePalTest.this,mList,R.layout.item_litepal);
        listview_litepal.setAdapter(adapter);
        listview_litepal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataSupport.delete(Fruits.class,i+1);
                mList.clear();
                checkAllData();
                adapter.setData(mList);
                adapter.notifyDataSetChanged();
            }
        });

        final TextView txt_litepal = (TextView)findViewById(R.id.txt_litepal);
        final LinearLayout edit_litepal = (LinearLayout) findViewById(R.id.edit_litepal);
        final EditText et_litepal = (EditText) findViewById(R.id.et_litepal);
        Button btn_litepal_save = (Button) findViewById(R.id.btn_litepal_save);

        if (mList.size() > 0) {
            txt_litepal.setText(mList.get(1).getFruitName());
        }
        txt_litepal.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                txt_litepal.setVisibility(View.GONE);
                edit_litepal.setVisibility(View.VISIBLE);
                return false;
            }
        });

        btn_litepal_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_litepal.getText().toString();
                ContentValues values = new ContentValues();
                values.put("fruitName",input);
                DataSupport.update(Fruits.class,values,1);
//                Fruits fruits = new Fruits();
//                fruits.setFruitName(input);
//                fruits.save();
                mList.clear();
                checkAllData();
                adapter.setData(mList);
                adapter.notifyDataSetChanged();
                txt_litepal.setText(mList.get(1).getFruitName());
                txt_litepal.setVisibility(View.VISIBLE);
                edit_litepal.setVisibility(View.GONE);
            }
        });
    }

    private void initData(){
        Fruits fruits1 = new Fruits(1,"西瓜","湖南","青色","六七月",0xff339BFF);
        fruits1.save();
        Fruits fruits2 = new Fruits(2,"奈里","湖南","淡青色","五六月",0xff25B14A);
        fruits2.save();
        Fruits fruits3 = new Fruits(3,"荔枝","广西","青色","六七月",0xffED4A4B);
        fruits3.save();
        Fruits fruits4 = new Fruits(4,"香蕉","湖南","青色","六七月",0xffFFE61C);
        fruits4.save();
        Fruits fruits5 = new Fruits(5,"哈密瓜","湖南","青色","六七月",0xff95477F);
        fruits5.save();
        Fruits fruits6 = new Fruits(6,"苹果","湖南","青色","六七月",0xff2B2B2B);
        fruits6.save();
        Fruits fruits7 = new Fruits(7,"葡萄","湖南","青色","六七月",0xff2028D0);
        fruits7.save();
        Fruits fruits8 = new Fruits(8,"柚子","湖南","青色","六七月",0xffDE5825);
        fruits8.save();
        Fruits fruits9 = new Fruits(9,"雪梨","湖南","青色","六七月",0xff0D0D0D);
        fruits9.save();
        Fruits fruits10 = new Fruits(10,"芒果","湖南","青色","六七月",0xffE35AC9);
        fruits10.save();
        list.add(fruits1);
        list.add(fruits2);
        list.add(fruits3);
        list.add(fruits4);
        list.add(fruits5);
        list.add(fruits6);
        list.add(fruits7);
        list.add(fruits8);
        list.add(fruits9);
        list.add(fruits10);
    }

    /**
     * 查询数据库里面所有数据
     */
    private void checkAllData(){
        mList = DataSupport.findAll(Fruits.class);
        if (mList == null || mList.size() == 0){
            initData();
        }
    }
    private class FruitsAdapter extends CommonAdapter<Fruits>{

        public FruitsAdapter(Context context, List<Fruits> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        private void setData(List<Fruits> data){
            mDatas = data;
            notifyDataSetChanged();
        }
        @Override
        public void convert(ViewHolder helper, Fruits item) {
            LinearLayout layout = helper.getView(R.id.item_litepal_layout);
            TextView name = helper.getView(R.id.item_litepal_name);
            TextView address = helper.getView(R.id.item_litepal_address);
            TextView color = helper.getView(R.id.item_litepal_color);
            TextView date = helper.getView(R.id.item_litepal_date);

            layout.setBackgroundColor(item.getFruitBG());
            name.setText(item.getFruitName());
            address.setText(item.getFruitAddress());
            color.setText(item.getFruitColor());
            date.setText(item.getFruitDate());
        }
    }
}
