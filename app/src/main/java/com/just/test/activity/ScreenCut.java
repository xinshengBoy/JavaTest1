package com.just.test.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.just.test.R;

/**
 * 一键截屏
 * @author user
 *
 */
public class ScreenCut extends Activity {
	private String FILEPATH = "/sdcard/zzh/";
	private Button btn_screenCut;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_screen_cut);
		btn_screenCut = (Button) findViewById(R.id.btn_screenCut);
		btn_screenCut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				shotCut(btn_screenCut);
			}
		});
	}

	private void shotCut(View v) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fName = FILEPATH+sd.format(new Date())+".png";
		//得到当前view所在view结构中的根view
		View view = v.getRootView();
		//设置属性 vv是你要截取的View
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		//取得位图
		Bitmap bitmap = view.getDrawingCache();
		if (bitmap != null) {
			try {
				File folder = new File(FILEPATH);
				if(folder.canWrite()){
					folder.delete();
					folder.mkdirs();
				}
				if (!folder.exists()) {
					folder.mkdir();//如果文件夹不存在则创建
				}
				FileOutputStream out = new FileOutputStream(fName);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		Toast.makeText(ScreenCut.this, "截屏成功，存储在"+fName, Toast.LENGTH_LONG).show();
	}
}
