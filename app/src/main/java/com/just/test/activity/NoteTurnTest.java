package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.just.test.R;
import com.just.test.custom.BookPageFactory;
import com.just.test.custom.PageWidget;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.io.IOException;

/**
 * 小说翻页
 * http://blog.csdn.net/hmg25/article/details/6419694
 * Created by admin on 2017/5/16.
 */

public class NoteTurnTest extends Activity {

    private Bitmap curPageBitmap,nextPageBitmap;
    private Canvas curPageCanvas,nextPageCanvas;
    private BookPageFactory pageFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);;
        final PageWidget widget = new PageWidget(NoteTurnTest.this);
        setContentView(widget);

        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = manager.getDefaultDisplay().getWidth();
        int height = manager.getDefaultDisplay().getHeight();

        curPageBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        nextPageBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        curPageCanvas = new Canvas(curPageBitmap);
        nextPageCanvas = new Canvas(nextPageBitmap);

        pageFactory = new BookPageFactory(width,height);
        //设置背景图片
        pageFactory.setBgBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_notebook));

        try {
            pageFactory.openbook("/sdcard/zzh/notebook.txt");
            pageFactory.onDraw(curPageCanvas);
        } catch (IOException e) {
            e.printStackTrace();
            LemonBubble.showError(NoteTurnTest.this,"电子书不存在，请将电子书放在SD卡根目录下面",3000);
        }

        widget.setBitmaps(curPageBitmap,curPageBitmap);
        widget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                boolean ret = false;
                if (view == widget){
                    if (event.getAction() == MotionEvent.ACTION_DOWN){
                        widget.abortAnimation();
                        widget.calcCornerXY(event.getX(),event.getY());

                        pageFactory.onDraw(curPageCanvas);
                        if (widget.DragToRight()){
                            try {
                                pageFactory.prePage();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (pageFactory.isfirstPage()){
                                return false;
                            }
                            pageFactory.onDraw(nextPageCanvas);
                        }else {
                            try {
                                pageFactory.nextPage();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (pageFactory.islastPage()){
                                return false;
                            }
                            pageFactory.onDraw(nextPageCanvas);
                        }
                        widget.setBitmaps(curPageBitmap,nextPageBitmap);
                    }
                    ret = widget.doTouchEvent(event);
                    return ret;
                }
                return false;
            }
        });
    }
}
