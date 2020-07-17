package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;

/**
 * 计算质数
 * Created by admin on 2017/5/17.
 * http://blog.csdn.net/lanxingfeifei/article/details/51769740
 */

public class CalculatePN extends Activity {

    private EditText et_calculate;
    private TextView txt_calcatePN;
    private int count = 0;
    private ArrayList<Integer> mList = new ArrayList<>();
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calculate_pn);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "计算质数");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        et_calculate = (EditText)findViewById(R.id.et_calculate);
        txt_calcatePN = (TextView) findViewById(R.id.txt_calcatePN);
        Button btn_start_calculatePN = (Button) findViewById(R.id.btn_start_calculatePN);
        btn_start_calculatePN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_calculate.getText().toString();
                if (input.equals("")){
                    LemonBubble.showError(CalculatePN.this,"请输入一个整数",3000);
                }else {
                    int data = Integer.parseInt(input);
                    if (mList.size() != 0){
                        mList.clear();
                    }
                    outer:
                    for (int i=2;i<=data;i++){
                        for (int j=2;j<=Math.sqrt(i);j++){
                            if (i != 2 && i%j == 0){
                                continue outer;
                            }
                        }
                        mList.add(i);
                    }
                    handler.sendEmptyMessageDelayed(0,500);
                }
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                result += mList.get(count)+"  ， ";
                txt_calcatePN.setText(result);
                if (count < mList.size()-1){
                    count ++;
                    handler.sendEmptyMessageDelayed(0,500);
                }else {
                    count = 0;
                    result = "";
                }
            }
        }
    };
}
