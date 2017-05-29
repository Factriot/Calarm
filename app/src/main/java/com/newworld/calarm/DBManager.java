package com.newworld.calarm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by 이은현 on 2017-05-24.
 */

public class DBManager extends SQLiteOpenHelper {
    private Context context;

    public DBManager (Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context, name, cursorFactory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
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

        Log.d("DBManager onCreate","DB생성");
        Toast.makeText(context, "Creating Table complete", Toast.LENGTH_SHORT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Toast.makeText(context, "version upgraded", Toast.LENGTH_SHORT);
    }

    public void insert(String _query) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(_query);
        database.close();
        Log.d("DBManager insert","데이터 삽입");
    }

    public void update(String _query) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(_query);
        database.close();
        /*
                dbManager.update("update FOOD_LIST set price = " + price + " where name = '" + name + "';");
         */
    }

    public void delete(String _query) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(_query);
        database.close();
        /*
                dbManager.delete("delete from FOOD_LIST where name = '" + name + "';");

         */
    }

    /*
    여기서 에러가 발생한 것 같다.
    FATAL EXCEPTION: main
                Process: com.newworld.calarm, PID: 3291
                java.lang.NullPointerException: Attempt to invoke virtual method 'java.util.ArrayList com.newworld.calarm.DBManager.dragData()' on a null object reference
                at com.newworld.calarm.ListViewAdapter.<init>(ListViewAdapter.java:21)
                at com.newworld.calarm.MainActivity.setListView(MainActivity.java:91)
                at com.newworld.calarm.MainActivity.onClick(MainActivity.java:46)
                at android.view.View.performClick(View.java:5637)
                at android.view.View$PerformClick.run(View.java:22429)
                at android.os.Handler.handleCallback(Handler.java:751)
                at android.os.Handler.dispatchMessage(Handler.java:95)
                at android.os.Looper.loop(Looper.java:154)
                at android.app.ActivityThread.main(ActivityThread.java:6119)
                at java.lang.reflect.Method.invoke(Native Method)
                at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:886)
                at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:776)
     */
    public ArrayList<String> dragData() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<String> string = new ArrayList<String>();

        Log.d("dragData","데이터 끌어옴");
        Cursor cursor = database.rawQuery("select * from ALARM_SETTINGS", null);
        while(cursor.moveToNext()) {
            //노트내용, 시간, 분, 알람on/off
            String  str = "";
            str += cursor.getString(1)
                    +","
                    +cursor.getInt(2)
                    +","
                    +cursor.getInt(3)
                    +","
                    +cursor.getString(6);
            string.add(str);
            /*
            받은 데이터를 자를  때는
            String[] array = str.split(",");
             */
        }
        return string;
    }
}
