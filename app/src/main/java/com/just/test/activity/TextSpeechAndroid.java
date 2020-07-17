package com.just.test.activity;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.just.test.R;

/**
 * 安卓自带文本朗读
 * @author user
 *
 */
public class TextSpeechAndroid extends Activity {

	private EditText et_textSpeech;
	private Button btn_textSpeech;
	private TextToSpeech mSpeech;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_textspeech);

		et_textSpeech = (EditText)findViewById(R.id.et_textSpeech);
		btn_textSpeech = (Button)findViewById(R.id.btn_textSpeech);
		btn_textSpeech.setEnabled(false);//不可点击，要先查询是否有此语种之后才可点击
		mSpeech = new TextToSpeech(this, new OnInitListener() {

			@Override
			public void onInit(int status) {
				if (status == TextToSpeech.SUCCESS) {
					int result = mSpeech.setLanguage(Locale.ENGLISH);
					if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
						Toast.makeText(TextSpeechAndroid.this, "暂无数据可用或暂无此语种", Toast.LENGTH_LONG).show();
					}else {
						btn_textSpeech.setEnabled(true);
						mSpeech.speak(et_textSpeech.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
					}
				}
			}
		});
		btn_textSpeech.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSpeech.speak(et_textSpeech.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
			}
		});
	}

	@Override
	protected void onDestroy() {
		if (mSpeech != null) {
			mSpeech.stop();
			mSpeech.shutdown();
		}
		super.onDestroy();
	}
}
