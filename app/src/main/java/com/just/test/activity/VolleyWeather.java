package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.network.XMLRequest;
import com.just.test.tools.BitmapCache;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Volley天气请求
 * volley imagerequest
 *
 * @author user
 */
public class VolleyWeather extends Activity implements OnClickListener {
    private ListView list_volleyWeather;
    private List<Bundle> mList = new ArrayList<>();
    private final String xmlUrl = "http://flash.weather.com.cn/wmaps/xml/china.xml";
    private ImageView iv_volleyImageRequest, iv_volleyImageLoader;
    private TextView txt_requestTextview;
    private Button btn_imageRequest, btn_imageLoader, btn_xmlRequest;
    private final String imageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495518782659&di=25b120262114749ae8543652d2de5715&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F160316%2F9-160316152R5.jpg";
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_volley_weather);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "Volley天气请求");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        mQueue = Volley.newRequestQueue(VolleyWeather.this);

        btn_imageRequest = (Button) findViewById(R.id.btn_imageRequest);
        btn_imageLoader = (Button) findViewById(R.id.btn_imageLoader);
        btn_xmlRequest = (Button) findViewById(R.id.btn_xmlRequest);
        iv_volleyImageRequest = (ImageView) findViewById(R.id.iv_volleyImageRequest);
        iv_volleyImageLoader = (ImageView) findViewById(R.id.iv_volleyImageLoader);
        txt_requestTextview = (TextView) findViewById(R.id.txt_requestTextview);
        list_volleyWeather = (ListView) findViewById(R.id.list_volleyWeather);

        btn_imageRequest.setOnClickListener(this);
        btn_imageLoader.setOnClickListener(this);
        btn_xmlRequest.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btn_imageRequest) {
            initImageRequest();
        } else if (v == btn_imageLoader) {
            initImageLoader();
        } else if (v == btn_xmlRequest) {
            initXMLRequest();
        }
    }

    /**
     * volley获取图片
     */
    private void initImageRequest() {
        ImageRequest imageRequest = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv_volleyImageRequest.setImageBitmap(response);
            }
        }, 0, 0, Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iv_volleyImageRequest.setImageResource(R.drawable.icon);//获取失败的时候显示默认图片
            }
        });
        mQueue.add(imageRequest);
    }

    /**
     * volley获取图片并带缓存机制
     */
    private void initImageLoader() {
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                // TODO Auto-generated method stub
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        //通过url加载图片
        ImageListener listener = ImageLoader.getImageListener(iv_volleyImageLoader, R.drawable.icon_app, R.drawable.icon_pw01);//要显示的控件，默认显示的图片，网络错误显示的图片
        imageLoader.get(imageUrl, listener);
    }

    /**
     * xml返回解析
     */
    private void initXMLRequest() {
        XMLRequest request = new XMLRequest(xmlUrl, new Response.Listener<XmlPullParser>() {

            @Override
            public void onResponse(XmlPullParser response) {
                try {
                    int eventType = response.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                String nodeName = response.getName();
                                if ("city".equals(nodeName)) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("sheng", response.getAttributeValue(0));//省或地区
                                    bundle.putString("puName", response.getAttributeValue(1));//地区拼音
                                    bundle.putString("cityName", response.getAttributeValue(2));//城市名称
                                    bundle.putString("weather", response.getAttributeValue(5));//天气情况
                                    bundle.putString("highT", response.getAttributeValue(6));//最高温度
                                    bundle.putString("lowT", response.getAttributeValue(7));//最低温度
                                    bundle.putString("wind", response.getAttributeValue(8));//风向

                                    mList.add(bundle);
                                }
                                break;
                        }
                        eventType = response.next();
                    }
                    handler.sendEmptyMessage(0);
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txt_requestTextview.setText(error.getMessage());
            }
        });
        mQueue.add(request);
    }

    /**
     * xml天气适配器
     */
    private class VolleyWeatherAdapter extends CommonAdapter<Bundle> {

        private VolleyWeatherAdapter(Context context, List<Bundle> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Bundle item) {
            TextView txt_cityName = helper.getView(R.id.txt_cityName);
            TextView txt_weather = helper.getView(R.id.txt_weather);
            TextView txt_temperature = helper.getView(R.id.txt_temperature);
            TextView txt_vane = helper.getView(R.id.txt_vane);

            txt_cityName.setText(item.getString("sheng") + item.getString("cityName"));
            txt_weather.setText(item.getString("weather"));
            txt_temperature.setText(item.getString("highT") + "~" + item.getString("lowT"));
            txt_vane.setText(item.getString("wind"));
        }

    }

    private final Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                VolleyWeatherAdapter adapter = new VolleyWeatherAdapter(VolleyWeather.this, mList, R.layout.layout_volley_weather_item);
                list_volleyWeather.setAdapter(adapter);
            }
        }
    };
}
