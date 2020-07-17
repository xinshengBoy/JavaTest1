package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * listview联动
 */
public class ListViewLianDong extends Activity {
    private ListView rightlist;
    private final String[][] cities = new String[][]{
            new String[]{"全部美食", "本帮江浙菜", "川菜", "粤菜", "湘菜", "东北菜", "台湾菜", "新疆/清真", "素菜", "火锅", "自助餐", "小吃快餐", "日本", "韩国料理",
                    "东南亚菜", "西餐", "面包甜点", "其他"},
            new String[]{"全部休闲娱乐", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
                    "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐"},
            new String[]{"全部购物", "综合商场", "服饰鞋包", "运动户外", "珠宝饰品", "化妆品", "数码家电", "亲子购物", "家居建材"
                    , "书店", "书店", "眼镜店", "特色集市", "更多购物场所", "食品茶酒", "超市/便利店", "药店"},
            new String[]{"全部休闲娱乐", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
                    "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐"},
            new String[]{"全", "咖啡厅", "酒吧", "茶馆", "KTV", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
                    "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐"},
            new String[]{"全部", "咖啡厅", "酒吧", "茶馆", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
                    "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐"},
            new String[]{"全部休", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
                    "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐"},
            new String[]{"全部休闲", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
                    "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐"},
            new String[]{"全部休闲娱", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
                    "DIY手工坊", "桌球馆", "桌面游戏"},
            new String[]{"全部休闲娱乐", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
                    "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐"},
            new String[]{"全部休闲aaa", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
                    "DIY手工坊", "桌球馆", "桌面游戏"},
    };
    private final String[] foods = new String[]{"全部频道", "美食", "休闲娱乐", "购物", "酒店", "丽人", "运动健身", "结婚", "亲子", "爱车", "生活服务"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_liandong_listview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "listview联动");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        ListView leftlist = (ListView) findViewById(R.id.leftlist);
        rightlist = (ListView) findViewById(R.id.rightlist);

        LeftListViewAdapter leftAdapter = new LeftListViewAdapter(ListViewLianDong.this, foods);
        leftlist.setAdapter(leftAdapter);
        leftlist.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RightListViewAdapter rightAdapter = new RightListViewAdapter(ListViewLianDong.this, cities, position);
                rightlist.setAdapter(rightAdapter);
            }
        });

    }

    /**
     * 左侧adapter
     */
    private class LeftListViewAdapter extends BaseAdapter {

        private final Context mContext;
        private final String[] mLeftData;

        private LeftListViewAdapter(Context context, String[] leftData) {
            this.mContext = context;
            this.mLeftData = leftData;
        }

        @Override
        public int getCount() {
            return mLeftData.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_liandong_item, parent);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.txt_liandong_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(mLeftData[position]);
            holder.textView.setTextSize(18);
            return convertView;
        }

        private class ViewHolder {
            public TextView textView;
        }

    }

    /**
     * 右侧adapter
     */
    private class RightListViewAdapter extends BaseAdapter {

        private final Context mContext;
        private final String[][] mLeftData;
        private final int foodpoition;

        private RightListViewAdapter(Context context, String[][] leftData, int positions) {
            this.mContext = context;
            this.mLeftData = leftData;
            this.foodpoition = positions;
        }

        @Override
        public int getCount() {
            return mLeftData.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_liandong_item, parent);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.txt_liandong_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(mLeftData[foodpoition][position]);
            holder.textView.setTextSize(16);
            return convertView;
        }

        private class ViewHolder {
            public TextView textView;
        }
    }
}
