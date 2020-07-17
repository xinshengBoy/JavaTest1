package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

/**
 * 加载pdf文件
 * http://blog.csdn.net/mapboo/article/details/52170016
 * https://github.com/barteksc/AndroidPdfViewer
 * compile 'com.github.barteksc:android-pdf-viewer:1.4.0'
 * Created by admin on 2017/6/21.
 */

public class PDFViewTest extends Activity {

    private PDFView view_pdfview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pdfview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "加载pdf文件");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        view_pdfview = (PDFView)findViewById(R.id.view_pdfview);
        File file = new File("/sdcard/zzh/voice.pdf");
        view_pdfview.fromFile(file)//加载路径
                .defaultPage(0)//默认打开的页面
                .swipeHorizontal(true)//是否横向滑动
                .enableAnnotationRendering(true)
                .enableDoubletap(true)//双击放大
                .load();//加载

    }
}
