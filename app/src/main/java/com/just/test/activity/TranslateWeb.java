package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.tools.EditTextTools;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 使用网页来进行单词的翻译
 * Created by admin on 2017/6/12.
 */

public class TranslateWeb extends Activity implements View.OnClickListener {

    private EditText et_translateWeb;
    private Button btn_web_translate;
    private WebView webview_translate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_translate_web);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 网页翻译");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
        initWebView();
    }

    private void initView() {
        et_translateWeb = (EditText) findViewById(R.id.et_translateWeb);
        btn_web_translate = (Button) findViewById(R.id.btn_web_translate);
        webview_translate = (WebView) findViewById(R.id.webview_translate);

        btn_web_translate.setOnClickListener(this);
    }

    private void initWebView() {
        WebSettings settings = webview_translate.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_web_translate) {
            EditTextTools.hideSoftInput(TranslateWeb.this);
            String input = et_translateWeb.getText().toString();
            if (input.equals("")) {
                LemonBubble.showError(TranslateWeb.this, "不能为空", 2000);
                return;
            }

            //判断输入的是否是中文
            boolean state = RegularExpression.patternMatch("^[\\u4e00-\\u9fa5]{0,}$", input);
            String url;
            if (state) {
                url = "http://fanyi.baidu.com/#zh/en/" + input;
            } else {
                url = "http://fanyi.baidu.com/#en/zh/" + input;
            }

            webview_translate.loadUrl(url);
        }
    }
}
