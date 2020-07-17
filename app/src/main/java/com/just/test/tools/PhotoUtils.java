package com.just.test.tools;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class PhotoUtils {
	Activity mActivity;
	public static int ALBUM = 0;// 相册
	public static int CAMERA = 1;// 相机
	public static final int TAKE_PHOTO = 3;
	public static final int CROP_PHOTO = 4;
	public static final int CHOOSE_PH = 5;
	public static final int ALBUM_CUT = 6;
	PictureCallback callback;
	static String imagePath = null;// 相册的图片地址
	static String mHeadPortraitPath = null;// //剪辑后Bitmap转为文件的地址
	private static Uri album_pic_uri;
	private static Uri cut_pic_uri;
	private static Bitmap bitMap = null;// 剪辑后的图片
	private static Uri imageUri;

	// public PhotoUtils(Activity mActivity, int what) {
	// this.mActivity = mActivity;
	// switch (what) {
	// case 0:
	// OpenAlbum();// 打开相册
	// break;
	// case 1:
	// OpenCamera();// 打开相机
	// break;
	// default:
	// break;
	// }
	//
	// }
	public static void onActivityResult(Activity act, int requestCode,
			int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case CHOOSE_PH:// 打开相册
				// 判断当前系统版本。
				if (Build.VERSION.SDK_INT >= 19) {
					// 4.4以上的系统版本使用这个方法处理。
					handleImageOnKitKat(act, data);
				} else {
					// 4.4以下的系统版本使用这个方法处理。
					handleImageBeforeKitKat(act, data);
				}
				break;
			case TAKE_PHOTO:// 打开相机之后启动剪辑
				try {
					Intent intent = new Intent("com.android.camera.action.CROP");
					intent.setDataAndType(imageUri, "image/*");
					intent.putExtra("scale", true);
					intent.putExtra("aspectX", 1);// 裁剪框比例
					intent.putExtra("aspectY", 1);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);// 剪完放在放在imageUri下。
					act.startActivityForResult(intent, CROP_PHOTO);// 启动裁剪程序。
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case CROP_PHOTO:
				try {
					bitMap = PictureUtil.decodeSampledBitmapFromResource(
							imageUri.getPath(), 500, 500);
					mHeadPortraitPath = PictureUtil.saveFile(bitMap).getPath();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case ALBUM_CUT:// 相册--剪辑图片
				try {
					bitMap = PictureUtil.decodeSampledBitmapFromResource(
							cut_pic_uri.getPath(), 500, 500);// 压缩图片
					mHeadPortraitPath = PictureUtil.saveFile(bitMap).getPath();// 剪辑后Bitmap转为文件的地址
					Log.i("test", "mHeadPortraitPath=" + mHeadPortraitPath);
					Log.i("test", "bitMap=" + mHeadPortraitPath);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}

	// 4.4以下的系统版本使用这个方法处理。
	private static void handleImageBeforeKitKat(Activity act, Intent data) {
		Uri uri = data.getData();
		imagePath = getImagePath(act, uri, null);
		displayImage(act, imagePath);// 相册--剪辑图片
	}

	// 4.4以上的系统版本使用这个方法处理。
	private static void handleImageOnKitKat(Activity act, Intent data) {
		Uri uri = data.getData();
		if (DocumentsContract.isDocumentUri(act, uri)) {
			String docId = DocumentsContract.getDocumentId(uri);
			if ("com.android.providers.media.documents".equals(uri
					.getAuthority())) {
				String id = docId.split(":")[1];
				String selection = MediaStore.Images.Media._ID + "=" + id;
				imagePath = getImagePath(act,
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
				Log.i("test", "4.4以上的imagePath=" + imagePath);
			} else if ("com.android.providers.downloads.documents".equals(uri
					.getAuthority())) {
				Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(docId));
				imagePath = getImagePath(act, contentUri, null);
				Log.i("test", "4.4以上的imagePath=" + imagePath);
			}
		} else if ("content".equalsIgnoreCase(uri.getScheme())) {
			imagePath = getImagePath(act, uri, null);
			Log.i("test", "4.4以上的imagePath=" + imagePath);
		}
		displayImage(act, imagePath);
	}

	// 拿到图片的路径，然后剪辑图片
	private static void displayImage(Activity act, String imagePath) {
		if (imagePath != null) {
			File album_pic_file = new File(imagePath);
			album_pic_uri = Uri.fromFile(album_pic_file);// 要剪辑图片的URI地址
			File cut_pic_file = new File(
					Environment.getExternalStorageDirectory(),
					"cut_pic_file.jpg");
			try {
				if (cut_pic_file.exists()) {
					cut_pic_file.delete();
				}
				cut_pic_file.createNewFile();

			} catch (Exception e) {

			}
			cut_pic_uri = Uri.fromFile(cut_pic_file);
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(album_pic_uri, "image/*");
			intent.putExtra("scale", true);
			intent.putExtra("aspectX", 1);// 裁剪框比例
			intent.putExtra("aspectY", 1);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, cut_pic_uri);// 剪完放在放在imageUri下。
			act.startActivityForResult(intent, ALBUM_CUT);// 启动裁剪程序。
		} else {
			Toast.makeText(act, "图片路径异常！", Toast.LENGTH_LONG).show();
		}
	}

	// 获取相册图片里的路径
	private static String getImagePath(Activity act, Uri uri, String selection) {
		String path = null;// 相册图片里的路径
		// getContentResolver：获得内容解析；query：查询。
		Cursor cursor = act.getContentResolver().query(uri, null, selection,
				null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				// getColumnIndex：获得列索引。
				path = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.DATA));
			}
			cursor.close();
		}
		return path;
	}

	public static void Open(Activity mActivity, int what) {
		switch (what) {
		case 0:
			OpenAlbum(mActivity);// 打开相册
			break;
		case 1:
			OpenCamera(mActivity);// 打开相机
			break;
		default:
			break;
		}
	}

	// 打开相机
	private static void OpenCamera(Activity mActivity) {
		File outputImage = new File(Environment.getExternalStorageDirectory(),
				"output_image.jpg");// Environment.getExternalStorageDirectory()获取SD卡的根目录.
		try {
			if (outputImage.exists()) {
				outputImage.delete();
			}
		} catch (Exception e) {

		}
		imageUri = Uri.fromFile(outputImage);
		Intent intent_2 = new Intent("android.media.action.IMAGE_CAPTURE");
		intent_2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		// 个人理解拍完放在imageUri下。
		mActivity.startActivityForResult(intent_2, TAKE_PHOTO);// 启动相机程序。
	}

	// 打开相册
	private static void OpenAlbum(Activity mActivity) {

		// 启动相册
		Intent intent_1 = new Intent("android.intent.action.GET_CONTENT");
		intent_1.setType("image/*");
		mActivity.startActivityForResult(intent_1, CHOOSE_PH);// 打开相册。
	}

	// public static void pushimagepath(PictureCallback callback) {
	// if (callback != null && imagePath != null) {
	// callback.imagePath(imagePath);
	// }
	// }
	//
	// public static void pushheadportraitpath(PictureCallback callback) {
	// if (callback != null && mHeadPortraitPath != null) {
	// callback.HeadPortraitPath(mHeadPortraitPath);
	// }
	// }

	public static void pushbitmap(PictureCallback callback) {
		if (callback != null) {
			callback.imagePath(imagePath);
			callback.HeadPortraitPath(mHeadPortraitPath);
			callback.Bitmap(bitMap);
		}
	}

	// 定义接口
	public interface PictureCallback {
		public void imagePath(String imagepath);// 相册图片里的路径

		public void HeadPortraitPath(String mHeadPortraitPath); // 剪辑后Bitmap转为文件的地址

		public void Bitmap(Bitmap bitMap);// // 剪辑后的图片
	}
}
