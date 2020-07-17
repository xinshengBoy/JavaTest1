package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.BiuEditText;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * EditText文字输入飞入效果
 * http://www.itlanbao.com/code/20151203/10000/100673.html
 * Created by admin on 2017/6/8.
 */

public class FlyingEditText extends Activity {

    private BiuEditText et_flying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flying_edittext);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "EditText文字输入飞入效果");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        et_flying = (BiuEditText)findViewById(R.id.et_flying);
        Button btn_flying_finish = (Button) findViewById(R.id.btn_flying_finish);
        btn_flying_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_flying.getText().toString();
                LemonBubble.showRight(FlyingEditText.this,input,3000);
                et_flying.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
    }
}
