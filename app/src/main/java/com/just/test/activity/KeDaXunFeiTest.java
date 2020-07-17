package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

/**
 * 科大讯飞语音简单版
 * Created by admin on 2017/4/27.
 */

public class KeDaXunFeiTest extends Activity implements View.OnClickListener{

    private EditText et_kedaxunfei;
    private Button btn_kedaxunfei;
    private SpeechSynthesizer mTts;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_kedaxunfei);
        initVoice();
        initView();
    }

    private void initView(){
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "科大讯飞语音");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        et_kedaxunfei = (EditText)findViewById(R.id.et_kedaxunfei);
        btn_kedaxunfei = (Button)findViewById(R.id.btn_kedaxunfei);
        btn_kedaxunfei.setOnClickListener(this);
    }
    private void initVoice(){
        SpeechUtility.createUtility(KeDaXunFeiTest.this, "appid=" + getString(R.string.app_id));

        mTts = SpeechSynthesizer.createSynthesizer(KeDaXunFeiTest.this, mTtsInitListener);
        //2.合成参数设置
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
    }
    @Override
    public void onClick(View view) {
        if(view == btn_kedaxunfei){
            //开始合成
            String input = et_kedaxunfei.getText().toString();
            mTts.startSpeaking(input, listener);
        }
    }

    private SynthesizerListener listener = new SynthesizerListener() {

        @Override
        public void onSpeakResumed() {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSpeakProgress(int arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSpeakPaused() {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSpeakBegin() {
            // TODO Auto-generated method stub
        }

        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onCompleted(SpeechError arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
            // TODO Auto-generated method stub
        }
    };

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(KeDaXunFeiTest.this, "初始化失败,错误码：" + code, Toast.LENGTH_SHORT).show();
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };
}
