package com.newworld.calarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by 이은현 on 2017-05-23.
 */

public class AlarmReceiver extends BroadcastReceiver {
    //알람을 받으면 하는 행동
    @Override
    public void onReceive(Context context, Intent intent) {
        //NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        //AlarmDataManager.getInstance().setAlarmEnabled(context, false);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setTicker("hi");
        mBuilder.setContentTitle("hi1");
        mBuilder.setContentText("hi3");

        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        //mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            nm.notify(0, mBuilder.build());//0대신 111이였음
        }else{
            nm.notify(0, mBuilder.getNotification());
        }
    }
}
