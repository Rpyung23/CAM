package com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.virtualcode7ecuadorvigitrack.myapplication.services.cServiceTimerToken;
import com.virtualcode7ecuadorvigitrack.myapplication.views.LogOutActivity;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class cSharedTokenValidation
{
    public Context mContext;

    /*
    public cSharedTokenValidation(Context mContext) {
        this.mContext = mContext;
    }

    public void writeToken(String status)
    {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("TokenValidation",Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("validation",status);

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

        mEditor.putString("timelogin", timeStamp);

        mEditor.apply();
    }

    public boolean readTokenValitation()
    {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("TokenValidation",
                Context.MODE_PRIVATE);

        String timeStampNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(Calendar.getInstance().getTime());

        String timeStampLogin = mSharedPreferences.getString("timelogin","");

        /*Date dateTimeNow = new Date(timeStampNow);
        Date dateTimeLogin = new Date(timeStampLogin);*/

        //Log.e("TIMESTAMP",timeStampLogin+" --> "+timeStampNow);

/*
        try {
            Date mDateLogin = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(timeStampLogin);

            Date mDateNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(timeStampNow);

            long startTime = mDateLogin.getTime();
            long endTime = mDateNow.getTime();
            long diffTime = endTime - startTime;
            long minutos = TimeUnit.MINUTES.convert(diffTime, TimeUnit.MILLISECONDS);

            if(minutos>=9)
            {

                Intent mIntentS = new Intent(mContext, cServiceTimerToken.class);
                mContext.stopService(mIntentS);


                writeToken("error");


                Intent mIntent = new Intent(mContext, LogOutActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(mIntent);


                return false;
            }

        } catch (ParseException e) {
            Log.e("TIMESTAMP",e.getMessage());
        }


        if (mSharedPreferences.getString("validation","").equals("ok"))
        {
            return true;
        }


        return false;
    }


    public boolean readTokenValitationFragmentInicio()
    {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("TokenValidation",
                Context.MODE_PRIVATE);


        if (mSharedPreferences.getString("validation","").equals("ok"))
        {
            return true;
        }


        return false;
    }*/

}
