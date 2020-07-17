package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;
import java.util.List;

/**
 * 侧滑删除
 * https://github.com/baoyongzhang/SwipeMenuListView
 * compile 'com.baoyz.swipemenulistview:library:1.3.0'
 * Created by admin on 2017/5/10.
 */

public class SwipeMenuListTest extends Activity {

    private SwipeMenuListView listview_swipemenu;
    private List<String> mList = new ArrayList<>();
    private SwipeMenuListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_swipemenulist);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "侧滑删除");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initData();

        listview_swipemenu = (SwipeMenuListView)findViewById(R.id.listview_swipemenu);
        adapter = new SwipeMenuListAdapter(getApplicationContext(),mList,R.layout.item_swipemenulist);
        listview_swipemenu.setAdapter(adapter);
        //创建侧滑后显示的内容
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(SwipeMenuListTest.this);
                openItem.setBackground(R.color.green);// set item background
                openItem.setWidth(dp2px(90));// set item width
                openItem.setTitle("打开");// set item title
                openItem.setTitleSize(18);// set item title fontsize
                openItem.setTitleColor(Color.WHITE);// set item title font color
                menu.addMenuItem(openItem);// add to menu

                //delete
                SwipeMenuItem deleteItem = new SwipeMenuItem(SwipeMenuListTest.this);
                deleteItem.setBackground(R.color.red);
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitle("删除");// set item title
                deleteItem.setTitleSize(18);// set item title fontsize
                deleteItem.setTitleColor(Color.WHITE);// set item title font color
//                deleteItem.setIcon(R.drawable.del_icon_normal);
                menu.addMenuItem(deleteItem);
            }
        };
        //listview添加item
        listview_swipemenu.setMenuCreator(creator);
        //设置侧滑的方向  DIRECTION_RIGHT,DIRECTION_LEFT
//        listview_swipemenu.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

        //设置每个侧滑item的点击事件
        listview_swipemenu.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0://打开
                        LemonBubble.showRight(SwipeMenuListTest.this,"正在打开"+mList.get(position),2000);
                        break;
                    case 1://删除
                        mList.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        listview_swipemenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                LemonBubble.showRight(SwipeMenuListTest.this,"正在打开"+mList.get(position),2000);
            }
        });

    }

    /**
     * 加入初始化数据
     */
    private void initData(){
        for (int i=1;i<56;i++){
            mList.add("人民的名义第"+i+"集");
        }
    }

    /**
     * 长度单位转换
     * @param dp  dp
     * @return  int
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private class  SwipeMenuListAdapter extends CommonAdapter<String>{

        public SwipeMenuListAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            TextView txt_item_swipemenulist = helper.getView(R.id.txt_item_swipemenulist);
            txt_item_swipemenulist.setText(item);
        }

        public void update(List<String> datas){
            mDatas = datas;
            this.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();//强制隐藏提示框
        if (mList != null){
            mList = null;
        }
    }
}
