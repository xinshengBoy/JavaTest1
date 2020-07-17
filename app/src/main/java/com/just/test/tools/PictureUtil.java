package com.just.test.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.ExifInterface;
import android.os.Environment;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Administrator on 2016/10/24 0024.
 */
public class PictureUtil {
	// 传入图片的手机路径获得图片的旋转角度
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return degree;
		}
		return degree;
	}

	// 传入bitmap和旋转角度，返回正常的图片
	public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
		if (degrees == 0 || null == bitmap) {
			return bitmap;
		}
		Matrix matrix = new Matrix();
		matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
		Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		if (null != bitmap) {
			bitmap.recycle();
		}
		return bmp;
	}

	// 把bitmap转为文件
	public static File saveFile(Bitmap bm) throws IOException {
		Random random = new Random();
		File myCaptureFile = new File(
				Environment.getExternalStorageDirectory(),
				System.currentTimeMillis() / 1000 + random.nextInt(100)
						+ random.nextInt(100) + ".png");
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
		bos.flush();
		bos.close();
		return myCaptureFile;
	}

	// 把bitmap转为文件
	public static File saveFile(Bitmap bm, String fileName) throws IOException {

		File myCaptureFile = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/com.example.photograph/cache/uil-images",
				fileName + ".png");
		if (myCaptureFile.exists()) {
			myCaptureFile.delete();
		}
		myCaptureFile.createNewFile();
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
		bos.flush();
		bos.close();
		return myCaptureFile;
	}

	// 压缩图片
	public static Bitmap decodeSampledBitmapFromResource(String pathName,
			int MyWidth, int MyHeight) throws Exception {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		/**
		 * 被赋值为true返回的Bitmap为null，虽然Bitmap是null了，但是BitmapFactory.
		 * Options的outWidth、
		 * outHeight和outMimeType属性都会被赋值。这个技巧让我们可以在加载图片之前就获取到图片的长宽值和MIME类型
		 * ，从而根据情况对图片进行压缩
		 */
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		// 得到压缩倍数。
		options.inSampleSize = calculateInSampleSize(options, MyWidth, MyHeight);
		// 设置为false，就能得到Bitmap.
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(pathName, options);

	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) throws Exception {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
		}
		return inSampleSize;
	}

	// 绘制圆角图片方法
	/**
	 * 利用BitmapShader绘制圆角图片
	 * 
	 * @param bitmap
	 *            待处理图片
	 * @param outWidth
	 *            结果图片宽度，一般为控件的宽度
	 * @param outHeight
	 *            结果图片高度，一般为控件的高度
	 * @param radius
	 *            圆角半径大小
	 * @return 结果图片
	 */
	public static Bitmap roundBitmapByShader(Bitmap bitmap, int outWidth,
			int outHeight, int radius) {
		if (bitmap == null) {
			throw new NullPointerException("Bitmap can't be null");
		}
		// 初始化缩放比
		float widthScale = outWidth * 1.0f / bitmap.getWidth();
		float heightScale = outHeight * 1.0f / bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.setScale(widthScale, heightScale);

		// 初始化绘制纹理图
		BitmapShader bitmapShader = new BitmapShader(bitmap,
				Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		// 根据控件大小对纹理图进行拉伸缩放处理
		bitmapShader.setLocalMatrix(matrix);

		// 初始化目标bitmap
		Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight,
				Bitmap.Config.ARGB_8888);

		// 初始化目标画布
		Canvas targetCanvas = new Canvas(targetBitmap);

		// 初始化画笔
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(bitmapShader);

		// 利用画笔将纹理图绘制到画布上面
		targetCanvas.drawRoundRect(new RectF(0, 0, outWidth, outWidth), radius,
				radius, paint);

		return targetBitmap;
	}
}
