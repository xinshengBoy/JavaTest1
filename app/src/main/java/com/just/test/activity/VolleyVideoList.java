package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 引用上证期权视频列表数据 图片和文字的volley请求
 *
 * @author user
 *
 */
public class VolleyVideoList extends Activity {

	private ListView list_volleyVideoList;
	private RequestQueue mQueue;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private String videoURL = "http://mb.sseinfo.com/ComInfoServer/getSOAInfocontroller.do?method=optionVideo";
	private List<Bundle> mList = new ArrayList<Bundle>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_volleyvideo);

		mQueue = Volley.newRequestQueue(VolleyVideoList.this);
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(VolleyVideoList.this));

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon) // resource or drawable
				.showImageForEmptyUri(R.drawable.icon) // resource or drawable
				.showImageOnFail(R.drawable.icon) // resource or drawable
				.cacheInMemory(true).cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		list_volleyVideoList = (ListView) findViewById(R.id.list_volleyVideoList);
		sendRequest();
	}

	private void sendRequest() {
		StringRequest request = new StringRequest(videoURL,
				new Response.Listener<String>() {

					public void onResponse(String response) {
						JSONArray array;
						try {
							//json数组里面又包含json数组
							JSONArray jsonArray = new JSONArray(response.toString());
							for (int k = 0; k < jsonArray.length(); k++) {
								JSONObject objects = jsonArray.optJSONObject(k);
								JSONObject value = objects.optJSONObject("value");
								array = value.optJSONArray("list");
								for (int i = 0; i < array.length(); i++) {
									Bundle bundle = new Bundle();
									JSONObject object = array.getJSONObject(i);
									bundle.putString("createTime",object.getString("createTime"));// 创建时间
									bundle.putString("docTitle",object.getString("docTitle"));// 标题
									bundle.putString("docImage",object.getString("extSSE_YJCB_SFJB"));// 图片
									bundle.putString("docVideo",object.getString("extStarttest_link"));// 视频链接

									mList.add(bundle);
								}

							}

						} catch (JSONException e) {
							e.printStackTrace();
						}

						handler.sendEmptyMessage(0);
					}
				}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(VolleyVideoList.this,"网络请求错误：" + error.toString(), Toast.LENGTH_LONG).show();
			}
		});

		mQueue.add(request);

//		new Thread(){
//			@Override
//			public void run() {
//				//拼接自己需要的参数，
//				try {
//				HttpClient httpClient = new DefaultHttpClient();
//				httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
//				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
//				HttpGet get = new HttpGet(videoURL);
//
//					HttpResponse httpResponse = httpClient.execute(get);
//					HttpEntity entity = httpResponse.getEntity();
//					if (entity != null){
//						BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));//注意编码的设置
//						String line = null;
//
//						while ((line = br.readLine()) != null){
//							Message msg = new Message();
//							msg.what = 0;
//							msg.obj = line;
//							handler.sendMessage(msg);
//						}
//					}
//				} catch(ConnectTimeoutException e){
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}.start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
//				String result = (String) msg.obj;
//				JSONArray array;
//				try {
//					//json数组里面又包含json数组
//					JSONArray jsonArray = new JSONArray(result);
//					for (int k = 0; k < jsonArray.length(); k++) {
//						JSONObject objects = jsonArray.optJSONObject(k);
//						JSONObject value = objects.optJSONObject("value");
//						array = value.optJSONArray("list");
//						for (int i = 0; i < array.length(); i++) {
//							Bundle bundle = new Bundle();
//							JSONObject object = array.getJSONObject(i);
//							bundle.putString("createTime",object.getString("createTime"));// 创建时间
//							bundle.putString("docTitle",object.getString("docTitle"));// 标题
//							bundle.putString("docImage",object.getString("extSSE_YJCB_SFJB"));// 图片
//							bundle.putString("docVideo",object.getString("extStarttest_link"));// 视频链接
//							
//							mList.add(bundle);
//						}
//						
//					}
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}

				VolleyVideoAdapter adapter = new VolleyVideoAdapter(VolleyVideoList.this, mList);
				list_volleyVideoList.setAdapter(adapter);
				list_volleyVideoList.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
						Intent intent = new Intent(VolleyVideoList.this,VideoContent.class);
						Bundle bundle = new Bundle();
						bundle.putString("docVideo", mList.get(position).getString("docVideo"));
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
			}
		};
	};

	private class VolleyVideoAdapter extends BaseAdapter{

		private List<Bundle> list;
		private Context context;

		public VolleyVideoAdapter(Context context,List<Bundle> list){
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHoder hoder;
			if (convertView == null) {
				hoder = new ViewHoder();
				convertView = LayoutInflater.from(context).inflate(R.layout.layout_volleyvideo_item, null);
				hoder.iv_videolist = (ImageView)convertView.findViewById(R.id.iv_videolist);
				hoder.txt_volleylist = (TextView)convertView.findViewById(R.id.txt_volleylist);
				convertView.setTag(hoder);
			}else {
				hoder = (ViewHoder) convertView.getTag();
			}

			hoder.txt_volleylist.setText(list.get(position).getString("docTitle"));
			String imageUrl = list.get(position).getString("docImage");
			ImageView imageView = hoder.iv_videolist;
			imageView.setTag(imageUrl);
//            holder.round.setVisibility(View.VISIBLE);
			imageLoader.displayImage(imageUrl, imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					super.onLoadingComplete(imageUri, view, loadedImage);
				}
			});
			imageView.setTag(imageUrl);
			return convertView;
		}

		private class ViewHoder{
			ImageView iv_videolist;
			TextView txt_volleylist;
		}
	}

}
