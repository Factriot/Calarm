package com.newworld.calarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmSettingActivity extends AppCompatActivity implements View.OnClickListener{

    // 알람 메니저
    private static AlarmManager alarmManager = null;
    private static PendingIntent pendingIntent = null;
    // 설정 일시
    private GregorianCalendar gregorianCalendar;
    //일자 설정
    private DatePicker datePicker;
    //시작 설정
    private TimePicker timeManager;
    //통지 메니저
    private NotificationManager notificationManager;

    //반복 알람 여부
    private boolean isRepeat = false;
    //진동 On/Off
    private  boolean isVibrate = true;

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


        //여기서도 에러발생
        //TimePicker시간 초기화
        timeManager = (TimePicker)findViewById(R.id.timePicker);
        Calendar calender = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= 23 ){
            timeManager.setHour(calender.get(Calendar.HOUR));
            timeManager.setMinute(calender.get(Calendar.MINUTE));
        }else{
            timeManager.setCurrentHour(calender.get(Calendar.HOUR));
            timeManager.setCurrentMinute(calender.get(Calendar.MINUTE));
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.cancelButton:
                Log.d("calcelButton", "취소버튼 눌림");
                this.finish();
                break;
            case R.id.okButton:
                Log.d("okButton", "ok버튼 눌림");
                setAlarm(this);
                this.finish();
                break;
        }
    }

    //알람 등록
    //에러있음~!!!
    private void setAlarm(Context context) {
        Log.d("setAlarm", "알람 등록");
        //버전별로 메써드가 달라서 다르게 설정시킴
        timeManager = (TimePicker)findViewById(R.id.timePicker);
        int hour, minute;
        if (Build.VERSION.SDK_INT >= 23 ){
            hour = timeManager.getHour();
            minute = timeManager.getMinute();
        }else{
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
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);//두번째 인자가 알람의 식별번호임

        //alarmManager.set(AlarmManager.RTC, temp, pendingIntent);//원래는 temp대신 System.currentTimeMillis()+second
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, second, 60*1000,pendingIntent);

        Toast.makeText(context,"alarm set",Toast.LENGTH_LONG).show();
    }
}
