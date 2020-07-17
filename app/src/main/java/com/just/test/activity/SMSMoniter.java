package com.just.test.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 短信监听
 * Created by Administrator on 2017/2/19.
 */

public class SMSMoniter extends Activity {

//    private TextView txt_smsmoniter_number,txt_smsmoniter_content,txt_smsmoniter_time;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_smsmoniter);
//
//        //// TODO: 2016/12/21 actionbar
//        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("back", true);
//        bundle.putString("leftText", null);
//        bundle.putString("title", "短信监听");
//        bundle.putBoolean("rightImage", false);
//        bundle.putString("rightText", null);
//        MyActionBar.actionbar(this,headerLayout,bundle);
//
//        txt_smsmoniter_number = (TextView)findViewById(R.id.txt_smsmoniter_number);
//        txt_smsmoniter_content = (TextView)findViewById(R.id.txt_smsmoniter_content);
//        txt_smsmoniter_time = (TextView)findViewById(R.id.txt_smsmoniter_time);

//        IntentFilter mFilter = new IntentFilter();//注册广播进行监听webview
//        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(smsReceiver,mFilter);
//    }

//    public BroadcastReceiver smsReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            SmsMessage msg = null;
//            Bundle bundle = intent.getExtras();
//            if (bundle != null){
//                Object [] pdusObj = (Object[]) bundle.get("pdus");
//                if (pdusObj != null && pdusObj.length != 0) {
//                    for (Object p : pdusObj) {
//                        msg = SmsMessage.createFromPdu((byte[]) p);
//                        String smsBody = msg.getMessageBody();
//                        String number = msg.getOriginatingAddress();
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String time = format.format(new Date(msg.getTimestampMillis()));
//
//                        txt_smsmoniter_number.setText(number);
//                        txt_smsmoniter_content.setText(smsBody);
//                        txt_smsmoniter_time.setText(time);
//                    }
//                }
//            }
//        }
//    };
//    public class SMSBroadcastReceiver extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            SmsMessage msg = null;
//            Bundle bundle = intent.getExtras();
//            if (bundle != null){
//                Object [] pdusObj = (Object[]) bundle.get("pdus");
//                for (Object p : pdusObj){
//                    msg = SmsMessage.createFromPdu((byte []) p);
//                    String smsBody = msg.getMessageBody();
//                    String number = msg.getOriginatingAddress();
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String time = format.format(new Date(msg.getTimestampMillis()));
//
//                    txt_smsmoniter_number.setText(number);
//                    txt_smsmoniter_content.setText(smsBody);
//                    txt_smsmoniter_time.setText(time);
//                }
//            }
//        }
//    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(smsReceiver);
//    }
}
