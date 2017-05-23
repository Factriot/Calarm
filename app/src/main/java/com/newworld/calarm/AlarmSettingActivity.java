package com.newworld.calarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import java.util.GregorianCalendar;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class AlarmSettingActivity extends AppCompatActivity implements View.OnClickListener{

    // 알람 메니저
    private AlarmManager alarmManager;
    // 설정 일시
    private GregorianCalendar gregorianCalendar;
    //일자 설정
    private DatePicker datePicker;
    //시작 설정
    private TimePicker timeManager;
    //통지 메니저
    private NotificationManager notificationManager;

    //반복 알람인지
    private boolean isRepeat = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        //제어권 가져오기
        //notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //gregorianCalendar = new GregorianCalendar();

        Log.d("AlarmSettingActivity","진입");
        findViewById(R.id.cancelButton).setOnClickListener(this);
        findViewById(R.id.okButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.cancelButton:
                this.finish();
                break;
            case R.id.okButton:
                setAlarm(this);
                break;
        }
    }

    //알람 등록
    //에러있음~!!!
    private void setAlarm(Context context) {
        Log.d("setAlarm", "setAlarm()");
        //버전별로 메써드가 달라서 다르게 설정시킴
        timeManager = (TimePicker)findViewById(R.id.timePicker);
        int hour, minute;
        if (Build.VERSION.SDK_INT >= 23 ){
            timeManager.setHour(gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY));
            timeManager.setMinute(gregorianCalendar.get(GregorianCalendar.MINUTE));
            //timeManager.setOnTimeChangedListener(timeManager, timeManager.getHour(), timeManager.getMinute());
            hour = timeManager.getHour();
            minute = timeManager.getMinute();
        }else{
            timeManager.setCurrentHour(gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY));
            timeManager.setCurrentMinute(gregorianCalendar.get(GregorianCalendar.MINUTE));
            //timeManager.setOnTimeChangedListener(timeManager, timeManager.getCurrentHour(), timeManager.getCurrentMinute());
            hour = timeManager.getCurrentHour();
            minute = timeManager.getCurrentMinute();
        }

        long alarmTime = (long)hour*(long)minute*60*1000;
        long currentTime = System.currentTimeMillis();
        long second = 0;
        long temp = alarmTime;
        if(alarmTime > currentTime){
            second = alarmTime - currentTime;
        }else if(alarmTime < currentTime){
            second = 24*60*60*1000 + alarmTime - currentTime;
        }else{
            second = 24*60*60*1000;
        }

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);//context에서 AlarmReceiver로 직접 전해줌
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);//두번째 인자가 알람의 식별번호임

        alarmManager.set(AlarmManager.RTC_WAKEUP, temp, pendingIntent);//원래는 temp대신 System.currentTimeMillis()+second
    }
}
