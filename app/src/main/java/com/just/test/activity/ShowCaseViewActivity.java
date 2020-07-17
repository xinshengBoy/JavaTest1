package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

/**
 * ShowCaseView 引导页
 * https://github.com/amlcurran/ShowcaseView
 * compile 'com.github.amlcurran.showcaseview:library:5.4.3'
 * Created by admin on 2017/4/22.
 */

public class ShowCaseViewActivity extends Activity implements View.OnClickListener{

    private ShowcaseView btn1View,btn2View,btn3View;
    private Button btn_showcaseviews,btn_showcaseviews2,btn_showcaseviews3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_showcaseview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "引导页");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_showcaseviews = (Button) findViewById(R.id.btn_showcaseviews);
        btn_showcaseviews2 = (Button) findViewById(R.id.btn_showcaseviews2);
        btn_showcaseviews3 = (Button) findViewById(R.id.btn_showcaseviews3);
        btn_showcaseviews.setOnClickListener(this);
        btn_showcaseviews2.setOnClickListener(this);
        btn_showcaseviews3.setOnClickListener(this);

        ViewTarget btn1 = new ViewTarget(btn_showcaseviews);
        ViewTarget btn2 = new ViewTarget(btn_showcaseviews2);
        ViewTarget btn3 = new ViewTarget(btn_showcaseviews3);

        final Button btn_1 = new Button(this);
        btn_1.setText("下一步");
        final Button btn_2 = new Button(this);
        btn_2.setText("下一步");
        final Button btn_3 = new Button(this);
        btn_3.setText("明白了");
        //// TODO: 2017/4/24 详细用法：http://www.jianshu.com/p/c4f226d76952
        btn1View = new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setTarget(btn1)
                .setContentTitle("第一步")
                .setContentText("输入用户名")
//                .replaceEndButton(btn_1)
                .build();
//        btn1View.hide();

        btn2View = new ShowcaseView.Builder(this)
                .withNewStyleShowcase()
                .setTarget(btn2)
                .setContentTitle("第二步")
                .setContentText("输入密码")
//                .replaceEndButton(btn_2)
                .build();
        btn2View.hide();

        btn3View = new ShowcaseView.Builder(this)
                .withHoloShowcase()
                .setTarget(btn3)
                .setContentTitle("第三步")
                .setContentText("点击注册按钮")
//                .replaceEndButton(btn_3)
                .build();
        btn3View.hide();



    }

    @Override
    public void onClick(View view) {
        if (view == btn_showcaseviews){
            btn1View.hide();
            btn2View.show();
        }else if (view == btn_showcaseviews2){
            btn2View.hide();
            btn3View.show();
        }else if (view == btn_showcaseviews3){
            btn3View.hide();
        }
    }
}
