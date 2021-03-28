package com.virtualcode7ecuadorvigitrack.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.virtualcode7ecuadorvigitrack.myapplication.notificaciones.cNotificationTokenLogin;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;
import com.virtualcode7ecuadorvigitrack.myapplication.views.LogOutActivity;

import es.dmoral.toasty.Toasty;

public class cServiceTimerToken extends Service
{
    private cNotificationTokenLogin mNotificationTokenLogin;

    private Handler mHandler = new Handler();
    private cSharedTokenValidation mSharedTokenValidation;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run()
        {

            /*cNotificationTokenLogin mNotificationTokenLogin = new cNotificationTokenLogin(getApplicationContext());
            mNotificationTokenLogin.getmNotificationManager().cancel(777);*/

            Intent mIntentS = new Intent(getApplicationContext(),cServiceTimerToken.class);
            stopService(mIntentS);

            mSharedTokenValidation = new cSharedTokenValidation(getApplicationContext());
            mSharedTokenValidation.writeToken("error");

            Intent mIntent = new Intent(getApplicationContext(), LogOutActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mIntent);

        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        mSharedTokenValidation = new cSharedTokenValidation(getApplicationContext());

        mSharedTokenValidation.writeToken("ok");

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {

            mNotificationTokenLogin = new cNotificationTokenLogin(getApplicationContext());
            startForeground(777,mNotificationTokenLogin.createNotificationWithChannel()
                    .build());

        }else
        {
            mNotificationTokenLogin = new cNotificationTokenLogin(getApplicationContext());
            startForeground(777,mNotificationTokenLogin.createNotificationManagerChannel()
                    .build());

        }

        //570000
        mHandler.postDelayed(mRunnable,570000);/** 9.5 minutos **/

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
