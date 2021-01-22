package com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.virtualcode7ecuadorvigitrack.myapplication.models.cSocio;

public class cSharedPreferenSocio
{
    private Context mContext;

    public cSharedPreferenSocio(Context mContext) {
        this.mContext = mContext;
    }
    public cSharedPreferenSocio() {
    }
    public boolean registerdatosSocio(cSocio mSocio)
    {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("clientes",Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("num_membresia",mSocio.getNum_membresia());
        mEditor.putString("nombre",mSocio.getNombre_socio());
        mEditor.putString("token",mSocio.getToken());
        mEditor.putInt("id_token_socio",mSocio.getId_token_socio());
        return mEditor.commit();
    }

    public cSocio leerdatosSocio()
    {
        cSocio oS = new cSocio();
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("clientes",Context.MODE_PRIVATE);
        oS.setNum_membresia(mSharedPreferences.getString("num_membresia","s/n"));
        oS.setId_token_socio(mSharedPreferences.getInt("id_token_socio",0));
        oS.setNombre_socio(mSharedPreferences.getString("nombre","s/n"));
        oS.setToken(mSharedPreferences.getString("token","s/n"));

        return oS;
    }

}
