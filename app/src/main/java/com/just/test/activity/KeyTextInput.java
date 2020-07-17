package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.custom.KeywordsFlow;
import com.just.test.widget.MyActionBar;

import java.util.Random;

/**
 * 关键字飞入飞出
 * 文章介绍
 * http://www.90159.com/2015/06/25/%E5%85%B3%E9%94%AE%E8%AF%8D%E9%9A%8F%E6%9C%BA%E9%A3%9E%E5%85%A5%E9%A3%9E%E5%87%BA%E6%95%88%E6%9E%9C/
 * Created by admin on 2017/5/4.
 */

public class KeyTextInput extends Activity implements View.OnClickListener{

    private KeywordsFlow keywords_flow;
    private Button btn_keyinput_in,btn_keyinput_out;
    public static final String[] keywords = { "国贸360", "人民路", "大石桥", "新通桥",
            "紫荆山", "医学院", "二七广场", "碧沙岗", "凯旋门", "居易国际", "百货大楼", "体育馆", "沙门村",
            "刘庄", "陈寨", "科技市场", "火车站" };
    public static final String[] keywords2 = { "火锅", "小吃", "咖啡", "电影院", "KTV",
            "茶馆", "足浴", "按摩", "超市", "银行", "酒店", "超市", "豫菜", "川菜", "蛋糕店", "医院",
            "面包店", "商场", "书店", "烧烤", "海鲜", "清真" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_keytext_input);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "关键字飞入飞出");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        keywords_flow = (KeywordsFlow)findViewById(R.id.keywords_flow);
        btn_keyinput_in = (Button)findViewById(R.id.btn_keyinput_in);
        btn_keyinput_out = (Button)findViewById(R.id.btn_keyinput_out);

        keywords_flow.setDuration(8001);
        keywords_flow.setOnClickListener(this);
        btn_keyinput_in.setOnClickListener(this);
        btn_keyinput_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_keyinput_in){
            keywords_flow.rubKeywords();//清除控件内的所有文字
            feedKeywordsFlow(keywords_flow,keywords);//重新填充文字
            keywords_flow.go2Show(KeywordsFlow.ANIMATION_IN);//显示的模式
            return;
        }else if (view == btn_keyinput_out){
            keywords_flow.rubKeywords();
            feedKeywordsFlow(keywords_flow,keywords2);
            keywords_flow.go2Show(KeywordsFlow.ANIMATION_OUT);
            return;
        }else if (view instanceof TextView){
            String key = ((TextView)view).getText().toString();
            Toast.makeText(getApplication(),key,Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * 填充关键词
     * @param kf  空间
     * @param arr   词组
     */
    private void feedKeywordsFlow(KeywordsFlow kf,String [] arr){
        Random random = new Random();
        for (int i=0;i<arr.length;i++){
            int ran = random.nextInt(arr.length);
            String tmp = arr[ran];
            kf.feedKeyword(tmp);
        }
    }
}
