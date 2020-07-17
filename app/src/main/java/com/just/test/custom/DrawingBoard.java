package com.just.test.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.just.test.tools.DensityUtil;

/**自定义画板
 * Created by Administrator on 2017/3/15.
 */

public class DrawingBoard  extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    //surfaceHolder实例
    private SurfaceHolder holder;
    //canvas对象
    private Canvas mCanvas;
    //控制子线程是否运行
    private boolean isStartDraw;
    //path实例
    private Path mPath = new Path();
    //paint实例
    private Paint mPaint = new Paint();

    public DrawingBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();//初始化
    }

    private void initView() {
        holder = getHolder();
        holder.addCallback(this);

        setFocusable(true);//设置可获得焦点
        setFocusableInTouchMode(true);//设置可触摸焦点
        this.setKeepScreenOn(true);//设置常亮
    }

    /**
     * 创建
     * @param surfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isStartDraw = true;
        new Thread(this).start();
    }

    /**
     * 改变
     * @param surfaceHolder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    /**
     * 销毁
     * @param surfaceHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isStartDraw = false;
    }

    @Override
    public void run() {
        while (isStartDraw){
            draw();
        }
    }

    private void draw(){
        try {
            mCanvas = holder.lockCanvas();//锁定实例化画布
            mCanvas.drawColor(Color.WHITE);//设置画布颜色

            mPaint.setStyle(Paint.Style.STROKE);//设置画笔样式
            mPaint.setStrokeWidth(DensityUtil.px2dip(getContext(),30));//设置画笔宽度
            mPaint.setColor(Color.BLACK);//设置画笔颜色
            mCanvas.drawPath(mPath, mPaint);
        }catch (Exception e){

        }finally {
            //对画布内容进行提交
            if (mCanvas != null){
                holder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    //重置画布
    public void reSet(){
        mPath.reset();
    }
}
