package com.newworld.calarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static com.newworld.calarm.MainActivity.dbManager;

/**
 * Created by 이은솔 on 2017-06-25.
 */

public class CalarmSettingActivity extends AlarmSettingActivity {
    private TimePicker timeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calarm_setting);

        Log.d("CalarmSettingActivity","진입");
        findViewById(R.id.cancelButton_calarm).setOnClickListener(this);
        findViewById(R.id.okButton_calarm).setOnClickListener(this);

        timeManager = (TimePicker)findViewById(R.id.timePicker_calarm);
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
            case R.id.cancelButton_calarm:
                Log.d("calcelButton", "취소버튼 눌림");
                this.finish();
                break;
            case R.id.okButton_calarm:
                Log.d("okButton", "ok버튼 눌림");
                //동일 시간의 알람 설정 불가
                setAlarm(this);
                this.finish();
                break;
        }
    }

    //알람 등록
    void setAlarm(Context context) {
        //알람에 사용할 id 초기화
        identifier = dbManager.getIdentifier();
        Log.d("getIdentifier", ""+identifier);
        Log.d("setAlarm", "알람 등록");
        //버전별로 메써드가 달라서 다르게 설정시킴
        timeManager = (TimePicker)findViewById(R.id.timePicker_calarm);
        int hour, minute;
        if (Build.VERSION.SDK_INT >= 23 ){
            hour = timeManager.getHour();
            minute = timeManager.getMinute();
        }else{
            hour = timeManager.getCurrentHour();
            minute = timeManager.getCurrentMinute();
        }

        long alarmTime = (long)hour*60*60*1000+(long)minute*60*1000;

        long currentTime = System.currentTimeMillis();
        long second = 0;
        if(alarmTime > currentTime){
            second = alarmTime - currentTime;
        }else if(alarmTime < currentTime){
            second = 24*60*60*1000 + alarmTime - currentTime;
        }else{
            second = 24*60*60*1000;
        }

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);//context에서 AlarmReceiver로 직접 전해줌
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, identifier, intent, 0);//두번째 인자가 알람의 식별번호임

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, 3*60*1000, pendingIntent);//원래는 temp대신 System.currentTimeMillis()+second
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, second, 5*60*1000,pendingIntent);

        insertData(hour, minute);
        Toast.makeText(context,"alarm set",Toast.LENGTH_LONG).show();
    }

    @Override
    void insertData(int hour, int minute) {
        final DBManager databaseManager = dbManager;
        final EditText editTextNote = (EditText)findViewById(R.id.note_calarm);
        final EditText editTextCallNumber = (EditText)findViewById(R.id.phoneNumber);

        String phone = editTextCallNumber.getText().toString();
        String note = editTextNote.getText().toString();
        if(note.isEmpty()) note = "Alarm";
        if(phone.isEmpty()){
            //전화를 쓰지 않았으면 중단.
            Toast.makeText(this,"phone number is empty",Toast.LENGTH_LONG).show();
            return;
        }
        databaseManager.insert("INSERT INTO ALARM_SETTINGS VALUES(null, '"+note+"', "+hour+", "+minute+", "+"'true', " +identifier+", 'on');");
        databaseManager.insert("INSERT INTO PHONE VALUES(null, "+identifier+", '"+phone+"');");
    }
}
