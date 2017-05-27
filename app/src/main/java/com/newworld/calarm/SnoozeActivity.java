package com.newworld.calarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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
                this.finish();
                break;
            case R.id.snoozeButton:
                Log.d("snoozeButton", "5분뒤 알람");
                this.finish();
                break;
        }
    }

    private void removeAlarm(){

    }
}
