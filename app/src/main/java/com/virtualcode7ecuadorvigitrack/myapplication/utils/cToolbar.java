package com.virtualcode7ecuadorvigitrack.myapplication.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.virtualcode7ecuadorvigitrack.myapplication.R;

public class cToolbar
{

    public static void show(AppCompatActivity activity, String title, boolean upButton, int ban)
    {
        Toolbar toolbar= null;

        if(ban==0)
        {
            toolbar = activity.findViewById(R.id.toolbar_);
        }else if(ban==1)
            {
                toolbar = activity.findViewById(R.id.toolbar_color);
            }else {

            toolbar = activity.findViewById(R.id.toolbar);

        }


        activity.setSupportActionBar(toolbar);

        activity.getSupportActionBar().setTitle(title);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }
}
