package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.GradienterView;
import com.just.test.widget.MyActionBar;

/**
 * 水平仪
 * Created by admin on 2017/4/27.
 */

public class Gradienter extends Activity implements SensorEventListener{

    GradienterView view_gradienter;
    private SensorManager manager;
    private int MAX_ANGLE = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gradienter);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "水平仪");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        view_gradienter = (GradienterView)findViewById(R.id.view_gradienter);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(Gradienter.this,manager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(Gradienter.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        manager.unregisterListener(Gradienter.this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float [] value = event.values;
        int type = event.sensor.getType();
        switch (type){
            case Sensor.TYPE_ORIENTATION:
                float yAngle = value[1];//y的仰角
                float zAngle = value[2];//z的仰角
                //气泡位于中间时的坐标
                int x = (view_gradienter.bg_view.getWidth() - view_gradienter.bubble.getWidth()) / 2;
                int y = (view_gradienter.bg_view.getHeight() - view_gradienter.bubble.getHeight()) / 2;
                if (Math.abs(zAngle) <= MAX_ANGLE){
                    //// TODO: 2017/4/27 还在最大偏角内
                    int deltaX = (int) ((view_gradienter.bg_view.getWidth() - view_gradienter.bubble.getWidth()) / 2 * zAngle /MAX_ANGLE);
                    x += deltaX;
                }else if (zAngle > MAX_ANGLE){
                    //气泡到最左边了
                    x = 0;
                }else {
                    x = view_gradienter.bg_view.getWidth() - view_gradienter.bubble.getWidth();
                }

                if (Math.abs(yAngle) <= MAX_ANGLE){
                    //// TODO: 2017/4/27 Y轴还在最大偏角内
                    int deltaY  = (int) ((view_gradienter.bg_view.getHeight() -view_gradienter.bubble.getHeight()) / 2 * yAngle /MAX_ANGLE);
                    y += deltaY;
                }else if (yAngle > MAX_ANGLE){
                    //气泡在最下边
                    y = view_gradienter.bg_view.getHeight() - view_gradienter.bubble.getHeight();
                }else {
                    //气泡在最右边
                    y = 0;
                }

                if (isContain(x,y)){
                    view_gradienter.bubbleX = x;
                    view_gradienter.bubblleY = y;
                }
                view_gradienter.postInvalidate();//刷新
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private boolean isContain(int x,int y){
        //计算气泡的圆心坐标
        int bubbleCx = x + view_gradienter.bubble.getWidth() / 2;
        int bubbleCy = y + view_gradienter.bubble.getHeight() /2;
        //计算水平仪的圆心坐标
        int bgCx = view_gradienter.bg_view.getWidth() / 2;
        int bgCy = view_gradienter.bg_view.getHeight() / 2;
        //计算气泡圆心与水平仪圆心的距离
        double distance = Math.sqrt((bubbleCx - bgCx) * (bubbleCx - bgCx) + (bubbleCy - bgCy) * (bubbleCy - bgCy));

        //若两个圆心的距离小于她们的半径差，即可认为处于该点的气泡依然位于仪表盘内
        if (distance < (view_gradienter.bg_view.getWidth() - view_gradienter.bubble.getWidth()) / 2){
            return true;
        }else {
            return false;
        }
    }
}
