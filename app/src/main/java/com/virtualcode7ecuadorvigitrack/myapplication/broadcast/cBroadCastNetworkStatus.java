package com.virtualcode7ecuadorvigitrack.myapplication.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.application.cApplication;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.cCheckNetwork;

import es.dmoral.toasty.Toasty;

public class cBroadCastNetworkStatus extends BroadcastReceiver
{
    public static cCheckNetwork mCheckNetwork;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        mCheckNetwork.checkNetwork(cApplication.getmApplicationInstance().checkInternet());
    }
}
