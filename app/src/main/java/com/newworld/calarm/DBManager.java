package com.newworld.calarm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

    public ArrayList<String> dragData() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<String> string = new ArrayList<String>();

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
