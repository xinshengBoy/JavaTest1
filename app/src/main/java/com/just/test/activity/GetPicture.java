package com.just.test.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 获取手机所有图片
 *
 * @author user
 *
 */
public class GetPicture extends Activity implements OnScrollListener{

	private GridView gridview_picture;
	private List<String> imagePath = new ArrayList<String>();// 图片文件的路径
	private static String[] imageFormatSet = new String[] { "jpg", "png", "gif" };// 合法的图片文件格式
	public static Map<String,Bitmap> gridviewBitmapCaches = new HashMap<String,Bitmap>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_getpicture);

		gridview_picture = (GridView) findViewById(R.id.gridview_picture);
		String sdpath="/sdcard/pptv/emoji/image/";//获得SD卡的路径
		getFiles(sdpath);//调用getFiles()方法获取SD卡上的全部图片
		Toast.makeText(GetPicture.this, imagePath.size(), Toast.LENGTH_LONG).show();
		if(imagePath.size()<1){//如果不存在文件图片
			return;
		}
		GetPictureAdapter adapter = new GetPictureAdapter(GetPicture.this, imagePath, R.layout.layout_getpicture_item);
		gridview_picture.setAdapter(adapter);

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

	class GetPictureAdapter extends CommonAdapter<String>{

		public GetPictureAdapter(Context context, List<String> mDatas,
								 int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, String item) {
			ImageView iv_getPicture = helper.getView(R.id.iv_getPicture);
			iv_getPicture.setAdjustViewBounds(true);
			iv_getPicture.setMaxWidth(150);
			iv_getPicture.setMaxHeight(113);
			iv_getPicture.setPadding(5, 5, 5, 5);//设置ImageView的内边距

			//首先我们先通过cancelPotentialLoad方法去判断imageview是否有线程正在为它加载图片资源，
			//如果有现在正在加载，那么判断加载的这个图片资源（url）是否和现在的图片资源一样，不一样则取消之前的线程（之前的下载线程作废）。
			//见下面cancelPotentialLoad方法代码
			if (cancelPotentialLoad(item, iv_getPicture)) {
				AsyncLoadImageTask task = new AsyncLoadImageTask(iv_getPicture);
				LoadedDrawable loadedDrawable = new LoadedDrawable(task);
				iv_getPicture.setImageDrawable(loadedDrawable);
				task.execute(helper.getPosition());
			}

//			try {
//				Bitmap bm=BitmapFactory.decodeFile(item);
//				iv_getPicture.setImageBitmap(bm);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}

		}

	}

	//释放图片的函数
	private void recycleBitmapCaches(int fromPosition,int toPosition){
		Bitmap delBitmap = null;
		for(int del=fromPosition;del<toPosition;del++){
			delBitmap = gridviewBitmapCaches.get(imagePath.get(del));
			if(delBitmap != null){
				//如果非空则表示有缓存的bitmap，需要清理
				//从缓存中移除该del->bitmap的映射
				gridviewBitmapCaches.remove(imagePath.get(del));
				delBitmap.recycle();
				delBitmap = null;
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
						 int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		//注释：firstVisibleItem为第一个可见的Item的position，从0开始，随着拖动会改变
		//visibleItemCount为当前页面总共可见的Item的项数
		//totalItemCount为当前总共已经出现的Item的项数
		recycleBitmapCaches(0,firstVisibleItem);
		recycleBitmapCaches(firstVisibleItem+visibleItemCount, totalItemCount);

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
	}

	private Bitmap getBitmapFromUrl(String url){
		Bitmap bitmap = null;
		bitmap = gridviewBitmapCaches.get(url);
		if(bitmap != null){
			System.out.println(url);
			return bitmap;
		}
		url = url.substring(0, url.lastIndexOf("/"));//处理之前的路径区分，否则路径不对，获取不到图片

		//bitmap = BitmapFactory.decodeFile(url);
		//这里我们不用BitmapFactory.decodeFile(url)这个方法
		//用decodeFileDescriptor()方法来生成bitmap会节省内存
		//查看源码对比一下我们会发现decodeFile()方法最终是以流的方式生成bitmap
		//而decodeFileDescriptor()方法是通过Native方法decodeFileDescriptor生成bitmap的

		try {
			FileInputStream is = new FileInputStream(url);
			bitmap = BitmapFactory.decodeFileDescriptor(is.getFD());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		bitmap = BitmapUtilities.getBitmapThumbnail(bitmap,120,150);
		return bitmap;
	}

	//加载图片的异步任务
	private class AsyncLoadImageTask extends AsyncTask<Integer, Void, Bitmap>{
		private String url = null;
		private final WeakReference<ImageView> imageViewReference;

		public AsyncLoadImageTask(ImageView imageview) {
			super();
			// TODO Auto-generated constructor stub
			imageViewReference = new WeakReference<ImageView>(imageview);
		}

		@Override
		protected Bitmap doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			Bitmap bitmap = null;
			this.url = imagePath.get(params[0]);
			bitmap = getBitmapFromUrl(url);
			gridviewBitmapCaches.put(imagePath.get(params[0]), bitmap);
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap resultBitmap) {
			// TODO Auto-generated method stub
			if(isCancelled()){
				resultBitmap = null;
			}
			if(imageViewReference != null){
				ImageView imageview = imageViewReference.get();
				AsyncLoadImageTask loadImageTask = getAsyncLoadImageTask(imageview);
				if (this == loadImageTask) {
					imageview.setImageBitmap(resultBitmap);
					imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				}
			}
			super.onPostExecute(resultBitmap);
		}
	}


	private boolean cancelPotentialLoad(String url,ImageView imageview){
		AsyncLoadImageTask loadImageTask = getAsyncLoadImageTask(imageview);

		if (loadImageTask != null) {
			String bitmapUrl = loadImageTask.url;
			if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
				loadImageTask.cancel(true);
			} else {
				// 相同的url已经在加载中.
				return false;
			}
		}
		return true;

	}

	//当 loadImageTask.cancel(true)被执行的时候，则AsyncLoadImageTask 就会被取消，
	//当AsyncLoadImageTask 任务执行到onPostExecute的时候，如果这个任务加载到了图片，
	//它也会把这个bitmap设为null了。
	//getAsyncLoadImageTask代码如下：
	private AsyncLoadImageTask getAsyncLoadImageTask(ImageView imageview){
		if (imageview != null) {
			Drawable drawable = imageview.getDrawable();
			if (drawable instanceof LoadedDrawable) {
				LoadedDrawable loadedDrawable = (LoadedDrawable)drawable;
				return loadedDrawable.getLoadImageTask();
			}
		}
		return null;
	}

	//该类功能为：记录imageview加载任务并且为imageview设置默认的drawable
	public static class LoadedDrawable extends ColorDrawable{
		private final WeakReference<AsyncLoadImageTask> loadImageTaskReference;

		public LoadedDrawable(AsyncLoadImageTask loadImageTask) {
			super(Color.TRANSPARENT);
			loadImageTaskReference =
					new WeakReference<AsyncLoadImageTask>(loadImageTask);
		}

		public AsyncLoadImageTask getLoadImageTask() {
			return loadImageTaskReference.get();
		}

	}
}
