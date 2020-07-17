package com.just.test.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.tools.ACache;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * webview网页缓存
 * Created by admin on 2017/4/17.
 */

public class WebViewCache extends Activity {

    private WebView webview_cache;
    private ProgressBar pb_webview_cache;
    private String html;
    //    private String url = "http://blog.csdn.net/catoop/article/details/30505389";
//    private String url = "https://www.taobao.com/";
    private String url = "https://detail.tmall.com/item.htm?id=545660907254&rn=4f68536342f94a74446e8c68365f7605&abbucket=19&ali_trackid=17_26f0c2b90b08dee3ce32b0e0bbf40b57&spm=a21bo.50862.201862-3.1.LogItz";
    private ACache aCache;
    private boolean isConnectAvalible;
    private WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview_cache);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "webview网页缓存");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);


        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);

        aCache = ACache.get(WebViewCache.this);
        String htmlCache = aCache.getAsString(url);

        pb_webview_cache = (ProgressBar) findViewById(R.id.pb_webview_cache);
        webview_cache = (WebView) findViewById(R.id.webview_cache);
        settings = webview_cache.getSettings();
        settings.setJavaScriptEnabled(true);//允许加载js
        settings.setDefaultTextEncodingName("UTF-8");

//        if (htmlCache != null && !htmlCache.equals("")){
//            webview_cache.loadData(htmlCache, "text/html", "UTF -8");//API提供的标准用法，无法解决乱码问题
//            webview_cache.loadData(htmlCache, "text/html; charset=UTF-8", null);//这种写法可以正确解码
//        }else {
//            getWebViewContent(url);
//        }
        isConnectAvalible = isNetworkConnected(WebViewCache.this);
        if (isConnectAvalible) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);//有网
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//无网
        }
        settings.setUseWideViewPort(true);//任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);//支持页面缩放
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //可访问文件
        settings.setAllowFileAccess(true);
        //支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true); //允许进行缓存
        settings.setAppCacheMaxSize(1024 * 1024 * 8);//设置缓冲大小
        String cacheDir = getApplicationContext().getDir("webcache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(cacheDir);//设置缓冲路径
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);

        webview_cache.requestFocusFromTouch();//获取输入焦点手势
        webview_cache.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                pb_webview_cache.setVisibility(View.VISIBLE);
//                view.loadUrl(url);//点击网页内的链接不会调用系统浏览器打开，就用本webview打开
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb_webview_cache.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                LemonBubble.showError(WebViewCache.this,"错误码："+errorCode+"。错误原因:"+description,5000);
                Toast.makeText(WebViewCache.this,"错误码："+errorCode+"。错误原因:"+description,Toast.LENGTH_LONG).show();
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                int statuCode = errorResponse.getStatusCode();
                Toast.makeText(WebViewCache.this,"错误码："+statuCode,Toast.LENGTH_LONG).show();

            }
        });
        webview_cache.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview_cache.canGoBack()) {
            webview_cache.goBack();
            webview_cache.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        webview_cache.removeAllViews();
        webview_cache.destroy();
        if (html != null) {
            html = null;
        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    private void getWebViewContent(final String url) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                html = new String(bytes);//获取网页html
                if (!html.equals("")) {
                    aCache.clear();
                    aCache.put(url, html);
                }
                handler.sendEmptyMessage(0);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
//                webview_cache.loadDataWithBaseURL("",html,"text/html","utf-8",null);
//                webview_cache.loadDataWithBaseURL(null,html,"text/html","utf-8","");
//                webview_cache.loadData(html,"text/html","utf-8");
//                webview_cache.loadData(html,"text/html; charset=UTF-8", null);
                webview_cache.loadData(html, "text/html", "UTF -8");//API提供的标准用法，无法解决乱码问题
                webview_cache.loadData(html, "text/html; charset=UTF-8", null);//这种写法可以正确解码
            } else if (msg.what == 1) {
                if (isConnectAvalible) {
                    settings.setCacheMode(WebSettings.LOAD_DEFAULT);//有网
                } else {
                    settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//无网
                }
                webview_cache.loadUrl(url);
            }
        }
    };

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }

            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info == null) {
                    isConnectAvalible = false;
                } else {
                    if (info.isAvailable()) {
                        isConnectAvalible = true;
                    } else {
                        isConnectAvalible = false;
                    }
                }

                handler.sendEmptyMessage(1);
            }
        }
    };

}
