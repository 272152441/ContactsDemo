package com.zxsoure.contactdemo;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> allNavData = new ArrayList<>();

    private List<String> showNavData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        getSupportActionBar().hide();

//        setContentView(R.layout.activity_main);
//        initData();
//        NavView navView = (NavView)findViewById(R.id.nav_id);
//
//        navView.initNavData(allNavData);
//
//        navView.invalidate();
//
//        navView.setNavListener(new NavView.NavViewListener() {
//            @Override
//            public void navChangeListener(String navContent) {
//                Log.i("TEST","show text :"+navContent);
//            }
//        });

        setContentView(R.layout.main_frag);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ContactFragment fragment = new ContactFragment();
        transaction.replace(R.id.demo_frag, fragment);
        transaction.commit();

    }


    private void initData(){
        allNavData.add("A");
        allNavData.add("B");
        allNavData.add("C");
        allNavData.add("D");
        allNavData.add("E");
        allNavData.add("F");
        allNavData.add("G");
        allNavData.add("H");
        allNavData.add("I");
        allNavData.add("J");
        allNavData.add("k");
        allNavData.add("L");
        allNavData.add("M");
        allNavData.add("N");
        allNavData.add("O");

        showNavData.add("A");
        showNavData.add("B");
        showNavData.add("D");
        showNavData.add("F");
        showNavData.add("G");
        showNavData.add("K");
        showNavData.add("L");
        showNavData.add("M");

    }
}
