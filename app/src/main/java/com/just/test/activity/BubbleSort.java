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

import java.util.ArrayList;
import java.util.List;

/**
 * 冒泡排序
 * http://www.apkbus.com/blog-340477-76592.html
 * Created by admin on 2017/11/27.
 */

public class BubbleSort extends Activity implements View.OnClickListener{

    private TextView txt_input,txt_check,txt_result;
    private EditText et_input;
    private Button btn_create;
    private int[] arr = new int[0];
    private int count = 0;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bubble_sort);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "冒泡排序");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView() {
        txt_input = (TextView)findViewById(R.id.txt_bubblesort_input);
        et_input = (EditText)findViewById(R.id.et_bubblesort);
        txt_check = (TextView)findViewById(R.id.txt_bubblesort_check);
        btn_create = (Button)findViewById(R.id.btn_bubblesort_create);
        txt_result = (TextView) findViewById(R.id.txt_bubblesort_result);

        txt_check.setOnClickListener(this);
        btn_create.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == txt_check){
            String input = et_input.getText().toString();
            if (input == ""){
                LemonBubble.showError(BubbleSort.this,"请输入数字",1000);
                et_input.setText("");
                et_input.requestFocus();//重新设置焦点
                return;
            }
            mList.add(input);
//            count = arr.length;
//            if (count == 0){
//                count = 0;
//            }else {
//                count += 1;
//            }
//            arr[count] = Integer.parseInt(input);
            et_input.setText("");
            et_input.requestFocus();//重新设置焦点
//            txt_input.setText(arr.toString());
            txt_input.setText(mList.toString());
        }else if (view == btn_create){
            String a = mList.toString();
            int b = Integer.parseInt(a);


            int[] as = {3,45,23,1,8,55,6,4};
//            StringBuffer sb1 = new StringBuffer();
//            for (int i=0;i<as.length;i++){
//                sb1.append(as[i]+",");
//            }
//            txt_input.setText(sb1.toString());
            int[] s =  BootSort(as);
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<s.length;i++){
                sb.append(s[i]+"-");
            }
            txt_result.setText(sb.toString());
        }
    }

    //冒泡排序
    private static int[] BootSort(int[] array) {
        for (int j = array.length-1; j >0; j--) {
            boolean flag=true;
            for (int i = 0; i < j; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];//本来数组位置；
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    flag=false;//优化，走进来了那就可以结束循环了
                }
            }
            if (flag){
                break;
            }
        }
        return array;
    }
}
