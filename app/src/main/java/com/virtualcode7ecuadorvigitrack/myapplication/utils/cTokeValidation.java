package com.virtualcode7ecuadorvigitrack.myapplication.utils;

import android.content.Context;

import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;
import com.virtualcode7ecuadorvigitrack.myapplication.views_socios.InicioSociosActivity;

public class cTokeValidation
{
    public static boolean validationToken(Context mContext)
    {
        cSharedTokenValidation mSharedTokenValidation  = new cSharedTokenValidation(mContext);

        return mSharedTokenValidation.readTokenValitation();
    }

}
