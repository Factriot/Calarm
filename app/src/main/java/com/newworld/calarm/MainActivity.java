package com.newworld.calarm;

import android.app.TabActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.TabHost;
/**
 * Created by 이은현 on 2017-05-19.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_practice);

        findViewById(R.id.alarmButton).setOnClickListener(this);
        findViewById(R.id.timerButton).setOnClickListener(this);
        findViewById(R.id.stopWatchButton).setOnClickListener(this);
        findViewById(R.id.settingButton).setOnClickListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new Tab1Activity())
                .commit();
        /*
        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("Tab Spec 1");
        tabSpec1.setContent(R.id.tab1);
        tabSpec1.setIndicator("Tab 1");
        tabHost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("Tab Spec 2");
        tabSpec2.setContent(R.id.tab2);
        tabSpec2.setIndicator("Tab 2");
        tabHost.addTab(tabSpec2);

        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("Tab Spec 3");
        tabSpec3.setContent(R.id.tab3);
        tabSpec3.setIndicator("Tab 3");
        tabHost.addTab(tabSpec3);

        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("Tab Spec 4");
        tabSpec4.setContent(R.id.tab4);
        tabSpec4.setIndicator("Tab 4");
        tabHost.addTab(tabSpec4);
        */

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
        }
    }
}
