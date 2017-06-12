package com.newworld.calarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by 이은현 on 2017-05-23.
 */

public class AlarmReceiver extends BroadcastReceiver {
    //Log.d("AlarmReceiver","알람 리시버");
    //알람을 받으면 하는 행동
    @Override
    public void onReceive(Context context, Intent intent) {
        //NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        //AlarmDataManager.getInstance().setAlarmEnabled(context, false);

        //진동 울리기
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 1000, 500, 1000, 500};
        vibrator.vibrate(pattern, -1);//일단 진동은 한번만
        //vibrator.cancel()은 새로 띄운 창에서!

        //receiver정상작동 확인
        Toast.makeText(context, "hi, there", Toast.LENGTH_LONG).show();

        try {
            intent = new Intent(context, SnoozeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            pendingIntent.send();
        } catch (PendingIntent.CanceledException ex) {
            ex.printStackTrace();
        }

        /*
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
        */
    }
}
