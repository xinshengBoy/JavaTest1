package com.just.test.activity;

import com.commit451.nativestackblur.NativeStackBlur;
import com.just.test.R;
import com.just.test.widget.FastBlur;
import com.just.test.widget.MyActionBar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 高斯模糊
 * 參考文章：https://github.com/Commit451/NativeStackBlur
 */
public class GaoSiMoHu extends Activity {

	private ImageView iv_gaosimohu,iv_gaosimohu2;
	private Button btn_gaosimohu;
	private boolean isMoHu = false;
	private Bitmap bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_gaosimohu);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "高斯模糊");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bg03);
		iv_gaosimohu = (ImageView)findViewById(R.id.iv_gaosimohu1);
		iv_gaosimohu2 = (ImageView)findViewById(R.id.iv_gaosimohu2);
		btn_gaosimohu = (Button)findViewById(R.id.btn_gaosimohu);
		btn_gaosimohu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isMoHu) {
					iv_gaosimohu.setImageResource(R.drawable.bg02);
					iv_gaosimohu2.setImageBitmap(bitmap);
					isMoHu = false;
					btn_gaosimohu.setText("进入高斯模糊");
				}else {
					Bitmap newBitmap = NativeStackBlur.process(bitmap,28);//设置模糊程度
					iv_gaosimohu2.setImageBitmap(newBitmap);
					new AsycMakeBitmap().execute();
					isMoHu = true;
					btn_gaosimohu.setText("返回正常模式");
				}
			}
		});
	}

	public class AsycMakeBitmap extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			iv_gaosimohu.setDrawingCacheEnabled(true);
			iv_gaosimohu.buildDrawingCache(true);
			Bitmap bitmap = iv_gaosimohu.getDrawingCache();
			return FastBlur.doBlur(bitmap, 30, true);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			iv_gaosimohu.setImageBitmap(result);
		}
	}
}
