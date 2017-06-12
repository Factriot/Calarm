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
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmSettingActivity extends AppCompatActivity implements View.OnClickListener{
    // 알람 메니저
    private static PendingIntent pendingIntent = null;
    private TimePicker timeManager;
    //통지 메니저
    private NotificationManager notificationManager;

    //반복 알람 여부
    private boolean isRepeat = false;
    //진동 On/Off
    private boolean isVibrate = true;
    //알람의 개별 식별자
    public static int identifier = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        Log.d("AlarmSettingActivity","진입");
        findViewById(R.id.cancelButton).setOnClickListener(this);
        findViewById(R.id.okButton).setOnClickListener(this);

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

        long alarmTime = (long)hour*60*60*1000+(long)minute*60*1000;
        /*
        long currentTime = System.currentTimeMillis();
        long second = 0;
        if(alarmTime > currentTime){
            second = alarmTime - currentTime;
        }else if(alarmTime < currentTime){
            second = 24*60*60*1000 + alarmTime - currentTime;
        }else{
            second = 24*60*60*1000;
        }
        */
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);//context에서 AlarmReceiver로 직접 전해줌
        pendingIntent = PendingIntent.getBroadcast(context, identifier++, intent, 0);//두번째 인자가 알람의 식별번호임

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, 2*60*1000, pendingIntent);//원래는 temp대신 System.currentTimeMillis()+second
        //alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, second, 60*1000,pendingIntent);

        insertData(hour, minute);
        Toast.makeText(context,"alarm set",Toast.LENGTH_LONG).show();
    }

    private void insertData(int hour, int minute) {
        //final DBManager databaseManager = new DBManager(getApplicationContext(), "AlarmSetting.db", null, 1);
        final DBManager databaseManager = MainActivity.dbManager;
        final EditText editTextNote = (EditText)findViewById(R.id.note);
        final Switch vibrate = (Switch)findViewById(R.id.vibrateControl);

        //DB에서 데이터를 가져와 보여줄 화면
        //final ListView list = (ListView)findViewById(R.id.listView);
        //DB에서 데이터를 가져와 보여줄 아이템
        //final LinearLayout item = (LinearLayout)findViewById(R.id.listItem);

        String note = editTextNote.getText().toString();
        if(note == null) note = "Alarm";
        databaseManager.insert("INSERT INTO ALARM_SETTINGS VALUES(null, '"+note+"', "+hour+", "+minute+", "+"'true', " +identifier+", 'on');");
        //databaseManager.insert(note, hour, minute, true, identifier);
    }
}
