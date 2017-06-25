package com.newworld.calarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

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
        string.append("call TEXT, ");
        string.append("identifier INTEGER, ");
        string.append("onOff TEXT);");

        StringBuffer string2 = new StringBuffer();
        string2.append("CREATE TABLE IF NOT EXISTS PHONE(");
        string2.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        string2.append("identifier INTEGER, ");
        string2.append("phoneNumber TEXT);");

        database.execSQL(string2.toString());
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

    public void insert(String _note, int _hour, int _minute, boolean _vibrate, int _identifier){
        SQLiteDatabase database = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("note", _note);
        values.put("hour", _hour);
        values.put("minute", _minute);
        values.put("vibrate", _vibrate);
        values.put("identifier", _identifier);
        values.put("onOff", "true");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = database.insert("ALARM_SETTINGS", null, values);
    }

    public void update(String _query) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(_query);
        database.close();
        Log.d("DBManager upgrade","데이터 베이스 업그레이드");
        /*
                dbManager.update("update FOOD_LIST set price = " + price + " where name = '" + name + "';");
         */
    }

    public void delete(String _query) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(_query);
        database.close();
        Log.d("DBManager delete","데이터 식제");
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
        // 이 아래에서 에러가 발생했음 널포인터 익셉션!!

        Cursor cursor = database.rawQuery("Select * from ALARM_SETTINGS", null);
        //cursor.moveToFirst();
        while(cursor.moveToNext()) {
            //노트내용, 시간, 분, 식별자
            String  str = "";
            str += cursor.getString(1)
                    +","
                    +cursor.getInt(2)
                    +","
                    +cursor.getInt(3)
                    +","
                    +cursor.getInt(5);
            string.add(str);
        }
        /*
        String sql = "Select note,hour,minute,onOff from ALARM_SETTINGS";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null) {
            int count = cursor.getCount();
            for(int i=0; i<count; i++){
                cursor.moveToNext();
                String str = cursor.getString(0)+","+cursor.getString(1)+","+cursor.getString(2)+","+cursor.getString(3);
            }
        }*/
        Log.d("dragData","데이터 끌어오기 완료");
        return string;
    }
    public int getIdentifier(){//새로 설정할 알람의 identifier 가져오기
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Integer> identifier = new ArrayList<Integer>();

        Log.d("getIdentifier","데이터 끌어옴");

        Cursor cursor = database.rawQuery("Select identifier from ALARM_SETTINGS", null);
        while(cursor.moveToNext()) {
            //모든 id가져오기
            identifier.add(cursor.getInt(0));
        }
        Log.d("getIdentifier","데이터 끌어오기 완료");

        //DB가 비어있으면 1 아니면 가장 큰 값+1
        return identifier.isEmpty()? 1: Collections.max(identifier)+1;
    }
    public int findIdentifier(String hour, String minute){//알람의 Id 찾기
        ArrayList<String> string = dragData();
        for(int i=0; i<string.size(); i++){
            String setting[] = string.get(i).split(",");
            if(setting[1].equals(hour) && setting[2].equals(minute)){//시간이 일치할 때
                return Integer.parseInt(setting[3]);
            }
        }
        Log.d("findIdentifier","시간을 찾지 못함");
        return -1;
    }
    public int findIdentifierById(int id){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("Select _id, identifier, phoneNumber from PHONE", null);
        while(cursor.moveToNext()) {
            //모든 id가져오기
            int identifier = cursor.getInt(1);
            int _id = cursor.getInt(0);
            Log.d("findPhoneNumber","_id: "+_id);
            if(id == _id) return identifier;
        }
        Log.d("findPhoneNumber","찾지 못함");
        return -1;
    }
    public String findPhoneNUmber(int id){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("Select _id, identifier, phoneNumber from PHONE", null);
        while(cursor.moveToNext()) {
            //모든 id가져오기
            int identifier = cursor.getInt(1);
            int _id = cursor.getInt(0);
            Log.d("findPhoneNumber","_id: "+_id);
            if(id == identifier) return cursor.getString(2);
        }
        Log.d("findPhoneNumber","찾지 못함");
        return null;
    }
}
