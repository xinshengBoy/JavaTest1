package com.just.test.activity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.just.test.R;

/**
 * 新建或删除文件夹
 */
public class CreateOrDeleteFile extends Activity implements OnClickListener {

	private Button btn_createFile, btn_deleteFile;
	private String FILEPATH = "/sdcard/zzh/";// 文件夹路径
	private String FILENAME = "zhongzhihua.txt";// 文件夹路径
	private File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_create_delete_file);

		btn_createFile = (Button) findViewById(R.id.btn_createFile);
		btn_deleteFile = (Button) findViewById(R.id.btn_deleteFile);

		btn_createFile.setOnClickListener(this);
		btn_deleteFile.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == btn_createFile) {
			file = new File(FILEPATH);
			if (!file.exists()) {
				try {
					file.mkdirs();// 创建文件夹
				} catch (Exception e) {
				}
			}

			File dir = new File(FILEPATH + FILENAME);
			if (!dir.exists()) {
				try {
					dir.createNewFile();// 新建文件
				} catch (Exception e) {
				}
			}

			FileWriter fw = null;
			BufferedWriter bw = null;
			String datetime = "";
			try {
				SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd"
						+ " " + "hh:mm:ss");
				datetime = tempDate.format(new java.util.Date()).toString();
				fw = new FileWriter(FILEPATH + FILENAME, true);//
				// 创建FileWriter对象，用来写入字符流
				bw = new BufferedWriter(fw); // 将缓冲对文件的输出
				String myreadline = datetime + "[]" + "我很好，你呢";

				bw.write(myreadline + "\n"); // 写入文件
				bw.newLine();
				bw.flush(); // 刷新该流的缓冲
				bw.close();
				fw.close();
				Toast.makeText(CreateOrDeleteFile.this,"新建并写入成功",Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
				try {
					bw.close();
					fw.close();
				} catch (IOException e1) {
				}
			}

		} else if (v == btn_deleteFile) {
			boolean isDelete = deleteFile(file);
			if (isDelete){
				Toast.makeText(CreateOrDeleteFile.this,"删除成功",Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(CreateOrDeleteFile.this,"删除失败",Toast.LENGTH_LONG).show();
			}
		}
	}

	private boolean deleteFile(File path) {
		if (file.exists() == false) {
			return true;
		} else {
			if (file.isFile()) {
				file.delete();
				return true;
			}
			if (file.isDirectory()) {
				File[] childFile = file.listFiles();
				if (childFile == null || childFile.length == 0) {
					file.delete();
					return true;
				}
				for (File f : childFile) {
					deleteFile(f);
				}
//                file.delete();
				return true;
			}
		}
		return false;
	}
}
