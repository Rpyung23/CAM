package com.virtualcode7ecuadorvigitrack.myapplication.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.virtualcode7ecuadorvigitrack.myapplication.MenuAdapter;
import com.virtualcode7ecuadorvigitrack.myapplication.R;

import java.util.ArrayList;

import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DuoMenuView duoMenuView = (DuoMenuView) findViewById(R.id.menu);
        ArrayList<String> mMenuOptions = new ArrayList<>();
        mMenuOptions.add("OPC 1");
        mMenuOptions.add("OPC 2");
        mMenuOptions.add("OPC 3");
        mMenuOptions.add("OPC 4");
        @SuppressLint("RestrictedApi")
        com.virtualcode7ecuadorvigitrack.myapplication.MenuAdapter menuAdapter = new MenuAdapter(mMenuOptions);
        duoMenuView.setAdapter(menuAdapter);
    }
}