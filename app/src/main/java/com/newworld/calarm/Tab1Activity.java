package com.newworld.calarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Tab1Activity extends Fragment {
    DBManager dbManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ArrayList<String> LIST_MENU = dbManager.dragData();
        View view = inflater.inflate(R.layout.activity_tab1, null);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.activity_list_item, LIST_MENU);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return inflater.inflate(R.layout.activity_tab1,container,false);
    }
}
