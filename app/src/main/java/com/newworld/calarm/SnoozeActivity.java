package com.newworld.calarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

import static com.newworld.calarm.MainActivity.dbManager;

/**
 * Created by 이은솔 on 2017-05-24.
 */

public class SnoozeActivity extends AppCompatActivity implements View.OnClickListener{
    private String[] time;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);

        findViewById(R.id.offButton).setOnClickListener(this);
        findViewById(R.id.snoozeButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.offButton:
                Log.d("offButton", "알람종료버튼");
                turnOffAlarm();
                this.finish();
                break;
            case R.id.snoozeButton:
                Log.d("snoozeButton", "5분뒤 알람");
                turnOffAlarm();
                snoozeAlarm(1000*60*5);
                this.finish();
                break;
        }
    }

    private void turnOffAlarm(){
        //알람 종료창이 떳을때 시간 가져오기
        /*
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH,MM");
        String formatTime = sdfNow.format(date);
        time = formatTime.split(",");
        Log.d("SnoozeActivity(turnOff)",formatTime);
        */
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        Log.d("SnoozeActivity(turnOff)",hour+":"+minute);

        Log.d("SnoozeActivity","알람 종료 시작");
        Intent intent = new Intent(this, MainActivity.class);
        //Intent intent = new Intent(this, AlarmReciever.class);
        //int id = dbManager.findIdentifier(time[0],time[1]);
        id = dbManager.findIdentifier(""+hour,""+minute);
        Log.d("SnoozeActivity","id: "+id);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        if(pendingIntent != null){
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            //DB건드리는 내용도 가져와야함
            Log.d("SnoozeActivity","알람 종료 완료");
        }
    }

    private void snoozeAlarm(int time) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis()+time, pendingIntent);

        String phone = dbManager.findPhoneNUmber(id);
        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
        try{
            startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
