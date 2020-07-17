package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 录音
 * Created by Administrator on 2017/1/13.
 */

public class RecordSpeak extends Activity implements View.OnTouchListener{

    private Button btn_recordSpeaks;
    private ListView listview_recordspeak;
    private List<String> mList;
    private MediaPlayer mPlayer = null;
    /** 录音存储路径 */
    private static final String PATH = "/sdcard/zzh/Record/";
    private String fileName = null;//存储的绝对路径
    private MediaRecorder mRecorder = null;//录音器
    private MyRecordVoiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recordspeak);

        initData();
        initView();
    }

    private void initView(){
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "录音");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_recordSpeaks = (Button)findViewById(R.id.btn_recordSpeaks);
        listview_recordspeak = (ListView)findViewById(R.id.listview_recordspeak);

        adapter = new MyRecordVoiceAdapter(this,mList,R.layout.item_recordspeak);
        listview_recordspeak.setAdapter(adapter);
        listview_recordspeak.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(mList.get(i));
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_recordSpeaks.setOnTouchListener(this);
    }

    private void initData(){
        mList = new ArrayList<>();
        mPlayer = new MediaPlayer();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startVoice();
                break;
            case MotionEvent.ACTION_UP:
                stopVoice();
                break;
        }
        return false;
    }

    /**
     * 开始录音
     */
    private void startVoice(){
        fileName = PATH + UUID.randomUUID().toString() + ".amr";
        String state = android.os.Environment.getExternalStorageState();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)){
            Toast.makeText(RecordSpeak.this,"SD卡不可用",Toast.LENGTH_LONG).show();
            return;
        }

        File directory = new File(fileName).getParentFile();
        if (!directory.exists() && !directory.mkdirs()){
            Toast.makeText(RecordSpeak.this,"文件夹未创建",Toast.LENGTH_LONG).show();
            return;
        }

        btn_recordSpeaks.setText("正在录音");
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            LemonBubble.showError(RecordSpeak.this,"录制失败",1000);
        }
    }

    /**
     * 停止录音
     */
    private void  stopVoice(){
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        mList.add(fileName);
        adapter = new MyRecordVoiceAdapter(this,mList,R.layout.item_recordspeak);
        listview_recordspeak.setAdapter(adapter);
        btn_recordSpeaks.setText("开始录音");
    }

    private class MyRecordVoiceAdapter extends CommonAdapter<String>{

        private MyRecordVoiceAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            TextView record = helper.getView(R.id.txt_recordspeak_list);
            record.setText(item);
        }
    }
}
