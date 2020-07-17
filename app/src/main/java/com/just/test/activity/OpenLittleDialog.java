package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

import me.leefeng.promptlibrary.OnAdClickListener;
import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * 轻量级开源提示框
 * http://p.codekk.com/detail/Android/limxing/Android-PromptDialog
 * https://github.com/limxing/Android-PromptDialog
 * compile 'com.github.limxing:Android-PromptDialog:1.1.1'
 * Created by admin on 2017/5/11.
 */

public class OpenLittleDialog extends Activity implements View.OnClickListener{

    private Button warm,loading,success,miss,info,custom,tc,close,other;
    private PromptDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_openlitte_dialog);

        initView();
    }

    private void initView(){
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "轻量级开源提示框");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        dialog = new PromptDialog(OpenLittleDialog.this);
        dialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(3000);

        warm = (Button)findViewById(R.id.btn_openLittleDialog_warm);
        loading = (Button)findViewById(R.id.btn_openLittleDialog_loading);
        success = (Button)findViewById(R.id.btn_openLittleDialog_success);
        miss = (Button)findViewById(R.id.btn_openLittleDialog_miss);
        info = (Button)findViewById(R.id.btn_openLittleDialog_info);
        custom = (Button)findViewById(R.id.btn_openLittleDialog_custom);
        tc = (Button)findViewById(R.id.btn_openLittleDialog_tc);
        close = (Button)findViewById(R.id.btn_openLittleDialog_close);
        other = (Button)findViewById(R.id.btn_openLittleDialog_other);

        warm.setOnClickListener(this);
        loading.setOnClickListener(this);
        success.setOnClickListener(this);
        miss.setOnClickListener(this);
        info.setOnClickListener(this);
        custom.setOnClickListener(this);
        tc.setOnClickListener(this);
        close.setOnClickListener(this);
        other.setOnClickListener(this);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                dialog.dismiss();
            }
        }
    };
    @Override
    public void onClick(View view) {
        if (view == warm){
            dialog.showWarn("前方高能，注意了哟");
        }else if (view == loading){
            dialog.showLoading("正在加载中");
        }else if (view == success){
            dialog.showSuccess("下载成功");
        }else if (view == miss){
            dialog.showError("网页开小差了");
        }else if (view == info){
            dialog.showInfo("您收到一条新通知");
        }else if (view == custom){
            dialog.showCustom(R.drawable.wechat_icon,"打开微信");
        }else if (view == tc){
            final PromptButton button = new PromptButton("确定", new PromptButtonListener() {
                @Override
                public void onClick(PromptButton promptButton) {
                    Toast.makeText(OpenLittleDialog.this,"注销成功",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            button.setTextColor(Color.GREEN);
            button.setFocusBacColor(Color.RED);

            dialog.showWarnAlert("你确定要退出么？",new PromptButton("取消", new PromptButtonListener() {
                @Override
                public void onClick(PromptButton promptButton) {
                    dialog.dismiss();
                }
            }),button);
        }else if (view == close){
            //展示广告
            dialog.getDefaultBuilder().backAlpha(150);
            Glide.with(OpenLittleDialog.this).load("https://timgsa.baidu.com/timg?image&quality=80&" +
                    "size=b9999_10000&sec=1495518782659&di=25b120262114749ae8543652d2de5715&" +
                    "imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F160316%2F9-160316152R5.jpg")
                    .into(dialog.showAd(true, new OnAdClickListener() {
                        @Override
                        public void onAdClick() {
                            Toast.makeText(OpenLittleDialog.this,"开始下载",Toast.LENGTH_SHORT).show();
                        }
                    }));
        }else if (view == other){
            //底部alertsheet
            PromptButton cancel = new PromptButton("取消",null);

            dialog.showAlertSheet("",true,cancel,new PromptButton("选项1",null)
            ,new PromptButton("选项2",null),new PromptButton("选项3",null),new PromptButton("选项4",null));
        }
    }

    @Override
    public void onBackPressed() {
        if (dialog.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
