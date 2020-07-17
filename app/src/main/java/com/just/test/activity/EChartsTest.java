package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.just.test.R;
import com.just.test.bean.EChartsBean;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * webview加载曲线图
 * 是一个加载js实现图标的工具，只需要在js里面定义自己需要的样式和数据
 * 参考网址：http://echarts.baidu.com/echarts2/doc/example.html
 * Created by admin on 2017/5/29.
 */

public class EChartsTest extends Activity {

    private WebView webview_echarts;
    private Spinner spinner_echarts;
    private List<EChartsBean> mList = new ArrayList<>();
    private ArrayList<String> spinnerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_echarts);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "webview加载曲线图");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initData();

        spinner_echarts = (Spinner) findViewById(R.id.spinner_echarts);
        webview_echarts = (WebView)findViewById(R.id.webview_echarts);

        WebSettings settings = webview_echarts.getSettings();
        settings.setJavaScriptEnabled(true);//支持js加载
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(true);//设置缩放工具
        settings.setUseWideViewPort(true);//扩大比例的缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);//自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染的优先级

        webview_echarts.loadUrl("file:///android_asset/echarts/biaozhunzhexiantu.html");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinselect, spinnerList);
        adapter.setDropDownViewResource(R.layout.item_dialogspinselect);
        spinner_echarts.setAdapter(adapter);
        spinner_echarts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinner_name = spinnerList.get(i);
                for (int j=0;j<mList.size();j++){
                    if (spinner_name.equals(mList.get(j).getEchartsName())){
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("url",mList.get(j).getEchartsUrl());
                        Message message = new Message();
                        message.what = 1;
                        message.setData(bundle1);
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mHandler.sendEmptyMessage(0);
    }

    private void initData(){
        String [] name = new String[]{"标准折线图","堆积折线图","标准折线图2","标准面积图","堆积面积图"
                ,"不等距折线图","不等距折线图2","面积图","折线图","对数轴"
                ,"标准柱状图","堆积柱状图","温度计式图表","组成瀑布图","变化瀑布图"
                ,"多系列叠层","标准条形图","堆积条形图","多维条形图","旋风条形图"
                ,"旋风条形图2","不等距柱形图","搭配时间轴","彩虹柱形图","多系列彩虹柱形图"
                ,"柱状图"};
        String [] url = new String[]{"file:///android_asset/echarts/biaozhunzhexiantu.html","file:///android_asset/echarts/duijizhexiantu.html","file:///android_asset/echarts/biaozhunzhexiantu2.html"
                ,"file:///android_asset/echarts/biaozhunmianjitu.html","file:///android_asset/echarts/duijimianjitu.html","file:///android_asset/echarts/budengjuzhexiantu.html"
                ,"file:///android_asset/echarts/budengjuzhexiantu2.html","file:///android_asset/echarts/mianjitu.html","file:///android_asset/echarts/zhexiantu.html"
                ,"file:///android_asset/echarts/duixiantu.html","file:///android_asset/echarts/biaozhunzhuzhuangtu.html","file:///android_asset/echarts/duijizhuzhuangtu.html"
                ,"file:///android_asset/echarts/wenduji.html","file:///android_asset/echarts/zuchengpubutu.html","file:///android_asset/echarts/bianhuapubutu.html"
                ,"file:///android_asset/echarts/duoxiliediecen.html","file:///android_asset/echarts/biaozhuntiaoxingtu.html","file:///android_asset/echarts/duijitiaoxingtu.html"
                ,"file:///android_asset/echarts/duoweitiaoxingtu.html","file:///android_asset/echarts/xuanfengtiaoxingtu.html","file:///android_asset/echarts/xuanfengtiaoxingtu2.html"
                ,"file:///android_asset/echarts/budengjuzhuxingtu.html","file:///android_asset/echarts/dapeishijianzhou.html","file:///android_asset/echarts/caihongzhuxingtu.html"
                ,"file:///android_asset/echarts/duoxiliecaihongzhuxingtu.html","file:///android_asset/echarts/zhuzhuangtu.html"};
        for (int i=0;i<name.length;i++){
            EChartsBean bean = new EChartsBean();
            bean.setEchartsName(name[i]);
            bean.setEchartsUrl(url[i]);
            mList.add(bean);
            spinnerList.add(name[i]);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                webview_echarts.loadUrl("javascript:aa();");
            }else if (msg.what == 1){
                Bundle bundle = msg.getData();
                String url = bundle.getString("url");
                webview_echarts.loadUrl(url);
                webview_echarts.loadUrl("javascript:aa();");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (spinnerList != null){
            spinnerList = null;
        }
        if (mList != null){
            mList = null;
        }
    }
}
