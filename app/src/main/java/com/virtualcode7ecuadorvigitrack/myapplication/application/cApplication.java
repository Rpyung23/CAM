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
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.sqlite.cSQLiteOpenHelper;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
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


        bandera = checkNetwork(connectivityManager);

        return bandera;
    }

    public boolean checkNetwork(ConnectivityManager connectivityManager){

        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = false;
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            isConnected = (mNetworkInfo!=null && mNetworkInfo.isConnected()
                    && isOnlineNet7()) ? true : false;
        }else{
            isConnected = (mNetworkInfo!=null && mNetworkInfo.isConnected()
                    && isOnlineNet7()) ? true : false;
        }
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
        policy();

        Boolean ipAddr = null;
        try {
            ipAddr = InetAddress.getByName("www.google.com").isReachable(500);
        } catch (IOException e) {
            e.printStackTrace();
            ipAddr = false;
            Toast.makeText(mApplicationInstance, "Ping TimeOut", Toast.LENGTH_SHORT).show();
        }
        //You can replace it with your name
        return ipAddr;
    }

    private static void policy()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static Boolean isOnlineNet7()
    {
        policy();
        boolean isOnline = false;
        /*try {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(500);
            urlc.connect();
            if (urlc.getResponseCode() == 200)
                isOnline =  true;
        } catch (IOException e) {
            Log.e("TAG", "Error checking internet connection", e);
            isOnline =  false;
        }
         */

        Socket mSocket = new Socket();
        try {
            mSocket.connect(new InetSocketAddress("8.8.8.8",53),500);
            mSocket.close();
            isOnline = true;
        } catch (IOException e) {
            e.printStackTrace();
            isOnline = false;
        }

        return isOnline;
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
