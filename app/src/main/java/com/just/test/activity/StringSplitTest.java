package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 字符串的四种分割方法
 * http://blog.csdn.net/huangwenyi1010/article/details/72673447
 * Created by admin on 2017/5/29.
 */

public class StringSplitTest extends Activity implements View.OnClickListener {

    private ListView listview_stringsplit;
    private Button btn_split, btn_StringTokenizer, btn_indexOf, btn_substring;
    private String[] array = new String[]{};
    private String datas = "腾讯体育5月29日讯 据《CBS》报道,保罗与马刺之间的流言,目前已经传的沸沸扬扬,然而沃神却出面泼了一盆冷水,在沃神看来,保罗最有可能的选择仍将是留在快船,在参加一档电台节目时,沃神这样说道," +
            "我看到的不是这样,在谈及加盟马刺的可能性时,沃神坦言,事实上我甚至不清楚这种流言是哪儿来的,今年春天我在马刺身上花费了许多时间,深知如果他们想要引进保罗,就得对工资帽做一次彻底的清理,马刺几乎不可能这么去做,换言之,他们几乎没有机会得到保罗," +
            "我的意思是,他们如果想要引进保罗,就得彻彻底底的颠覆自己的现有阵容,然后将赌注压在一位30岁出头,巅峰期不知还能维持多久的控卫身上,因此在我看来,毫无疑问的一点是,快船与保罗续约的可能性,要远大于马刺得到保罗,并给予他特权," +
            "尤其是在经济方面,留在洛杉矶,与转投其他球队,会有很大的差别,因此我很难想象他会离开快船,除了薪金方面的巨大差异外,洛杉矶的市场同样不容忽视,这会影响到他的商业赞助,综合以上这些来看,快船与保罗续约的可能性,压倒了其余任何可能,沃神总结道," +
            "当快船又一次令人失望的首轮出局后,保罗离队的流言被传的沸沸扬扬,而加盟马刺,与莱昂纳德以及阿尔德里奇联手,听起来非常有趣,尤其是前几天,关于保罗与马刺彼此之间互相产生兴趣的说法,不仅引爆了转会市场,也令快船感到担忧," +
            "不过对于马刺来说,球队引进保罗最大的障碍便在于今年夏天大约只有800-900万的薪金空间,这意味着他们想要签下保罗,得同时交易掉加索尔与格林,或者大量角色球员,这显然会令圣城伤筋动骨,亦或是,保罗需要接受大幅度的降薪,";
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stringsplit);
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "字符串的四种分割方法");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView() {
        btn_split = (Button) findViewById(R.id.btn_split);
        btn_StringTokenizer = (Button) findViewById(R.id.btn_StringTokenizer);
        btn_indexOf = (Button) findViewById(R.id.btn_indexOf);
        btn_substring = (Button) findViewById(R.id.btn_substring);
        listview_stringsplit = (ListView) findViewById(R.id.listview_stringsplit);

        handler.sendEmptyMessage(1);

        btn_split.setOnClickListener(this);
        btn_StringTokenizer.setOnClickListener(this);
        btn_indexOf.setOnClickListener(this);
        btn_substring.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_split) {
            handler.sendEmptyMessage(1);
        } else if (view == btn_StringTokenizer) {
            handler.sendEmptyMessage(2);
        } else if (view == btn_indexOf) {
            handler.sendEmptyMessage(3);
        } else if (view == btn_substring) {
            handler.sendEmptyMessage(4);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (mList.size() > 0) {
                    mList.clear();
                }
                array = datas.split(",");
                for (int i = 0; i < array.length; i++) {
                    mList.add(i + "--" + array[i]);
                }
                array = null;
                setData(mList);
            } else if (msg.what == 2) {
                if (mList.size() > 0) {
                    mList.clear();
                }
                StringTokenizer st = new StringTokenizer(datas, ",");
                while(st.hasMoreTokens()) {
                    mList.add(st.countTokens() + "--" + st.nextToken());
                }
                array = null;
                setData(mList);
            } else if (msg.what == 3) {
                if (mList.size() > 0) {
                    mList.clear();
                }
                int index = datas.indexOf(",");
                for (int i = 0; i < index; i++) {
                    String result = datas.substring(0, index);
//                    result = result.substring(index);
                    mList.add(i + "--" + result);

                }
                setData(mList);
            } else if (msg.what == 4) {
                setData(mList);
            }
        }
    };

    private void setData(List<String> list) {
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(StringSplitTest.this,android.R.layout.simple_list_item_1,data);
        StringSplitAdater adapter = new StringSplitAdater(StringSplitTest.this, list, R.layout.item_custombtn);
        listview_stringsplit.setAdapter(adapter);
    }

    private class StringSplitAdater extends CommonAdapter<String> {

        public StringSplitAdater(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            TextView text1 = helper.getView(R.id.textView1);
            text1.setText(item);
        }
    }
}
