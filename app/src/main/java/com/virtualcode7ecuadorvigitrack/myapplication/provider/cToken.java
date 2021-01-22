package com.virtualcode7ecuadorvigitrack.myapplication.provider;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class cToken
{
    private Context mContext;

    public Task<String> readTokenMovil()
    {
        return FirebaseMessaging.getInstance().getToken();
    }

}
