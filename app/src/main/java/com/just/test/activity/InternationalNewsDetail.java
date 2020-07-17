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
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 国际新闻详情
 * Created by admin on 2017/6/1.
 */

public class InternationalNewsDetail extends Activity {

    private WebView webviews;
    private ProgressBar progressBars;
    private TextView refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_internaionalnews_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", title);
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        webviews = (WebView)findViewById(R.id.webview_international_detail);
        progressBars = (ProgressBar)findViewById(R.id.pb_international_detail);
        refresh = (TextView)findViewById(R.id.txt_internationalNews_refresh);

        WebSettings settings = webviews.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(true);//设置缩放工具
        settings.setUseWideViewPort(true);//扩大比例的缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);//自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染的优先级

        webviews.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                progressBars.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.GONE);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBars.setVisibility(View.GONE);
                refresh.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                refresh.setVisibility(View.VISIBLE);
            }
        });
        webviews.loadUrl(url);
    }
}
