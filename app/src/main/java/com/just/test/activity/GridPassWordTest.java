package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.tools.EditTextTools;
import com.just.test.widget.MyActionBar;
import com.jungly.gridpasswordview.GridPasswordView;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 仿支付宝输入密码框效果
 * 参考网址：https://github.com/Jungerr/GridPasswordView
 * compile 'com.jungly:gridPasswordView:0.3'
 * Created by admin on 2017/6/13.
 */

public class GridPassWordTest extends Activity {

    private GridPasswordView gridPassword1,gridPassword2,gridPassword3,gridPassword4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grid_password);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "仿支付宝输入密码框");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        gridPassword1 = (GridPasswordView)findViewById(R.id.view_gridPassword1);
        Button btn_grid_password1 = (Button)findViewById(R.id.btn_grid_password1);
        btn_grid_password1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextTools.hideSoftInput(GridPassWordTest.this);
                String input = gridPassword1.getPassWord();
                LemonBubble.showRight(GridPassWordTest.this,input,2000);
                gridPassword1.clearPassword();
            }
        });
        gridPassword2 = (GridPasswordView)findViewById(R.id.view_gridPassword2);
        Button btn_grid_password2 = (Button)findViewById(R.id.btn_grid_password2);
        btn_grid_password2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextTools.hideSoftInput(GridPassWordTest.this);
                String input = gridPassword2.getPassWord();
                LemonBubble.showRight(GridPassWordTest.this,input,2000);
                gridPassword2.clearPassword();
            }
        });
        gridPassword3 = (GridPasswordView)findViewById(R.id.view_gridPassword3);
        Button btn_grid_password3 = (Button)findViewById(R.id.btn_grid_password3);
        btn_grid_password3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextTools.hideSoftInput(GridPassWordTest.this);
                String input = gridPassword3.getPassWord();
                LemonBubble.showRight(GridPassWordTest.this,input,2000);
                gridPassword3.clearPassword();
            }
        });
        gridPassword4 = (GridPasswordView)findViewById(R.id.view_gridPassword4);
        Button btn_grid_password4 = (Button)findViewById(R.id.btn_grid_password4);
        btn_grid_password4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextTools.hideSoftInput(GridPassWordTest.this);
                String input = gridPassword4.getPassWord();
                LemonBubble.showRight(GridPassWordTest.this,input,2000);
                gridPassword4.clearPassword();
            }
        });
        gridPassword4.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                LemonBubble.showRight(GridPassWordTest.this,psw,2000);
                gridPassword4.clearPassword();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
    }
}
