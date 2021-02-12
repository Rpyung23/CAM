package com.virtualcode7ecuadorvigitrack.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.virtualcode7ecuadorvigitrack.myapplication.notificaciones.cNotificationTokenLogin;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;

public class cServiceTimerToken extends Service
{
    private cNotificationTokenLogin mNotificationTokenLogin;

    private Handler mHandler = new Handler();
    private cSharedTokenValidation mSharedTokenValidation;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run()
        {

            mSharedTokenValidation = new cSharedTokenValidation(getApplicationContext());
            mSharedTokenValidation.writeToken("error");
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

        mHandler.postDelayed(mRunnable,600000);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
