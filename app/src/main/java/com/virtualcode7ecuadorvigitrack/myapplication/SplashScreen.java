package com.virtualcode7ecuadorvigitrack.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.DatePicker;

import com.virtualcode7ecuadorvigitrack.myapplication.views.InicioActivity;

public class SplashScreen extends AppCompatActivity
{
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run()
        {
            Intent mIntent = new Intent(SplashScreen.this, InicioActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mIntent);
            /*Intent mIntentInvitados = new Intent(SplashScreen.this, InvitadosActivity.class);
            mIntentInvitados.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mIntentInvitados);*/


            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //comprobarFechaCad();

        Handler mHandler = new Handler();
        mHandler.postDelayed(mRunnable,3000);

        Log.e("M", Build.MODEL.toString());
        Log.e("M",Build.RADIO.toString());
        Log.e("M",Build.DISPLAY);
        Log.e("M",Build.getRadioVersion());
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.e("SC",String.valueOf(metrics.scaledDensity));
        Log.e("DP",String.valueOf(metrics.densityDpi));
        Log.e("D",""+metrics.density);
        Log.e("HP",""+metrics.heightPixels);
        Log.e("WP",""+metrics.widthPixels);
        Log.e("RV",Build.getRadioVersion());

    }


    private void comprobarFechaCad()
    {
        DatePicker datePicker = new DatePicker(SplashScreen.this);
        int  year = datePicker.getYear();
        int dia = datePicker.getDayOfMonth();
        int mes =  datePicker.getMonth()+1;
        String finEvalua_feha = "2021/1/14";

        if (dia>=18)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
            builder.setTitle("Desarrollado por VirtualCode7");
            builder.setMessage("Prueba Gratuita Finalizada ... ");
            builder.setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else
        {
            Handler mHandler = new Handler();
            mHandler.postDelayed(mRunnable,3000);
        }
    }

}