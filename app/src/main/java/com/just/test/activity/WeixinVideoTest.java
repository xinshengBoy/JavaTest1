package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.custom.RecordProgress;
import com.just.test.widget.MyActionBar;
import com.lqr.videorecordview.LQRVideoRecordView;

/**
 * 仿微信小视频
 compile 'com.lqr.videorecordview:library:1.0.0'
 * http://git.oschina.net/CSDNLQR/LQRViedoRecordView/tree/master
 * Created by admin on 2017/4/25.
 */

public class WeixinVideoTest extends Activity implements View.OnTouchListener,LQRVideoRecordView.OnRecordStausChangeListener{

    private TextView txt_weixin_tip;
    private LQRVideoRecordView weixinvideos;
    private RecordProgress record_progress;
    private Button btn_startVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weixinvideo);

        handler.sendEmptyMessageDelayed(0,3000);

    }

    private void initView(){
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "仿微信小视频");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        weixinvideos = (LQRVideoRecordView) findViewById(R.id.weixinvideos);
        txt_weixin_tip = (TextView)findViewById(R.id.txt_weixin_tip);
        record_progress = (RecordProgress)findViewById(R.id.record_progress);
        btn_startVideo = (Button)findViewById(R.id.btn_startVideo);

        record_progress.setRecordTime(10);
        btn_startVideo.setOnTouchListener(this);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                initView();
            }
        }
    };
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view == btn_startVideo){
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    //开始录制
                    record_progress.start();//进度条启动
                    record_progress.setProgressColor(Color.parseColor("#1AAD19"));
                    txt_weixin_tip.setVisibility(View.VISIBLE);//显示提示信息
                    txt_weixin_tip.setText("上滑取消");

                    weixinvideos.record(WeixinVideoTest.this);//开始录制
                    break;
                case MotionEvent.ACTION_UP:
                    //停止录制
                    record_progress.stop();
                    txt_weixin_tip.setVisibility(View.GONE);
                    //判断时长
                    if (weixinvideos.getTimeCount() > 3){
                        if (!isCancel(view,motionEvent)){
                            onRecrodFinish();//录制完成
                        }
                    }else {
                        if (!isCancel(view,motionEvent)){
                            Toast.makeText(WeixinVideoTest.this,"录制时间过短",Toast.LENGTH_SHORT).show();
                            if (weixinvideos.getVecordFile() != null){
                                weixinvideos.getVecordFile().delete();//删除相应的小视频
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    //上滑
                    if (isCancel(view,motionEvent)){
                        txt_weixin_tip.setVisibility(View.VISIBLE);
                        txt_weixin_tip.setText("松开取消");
                        record_progress.setProgressColor(Color.parseColor("#FF1493"));
                    }else {
                        txt_weixin_tip.setVisibility(View.VISIBLE);
                        txt_weixin_tip.setText("上滑取消");
                        record_progress.setProgressColor(Color.parseColor("#1AAD19"));
                    }
                    break;
            }
        }
        return true;
    }

    private boolean isCancel(View view,MotionEvent event){
        int [] location = new int[2];
        view.getLocationOnScreen(location);
        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth() || event.getRawY() < location[1]-40){
            return true;
        }
        return false;
    }

    @Override
    public void onRecrodFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_weixin_tip.setVisibility(View.GONE);
                resetVideoRecord();
                //打开播放界面
                Intent intent = new Intent(WeixinVideoTest.this,WeixinVideoSuccess.class);
                Bundle bundle = new Bundle();
                bundle.putString("filePath",weixinvideos.getVecordFile().toString());//传入视频路径
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRecording(int timeCount, int recordMaxTime) {

    }

    @Override
    public void onRecordStart() {

    }

    /**
     * 停止录制，释放相机后再重新打开相机
     */
    public void resetVideoRecord(){
        weixinvideos.stop();
        weixinvideos.openCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (weixinvideos != null){
            weixinvideos = null;
        }
    }
}
