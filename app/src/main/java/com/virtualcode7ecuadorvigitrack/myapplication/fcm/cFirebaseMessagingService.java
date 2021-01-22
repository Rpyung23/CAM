package com.virtualcode7ecuadorvigitrack.myapplication.fcm;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import es.dmoral.toasty.Toasty;

public class cFirebaseMessagingService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        //Toasty.error(getApplicationContext(),remoteMessage.getNotification().getBody().toString(),Toasty.LENGTH_LONG).show();
    }

}
