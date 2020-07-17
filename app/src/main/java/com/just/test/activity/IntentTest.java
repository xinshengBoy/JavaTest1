package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.StudentInformation;
import com.just.test.widget.MyActionBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Intent传值
 * Created by Administrator on 2017/3/22.
 */

public class IntentTest extends Activity {

    private TextView txt_intentTest_result;
    private List<StudentInformation> mList = new ArrayList<>();
    private int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intent_test);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "Intent传值测试");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initData();

        txt_intentTest_result = (TextView)findViewById(R.id.txt_intentTest_result);
        Button btn_intentTest_goto = (Button)findViewById(R.id.btn_intentTest_goto);
        btn_intentTest_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntentTest.this,IntentTest2.class);
                intent.putExtra("data",(Serializable) mList);
                startActivityForResult(intent,requestCode);
            }
        });
    }

    private void initData(){
        for (int i=0;i<30;i++){
            StudentInformation information = new StudentInformation();
            information.setName("张"+i);
            information.setCheck(false);
            mList.add(information);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mList = (List<StudentInformation>) data.getSerializableExtra("data");
        String result = "";
        if (requestCode == 1){
            for (int i=0;i<mList.size();i++){
                if (mList.get(i).isCheck()){
                    result += mList.get(i).getName() + "...";
                }
            }
            txt_intentTest_result.setText(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
