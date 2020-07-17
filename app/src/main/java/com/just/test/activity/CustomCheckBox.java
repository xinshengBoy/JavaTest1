package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.custom.AnimateCheckBox;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义checkbox选择框
 * 参考网址：https://github.com/hanks-zyh/AnimateCheckBox
 * Created by admin on 2017/6/13.
 */

public class CustomCheckBox extends Activity {

    private List<Map<String,String>> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_custom_checkbox);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "自定义选择框");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        LemonBubble.showRoundProgress(CustomCheckBox.this,"加载中");
        initData();
    }

    private void initData(){
        for (int i=1;i<30;i++){
            Map<String,String> map = new HashMap<>();
            map.put("name","平凡的世界第"+i+"集");
            boolean isCheck = i % 2 == 0;
            map.put("check",String.valueOf(isCheck));

            mList.add(map);
        }
        mHandler.sendEmptyMessage(0);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                ListView listview_custom_checkbox = (ListView) findViewById(R.id.listview_custom_checkbox);
                CustomCheckBoxAdapter adapter = new CustomCheckBoxAdapter(CustomCheckBox.this,mList,R.layout.item_custom_checkbox);
                listview_custom_checkbox.setAdapter(adapter);
                LemonBubble.hide();
            }
        }
    };
    private class CustomCheckBoxAdapter extends CommonAdapter<Map<String,String>>{

        private CustomCheckBoxAdapter(Context context, List<Map<String, String>> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Map<String, String> item) {
            TextView name = helper.getView(R.id.txt_item_customCheckbox);
            AnimateCheckBox customCheckBox = helper.getView(R.id.cb_item_checkBox);

            name.setText(item.get("name"));
            String check = item.get("check");
            customCheckBox.setChecked(Boolean.parseBoolean(check));
            customCheckBox.setOnCheckedChangeListener(new AnimateCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View buttonView, boolean isChecked) {
                    if (isChecked) {
                        LemonBubble.showRight(CustomCheckBox.this,"谢谢你选择了我",2000);
                    }else {
                        LemonBubble.showError(CustomCheckBox.this,"你就这样狠心抛弃我么",2000);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        if (mList != null){
            mList = null;
        }
    }
}
