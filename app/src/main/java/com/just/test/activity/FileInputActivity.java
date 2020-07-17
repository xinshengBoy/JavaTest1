package com.just.test.activity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.tools.FileHelper;
import com.just.test.widget.MyActionBar;

public class FileInputActivity extends Activity implements OnClickListener {

	private Button save, read, read_raw, read_asset,save_sdcard, read_sdcard;
	private EditText edit,edit_sdcard;
	private TextView result,result_raw,result_asset,result_sdcard;
	private String FILENAME = "fileTest.txt";
	private String ASSETNAME = "assettest.txt";
	private int RAWNAME = R.raw.rawtest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fileinputstream);
		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "流文件的存储与读取");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		init();
	}

	private void init() {
		//文件流
		edit = (EditText) findViewById(R.id.et_fileinputstream);
		save = (Button) findViewById(R.id.btn_fileinputstream_save);
		read = (Button) findViewById(R.id.btn_fileinputstream_read);
		result = (TextView) findViewById(R.id.txt_fileinputstream_read);
		//raw
		read_raw = (Button) findViewById(R.id.btn_fileinputstream_read_raw);
		result_raw = (TextView) findViewById(R.id.txt_fileinputstream_read_raw);
		//asset
		read_asset = (Button) findViewById(R.id.btn_fileinputstream_read_asset);
		result_asset = (TextView) findViewById(R.id.txt_fileinputstream_read_asset);
		//sdcard
		edit_sdcard = (EditText) findViewById(R.id.et_fileinputstream_sdcard);
		save_sdcard = (Button) findViewById(R.id.btn_fileinputstream_save_sdcard);
		read_sdcard = (Button) findViewById(R.id.btn_fileinputstream_read_sdcard);
		result_sdcard = (TextView) findViewById(R.id.txt_fileinputstream_read_sdcard);

		save.setOnClickListener(this);
		read.setOnClickListener(this);
		read_raw.setOnClickListener(this);
		read_asset.setOnClickListener(this);
		save_sdcard.setOnClickListener(this);
		read_sdcard.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == save) {
			boolean state = fileSave(this,FILENAME);
			if (state) {
				Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
				edit.setText("");
			} else {
				Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
			}
		} else if (v == read) {
			String results = fileRead(this,FILENAME);
			if (results == null || results.equals("")) {
				Toast.makeText(this, "暂无内容", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(this, "读取成功", Toast.LENGTH_SHORT).show();
				result.setText(results);
			}
		} else if (v == read_raw) {
			String results = fileReadRaw(this,RAWNAME);
			if (results == null || results.equals("")) {
				Toast.makeText(this, "暂无内容", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(this, "读取成功", Toast.LENGTH_SHORT).show();
				result_raw.setText(results);
			}
		} else if (v == read_asset) {
			String results = fileReadAsset(this,ASSETNAME);
			if (results == null || results.equals("")) {
				Toast.makeText(this, "暂无内容", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(this, "读取成功", Toast.LENGTH_SHORT).show();
				result_asset.setText(results);
			}
		}else if (v == save_sdcard) {
			boolean state = fileSaveSdcard(FILENAME);
			if (state) {
				Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
				edit_sdcard.setText("");
			} else {
				Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
			}
		} else if (v == read_sdcard) {
			String results = fileReadSdcard(FILENAME);
			if (results == null || results.equals("")) {
				Toast.makeText(this, "暂无内容", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(this, "读取成功", Toast.LENGTH_SHORT).show();
				result_sdcard.setText(results);
			}
		}

	}
	//文件流保存
	private boolean fileSave(Context context,String fileName) {
		String input = edit.getText().toString();
		if (input == null || input.equals("")) {
			Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
		} else {
			FileOutputStream os;
			input = input  + "\n";
			try {
				os = context.openFileOutput(fileName, Context.MODE_APPEND);
				os.write(input.getBytes());
				os.close();
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return false;
	}
	//文件流读取
	private String fileRead(Context context,String fileName) {
		try {
			FileInputStream is = context.openFileInput(fileName);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			byte[] data = bos.toByteArray();
			return new String(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//Raw读取
	private String fileReadRaw(Context context,int raws) {
		String raw;
		try {
			InputStream is = context.getResources().openRawResource(raws);
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
			StringBuffer buffer = new StringBuffer("");
			while ((raw=br.readLine())!= null){
				buffer.append(raw);
				buffer.append("\n");
			}

			return buffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//Asset读取
	private String fileReadAsset(Context context,String fileName) {
		try{
			//得到资源中的asset数据流
			InputStream in = context.getResources().getAssets().open(fileName);
			int length = in.available();
			byte [] buffer = new byte[length];
			in.read(buffer);
			in.close();
//			res = EncodingUtils.getString(buffer, "UTF-8");
			return buffer.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	//Sdcard保存
	private boolean fileSaveSdcard(String fileName) {
		String input = edit_sdcard.getText().toString();
		if (input == null || input.equals("")) {
			Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
		} else {
			FileHelper helper = new FileHelper(this);
			try {
				boolean hasFile = helper.hasFile(fileName);
				if (!hasFile){
					helper.createFile(fileName);
				}

				helper.writeFile(input + "\n",fileName);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return false;
	}
	//Sdcard读取
	private String fileReadSdcard(String fileName) {
		FileHelper helper = new FileHelper(this);
		try {
			boolean hasFile = helper.hasFile(fileName);
			if (!hasFile){
				helper.createFile(fileName);
			}

			String result = helper.readFile(fileName);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
