package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.github.florent37.fiftyshadesof.FiftyShadesOf;

/**
 * 页面预加载
 * https://github.com/florent37/FiftyShadesOf
 * compile 'com.github.florent37:fiftyshadesof:1.0.0'
 * Created by admin on 2017/6/23.
 */

public class FiftyShadesTest extends Activity {

    private TextView txt_fiftyShade;
    public FiftyShadesOf fiftyShadesOf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fifty_shades);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 页面预加载");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView(){
        Button btn_fiftyShade = (Button) findViewById(R.id.btn_fiftyShade);
        txt_fiftyShade = (TextView)findViewById(R.id.txt_fiftyShade);
        //// TODO: 2017/6/23  设置固定高度，超出部分可滑动
        txt_fiftyShade.setMaxHeight(300);
        txt_fiftyShade.setMovementMethod(ScrollingMovementMethod.getInstance());
        txt_fiftyShade.setTextIsSelectable(true);//// TODO: 2017/6/23 设置长按可复制

        btn_fiftyShade.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (txt_fiftyShade.getText().length() > 0){
                    txt_fiftyShade.setText("");
                }
                fiftyShadesOf = FiftyShadesOf.with(FiftyShadesTest.this).on(txt_fiftyShade).start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fiftyShadesOf.stop();
                        txt_fiftyShade.setText("热门技术\n" +
                                "\n" +
                                "Kotlin初探\n" +
                                "Kotlin成了Google的亲儿子，现在赶紧来学学\n" +
                                "跟着google学习mvp架构\n" +
                                "跟着google学习mvp架构（clean篇）\n" +
                                "给初学者的RxJava2.0教程(一) ：基本工作原理\n" +
                                "给初学者的RxJava2.0教程(二) ：强大的线程控制\n" +
                                "给初学者的RxJava2.0教程(三) ：map和flatMap操作符\n" +
                                "给初学者的RxJava2.0教程(四) ：zip操作符使用\n" +
                                "给初学者的RxJava2.0教程(五) ：背压（Backpressure）\n" +
                                "给初学者的RxJava2.0教程(六) ：治理Backpressure\n" +
                                "给初学者的RxJava2.0教程(七) ：Flowable\n" +
                                "给初学者的RxJava2.0教程(八) ：Flowable缓存\n" +
                                "给初学者的RxJava2.0教程(九) ：响应式拉取\n" +
                                "你一定会用到的RxJava常用操作符\n" +
                                "重识OkHttp：从深入了解到源码分析\n" +
                                "Android网络请求改造之路（retrofit+rxjava）\n" +
                                "Gradle 构建:从入门到实战\n" +
                                "Scorller的使用详解\n" +
                                "Android动画实现详解\n" +
                                "View\n" +
                                "\n" +
                                "Android之自定义View：侧滑删除\n" +
                                "Android实用：TextView实现打印机效果\n" +
                                "Android实用View:炫酷的进度条\n" +
                                "Android实用View：仿微信支付密码输入框\n" +
                                "沉浸式管理：让你的APP更优雅\n" +
                                "Android美团首页分类按钮（含音频教程）\n" +
                                "Android自定义动画酷炫的提交按钮\n" +
                                "Android实现毛玻璃效果(高斯模糊)效果\n" +
                                "Android灵魂画家的18种混合模式\n" +
                                "代码封装\n" +
                                "\n" +
                                "BaseActivity的封装思想\n" +
                                "你知道几种单例模式？（文末彩蛋）\n" +
                                "对于有多种可替代解决方案的业务逻辑，提供一种快速更换的思路\n" +
                                "武林秘籍之设计模式迷你手册");
                    }
                },3000);
            }
        });
    }
}
