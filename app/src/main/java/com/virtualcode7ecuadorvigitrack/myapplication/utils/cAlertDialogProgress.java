package com.virtualcode7ecuadorvigitrack.myapplication.utils;

import android.app.AlertDialog;
import android.content.Context;

import androidx.core.app.ActivityCompat;

import dmax.dialog.SpotsDialog;

public class cAlertDialogProgress
{
    public static AlertDialog showAlertProgress(Context mContext, String mensaje,boolean cancel)
    {
        AlertDialog mAlertDialog = null;
        SpotsDialog.Builder mSpotsDialog = new SpotsDialog.Builder();
        mSpotsDialog.setContext(mContext);
        mSpotsDialog.setMessage(mensaje);
        mSpotsDialog.setCancelable(cancel);
        mAlertDialog = mSpotsDialog.build();
        return mAlertDialog;
    }

    public static void closeAlertProgress(AlertDialog mAlertDialog)
    {
        mAlertDialog.cancel();
        mAlertDialog.dismiss();
    }
}
