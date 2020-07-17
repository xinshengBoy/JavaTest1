package com.just.test.activity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youngkaaa.ycircleview.CircleView;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 传感器
 * Created by admin on 2017/4/26.
 */

public class SensorTest extends Activity implements SensorEventListener{

    private TextView txt_sensor_oritation,txt_sensor_magnetic,txt_sensor_temperature,txt_sensor_light,txt_sensor_pressure;
    private SensorManager manager;
    private CircleView iv_oritations;
    private float currentDegree = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sensor_test);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "倒计时和圆形进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        iv_oritations = (CircleView)findViewById(R.id.iv_oritations);
        txt_sensor_oritation = (TextView)findViewById(R.id.txt_sensor_oritation);
        txt_sensor_magnetic = (TextView)findViewById(R.id.txt_sensor_magnetic);
        txt_sensor_temperature = (TextView)findViewById(R.id.txt_sensor_temperature);
        txt_sensor_light = (TextView)findViewById(R.id.txt_sensor_light);
        txt_sensor_pressure = (TextView)findViewById(R.id.txt_sensor_pressure);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //// TODO: 2017/4/26 注册传感器
        //// TODO: 2017/4/26 方向
        manager.registerListener(this,manager.getDefaultSensor(Sensor.TYPE_ORIENTATION),manager.SENSOR_DELAY_GAME);
        //// TODO: 2017/4/26 磁场
        manager.registerListener(this,manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),manager.SENSOR_DELAY_GAME);
        //// TODO: 2017/4/26 温度
        manager.registerListener(this,manager.getDefaultSensor(Sensor.TYPE_TEMPERATURE),manager.SENSOR_DELAY_GAME);
        //// TODO: 2017/4/26 光
        manager.registerListener(this,manager.getDefaultSensor(Sensor.TYPE_LIGHT),manager.SENSOR_DELAY_GAME);
        //// TODO: 2017/4/26 压力
        manager.registerListener(this,manager.getDefaultSensor(Sensor.TYPE_PRESSURE),manager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null){
            manager.unregisterListener(SensorTest.this);//解绑
        }
    }

    @Override
    protected void onPause() {
        manager.unregisterListener(SensorTest.this);//解绑
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //// TODO: 2017/4/26 改变时回掉
        float [] value = sensorEvent.values;//获取值
        int type = sensorEvent.sensor.getType();//获取类型
        StringBuilder builder = null;

        switch (type){
            case Sensor.TYPE_ORIENTATION://方向传感器
                builder = new StringBuilder();
                builder.append("绕Z轴转过的角度："+value[0]+"\n");
                builder.append("绕X轴转过的角度："+value[1]+"\n");
                builder.append("绕Y轴转过的角度："+value[2]+"\n");
                txt_sensor_oritation.setText(builder.toString());

                //指南针旋转
                float degree = value[0];
                RotateAnimation animation = new RotateAnimation(currentDegree,-degree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                //设置持续时间
                animation.setDuration(200);
                iv_oritations.startAnimation(animation);//图片开始运行动画
                currentDegree = -degree;//将角度赋值给当前的角度

                break;
            case Sensor.TYPE_MAGNETIC_FIELD://磁场传感器
                builder = new StringBuilder();
                builder.append("X的角度："+value[0]+"\n");
                builder.append("Y的角度："+value[1]+"\n");
                builder.append("Z的角度："+value[2]+"\n");
                txt_sensor_magnetic.setText(builder.toString());
                break;
            case Sensor.TYPE_TEMPERATURE://温度传感器
                float temprature = value[0];

                builder = new StringBuilder();
                builder.append("当前温度为："+String.valueOf(temprature)+"°C");

                txt_sensor_temperature.setText(String.valueOf(temprature)+"°C");
                break;
            case Sensor.TYPE_LIGHT://光传感器
                builder = new StringBuilder();
                builder.append("当前光的强度为："+value[0]);
                txt_sensor_light.setText(builder.toString());
                break;
            case Sensor.TYPE_PRESSURE://压力传感器
                builder = new StringBuilder();
                builder.append("当前的压力为："+value[0]);
                txt_sensor_pressure.setText(builder.toString());
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
