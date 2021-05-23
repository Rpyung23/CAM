package com.virtualcode7ecuadorvigitrack.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.eventos.cAdapterEventosDetailsScroll;
import com.virtualcode7ecuadorvigitrack.myapplication.application.cApplication;
import com.virtualcode7ecuadorvigitrack.myapplication.broadcast.cBroadCastNetworkStatus;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.sqlite.cSQLEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.*;

public class EventosViewPagerContenidoActivity extends AppCompatActivity implements cCheckNetwork
{
    private ViewPager2 mViewPager2;
    private cAdapterEventosDetailsScroll mAdapterEventosDetailsScroll;
    private int posInicial;
    private ArrayList<cEventos> mEventosArrayList;
    private AlertDialog mAlertDialog;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private cEventos oE = new cEventos();
    private cBroadCastNetworkStatus mBroadCastNetworkStatus = new cBroadCastNetworkStatus();
    private cSQLEventos mSqlEventos = new cSQLEventos();
    private androidx.appcompat.app.AlertDialog mAlertDialogOffNetwork;
    private boolean banderaOnCreate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_view_pager_contenido);

        mViewPager2 = findViewById(R.id.viewpager2eventos);

        posInicial = getIntent().getIntExtra("posicion",0);
        oE = (cEventos) getIntent().getSerializableExtra("evento");
        mEventosArrayList = (ArrayList<cEventos>) getIntent().getSerializableExtra("eventos");
        mEventosArrayList.add(0,oE);

        new cToolbar().show(EventosViewPagerContenidoActivity.this,"",true,0);


        mAdapterEventosDetailsScroll = new cAdapterEventosDetailsScroll(
                EventosViewPagerContenidoActivity.this
                ,mEventosArrayList);

        mViewPager2.setAdapter(mAdapterEventosDetailsScroll);

        initBroadCastReciver();

    }

    private void initBroadCastReciver()
    {

        cBroadCastNetworkStatus.mCheckNetwork = this;

        IntentFilter mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mIntentFilter.addAction(ConnectivityManager.EXTRA_CAPTIVE_PORTAL);

        this.registerReceiver(mBroadCastNetworkStatus,mIntentFilter);
    }

    private void readEventoApiRest(int position)
    {

        if (cApplication.getmApplicationInstance().checkInternet()){
            consumoServiceDeatilsEventos(position);
        }else{
            //visibleLoading();
            cEventos mEventos = mSqlEventos.readEvento(mEventosArrayList.get(position).getId_evento());
            mEventosArrayList.get(position).setContenido(mEventos.getContenido());
            mEventosArrayList.get(position).setUri_foto_imagen_principal(mEventos.getUri_foto_imagen_principal());
            mAdapterEventosDetailsScroll.notifyItemChanged(position);
            //invisibleLoading();
        }

    }

    private void consumoServiceDeatilsEventos(int position)
    {
        mAlertDialog = new cAlertDialogProgress().showAlertProgress(EventosViewPagerContenidoActivity.this,
                "CONSULTANDO...",false);
        mAlertDialog.show();

        mStringRequest = new StringRequest(Request.Method.GET, getString(R.string.api_rest_eventos_id)
                + mEventosArrayList.get(position)
                .getId_evento(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObjectRes = new JSONObject(response);
                    if (jsonObjectRes.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArray = jsonObjectRes.getJSONArray("resultado");
                        JSONObject mJsonObject = mJsonArray.getJSONObject(0);

                        mEventosArrayList.get(position).setContenido(mJsonObject.getString("contenido"));
                        mEventosArrayList.get(position).setUri_foto_imagen_principal(mJsonObject.getString("url_imagen_principal"));

                        mSqlEventos.updateEventoContenido(mEventosArrayList.get(position));

                        mAdapterEventosDetailsScroll.notifyItemChanged(position);

                    }else
                    {
                        Toasty.warning(getApplicationContext(),"No se pudo realizar la consulta"
                                ,Toasty.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new cAlertDialogProgress().closeAlertProgress(mAlertDialog);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toasty.error(getApplicationContext(),error.getMessage(),Toasty.LENGTH_LONG).show();
                new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
            }
        })
        {
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> oMap = new HashMap<>();
                oMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                return oMap;
            }
        };

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(mStringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostResume()
    {
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                readEventoApiRest(position);
            }
        });

        mViewPager2.setCurrentItem(posInicial,true);

        super.onPostResume();
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    protected void onDestroy()
    {
        this.unregisterReceiver(mBroadCastNetworkStatus);
        super.onDestroy();
    }

    @Override
    public void checkNetwork(boolean bandera)
    {
        if (!bandera){
            /**Alert Sin Internet***/



            View mView = LayoutInflater.from(EventosViewPagerContenidoActivity.this)
                    .inflate(R.layout.alert_off_network,null,false);

            androidx.appcompat.app.AlertDialog.Builder mAlertDialogBuilder = new androidx.appcompat.app.AlertDialog
                    .Builder(EventosViewPagerContenidoActivity.this);

            mAlertDialogBuilder.setView(mView);

            MaterialButton materialButton = mView.findViewById(R.id.idMateriButtonCheckNetwork);

            materialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (mAlertDialogOffNetwork.isShowing()){
                        mAlertDialogOffNetwork.hide();
                        mAlertDialogOffNetwork.cancel();
                    }
                }
            });

            mAlertDialogOffNetwork = mAlertDialogBuilder.create();
            mAlertDialogOffNetwork.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.trnsparente)));
            mAlertDialogOffNetwork.getWindow()
                    .setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if (banderaOnCreate){
                banderaOnCreate = false;
                return;
            }

            mAlertDialogOffNetwork.show();
        }else{
            if (mAlertDialogOffNetwork!=null && mAlertDialogOffNetwork.isShowing()){
                mAlertDialogOffNetwork.hide();
                mAlertDialogOffNetwork.cancel();
            }
        }
    }
}