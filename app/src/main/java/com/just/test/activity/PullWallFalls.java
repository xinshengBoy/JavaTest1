package com.just.test.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;

/**
 * 瀑布流
 * @author user
 *
 */
public class PullWallFalls extends Activity implements OnClickListener{

	private int imageWidth;
	private List<String> imagePath = new ArrayList<String>();// 图片文件的路径
	private static String[] imageFormatSet = new String[] { "jpg", "png", "gif" };// 合法的图片文件格式
	private LinearLayout layout_cache,layout_image1,layout_image2,layout_image3;
	private boolean isItem = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_pullwallfalls);

		Display display = this.getWindowManager().getDefaultDisplay();
		imageWidth = display.getWidth()/3;
		String sdpath="/sdcard/my/照片/";//获得SD卡的路径
		getFiles(sdpath);

		findView();
	}

	private void findView() {
		layout_cache = (LinearLayout)this.findViewById(R.id.layout_cache);
		layout_image1 = (LinearLayout)this.findViewById(R.id.layout_image1);
		layout_image2 = (LinearLayout)this.findViewById(R.id.layout_image2);
		layout_image3 = (LinearLayout)this.findViewById(R.id.layout_image3);

		LayoutParams lp1 = layout_image1.getLayoutParams();
		lp1.width = imageWidth;
		layout_image1.setLayoutParams(lp1);

		LayoutParams lp2 = layout_image2.getLayoutParams();
		lp2.width = imageWidth;
		layout_image2.setLayoutParams(lp2);

		LayoutParams lp3 = layout_image3.getLayoutParams();
		lp3.width = imageWidth;
		layout_image3.setLayoutParams(lp3);

		int j = 0;
		for (int i = 0; i < imagePath.size(); i++) {
			addImageToCache(imagePath.get(i),j);
			j++;
			if (j == 3) {
				j = 0;
			}
		}


	}

	private void addImageToCache(String fileName,int j) {
		ImageView iv = (ImageView) LayoutInflater.from(this).inflate(R.layout.layout_pullwallfall_item, null);
		Bitmap bitmap = BitmapFactory.decodeFile(fileName);
		iv.setImageBitmap(bitmap);
		if (j == 0) {
			layout_image1.addView(iv);
		}else if (j == 1) {
			layout_image2.addView(iv);
		}else {
			layout_image3.addView(iv);
		}

		layout_image1.setOnClickListener(this);
		layout_image2.setOnClickListener(this);
		layout_image3.setOnClickListener(this);

//		iv.setTag(fileName);
//		Drawable drawable = DrawableManager.getInstance().fetchDrawableOnThread(fileName, null, new ImageCallback() {
//
//			@Override
//			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
//				ImageView view = (ImageView) layout_cache.findViewWithTag(imageUrl);
//				if (view != null && imageDrawable != null) {
//					int oldWidth = imageDrawable.getIntrinsicWidth();
//					int oldHeight = imageDrawable.getIntrinsicHeight();
//					LayoutParams lp = view.getLayoutParams();
//					lp.height = (oldHeight*imageWidth)/oldWidth;
//					view.setPadding(0, 2, 0, 0);
//					view.setLayoutParams(lp);
//					view.setImageDrawable(imageDrawable);
//				}
//			}
//		});
//
//		if (drawable != null) {
//			int oldWidth = drawable.getIntrinsicWidth();
//			int oldHeight = drawable.getIntrinsicHeight();
//			LayoutParams lp = iv.getLayoutParams();
//			lp.height = (oldHeight*imageWidth)/oldWidth;
//			iv.setPadding(0, 2, 0, 0);
//			iv.setLayoutParams(lp);
//			iv.setImageDrawable(drawable);
//		}
	}

	/*
	 * 方法:用于遍历指定路径 参数:String url遍历路径 无返回值
	 */
	private void getFiles(String url) {
		File files = new File(url);// 创建文件对象
		File[] file = files.listFiles();
		try {
			for (File f : file) {// 通过for循环遍历获取到的文件数组
				if (f.isDirectory()) {// 如果是目录，也就是文件夹
					getFiles(f.getAbsolutePath());// 递归调用
				} else {
					if (isImageFile(f.getPath())) {// 如果是图片文件
						imagePath.add(f.getPath());// 将文件的路径添加到List集合中
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();// 输出异常信息
		}
	}
	/*
	 * 方法:判断是否为图片文件 参数:String path图片路径 返回:boolean 是否是图片文件，是true，否false
	 */
	private static boolean isImageFile(String path) {
		for (String format : imageFormatSet) {// 遍历数组
			if (path.contains(format)) {// 判断是否为合法的图片文件
				return true;
			}
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		isItem = true;
		if (isItem) {
			ImageView imageView = new ImageView(this);
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath.get(3));
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
			layout_cache.setVisibility(View.GONE);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			addContentView(imageView,params);
		}


	}
}
