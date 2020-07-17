package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式的使用
 * http://blog.csdn.net/qq_36988978/article/details/72731333
 * Created by admin on 2017/5/25.
 */

public class RegularExpression extends Activity implements View.OnClickListener{

    private EditText et_regular_password,et_regular_chinese,et_regular_strings,et_regular_email,et_regular_idCard,et_regular_dates,et_regular_money,et_regular_phoneNumber,et_regular_getUrl;
    private Button btn_regular_password,btn_regular_chinese,btn_regular_strings,btn_regular_email,btn_regular_idCard,btn_regular_dates,btn_regular_money,btn_regular_phoneNumber,btn_regular_getUrl;
    private TextView txt_regular_password,txt_regular_chinese,txt_regular_strings,txt_regular_email,txt_regular_idCard,txt_regular_dates,txt_regular_money,txt_regular_phoneNumber,txt_regular_getUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_regular_expression);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "正则表达式");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView(){
        //1 . 校验密码强度
        et_regular_password = (EditText)findViewById(R.id.et_regular_password);
        btn_regular_password = (Button)findViewById(R.id.btn_regular_password);
        txt_regular_password = (TextView)findViewById(R.id.txt_regular_password);
        //2. 校验中文
        et_regular_chinese = (EditText)findViewById(R.id.et_regular_chinese);
        btn_regular_chinese = (Button)findViewById(R.id.btn_regular_chinese);
        txt_regular_chinese = (TextView)findViewById(R.id.txt_regular_chinese);
        //3. 由数字、26个英文字母或下划线组成的字符串
        et_regular_strings = (EditText)findViewById(R.id.et_regular_strings);
        btn_regular_strings = (Button)findViewById(R.id.btn_regular_strings);
        txt_regular_strings = (TextView)findViewById(R.id.txt_regular_strings);
        //4. 校验E-Mail 地址
        et_regular_email = (EditText)findViewById(R.id.et_regular_email);
        btn_regular_email = (Button)findViewById(R.id.btn_regular_email);
        txt_regular_email = (TextView)findViewById(R.id.txt_regular_email);
        //5. 校验身份证号码-
        et_regular_idCard = (EditText)findViewById(R.id.et_regular_idCard);
        btn_regular_idCard = (Button)findViewById(R.id.btn_regular_idCard);
        txt_regular_idCard = (TextView)findViewById(R.id.txt_regular_idCard);
        //6.校验日期 “yyyy-mm-dd“ 格式的日期校验，已考虑平闰年。
        et_regular_dates = (EditText)findViewById(R.id.et_regular_dates);
        btn_regular_dates = (Button)findViewById(R.id.btn_regular_dates);
        txt_regular_dates = (TextView)findViewById(R.id.txt_regular_dates);
        //7.金额校验，精确到2位小数。
        et_regular_money = (EditText)findViewById(R.id.et_regular_money);
        btn_regular_money = (Button)findViewById(R.id.btn_regular_money);
        txt_regular_money = (TextView)findViewById(R.id.txt_regular_money);
        //8.校验手机号
        et_regular_phoneNumber = (EditText)findViewById(R.id.et_regular_phoneNumber);
        btn_regular_phoneNumber = (Button)findViewById(R.id.btn_regular_phoneNumber);
        txt_regular_phoneNumber = (TextView)findViewById(R.id.txt_regular_phoneNumber);
        //9.提取URL链接
        et_regular_getUrl = (EditText)findViewById(R.id.et_regular_getUrl);
        btn_regular_getUrl = (Button)findViewById(R.id.btn_regular_getUrl);
        txt_regular_getUrl = (TextView)findViewById(R.id.txt_regular_getUrl);

        btn_regular_password.setOnClickListener(this);
        btn_regular_chinese.setOnClickListener(this);
        btn_regular_strings.setOnClickListener(this);
        btn_regular_email.setOnClickListener(this);
        btn_regular_idCard.setOnClickListener(this);
        btn_regular_dates.setOnClickListener(this);
        btn_regular_money.setOnClickListener(this);
        btn_regular_phoneNumber.setOnClickListener(this);
        btn_regular_getUrl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_regular_password){
            //1 . 校验密码强度 8-10位
            String result = et_regular_password.getText().toString();
            if (result.equals("")){
                showNotice(false,"请输入密码");
            }else {
                boolean state = patternMatch("^(?=.*\\d)(?=.*[a-z]).{5,100}$",result);
                if (state){
                    showNotice(true,"密码长度合格");
                }else {
                    showNotice(false,"密码长度不合格");
                }
                txt_regular_password.setText(state ? "密码长度合格" : "密码长度不合格");
            }
        }else if (view == btn_regular_chinese){
            //2. 校验中文
            String result = et_regular_chinese.getText().toString();
            if (result.equals("")){
                showNotice(false,"请输入字符串");
            }else {
                boolean state = patternMatch("^[\\u4e00-\\u9fa5]{0,}$",result);
                if (state){
                    showNotice(true,"输入的是中文");
                }else {
                    showNotice(false,"输入的不是中文");
                }
                txt_regular_chinese.setText(state ? "输入的是中文" : "输入的不是中文");
            }
        }else if (view == btn_regular_strings){
            //3. 由数字、26个英文字母或下划线组成的字符串
            String result = et_regular_strings.getText().toString();
            if (result.equals("")){
                showNotice(false,"请输入字符串");
            }else {
                boolean state = patternMatch("^\\w+$",result);
                if (state){
                    showNotice(true,"输入的是数字、字母和下划线组成的字符串");
                }else {
                    showNotice(false,"输入的不是数字、字母和下划线组成的字符串");
                }
                txt_regular_strings.setText(state ? "输入的是数字、字母和下划线组成的字符串" : "输入的不是数字、字母和下划线组成的字符串");
            }
        }else if (view == btn_regular_email){
            //4. 校验E-Mail 地址
            String result = et_regular_email.getText().toString();
            if (result.equals("")){
                showNotice(false,"请输入邮箱");
            }else {
                boolean state = patternMatch("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?",result);
                if (state){
                    showNotice(true,"输入的是邮箱地址");
                }else {
                    showNotice(false,"输入的不是邮箱地址");
                }
                txt_regular_email.setText(state ? "输入的是邮箱地址" : "输入的不是邮箱地址");
            }
        }else if (view == btn_regular_idCard){
            //5. 校验身份证号码-
            String result = et_regular_idCard.getText().toString();
            if (result.equals("")){
                showNotice(false,"请输入身份证号码");
            }else {
                boolean state = false;
                if (result.length() == 15){
                    state = patternMatch("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$",result);
                }else if (result.length() == 18){
                    state = patternMatch("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$",result);
                }
                if (state){
                    showNotice(true,"输入的是身份证号码");
                }else {
                    showNotice(false,"输入的不是身份证号码");
                }
                txt_regular_idCard.setText(state ? "输入的是身份证号码" : "输入的不是身份证号码");
            }
        }else if (view == btn_regular_dates){
            //6.校验日期 “yyyy-mm-dd“ 格式的日期校验，已考虑平闰年。
            String result = et_regular_dates.getText().toString();
            if (result.equals("")){
                showNotice(false,"请输入日期");
            }else {
                boolean state = patternMatch("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$",result);
                if (state){
                    showNotice(true,"输入的是日期");
                }else {
                    showNotice(false,"输入的不是日期");
                }
                txt_regular_dates.setText(state ? "输入的是日期" : "输入的不是日期");
            }
        }else if (view == btn_regular_money){
            //7.金额校验，精确到2位小数。
            String result = et_regular_money.getText().toString();
            if (result.equals("")){
                showNotice(false,"请输入金额");
            }else {
                boolean state = patternMatch("^[0-9]+(.[0-9]{2})?$",result);
                if (state){
                    showNotice(true,"输入的是金额");
                }else {
                    showNotice(false,"输入的不是金额");
                }
                txt_regular_money.setText(state ? "输入的是金额" : "输入的不是金额");
            }
        }else if (view == btn_regular_phoneNumber){
            //8.校验手机号  下面是国内 13、14,15、18开头的手机号正则表达式。（可根据目前国内收集号扩展前两位开头号码）
            String result = et_regular_phoneNumber.getText().toString();
            if (result.equals("")){
                showNotice(false,"请输入手机号");
            }else {
                boolean state = patternMatch("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$",result);
                if (state){
                    showNotice(true,"输入的是手机号");
                }else {
                    showNotice(false,"输入的不是手机号");
                }
                txt_regular_phoneNumber.setText(state ? "输入的是手机号" : "输入的不是手机号");
            }
        }else if (view == btn_regular_getUrl){
            //9.提取URL链接  可以筛选出一段文本中的URL。
            String result = et_regular_getUrl.getText().toString();
            if (result.equals("")){
                showNotice(false,"请输入URL链接");
            }else {
                boolean state = patternMatch("^(f|ht){1}(tp|tps):\\/\\/([\\w-]+\\.)+[\\w-]+(\\/[\\w- ./?%&=]*)?",result);
                if (state){
                    showNotice(true,"输入的是URL链接");
                }else {
                    showNotice(false,"输入的不是URL链接");
                }
                txt_regular_getUrl.setText(state ? "输入的是URL链接" : "输入的不是URL链接");
            }
        }
    }

    private void showNotice(boolean isRight,String title){
        if (isRight){
            LemonBubble.showRight(RegularExpression.this,title,2000);
        }else {
            LemonBubble.showError(RegularExpression.this, title, 2000);
        }
    }

    public static boolean patternMatch(String rule,String input){
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(input);
        return m.matches();
    }
}
