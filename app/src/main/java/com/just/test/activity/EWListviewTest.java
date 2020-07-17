package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.listview.EWListView;
import com.just.test.listview.EWListViewChildET;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 定位单词的列表
 * https://github.com/jcodeing/ExtractWordView
 * Created by admin on 2017/5/10.
 */

public class EWListviewTest extends Activity {

    private EWListView listview_ew;
    private List<String> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ew_listview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "定位单词的列表");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initData();

        listview_ew = (EWListView)findViewById(R.id.listview_ew);
        listview_ew.activity=this;//// TODO: 2017/5/10 这是一个坑，一定要加这句，不然会空指针，因为context没有传过去
        EWListviewAdapter adapter = new EWListviewAdapter(EWListviewTest.this,mList,R.layout.item_ew_listview);
        listview_ew.setAdapter(adapter);
    }

    private void initData(){
        mList.add("Hold fast to dreams \\n紧紧抓住梦想，");
        mList.add("For if dreams die \\n梦想若是消亡");
        mList.add("Life is a broken-winged bird \\n生命就象鸟儿折了翅膀");
        mList.add("That can never fly. \\n再也不能飞翔");
        mList.add("Hold fast to dreams \\n紧紧抓住梦想，");
        mList.add("For when dreams go \\n梦想若是消丧");
        mList.add("Life is a barren field \\n生命就象贫瘠的荒野，");
        mList.add("Frozen only with snow v \\n雪覆冰封，万物不再生");
        mList.add("The world in the morning to get everything \\n世界在清晨获取万物");
        mList.add("And you lose the world in the early morning \\n而你在清晨失去世界");
        mList.add("Don\\'t let the early morning and then go \n" +
                "不要让清晨随之而去");
    }
    private class EWListviewAdapter extends CommonAdapter<String>{

        public EWListviewAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            EWListViewChildET ewet = helper.getView(R.id.item_ewlistview_childet);
            ewet.setText(item);
        }
    }
}
