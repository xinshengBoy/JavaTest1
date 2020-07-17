package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * 根据GPS获取实时经纬度，并通过网络查询当前的文字位置
 * Created by admin on 2017/4/27.
 */
@SuppressWarnings("MissingPermission")
public class NowLocation extends Activity {

    private EditText et_nowlocation;
    private TextView txt_nowlocation1, txt_nowlocation2;
    private int count = 3000;
    private LocationManager manager;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nowlocation);
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "实时位置");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        et_nowlocation = (EditText) findViewById(R.id.et_nowlocation);
        Button btn_nowlocation = (Button) findViewById(R.id.btn_nowlocation);
        txt_nowlocation1 = (TextView) findViewById(R.id.txt_nowlocation1);
        txt_nowlocation2 = (TextView) findViewById(R.id.txt_nowlocation2);

        btn_nowlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = et_nowlocation.getText().toString();
                int times = Integer.parseInt(time);
                if (times < 3) {
                    Toast.makeText(NowLocation.this, "频率过快", Toast.LENGTH_SHORT).show();
                    return;
                }
                count = times * 1000;
                handler.sendEmptyMessage(0);
            }
        });
        //位置管理
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //判断网络是否可用
        if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            handler.sendEmptyMessage(0);
        } else {
            Toast.makeText(NowLocation.this, "网络暂不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        //从GPS获取最近的定位信息
//        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        updateView(location);
//
//        handler.sendEmptyMessage(0);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, count, 1, listener);
//                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,count,1,listener);
            } else if (msg.what == 1) {
                Bundle bundle = msg.getData();
                txt_nowlocation2.setText("当前位置为：" + bundle.getString("address"));
            }
        }
    };
    protected final LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
            //解除监听
            manager.removeUpdates(listener);
            updateView(mLocation);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            String a = "aaaaa";
        }

        @Override
        public void onProviderEnabled(String s) {
            updateView(manager.getLastKnownLocation(s));
        }

        @Override
        public void onProviderDisabled(String s) {
            updateView(null);
        }
    };

    private void updateView(Location location) {
        if (location != null) {
            double jingdu = location.getLongitude();
            double weidu = location.getLatitude();

            txt_nowlocation1.setText("当前经纬度：精度=" + jingdu + ",维度=" + weidu);
            String url = "http://maps.google.com/maps/api/geocode/json?latlng=" + weidu + "," + jingdu + "&language=zh-CN&sensor=true";

            OkHttpClient okHttpClient = new OkHttpClient();
            final okhttp3.Request request = new okhttp3.Request.Builder().url(
                    url).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("okhttp", "okhttp" + e);
                }

                @Override
                public void onResponse(Call call, final Response response)
                        throws IOException {
                    try {
                        // 注意：这里面不能做任何视图的处理
                        JSONObject object = new JSONObject(response.body().string());
                        JSONArray array = object.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object1 = array.getJSONObject(i);
                            String address = object1.getString("formatted_address");
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("address", address);
                            message.setData(bundle);
                            message.what = 1;
                            handler.sendMessage(message);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocation != null) {
            mLocation = null;
        }
        if (manager != null) {
            manager = null;
        }
    }
}
