package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 记住密码
 * Created by Administrator on 2017/1/21.
 */

public class RemerberPWD extends Activity implements View.OnClickListener{

    private EditText et_remerber_name,et_remerber_pwd;
    private Button btn_remerber_login;
    private CheckBox cb_remerber_check;
    private SharedPreferences sp;
    private ImageView iv_remerber_seeOrHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_remerberpwd);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "记住密码");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        et_remerber_name = (EditText)findViewById(R.id.et_remerber_name);
        et_remerber_pwd = (EditText)findViewById(R.id.et_remerber_pwd);
        btn_remerber_login = (Button)findViewById(R.id.btn_remerber_login);
        cb_remerber_check = (CheckBox)findViewById(R.id.cb_remerber_check);
        iv_remerber_seeOrHide = (ImageView)findViewById(R.id.iv_remerber_seeOrHide);

        sp = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sp.getBoolean("remerber",false)){
            String name = sp.getString("name","");
            String pwd = sp.getString("pwd","");

            et_remerber_name.setText(name);
            et_remerber_pwd.setText(pwd);
            cb_remerber_check.setChecked(true);
        }
        btn_remerber_login.setOnClickListener(this);
        iv_remerber_seeOrHide.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    et_remerber_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_remerber_seeOrHide.setImageResource(R.drawable.icon_pw01);
                }else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    et_remerber_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_remerber_seeOrHide.setImageResource(R.drawable.icon_pw02);
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        String name = et_remerber_name.getText().toString();
        String pwd = et_remerber_pwd.getText().toString();

        if (name == null || pwd == null || name.equals("") || pwd.equals("")){
            Toast.makeText(RemerberPWD.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences.Editor editor = sp.edit();
            if (cb_remerber_check.isChecked()){
                editor.putString("name",name);
                editor.putString("pwd",pwd);
                editor.putBoolean("remerber",true);
            }else {
                editor.putBoolean("remerber",false);
            }
            editor.commit();
            Toast.makeText(RemerberPWD.this,"登录成功",Toast.LENGTH_SHORT).show();
        }


    }
}
