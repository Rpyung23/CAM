package com.virtualcode7ecuadorvigitrack.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterEventosDetailsScroll;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterNoticiasDetailsScroll;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class EventosViewPagerContenidoActivity extends AppCompatActivity
{
    private ViewPager2 mViewPager2;
    private cAdapterEventosDetailsScroll mAdapterEventosDetailsScroll;
    private int posInicial;
    private ArrayList<cEventos> mEventosArrayList;
    private AlertDialog mAlertDialog;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private cEventos oE = new cEventos();

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

        //posInicial = getIntent().getIntExtra("pos_inicial",0);

        //mAdapterNoticias = new cAdapterNoticias(NoticiasViewPagerContenidoActivity.this,mNoticiasArrayList);
        mAdapterEventosDetailsScroll = new cAdapterEventosDetailsScroll(EventosViewPagerContenidoActivity.this
                ,mEventosArrayList);

        mViewPager2.setAdapter(mAdapterEventosDetailsScroll);




        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                readEventoApiRest(position);
            }
        });

        mViewPager2.setCurrentItem(posInicial);









    }

    private void readEventoApiRest(int position)
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

                        mEventosArrayList.get(position).setAcerca_de(mJsonObject.getString("contenido"));

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
        /*
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.api_rest_eventos_id) + oEventos.getId_evento(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            if (response.getString("codigo").equals("200"))
                            {
                                JSONArray mJsonArray = response.getJSONArray("resultado");
                                JSONObject mJsonObject = mJsonArray.getJSONObject(0);

                                mTextViewContenido.setText(Html.fromHtml(mJsonObject.getString("contenido")));
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
        });*/

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
    public void onBackPressed() {
        finish();
    }
}