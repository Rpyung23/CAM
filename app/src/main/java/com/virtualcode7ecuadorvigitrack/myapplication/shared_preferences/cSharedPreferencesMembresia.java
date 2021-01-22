package com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.virtualcode7ecuadorvigitrack.myapplication.models.cMembresiaSocio;

public class cSharedPreferencesMembresia
{
    private Context mContext;

    public cSharedPreferencesMembresia(Context mContext) {
        this.mContext = mContext;
    }

    public cSharedPreferencesMembresia() {
    }


    public boolean writeMembresia(cMembresiaSocio oMembresiaSocio)
    {

        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("Membresia",Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt("idMembresia",oMembresiaSocio.getIdMembresia());
        mEditor.putString("membresiaToken",oMembresiaSocio.getMembresiaToken());
        mEditor.putString("membresiaTitular",oMembresiaSocio.getMembresiaTitular());
        mEditor.putString("membresiaPassword",oMembresiaSocio.getMembresiaPassword());
        mEditor.putString("membresiaEmail",oMembresiaSocio.getMembresiaEmail());
        mEditor.putString("membresiaNo",oMembresiaSocio.getMembresiaNo());
        mEditor.putString("membresiaEstado",oMembresiaSocio.getMembresiaEstado());
        mEditor.putString("membresiaFechaIngreso",oMembresiaSocio.getMembresiaFechaIngreso());
        mEditor.putString("membresiaConexion",oMembresiaSocio.getMembresiaConexion());
        mEditor.putString("membresiaTokenNotification",oMembresiaSocio.getMembresiaTokenNotification());
        mEditor.putString("membresiaTipo",oMembresiaSocio.getMembresiaTipo());
        mEditor.putString("membresiaTokenAplicacion",oMembresiaSocio.getMembresiaTokenAplicacion());
        mEditor.putString("membresiaCelular",oMembresiaSocio.getMembresiaCelular());
        mEditor.putString("membresiaSaldo",oMembresiaSocio.getMembresiaSaldo());
        return mEditor.commit();
    }


    public cMembresiaSocio readMembresia()
    {
        cMembresiaSocio oM = new cMembresiaSocio();

        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("Membresia",Context.MODE_PRIVATE);
        oM.setIdMembresia(mSharedPreferences.getInt("idMembresia",0));
        oM.setMembresiaToken(mSharedPreferences.getString("membresiaToken",""));
        oM.setMembresiaTitular(mSharedPreferences.getString("membresiaTitular",""));
        oM.setMembresiaPassword(mSharedPreferences.getString("membresiaPassword",""));
        oM.setMembresiaEmail(mSharedPreferences.getString("membresiaEmail",""));
        oM.setMembresiaNo(mSharedPreferences.getString("membresiaNo",""));
        oM.setMembresiaEstado(mSharedPreferences.getString("membresiaEstado",""));
        oM.setMembresiaFechaIngreso(mSharedPreferences.getString("membresiaFechaIngreso",""));
        oM.setMembresiaConexion(mSharedPreferences.getString("membresiaConexion",""));
        oM.setMembresiaTokenNotification(mSharedPreferences.getString("membresiaTokenNotification",""));
        oM.setMembresiaTipo(mSharedPreferences.getString("membresiaTipo",""));
        oM.setMembresiaTokenAplicacion(mSharedPreferences.getString("membresiaTokenAplicacion",""));
        oM.setMembresiaCelular(mSharedPreferences.getString("membresiaCelular",""));
        oM.setMembresiaSaldo(mSharedPreferences.getString("membresiaSaldo",""));
        return oM;
    }

}
