package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import org.textmining.text.extraction.WordExtractor;

import java.io.File;
import java.io.FileInputStream;

/**
 * 读取word文档
 */
public class ReadWordDOCActivity extends Activity {

	private TextView txt_read_word_doc;
	private SeekBar seekbar_worddoc;
	private int MAX = 50;
	private int MIN = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_read_worddoc);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "读取word文档");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		txt_read_word_doc = (TextView) findViewById(R.id.txt_read_word_doc);
		seekbar_worddoc = (SeekBar)findViewById(R.id.seekbar_worddoc);
		seekbar_worddoc.setMax(MAX);
		seekbar_worddoc.setProgress(16);
		seekbar_worddoc.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
				Message message = new Message();
				message.what = 1;
				message.arg1 = progress;
				mHandler.sendMessage(message);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		mHandler.sendEmptyMessage(0);
	}

	/**
	 * 读取doc文档
	 * @param file 文档路径
	 * @return  返回读取的结果
     */
	private String readWord(String file) {
		// 创建输入流读取doc文件
		FileInputStream in;
		String text = null;
		try {
			in = new FileInputStream(new File(file));
			WordExtractor extractor;
			// 创建WordExtractor
			extractor = new WordExtractor();
			// 对doc文件进行提取
			text = extractor.extractText(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				String str = readWord(Environment.getExternalStorageDirectory().getAbsolutePath()+"/sse.doc" );
				txt_read_word_doc.setText(str);
			}else if (msg.what == 1){
				int size = msg.arg1;
				if (size >= 50){
					size = 50;
				}else if (size <=16){
					size = 16;
				}
				txt_read_word_doc.setTextSize(size);
			}
		}
	};
}
