package com.just.test.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.just.test.R;

public class MiniChrome extends Activity {

	private EditText et_minichrome;
	private Button btn_minichrome;
	private ProgressBar pb_minichrome;
	private WebView webview_minichrome;
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" }) @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_minichrome);
		
		et_minichrome = (EditText)findViewById(R.id.et_minichrome);
		btn_minichrome = (Button)findViewById(R.id.btn_minichrome);
		pb_minichrome= (ProgressBar)findViewById(R.id.pb_minichrome);
		webview_minichrome = (WebView)findViewById(R.id.webview_minichrome);
		WebSettings settings = webview_minichrome.getSettings();
		//支持js 		
		settings.setJavaScriptEnabled(true);
		//设置字符编码		
		settings.setDefaultTextEncodingName("utf-8");
		// 支持缩放		
		settings.setSupportZoom(true);		
		// //启用内置缩放装置		
		settings.setBuiltInZoomControls(true);		
		// 支持自动加载图片		
		settings.setLoadsImagesAutomatically(true);
		// 支持内容重新布局		
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		
		btn_minichrome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideSoftInput();
				String url = et_minichrome.getText().toString();
				if (url.equals("")) {
					Toast.makeText(MiniChrome.this, "网址不能为空", Toast.LENGTH_SHORT).show();
				}else if(!url.contains("www.")){
					Toast.makeText(MiniChrome.this, "您输入的网址格式不正确", Toast.LENGTH_SHORT).show();
					url = "";
				}else {
					Toast.makeText(MiniChrome.this, "网址有效", Toast.LENGTH_SHORT).show();
					// 加载需要显示的网页		
					webview_minichrome.loadUrl("http://"+url);
					//覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
					webview_minichrome.setWebViewClient(new WebViewClient(){
						
						@Override			
						public boolean shouldOverrideUrlLoading(WebView view, String url) {	
							// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
							view.loadUrl(url);
							return true;
						}
					}); 
					webview_minichrome.setWebChromeClient(new WebChromeClient(){
						public void onProgressChanged(WebView view, int newProgress) {
							pb_minichrome.setProgress(newProgress);
						};
					});
				}
			}
		});
		
		
	}
	
	private void hideSoftInput() {
		InputMethodManager im = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (this.getCurrentFocus() != null) {
			im.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
