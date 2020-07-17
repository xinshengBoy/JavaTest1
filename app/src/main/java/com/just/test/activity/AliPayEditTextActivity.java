package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.tools.EditTextTools;
import com.just.test.widget.AliPayPassWordView;
import com.just.test.widget.MyActionBar;

/**
 * 支付宝密码
 * Created by Administrator on 2017/2/12.
 */

public class AliPayEditTextActivity extends Activity{

    private AliPayPassWordView et_aliPay;
    private Button btn_aliPay_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alipay_edittext);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "支付宝密码");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        et_aliPay = (AliPayPassWordView)findViewById(R.id.et_aliPay);
        btn_aliPay_reset = (Button)findViewById(R.id.btn_aliPay_reset);
        btn_aliPay_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_aliPay.reSetPassWord();
            }
        });
        //自动弹出软键盘
        EditTextTools.showSoftInput(et_aliPay);
    }
}
