package com.newworld.calarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AlarmSettingActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addAlarmButton:
                Intent intent = new Intent(getApplicationContext(), AlarmSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.deleteAlarmButton:
                break;
        }
    }
}
