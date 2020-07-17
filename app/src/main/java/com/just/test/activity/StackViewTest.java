package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.StackView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 折叠图片
 * Created by Administrator on 2017/3/1.
 */

public class StackViewTest extends Activity implements View.OnClickListener{

    private Button btn_stackview_previous,btn_stackview_next;
    private StackView stack_image;
    private int [] images = new int[]{R.drawable.bg01,R.drawable.bg02,R.drawable.bg03,R.drawable.bg04,R.drawable.beautifalgirl,R.drawable.friend,R.drawable.friendship4};
    private List<Map<String,Object>> mList;
    private ProgressBar pb_horizontal;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stackview_test);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "折叠图片");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_stackview_previous = (Button)findViewById(R.id.btn_stackview_previous);
        btn_stackview_next = (Button)findViewById(R.id.btn_stackview_next);
        stack_image = (StackView)findViewById(R.id.stack_image);
        pb_horizontal = (ProgressBar)findViewById(R.id.pb_horizontal);
        mList = new ArrayList<>();
        for (int i=0;i<images.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("image",images[i]);
            mList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(StackViewTest.this,mList,R.layout.item_stackview,new String[]{"image"},new int[]{R.id.iv_stackview});
        stack_image.setAdapter(adapter);

        btn_stackview_next.setOnClickListener(this);
        btn_stackview_previous.setOnClickListener(this);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        },0,1000);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_stackview_previous){
            stack_image.showPrevious();
        }else if (view == btn_stackview_next){
            stack_image.showNext();
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                progress +=10;
                pb_horizontal.setMax(100);
                pb_horizontal.setProgress(progress);
            }
        }
    };
}
