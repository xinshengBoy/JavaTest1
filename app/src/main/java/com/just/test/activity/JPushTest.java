package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 极光推送点击通知跳转详情的展示页面
 * Created by admin on 2017/5/3.
 */

public class JPushTest extends Activity{

    private WebView webview_jpush;
    private ProgressBar pb_jpush;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jpushtest);
        /**获取从MyReceiver里面获取到的内容,主要分为两部分，前面是标题，后面是链接，中间用一个*号隔开**/
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        String  [] results = result.split("[*]");

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", results[0]);
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        webview_jpush = (WebView)findViewById(R.id.webview_jpush);
        pb_jpush = (ProgressBar) findViewById(R.id.pb_jpush);

        initWebView(results[1]);
    }

    private void initWebView(final String url) {
        WebSettings settings = webview_jpush.getSettings();
        settings.setJavaScriptEnabled(true);//支持js加载
        settings.setSupportZoom(true);//支持缩放
//        settings.setBuiltInZoomControls(true);//设置缩放工具
        settings.setUseWideViewPort(true);//扩大比例的缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);//自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webview_jpush.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                pb_jpush.setVisibility(View.VISIBLE);//网页正在加载中
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);//网页加载完成
                pb_jpush.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                String str = "file:///android_asset/error_page.jpg";
                webview_jpush.loadUrl(str);
                super.onReceivedError(view, request, error);
            }
        });
        webview_jpush.loadUrl(url);
    }
}
