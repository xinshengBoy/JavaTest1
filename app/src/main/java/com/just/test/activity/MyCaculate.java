package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 计算器
 * Created by Administrator on 2017/1/18.
 */

public class MyCaculate extends Activity implements View.OnClickListener{

    private TextView txt_caculate_result,txt_caculate_result2;
    private TextView clearAll,clear,kexue,mo;
    private TextView seven,eight,nine,chu;
    private TextView four,five,six,cheng;
    private TextView one,two,three,jian;
    private TextView zero,point,dengyu,jia;
    private String result = "";
    private String clickText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_caculate);
        initView();
    }

    private void initView(){
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "计算器");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        txt_caculate_result2 = (TextView) findViewById(R.id.txt_caculate_result2);
        txt_caculate_result = (TextView) findViewById(R.id.txt_caculate_result);

        kexue = (TextView)findViewById(R.id.txt_caculate_kexue);
        clearAll = (TextView)findViewById(R.id.txt_caculate_clearAll);
        clear = (TextView)findViewById(R.id.txt_caculate_clear);
        mo = (TextView)findViewById(R.id.txt_caculate_mo);

        seven = (TextView)findViewById(R.id.txt_caculate_seven);
        eight = (TextView)findViewById(R.id.txt_caculate_eight);
        nine = (TextView)findViewById(R.id.txt_caculate_nine);
        chu = (TextView)findViewById(R.id.txt_caculate_chu);

        four = (TextView)findViewById(R.id.txt_caculate_four);
        five = (TextView)findViewById(R.id.txt_caculate_five);
        six = (TextView)findViewById(R.id.txt_caculate_six);
        cheng = (TextView)findViewById(R.id.txt_caculate_cheng);

        one = (TextView)findViewById(R.id.txt_caculate_one);
        two = (TextView)findViewById(R.id.txt_caculate_two);
        three = (TextView)findViewById(R.id.txt_caculate_three);
        jian = (TextView)findViewById(R.id.txt_caculate_jian);

        zero = (TextView)findViewById(R.id.txt_caculate_zero);
        point = (TextView)findViewById(R.id.txt_caculate_point);
        dengyu = (TextView)findViewById(R.id.txt_caculate_dengyu);
        jia = (TextView)findViewById(R.id.txt_caculate_jia);

        kexue.setOnClickListener(this);
        clearAll.setOnClickListener(this);
        clear.setOnClickListener(this);
        mo.setOnClickListener(this);

        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        chu.setOnClickListener(this);

        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        cheng.setOnClickListener(this);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        jian.setOnClickListener(this);

        zero.setOnClickListener(this);
        point.setOnClickListener(this);
        dengyu.setOnClickListener(this);
        jia.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == kexue){

        }else if (view == clearAll){
            result = "C";
            setResults(result);
        }else if (view == clear){

        }else if (view == mo){
            clickText = "%";
            result += clickText;
            setResults(result);
        }else if (view == seven){
            clickText = "7";
            result += clickText;
            setResults(result);
        }else if (view == eight){
            clickText = "8";
            result += clickText;
            setResults(result);
        }else if (view == nine){
            clickText = "9";
            result += clickText;
            setResults(result);
        }else if (view == chu){
            clickText = "÷";
            result += clickText;
            setResults(result);
        }else if (view == four){
            clickText = "4";
            result += clickText;
            setResults(result);
        }else if (view == five){
            clickText = "5";
            result += clickText;
            setResults(result);
        }else if (view == six){
            clickText = "6";
            result += clickText;
            setResults(result);
        }else if (view == cheng){
            clickText = "×";
            result += clickText;
            setResults(result);
        }else if (view == one){
            clickText = "1";
            result += clickText;
            setResults(result);
        }else if (view == two){
            clickText = "2";
            result += clickText;
            setResults(result);
        }else if (view == three){
            clickText = "3";
            result += clickText;
            setResults(result);
        }else if (view == jian){
            clickText = "-";
            result += clickText;
            setResults(result);
        }else if (view == zero){
            clickText = "0";
            result += clickText;
            setResults(result);
        }else if (view == point){
            clickText = ".";
            result += clickText;
            setResults(result);
        }else if (view == dengyu){
            clickText = "=";
            result += clickText;
            setResults(result);
        }else if (view == jia){
            clickText = "+";
            result += clickText;
            setResults(result);
        }
    }

    private void setResults(String txt){
        if (txt.contains("=")) {
            String ss = txt.substring(0,txt.length()-1);
            if (ss.contains("+")) {
                String[] array = ss.split("[+]");
                float x = Float.parseFloat(array[0]);
                float y = Float.parseFloat(array[1]);
                txt_caculate_result2.setText(txt);
                txt_caculate_result.setText((x + y) + "");
            } else if (ss.contains("-")) {
                String[] array = ss.split("[-]");
                float x = Float.parseFloat(array[0]);
                float y = Float.parseFloat(array[1]);
                txt_caculate_result2.setText(txt);
                txt_caculate_result.setText((x - y) + "");
            } else if (ss.contains("×")) {
                String[] array = ss.split("[×]");
                float x = Float.parseFloat(array[0]);
                float y = Float.parseFloat(array[1]);
                txt_caculate_result2.setText(txt);
                txt_caculate_result.setText((x * y) + "");
            } else if (ss.contains("÷")) {
                String[] array = ss.split("[÷]");
                float x = Float.parseFloat(array[0]);
                float y = Float.parseFloat(array[1]);
                txt_caculate_result2.setText(txt);
                txt_caculate_result.setText((x / y) + "");
            }  else if (ss.contains("%")) {
                String[] array = ss.split("[%]");
                float x = Float.parseFloat(array[0]);
                float y = Float.parseFloat(array[1]);
                txt_caculate_result2.setText(txt);
                txt_caculate_result.setText((x % y) + "");
            }
        }else if (txt.contains("C")){
            txt_caculate_result.setText("");
            txt_caculate_result2.setText("");
            result = "";
        }else {
            txt_caculate_result.setText(txt);
        }
    }
}
