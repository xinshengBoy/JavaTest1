package com.just.test.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 网络判断
 * Created by Administrator on 2017/1/19.
 */

public class NetWorkChange extends Activity {

    private TextView txt_networkchange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_networkchange);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "网络判断");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        txt_networkchange = (TextView)findViewById(R.id.txt_networkchange);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,filter);
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null){
                return;
            }

            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info == null){
                    Toast.makeText(context,"当前网络不可用",Toast.LENGTH_SHORT).show();
                    txt_networkchange.setText("当前网络为：不可用");
                }else {
                    if (info.isAvailable()){
                        int netType = info.getType();
                        if (info.isConnected()){
                            if (netType == 0){
                                txt_networkchange.setText("当前网络为：数据流量");
                                Toast.makeText(context,"当前网络为：数据流量",Toast.LENGTH_SHORT).show();
                            }else if (netType == 1){
                                txt_networkchange.setText("当前网络为：WIFI");
                                Toast.makeText(context,"当前网络为：WIFI",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        Toast.makeText(context,"当前网络不可用",Toast.LENGTH_SHORT).show();
                        txt_networkchange.setText("当前网络为：不可用");
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
