package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.FadeInTextView;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 一个自定义的逐字逐句加载显示的textview控件
 * fadeInTextView
 * 参考文档：https://github.com/lygttpod/AndroidCustomView/blob/master/app/src/main/java/com/allen/androidcustomview/widget/FadeInTextView.java
 * Created by admin on 2017/6/22.
 */

public class FadeInTextTest extends Activity {

    private EditText et_fadeInText;
    private FadeInTextView view_fadeInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fadeintext);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "逐字逐句显示");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView() {
        et_fadeInText = (EditText)findViewById(R.id.et_fadeInText);
        Button btn_startFadeInText = (Button) findViewById(R.id.btn_startFadeInText);
        view_fadeInText = (FadeInTextView)findViewById(R.id.view_fadeInText);

        btn_startFadeInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_fadeInText.getText().toString();
                if (input.equals("")){
                    LemonBubble.showError(FadeInTextTest.this,"请输入文字",1000);
                    return;
                }else {
                    view_fadeInText.setTextString(input)
                            .startFadeInAnimation()
                            .setTextAnimationListener(new FadeInTextView.TextAnimationListener() {
                                @Override
                                public void animationFinish() {
                                    LemonBubble.showRight(FadeInTextTest.this,"显示完成",1000);
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
    }
}
