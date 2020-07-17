package com.just.test.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bolex.pressscan.ScanTools;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 长按识别二维码
 * 参考网址：http://p.codekk.com/detail/Android/BolexLiu/PressScanCode
 * https://github.com/BolexLiu/PressScanCode
 * compile 'com.github.BolexLiu:PressScanCode:v1.0.0'
 * Created by admin on 2017/6/3.
 */

public class DistinguishErCode extends Activity {

    private EditText et_distinguish;
    private ImageView iv_distinguish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_distinguish_ercode);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 长按识别二维码");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView(){
        et_distinguish = (EditText)findViewById(R.id.et_distinguish);
        Button btn_distinguish_check = (Button) findViewById(R.id.btn_distinguish_check);
        iv_distinguish = (ImageView)findViewById(R.id.iv_distinguish);

        btn_distinguish_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_distinguish.getText().toString();
                if (input.equals("")){
                    LemonBubble.showError(DistinguishErCode.this,"请输入内容",1000);
                    return;
                }
                LemonBubble.showRoundProgress(DistinguishErCode.this, "加载中");
                createErCode(input);
            }
        });

        //使用方法
        iv_distinguish.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ScanTools.scanCode(view, new ScanTools.ScanCall() {
                    @Override
                    public void getCode(String s) {
                        LemonBubble.showRight(DistinguishErCode.this,s,5000);
                    }
                });
                return true;
            }
        });
    }

    private void createErCode(String msg){
        Bitmap bitmap = QRCode.createQRImage(msg);
        iv_distinguish.setImageBitmap(bitmap);

        LemonBubble.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
    }
}
