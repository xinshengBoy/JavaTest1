package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

/**
 * CodeView 代码加载器
 * compile 'thereisnospon.codeview:codeview:0.3.1'
 * https://github.com/Thereisnospon/CodeView
 * Created by admin on 2017/4/21.
 */

public class CodeViewActivity extends Activity {

    private ProgressBar pb_codeview;
    private CodeView codeviews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_codeview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "代码加载器");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        codeviews = (CodeView) findViewById(R.id.codeviews);
        pb_codeview = (ProgressBar) findViewById(R.id.pb_codeview);
        pb_codeview.setVisibility(View.VISIBLE);
        handler.sendEmptyMessageDelayed(0,1000);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
//                String code = fileReadAsset(CodeViewActivity.this,"clubs2.json");
                String code2 = getFromAssets("CountDowns.java");
                codeviews.setTheme(CodeViewTheme.IR_BLACK);
                codeviews.showCode(code2);
                pb_codeview.setVisibility(View.GONE);
            }
        }
    };

    /**
     * 读取asset目录下文件
     * @param context 上下文
     * @param fileName 要读取的文件名
     * @return 读取出来的结果字符串
     */
    private String fileReadAsset(Context context, String fileName) {
        try{
            //得到资源中的asset数据流
            InputStream in = context.getResources().getAssets().open(fileName);
            int length = in.available();
            byte [] buffer = new byte[length];
            in.read(buffer);
            in.close();
            return buffer.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取asset目录下面的文件
     * @param fileName 要读取的文件名
     * @return 返回读取出来的结果字符串
     */
    public String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line + "\n";
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
