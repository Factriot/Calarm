package com.newworld.calarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.newworld.calarm.MainActivity.dbManager;

public class Tab1Activity extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ArrayList<String> LIST_MENU = dbManager.dragData();
        if(LIST_MENU.isEmpty()) Log.d("Tab1","LIST_MENU is empty");
        View view = inflater.inflate(R.layout.activity_tab1, null);
        //ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.activity_list_item, LIST_MENU);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //return inflater.inflate(R.layout.activity_tab1,container,false);
        return view;
    }
}
