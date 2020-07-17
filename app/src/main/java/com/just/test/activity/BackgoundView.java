package com.just.test.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 色谱  获取背景的颜色值
 * compile 'com.android.support:palette-v7:25.3.1'
 * Created by admin on 2017/4/28.
 */

public class BackgoundView extends Activity {

    private RelativeLayout views;
    private TextView txt_backgoundview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_backgoundview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "色谱");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        views = (RelativeLayout)findViewById(R.id.view_backgoundview);
        txt_backgoundview = (TextView) findViewById(R.id.txt_backgoundview);

        views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.sendEmptyMessage(0);
            }
        });

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                Bitmap bitmap = convertViewToBitmap(views);
                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant = palette.getVibrantSwatch();
                        if (vibrant != null){
                            txt_backgoundview.setText("当前的颜色为:"+vibrant.getRgb());
                            txt_backgoundview.setBackgroundColor(vibrant.getRgb());
                        }
                    }
                });
//                Palette p = Palette.from(convertViewToBitmap(views)).generate();
//                Palette.Swatch swatch = p.getVibrantSwatch();
//                if (swatch != null){
//                    txt_backgoundview.setText("当前的颜色为:"+colorBurn(swatch.getRgb()));
//                    txt_backgoundview.setBackgroundColor(colorBurn(swatch.getRgb()));
//                }
            }
        }
    };
    public Bitmap convertViewToBitmap(View view){
        view.setDrawingCacheEnabled(true);//允许获取图片的缓存内容
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    private int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }
}
