package com.just.test.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 黑白照片
 */
public class BlackAndWhitePhoto extends Activity {

	private ImageView iv_blackAndWhitePhoto;
	private Button btn_blackAndWhitePhoto;
	private boolean isWhite = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_black_white_photo);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", "返回");
		bundle.putString("title", "黑白照片");
		bundle.putBoolean("rightImage", true);
		bundle.putString("rightText", "搜索");
		MyActionBar.actionbar(this,headerLayout,bundle);
		//左侧图片（返回按钮）点击事件
		MyActionBar.LeftBar(new MyActionBar.LeftListener() {
			@Override
			public void onLeftClick() {
				Toast.makeText(BlackAndWhitePhoto.this,"左侧图标",Toast.LENGTH_SHORT).show();
				BlackAndWhitePhoto.this.finish();
			}
		});
		//左侧文字
		MyActionBar.LeftImgBar(new MyActionBar.LeftImgListener() {
			@Override
			public void onLeftImgClick() {
				Toast.makeText(BlackAndWhitePhoto.this,"左侧文字",Toast.LENGTH_SHORT).show();
			}
		});
		//中间标题
		MyActionBar.TitleBar(new MyActionBar.TitleListener() {
			@Override
			public void onTitleClick() {
				Toast.makeText(BlackAndWhitePhoto.this,"中间标题",Toast.LENGTH_SHORT).show();
			}
		});
		//右侧文字
		MyActionBar.RightBar(new MyActionBar.RightListener() {
			@Override
			public void onRightClick() {
				Toast.makeText(BlackAndWhitePhoto.this,"右侧文字",Toast.LENGTH_SHORT).show();
			}
		});
		//右侧图标
		MyActionBar.RightImgBar(new MyActionBar.RightImgListener() {
			@Override
			public void oRightImgClick() {
				Toast.makeText(BlackAndWhitePhoto.this,"右侧图标",Toast.LENGTH_SHORT).show();
			}
		});

		iv_blackAndWhitePhoto = (ImageView)findViewById(R.id.iv_blackAndWhitePhoto);
		btn_blackAndWhitePhoto = (Button)findViewById(R.id.btn_blackAndWhitePhoto);

		btn_blackAndWhitePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isWhite) {
					iv_blackAndWhitePhoto.setImageResource(R.drawable.bg02);
					isWhite = false;
					btn_blackAndWhitePhoto.setText("黑白图片");
				}else {
					Bitmap whiteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg02);
					Bitmap bitmap = convertToBlackWhite(whiteBitmap);
					iv_blackAndWhitePhoto.setImageBitmap(bitmap);
					isWhite = true;
					btn_blackAndWhitePhoto.setText("常规图片");
				}
			}
		});
	}
	/**
	 * 黑白图片
	 * @param bitmap
	 * @return
	 */
	private Bitmap convertToBlackWhite(Bitmap bitmap) {
		//获取宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		//通过位图的大小创建像素点数组
		int [] pixels = new int [width*height];
		//得到图片的像素点
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF<<24;//透明度
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width*i+j];
				int red = ((grey&0x00FF0000)>>16);
				int green = ((grey&0x0000FF00)>>8);
				int blue = (grey&0x000000FF);

				grey = (int) (red*0.3 + green*0.59 + blue*0.11);
				grey = alpha | (grey<<16) | (grey<<8) | grey;
				pixels[width*i+j] = grey;
			}
		}
		//设置图片为黑白色
		//创建一张同样大小的图片
		Bitmap newBitmap = Bitmap.createBitmap(width,height,Config.ARGB_8888);
		//给图片设置颜色
		newBitmap.setPixels(pixels, 0, width,0,0,width,height);
		//返回新建图片
		return newBitmap;
	}
}
