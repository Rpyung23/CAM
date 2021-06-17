package com.virtualcode7ecuadorvigitrack.myapplication.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.virtualcode7ecuadorvigitrack.myapplication.models.cEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.sqlite.cSQLiteOpenHelper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class cApplication extends Application
{
    public static cApplication mApplicationInstance;
    private cSQLiteOpenHelper mSqLiteOpenHelper;
    private ConnectivityManager connectivityManager;

    /**
     * 1 -> NOTICIAS
     * 2 -> EVENTOS
     * **/

    public static int bandera_fragment = 1;


    /*private List<cNoticias> mNoticiasList;
    private List<cEventos> mEventosList;
    private cNoticias mNoticia;
    private cEventos mEvento;*/

    @Override
    public void onCreate()
    {
        super.onCreate();
        mApplicationInstance = this;
        mSqLiteOpenHelper = new cSQLiteOpenHelper(getApplicationContext());


        connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        /*mNoticiasList = new ArrayList<>();
        mEventosList = new ArrayList<>();
        mNoticia = new cNoticias();
        mEvento = new cEventos();*/
    }


    public static cApplication getmApplicationInstance() {
        return mApplicationInstance;
    }




    public boolean checkInternet()
    {

        boolean bandera = false;

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            /*bandera = checkNetwork21(getApplicationContext(),connectivityManager);*/
            bandera = checkNetwork(connectivityManager);
        }else{

            bandera = checkNetwork(connectivityManager);
        }


        return bandera;
    }

    public boolean checkNetwork(ConnectivityManager connectivityManager){

        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isConnected = (mNetworkInfo!=null && mNetworkInfo.isConnected()
                && isOnlineNet()) ? true : false;
        return isConnected;
    }


    public boolean checkNetwork21(Context mContext, ConnectivityManager connectivityManager)
    {
        boolean isConnected = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Network[] allNetworks = connectivityManager.getAllNetworks(); // added in API 21 (Lollipop)

            for (Network network : allNetworks) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                if (networkCapabilities != null) {
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                    {
                        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();

                        isConnected = (mNetworkInfo!=null && mNetworkInfo.isConnected()
                                && isOnlineNet()) ? true : false;
                    }

                }
            }
        }
        return isConnected;
    }




    public static Boolean isOnlineNet()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Boolean ipAddr = null;
        try {
            ipAddr = InetAddress.getByName("www.google.com").isReachable(500);
        } catch (IOException e) {
            e.printStackTrace();
            ipAddr = false;
        }
        //You can replace it with your name
        return ipAddr;
    }



/*
    public List<cNoticias> getmNoticiasList() {
        return mNoticiasList;
    }

    public void setmNoticiasList(List<cNoticias> mNoticiasList) {
        this.mNoticiasList = mNoticiasList;
    }

    public List<cEventos> getmEventosList() {
        return mEventosList;
    }

    public void setmEventosList(List<cEventos> mEventosList) {
        this.mEventosList = mEventosList;
    }

    public cNoticias getmNoticia() {
        return mNoticia;
    }

    public void setmNoticia(cNoticias mNoticia) {
        this.mNoticia = mNoticia;
    }

    public cEventos getmEvento() {
        return mEvento;
    }

    public void setmEvento(cEventos mEvento) {
        this.mEvento = mEvento;
    }*/

    public static void setmApplicationInstance(cApplication mApplicationInstance) {
        cApplication.mApplicationInstance = mApplicationInstance;
    }

    public cSQLiteOpenHelper getmSqLiteOpenHelper() {
        return mSqLiteOpenHelper;
    }

    public void setmSqLiteOpenHelper(cSQLiteOpenHelper mSqLiteOpenHelper) {
        this.mSqLiteOpenHelper = mSqLiteOpenHelper;
    }
}
