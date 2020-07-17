package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.custom.HintPopupWindow;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;

/**
 * 一个简单的pop弹框
 * Created by admin on 2017/5/2.
 */

public class SimplePopWindow extends Activity implements View.OnClickListener{

    private Button btn_simpleShowPop;
    private ImageView iv_simpleShowPop;
    private HintPopupWindow pop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_simplepop);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "一个简单的pop弹框");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_simpleShowPop = (Button)findViewById(R.id.btn_simpleShowPop);
        iv_simpleShowPop = (ImageView)findViewById(R.id.iv_simpleShowPop);

        btn_simpleShowPop.setOnClickListener(this);
        iv_simpleShowPop.setOnClickListener(this);

        initData();
    }

    private void initData(){
        ArrayList<String> mList = new ArrayList<>();
        for (int i=1;i<6;i++){
            mList.add("人民的名义第"+i+"集");
        }

        ArrayList<View.OnClickListener> onClick = new ArrayList<>();
        for (int i=0;i<mList.size();i++){
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SimplePopWindow.this,"item点击事件触发",Toast.LENGTH_LONG).show();
                }
            };
            onClick.add(clickListener);
        }

        pop = new HintPopupWindow(SimplePopWindow.this,mList,onClick);
    }
    @Override
    public void onClick(View view) {
        if (view == btn_simpleShowPop){
            pop.showPopupWindow(btn_simpleShowPop);
        }else if (view == iv_simpleShowPop){
            pop.showPopupWindow(iv_simpleShowPop);
        }
    }
}
