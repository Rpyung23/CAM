package com.virtualcode7ecuadorvigitrack.myapplication.views_socios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterRecivos;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cReciver;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferenSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class RecivosCuentaActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private ArrayList<cReciver> mReciverArrayList = new ArrayList<>();
    private cAdapterRecivos mAdapterRecivos;

    private StringRequest mStringRequestRecibos;
    private RequestQueue mRequestQueue;

    private AlertDialog mAlertDialogPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recivos_cuenta);
        cToolbar.show(RecivosCuentaActivity.this,"RECIBOS",true,1);
        mAlertDialogPro = new cAlertDialogProgress()
                .showAlertProgress(RecivosCuentaActivity.this,"CONSULTANDO",false);
        mRecyclerView = findViewById(R.id.id_recyclerViewRecivos);

        consumoApiRest();

    }

    private void llenarRecyclerView()
    {
        mAdapterRecivos = new cAdapterRecivos(mReciverArrayList, RecivosCuentaActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent mIntent = new Intent(RecivosCuentaActivity.this,ReciboDetalleActivity.class);
                mIntent.putExtra("oReciver", mReciverArrayList.get(mRecyclerView.getChildAdapterPosition(view)));
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
            }
        });

        mRecyclerView.setAdapter(mAdapterRecivos);
        new cAlertDialogProgress().closeAlertProgress(mAlertDialogPro);
    }

    private void llenarArraysList()
    {
        for (int i=1;i<9;i++)
        {
            cReciver oReciver = new cReciver();
            oReciver.setDinero_total("$ "+"3.965.00");
            oReciver.setNum_recivo("Recibo 2020156"+i);
            oReciver.setFecha("12/0"+i+"2020");
            oReciver.setTexto_main("Casillero HGA-971");
            mReciverArrayList.add(oReciver);
        }
    }


    public void consumoApiRest()
    {
        mAlertDialogPro.show();
        mStringRequestRecibos = new StringRequest(Request.Method.GET,
                getString(R.string.api_rest_recibos)+new cSharedPreferenSocio(RecivosCuentaActivity.this)
                        .leerdatosSocio().getId_token_socio()+"/recibos",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                try {
                    JSONObject mJsonObject = new JSONObject(response);
                    if (mJsonObject.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArray = mJsonObject.getJSONArray("resultado");
                        if (mJsonArray.length()>0)
                        {
                            for (int i=0;i<mJsonArray.length();i++)
                            {
                                JSONObject mJsonObjectR = mJsonArray.getJSONObject(i);
                                cReciver oReciver = new cReciver();

                                oReciver.setId_recibo(mJsonObjectR.getInt("id"));
                                oReciver.setNum_recivo(mJsonObjectR.getString("no_de_recibo"));
                                oReciver.setRecibo_facturado(mJsonObjectR.getString("recibo_facturado"));
                                oReciver.setFecha(mJsonObjectR.getString("recibo_fecha"));
                                oReciver.setTexto_main(mJsonObjectR.getString("recibo_concepto"));
                                oReciver.setDinero_total(mJsonObjectR.getString("recibo_monto"));
                                oReciver.setRecibo_pdf(mJsonObjectR.getString("recibo_pdf"));
                                oReciver.setRecibo_factura(mJsonObjectR.getString("recibo_factura"));

                                mReciverArrayList.add(oReciver);
                            }

                            llenarRecyclerView();
                        }else
                            {
                                new cAlertDialogProgress().closeAlertProgress(mAlertDialogPro);
                                Toasty.info(RecivosCuentaActivity.this,"NO EXISTEN DATOS",
                                        Toasty.LENGTH_LONG).show();
                            }
                    }else
                        {
                            new cAlertDialogProgress().closeAlertProgress(mAlertDialogPro);
                            Toasty.error(RecivosCuentaActivity.this,"ERROR DE CONSULTA ",
                                    Toasty.LENGTH_LONG).show();
                        }
                } catch (JSONException e)
                {
                    new cAlertDialogProgress().closeAlertProgress(mAlertDialogPro);
                    Toasty.warning(RecivosCuentaActivity.this,"TRY RECIVOS "+e.getMessage(),
                            Toasty.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                new cAlertDialogProgress().closeAlertProgress(mAlertDialogPro);
                Toasty.error(RecivosCuentaActivity.this,error.getMessage(),
                        Toasty.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String>stringHashMap = new HashMap<>();
                stringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                stringHashMap.put("token",new cSharedPreferenSocio(RecivosCuentaActivity.this)
                        .leerdatosSocio().getToken());
                return stringHashMap;
            }

        };

        mRequestQueue = Volley.newRequestQueue(RecivosCuentaActivity.this);
        mRequestQueue.add(mStringRequestRecibos);
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

}