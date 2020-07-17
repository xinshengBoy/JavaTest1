package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.xw.repo.XEditText;

/**
 * EditText输入电话号码、银行卡号自动添加空格分割
 * http://www.itlanbao.com/code/20151125/10000/100660.html
 * com.xw.repo:xedittext:2.0.2@aar
 * https://github.com/woxingxiao/XEditText
 * Created by admin on 2017/6/8.
 */

public class XEditTextText extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xedittext);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "自动添加空格分割");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        XEditText phone = (XEditText)findViewById(R.id.et_xedittext_phone);
        XEditText bank = (XEditText)findViewById(R.id.et_xedittext_bank);

        phone.setPattern(new int[]{3,4,4});
        bank.setPattern(new int[]{4,4,4,4});
    }
}
