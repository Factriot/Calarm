package com.newworld.calarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 이은솔 on 2017-05-28.
 */

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<String> arrayList;
    private DBManager dbManager;

    public ListViewAdapter(DBManager dbManager) {
        this.dbManager = dbManager;
        arrayList = dbManager.dragData();
    }

    //현재 아이템 갯수를 반환
    @Override
    public int getCount() {
        return arrayList.size();
    }

    //Object 캐스팅 하기
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        //DB에서 정보를 가져와서 자른걸 저장 노트내용, 시간, 분, 알람on/off
        String[] stringArray = arrayList.get(pos).split(",");

        //리스트뷰가 길어져서 안보이는 아이템은 convertView가 0으로 들어옴
        if(convertView == null) {
            //View가 null일때 layout얻어오기
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_listview_item, parent, false);

            //note내용 추가
            TextView note = (TextView) convertView.findViewById(R.id.note);
            note.setText(stringArray[0]);

            //시간 내용 추가
            TextView time = (TextView)convertView.findViewById(R.id.time);
            TextView ampm = (TextView)convertView.findViewById(R.id.ampm);
            int hour = Integer.parseInt(stringArray[1]);
            int minute = Integer.parseInt(stringArray[2]);
            String when;
            if(hour > 12) {
                hour -= 12;
                when = "PM";
            }else{
                when = "AM";
            }
            time.setText(when.toString());

            //이벤트 정의는 나중에
        }
        return convertView;
    }
}
