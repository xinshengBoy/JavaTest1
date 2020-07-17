package com.just.test.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.tools.DensityUtil;
import com.just.test.widget.MyActionBar;

/**
 * 画板2
 * Created by Administrator on 2017/3/15.
 */

public class DrawingBoardActivity2 extends Activity {

    private ImageView iv_drawing;
    private Button btn_drawReset;
    private Canvas canvas;
    private Paint paint;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawingboard2);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "画板2");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        iv_drawing = (ImageView)findViewById(R.id.iv_drawing);
        btn_drawReset = (Button)findViewById(R.id.btn_drawReset);
        btn_drawReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_drawing.setImageBitmap(null);
                showImage();
            }
        });
        showImage();
    }

    private void showImage(){
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //创建一张空白的图片
        mBitmap = Bitmap.createBitmap(width,1280, Bitmap.Config.ARGB_8888);
        //创建画布
        canvas = new Canvas(mBitmap);
        //白色背景
        canvas.drawColor(Color.WHITE);
        //创建画笔
        paint = new Paint();
        //蓝色画笔
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);//设置画笔样式
        //画笔宽度
        paint.setStrokeWidth(DensityUtil.px2dip(getApplicationContext(),30));

        //画白色背景
        canvas.drawBitmap(mBitmap,new Matrix(),paint);
        //显示
        iv_drawing.setImageBitmap(mBitmap);

        iv_drawing.setOnTouchListener(new View.OnTouchListener() {
            float x,y;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case  MotionEvent.ACTION_MOVE:
                        //获取移动后的坐标
                        float endX = event.getX();
                        float endY = event.getY();
                        //在开始和结束之间画线
                        canvas.drawLine(x,y,endX,endY,paint);
                        //重置开始点
                        x = event.getX();
                        y = event.getY();
                        iv_drawing.setImageBitmap(mBitmap);
                        break;
                }

                return true;
            }
        });
    }
}
