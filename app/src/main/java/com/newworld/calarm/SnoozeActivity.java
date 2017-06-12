package com.newworld.calarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.newworld.calarm.MainActivity.dbManager;

/**
 * Created by 이은솔 on 2017-05-24.
 */

public class SnoozeActivity extends AppCompatActivity implements View.OnClickListener{

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
                this.finish();
                break;
        }
    }

    private void turnOffAlarm(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH,MM");
        String formatTime = sdfNow.format(date);
        String[] time = formatTime.split(",");

        Log.d("SnoozeActivity","알람 종료 시작");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        int id = dbManager.findIdentifier(time[0],time[1]);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(pendingIntent != null){
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            //DB건드리는 내용도 가져와야함
            Log.d("SnoozeActivity","알람 종료 완료");
        }
    }
}
