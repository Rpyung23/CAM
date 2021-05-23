package com.virtualcode7ecuadorvigitrack.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.noticias.cAdapterNoticiasDetailsScroll;
import com.virtualcode7ecuadorvigitrack.myapplication.application.cApplication;
import com.virtualcode7ecuadorvigitrack.myapplication.broadcast.cBroadCastNetworkStatus;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.sqlite.cSQLNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.*;
public class NoticiasViewPagerContenidoActivity extends AppCompatActivity implements cCheckNetwork
{
    private ViewPager2 mViewPager2Noticias;

    private List<cNoticias> mNoticiasArrayList_;

    private int posInicial = 0;
    private cAdapterNoticiasDetailsScroll mAdapterNoticias;
    private JsonObjectRequest mJsonObjectRequest;
    private RequestQueue mRequestQueue;
    private AlertDialog mAlertDialog;

    private cNoticias oN =  new cNoticias();

    private cSQLNoticias mSqlNoticias = new cSQLNoticias();
    private cBroadCastNetworkStatus mBroadCastNetworkStatus = new cBroadCastNetworkStatus();
    private androidx.appcompat.app.AlertDialog mAlertDialogOffNetwork;
    private boolean banderaOnCreate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_view_pager_contenido);


        mViewPager2Noticias = findViewById(R.id.viewpager2noticias);

        posInicial = getIntent().getIntExtra("posicion",0);
        Log.e("postion_pageInicial",String.valueOf(posInicial));

        oN = (cNoticias) getIntent().getSerializableExtra("noticia");


        new cToolbar().show(NoticiasViewPagerContenidoActivity.this,"",true,0);

        mNoticiasArrayList_ = (ArrayList<cNoticias>) getIntent().getSerializableExtra("noticias");


        mNoticiasArrayList_.add(0,oN);

        mAdapterNoticias = new cAdapterNoticiasDetailsScroll
                (getApplicationContext(),mNoticiasArrayList_);

        mViewPager2Noticias.setAdapter(mAdapterNoticias);

        initBroadCastNetwork();
    }

    private void readNoticiaApiRest(int position)
    {
        if (cApplication.getmApplicationInstance().checkInternet()){
            consumoServiceDeatilsNoticias(position);
        }else{
            //visibleLoading();
            cNoticias mNoticias = mSqlNoticias.readNoticia(mNoticiasArrayList_.get(position).getId_noticias());
            if (mNoticias!=null){
                mNoticiasArrayList_.get(position).setTextoNoticia(mNoticias.getTextoNoticia());
                mNoticiasArrayList_.get(position).setmUriPictureContenidoNoticia(mNoticias.getmUriPictureContenidoNoticia());
                mAdapterNoticias.notifyItemChanged(position);
            }else{
                Toasty.info(getApplicationContext(),"Noticia no disponible",Toasty.LENGTH_LONG)
                        .show();
            }
            //invisibleLoading();
        }
    }

    private void consumoServiceDeatilsNoticias(int position)
    {
        mAlertDialog = new cAlertDialogProgress().showAlertProgress(NoticiasViewPagerContenidoActivity.this,
                "CONSULTANDO...",false);
        mAlertDialog.show();

        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.api_rest_noticias_id)+mNoticiasArrayList_.get(position).getId_noticias(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if(response.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArray = response.getJSONArray("resultado");
                        JSONObject mJsonObject = mJsonArray.getJSONObject(0);


                        mNoticiasArrayList_.get(position).setmUriPictureContenidoNoticia(mJsonObject.getString("url_imagen_principal"));
                        mNoticiasArrayList_.get(position).setTextoNoticia(mJsonObject.getString("contenido"));

                        mSqlNoticias.updateNoticiaContenido(mNoticiasArrayList_.get(position));

                        mAdapterNoticias.notifyItemChanged(position);
                    }else
                    {
                        Toasty.warning(getApplicationContext(),"No se puedo realizar la consulta"
                                ,Toasty.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
                //invisibleLoading();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toasty.error(getApplicationContext(),
                        error.getMessage(),Toasty.LENGTH_LONG).show();
                new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
                //invisibleLoading();
            }
        }){
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> oMap = new HashMap<>();
                oMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                return oMap;
            }
        };

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(mJsonObjectRequest);
    }


    private void initBroadCastNetwork()
    {
        cBroadCastNetworkStatus.mCheckNetwork = this;

        IntentFilter mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mIntentFilter.addAction(ConnectivityManager.EXTRA_CAPTIVE_PORTAL);

        this.registerReceiver(mBroadCastNetworkStatus,mIntentFilter);
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
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onPostResume() {

        mViewPager2Noticias.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
        {
            @Override
            public void onPageSelected(int position)
            {
                Log.e("postion_page_titulo",mNoticiasArrayList_.get(position).getTitulo());
                Log.e("postion_page",String.valueOf(position));
                readNoticiaApiRest(position);
            }
        });
        mViewPager2Noticias.setCurrentItem(posInicial,true);
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mBroadCastNetworkStatus);
        super.onDestroy();
    }

    @Override
    public void checkNetwork(boolean bandera) {
        if (!bandera){
            /**Alert Sin Internet***/


            View mView = LayoutInflater.from(NoticiasViewPagerContenidoActivity.this)
                    .inflate(R.layout.alert_off_network,null,false);

            androidx.appcompat.app.AlertDialog.Builder mAlertDialogBuilder = new androidx.appcompat.app.AlertDialog
                    .Builder(NoticiasViewPagerContenidoActivity.this);

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

            if (mAlertDialogOffNetwork!=null && mAlertDialogOffNetwork.isShowing())
            {
                mAlertDialogOffNetwork.hide();
                mAlertDialogOffNetwork.cancel();
            }
        }
    }
}