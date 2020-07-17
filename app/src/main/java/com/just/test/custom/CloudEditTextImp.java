package com.just.test.custom;

import android.content.Context;
import android.util.AttributeSet;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自动识别和添加邮箱后缀
 * Created by admin on 2017/6/12.
 */

public class CloudEditTextImp extends CloudEditText {
    private Context context;

    public CloudEditTextImp(Context context) {
        super(context);
        this.context = context;
    }

    public CloudEditTextImp(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CloudEditTextImp(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @Override
    public boolean checkInputSpan(String showText, String returnText) {
        if(returnText.contains("@")){
            boolean result = patternMatch("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?",returnText);
            if(!result){
                LemonBubble.showError(context,"请输入一个邮箱",2000);
            }
            return result;
        }else{
            if(returnText.contains("+")){
                LemonBubble.showError(context,"手机号前请不要加区号",2000);
                return false;
            }else{
                boolean result = patternMatch("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$",returnText);
                if(!result){
                    LemonBubble.showError(context,"请输入一个手机号",2000);
                }
                return result;
            }
        }
    }

    private boolean patternMatch(String rule,String input){
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(input);
        return m.matches();
    }
}
