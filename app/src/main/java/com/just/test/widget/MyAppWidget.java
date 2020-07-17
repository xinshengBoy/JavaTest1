
package com.just.test.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.just.test.R;

/**
 * 创建桌面小部件
 * Created by admin on 2017/7/12.
 */

public class MyAppWidget extends AppWidgetProvider {

    private final String ACTION_BUTTON = "action_button";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent == null){
            return;
        }

        String action = intent.getAction();
        if (action.equals(ACTION_BUTTON)){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_test);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);//实例化
            ComponentName componentName = new ComponentName(context,MyAppWidget.class);
            manager.updateAppWidget(componentName,remoteViews);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_test);
        remoteViews.setOnClickPendingIntent(R.id.appwidget_layout,pendingIntent);
        appWidgetManager.updateAppWidget(R.id.appwidget_layout,remoteViews);
    }
}
