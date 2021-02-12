package com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class cSharedTokenValidation
{
    public Context mContext;

    public cSharedTokenValidation(Context mContext) {
        this.mContext = mContext;
    }

    public void writeToken(String status)
    {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("TokenValidation",Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("validation",status);
        mEditor.apply();
    }

    public boolean readTokenValitation()
    {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("TokenValidation",Context.MODE_PRIVATE);
        if (mSharedPreferences.getString("validation","").equals("ok"))
        {
            return true;
        }
        return false;
    }

}
