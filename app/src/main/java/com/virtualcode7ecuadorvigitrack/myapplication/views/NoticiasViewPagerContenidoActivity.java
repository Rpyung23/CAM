package com.virtualcode7ecuadorvigitrack.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.noticias.cAdapterNoticiasDetailsScroll;
import com.virtualcode7ecuadorvigitrack.myapplication.application.cApplication;
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

public class NoticiasViewPagerContenidoActivity extends AppCompatActivity
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_view_pager_contenido);



        mViewPager2Noticias = findViewById(R.id.viewpager2noticias);



        posInicial = getIntent().getIntExtra("posicion",0);
        Log.e("postion_pageInicial",String.valueOf(posInicial));

        oN = (cNoticias) getIntent().getSerializableExtra("noticia");

        //oN = cApplication.getmApplicationInstance().getmNoticia();

        new cToolbar().show(NoticiasViewPagerContenidoActivity.this,"",true,0);

        mNoticiasArrayList_ = (ArrayList<cNoticias>) getIntent().getSerializableExtra("noticias");

        //mNoticiasArrayList_ = cApplication.getmApplicationInstance().getmNoticiasList();

        mNoticiasArrayList_.add(0,oN);

        //cApplication.getmApplicationInstance().getmNoticiasList().add(0,cApplication.getmApplicationInstance().getmNoticia());

        mAdapterNoticias = new cAdapterNoticiasDetailsScroll
                (getApplicationContext(),mNoticiasArrayList_);

        mViewPager2Noticias.setAdapter(mAdapterNoticias);




        //mAdapterNoticias.notifyDataSetChanged();

    }

    private void readNoticiaApiRest(int position)
    {
        if (cApplication.getmApplicationInstance().checkInternet()){
            consumoServiceDeatilsNoticias(position);
        }else{
            //visibleLoading();
            cNoticias mNoticias = mSqlNoticias.readNoticia(mNoticiasArrayList_.get(position).getId_noticias());
            mNoticiasArrayList_.get(position).setTextoNoticia(mNoticias.getTextoNoticia());
            mNoticiasArrayList_.get(position).setmUriPictureContenidoNoticia(mNoticias.getmUriPictureContenidoNoticia());
            mAdapterNoticias.notifyItemChanged(position);
            //invisibleLoading();
        }
    }

    private void consumoServiceDeatilsNoticias(int position)
    {
        mAlertDialog = new cAlertDialogProgress().showAlertProgress(NoticiasViewPagerContenidoActivity.this,
                "CONSULTANDO...",false);
        mAlertDialog.show();

        //visibleLoading();

        //Toast.makeText(this, "pos : "+position, Toast.LENGTH_SHORT).show();

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

}