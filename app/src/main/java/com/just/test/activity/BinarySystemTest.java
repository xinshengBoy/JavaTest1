package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.tools.BinarySystemTools;
import com.just.test.tools.SnackbarUtil;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 二进制和字符串的互转
 * Created by admin on 2017/5/22.
 */

public class BinarySystemTest extends Activity implements View.OnClickListener{

    private EditText et_binary1,et_binary2;
    private Button btn_binary1,btn_binary2;
    private TextView txt_binary1,txt_binary2;
    private String putResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binary_system);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "二进制和字符串的互转");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);
        initView();
    }

    private void initView(){
        et_binary1 = (EditText)findViewById(R.id.et_binary1);
        et_binary2 = (EditText)findViewById(R.id.et_binary2);
        btn_binary1 = (Button)findViewById(R.id.btn_binary1);
        btn_binary2 = (Button)findViewById(R.id.btn_binary2);
        txt_binary1 = (TextView)findViewById(R.id.txt_binary1);
        txt_binary2 = (TextView)findViewById(R.id.txt_binary2);

        SpannableStringBuilder builder = new SpannableStringBuilder("可以点击的");
        //设置前景色
        builder.setSpan(new ForegroundColorSpan(Color.RED),2,4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置点击
        txt_binary1.setMovementMethod(LinkMovementMethod.getInstance());
        builder.setSpan(new TextClick(),2,4,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_binary1.setText(builder);
        btn_binary1.setOnClickListener(this);
        btn_binary2.setOnClickListener(this);

        //toast的另一个相同功能为snackbar，这是普通样式
//        Snackbar.make(btn_binary1,"你想干嘛",Snackbar.LENGTH_LONG).setAction("看看", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                initToast(true,"OK");
//            }
//        }).setActionTextColor(Color.RED).show();
        //简单示例
//        SnackbarUtil.ShortSnackbar(txt_binary1,"妹子向你发来一条信息",SnackbarUtil.Info).show();
        //这是自定义修改文字颜色和背景的样式
        SnackbarUtil.IndefiniteSnackbar(btn_binary1,"一大波妹子过来了",2,R.color.my_floater_color,R.color.main_color).show();
    }

    private class TextClick extends ClickableSpan{

        @Override
        public void onClick(View view) {
            initToast(true,"点击了");
        }
    }
    @Override
    public void onClick(View view) {
        if (view == btn_binary1){
            String input = et_binary1.getText().toString();
            if (input.equals("")){
                initToast(false,"请输入字符串");
            }else {
                String result = BinarySystemTools.stringToByte(input);
                txt_binary1.setText(result);
                putResult = result;
            }
        }else if (view == btn_binary2){
            String input = et_binary2.getText().toString();
            if (input.equals("")){
                initToast(false,"请输入二进制");
                String result = BinarySystemTools.byteToString(putResult.getBytes());
                txt_binary2.setText(result);

            }else {
                String result = BinarySystemTools.byteToString(input.getBytes());
                txt_binary2.setText(result);
            }
        }
    }

    private void initToast(boolean isSuccess,String msg){
        if (isSuccess) {
            LemonBubble.showRight(BinarySystemTest.this,msg,3000);
        }else {
            LemonBubble.showError(BinarySystemTest.this,msg,3000);
        }
    }
}
