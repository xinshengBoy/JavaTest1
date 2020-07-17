package com.just.test.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * android 通知呼吸灯
 * Created by admin on 2017/6/6.
 */

public class BreathLight extends Activity {

    final int ID_LED = 0x123;
    private NotificationManager mNotificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_breathlight);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "android 通知呼吸灯");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        Button btn_breathLight_click = (Button)findViewById(R.id.btn_breathLight_click);
        btn_breathLight_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent = new Intent(BreathLight.this,BreathLight.class);
                PendingIntent pi = PendingIntent.getActivity(BreathLight.this,0,intent,0);
                Notification notification = new Notification.Builder(BreathLight.this)
                        .setAutoCancel(true)
                        .setTicker("有新消息")
                        .setSmallIcon(R.drawable.wechat_icon)
                        .setContentTitle("一条新通知")
                        .setContentText("明天要下雨")
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pi).build();
                manager.notify(ID_LED,notification);
//                sendNotificatuion();
            }
        });
    }

    private void sendNotificatuion(){
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) getSystemService(Activity.NOTIFICATION_SERVICE);
        }
        mNotificationManager.cancel(ID_LED);
        Notification notice = new Notification();
        notice.ledARGB= 0xffC80101;
        notice.ledOnMS = 1;
        notice.ledOffMS = 0;
        notice.flags |= Notification.FLAG_SHOW_LIGHTS;
//        Intent intent = new Intent();
//        PendingIntent des = PendingIntent.getActivity(this,
//                0, intent,
//                PendingIntent.FLAG_CANCEL_CURRENT);
//        notice.setLatestEventInfo(this, null, null, des);
        mNotificationManager.notify(ID_LED, notice);
    }
}
