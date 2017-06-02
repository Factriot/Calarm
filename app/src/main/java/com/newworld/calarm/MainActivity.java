package com.newworld.calarm;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * Created by 이은현 on 2017-05-19.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //알람을 보여주는 창 설정을 위해 필요
    private ListView listView;
    private ListViewAdapter adapter;

    //DB관리자 만들기
    public static DBManager dbManager;

    //Test DB준비
    String databaseName = "ALARM_SETTINGS";
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_practice);
        //DBManager 생성하기
        dbManager = new DBManager(getApplicationContext(), "ALARM_SETTINGS", null, 1);
        Log.d("MainActivity, DB","MainActivity생성");

        //TestDB 만들기
        database = openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
        StringBuffer string = new StringBuffer();
        string.append("CREATE TABLE IF NOT EXISTS ALARM_SETTINGS(");
        string.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        string.append("note TEXT, ");
        string.append("hour INTEGER, ");
        string.append("minute INTEGER, ");
        string.append("vibrate TEXT, ");
        string.append("identifier INTEGER, ");
        string.append("onOff TEXT);");
        database.execSQL(string.toString());
        Log.d("in Mainactivity","Test DB생성 완료");

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
                //setListView(dbManager);
                Log.d("setListView", "알람창 화면");
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
            findFragmentById()를 사용해보자.
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

    private void setListView(DBManager dbManager) {//리스트뷰에 내용을 추가하기 위한 함수
        adapter = new ListViewAdapter(dbManager);
        listView = (ListView)findViewById(R.id.listView);
        //어뎁터 연결
        listView.setAdapter(adapter);
        //listView.setOnClickListener(onClickListItem);
    }
/*
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
    */
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