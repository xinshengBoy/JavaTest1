package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.util.Locale;

/**
 * 朗读
 * Created by Administrator on 2017/1/12.
 */

public class SpeakEnglish extends Activity {

    private EditText et_speakEnglish;
    private Button btn_speakEnglish;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_speakenglish);
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "朗读");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        et_speakEnglish = (EditText)findViewById(R.id.et_speakenglish);
        btn_speakEnglish = (Button)findViewById(R.id.btn_speakEnglish);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    //设置使用美式英语朗读
                    int result = tts.setLanguage(Locale.US);
                    if (result != TextToSpeech.LANG_AVAILABLE && result != TextToSpeech.LANG_COUNTRY_AVAILABLE){
                        Toast.makeText(SpeakEnglish.this,"暂时不支持此语言的朗读",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btn_speakEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(et_speakEnglish.getText().toString(),TextToSpeech.QUEUE_ADD,null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null){
            tts.shutdown();
        }
    }
}
