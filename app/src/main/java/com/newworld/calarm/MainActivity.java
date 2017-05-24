package com.newworld.calarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 이은현 on 2017-05-19.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_practice);

        //버튼 등록
        findViewById(R.id.alarmButton).setOnClickListener(this);
        findViewById(R.id.timerButton).setOnClickListener(this);
        findViewById(R.id.stopWatchButton).setOnClickListener(this);
        findViewById(R.id.settingButton).setOnClickListener(this);
        findViewById(R.id.alarmSettingButton).setOnClickListener(this);
        //findViewById(R.id.addAlarmButton).setOnClickListener(this);
        //findViewById(R.id.deleteAlarmButton).setOnClickListener(this);

        //초기화면 지정
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new Tab1Activity())
                .commit();
    }

    @Override
    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.alarmButton:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new Tab1Activity())
                        .commit();
                break;
            case R.id.timerButton:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new Tab2Activity())
                        .commit();
                break;
            case R.id.stopWatchButton:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new Tab3Activity())
                        .commit();
                break;
            case R.id.settingButton:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new Tab4Activity())
                        .commit();
                break;
            case R.id.alarmSettingButton:
                Intent intent = new Intent(MainActivity.this, AlarmSettingActivity.class);
                startActivity(intent);
                break;

            /*
            //메인에서 하는게 맞는 것 같음
            case R.id.addAlarmButton:
                //Intent intent = new Intent(MainActivity.this, AlarmSettingActivity.class);
                //Intent intent = new Intent(getActivity(), AlarmSettingActivity.class);
                startActivity(intent);
                break;

            case R.id.deleteAlarmButton:
                break;
                */
        }
    }

    /*
    private Activity activity;
    //Fragment를 상속받지 않기 때문에 문제가 생기는 듯
    @Override
    public void Fragment.onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity){
            activity = (Activity) context;
        }
    }
    */
}