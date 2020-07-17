package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 介绍引导页
 * 参考：https://github.com/guangzq/StepDialog
 * compile 'com.zqg:library:1.0.1'
 * Created by admin on 2017/6/29.
 */

public class IntroducePage extends Activity implements View.OnClickListener{

    private int [] imgs = new int[]{R.drawable.bg01,R.drawable.bg02,R.drawable.bg03,R.drawable.bg04};
    private Button btn_introduce1,btn_introduce2,btn_introduce3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_introduce_page);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "介绍引导页");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView() {
        btn_introduce1 = (Button)findViewById(R.id.btn_introduce1);
        btn_introduce2 = (Button)findViewById(R.id.btn_introduce2);
        btn_introduce3 = (Button)findViewById(R.id.btn_introduce3);

        btn_introduce1.setOnClickListener(this);
        btn_introduce2.setOnClickListener(this);
        btn_introduce3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        if (view == btn_introduce1){
//            //默认样式
//            StepDialog.getInstance().setImages(imgs)
//                    .setCanceledOnTouchOutside(true)
//                    .show(getFragmentManager())
//                    .setOnCancelListener(new StepDialog.OnCancelListener() {
//                        @Override
//                        public void onCancel(int position) {
//                            if (position == imgs.length-1){
//                                String a = "1";
//                                Toast.makeText(IntroducePage.this,"已经到最后一页了",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }else if (view == btn_introduce2){
//            //折叠切换样式
//            StepDialog.getInstance().setImages(imgs)
//                    .setCanceledOnTouchOutside(false)
//                    .setPageTransformer(new DepthPageTransformer())
//                    .show(getFragmentManager());
//        }else if (view == btn_introduce3){
//            //前一张完全滑出再显示的样式
//            StepDialog.getInstance().setImages(imgs)
//                    .setCanceledOnTouchOutside(false)
//                    .setPageTransformer(new ZoomOutPageTransformer())
//                    .show(getFragmentManager());
//        }
    }
}
