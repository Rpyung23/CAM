package com.virtualcode7ecuadorvigitrack.myapplication.notificaciones;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.virtualcode7ecuadorvigitrack.myapplication.R;

public class cNotificationTokenLogin
{
    private Context mContext;
    private String channel_temporizador_login ="com.virtualcode7ecuadorvigitrack.myapplication.temporizador";
    private String name_channel_temporizador_login ="temporizador_login";
    private NotificationChannel mNotificationChannel;
    private NotificationManager mNotificationManager;

    public cNotificationTokenLogin(Context context)
    {
        this.mContext = context;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    public NotificationManager getmNotificationManager()
    {
        if (mNotificationManager==null)
        {
            mNotificationManager = (NotificationManager)
                    getmContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mNotificationManager;
    }

    public void setmNotificationManager(NotificationManager mNotificationManager) {
        this.mNotificationManager = mNotificationManager;
    }

    private NotificationChannel createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            mNotificationChannel = new NotificationChannel(channel_temporizador_login
                    ,name_channel_temporizador_login
                    ,NotificationManager.IMPORTANCE_HIGH);
            return  mNotificationChannel;
        }
        return null;
    }

    public Notification.Builder createNotificationManagerChannel()
    {
        Notification.Builder mBuilder = new Notification.Builder(getmContext());
        mBuilder.setContentTitle("NOTIFICACION LOGIN CAM");
        mBuilder.setContentText("Login iniciado");
        mBuilder.setSmallIcon(R.drawable.ic_asturian_primary_color);
        mBuilder.setShowWhen(true);
        mBuilder.setVisibility(Notification.VISIBILITY_SECRET);
        //mBuilder.setStyle(new Notification.BigTextStyle().bigText(msm));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setVibrate(new long[]{100,200,300,500,300,200,100,800});
        //mBuilder.setSound(Uri.parse(""+R.raw.));
        return mBuilder;
    }


    public Notification.Builder createNotificationWithChannel()
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            getmNotificationManager().createNotificationChannel(createNotificationChannel());
            Notification.Builder mBuilder = new Notification.Builder(getmContext());
            mBuilder.setChannelId(channel_temporizador_login);
            mBuilder.setContentTitle("NOTIFICACION LOGIN CAM");
            mBuilder.setContentText("Login");
            mBuilder.setSmallIcon(R.drawable.ic_asturian_primary_color);
            mBuilder.setShowWhen(true);
            mBuilder.setVisibility(Notification.VISIBILITY_SECRET);
            mBuilder.setStyle(new Notification.BigTextStyle().bigText("Login"));
            return mBuilder;
        }else {return null;}
    }


}
