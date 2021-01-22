package com.virtualcode7ecuadorvigitrack.myapplication.views_socios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterStatusCuentaSocios;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cStatusCuentaSocios;
import com.virtualcode7ecuadorvigitrack.myapplication.pdf.ViewPdfActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.*;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class StatusCuentaSociosActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerViewStateCuenta;
    private cAdapterStatusCuentaSocios mAdapterStatusCuentaSocios;
    private ArrayList<cStatusCuentaSocios> mStatusCuentaSociosArrayList = new ArrayList<>();
    private StringRequest mStringRequestStatusCuenta;
    private RequestQueue mRequestQueue;
    private AlertDialog mAlertDialogProgress;

    private TextView mTextViewNameSocio;
    private TextView mTextViewNumMembre;

    private TextView mTextViewTipoMem;
    private TextView mTextViewFechadesde;
    private TextView mTextViewSaldo;

    private cSharedPreferenSocio mSharedPreferenSocio;
    private cSharedPreferencesMembresia mSharedPreferencesMembresia;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_cuenta_socios);
        new cToolbar().show(StatusCuentaSociosActivity.this,"ESTADO DE CUENTA",true,1);
        mRecyclerViewStateCuenta = findViewById(R.id.id_recyclerViewCuentaStatusSocios);
        mTextViewNameSocio = findViewById(R.id.id_textview_name_socio);
        mTextViewNumMembre = findViewById(R.id.id_textview_num_socio);
        mTextViewTipoMem = findViewById(R.id.id_textview_tipo_mem);
        mTextViewFechadesde = findViewById(R.id.id_textview_fecha_desde);
        mTextViewSaldo = findViewById(R.id.id_textview_saldo);

        mSharedPreferenSocio = new cSharedPreferenSocio(StatusCuentaSociosActivity.this);
        mSharedPreferencesMembresia = new cSharedPreferencesMembresia(StatusCuentaSociosActivity.this);


        mTextViewNameSocio.setText(mSharedPreferenSocio.leerdatosSocio().getNombre_socio());
        mTextViewNumMembre.setText(mSharedPreferenSocio.leerdatosSocio().getNum_membresia());
        mTextViewTipoMem.setText(mSharedPreferencesMembresia.readMembresia().getMembresiaTipo());
        mTextViewFechadesde.setText(mSharedPreferencesMembresia.readMembresia().getMembresiaFechaIngreso());
        mTextViewSaldo.setText("$ "+mSharedPreferencesMembresia.readMembresia().getMembresiaSaldo());

        mAlertDialogProgress= new cAlertDialogProgress().showAlertProgress(StatusCuentaSociosActivity.this,"CONSULTANDO",false);
        llenarArraysListStatusSocios();
    }

    private void llenarArraysListStatusSocios()
    {

        mStringRequestStatusCuenta = new StringRequest(Request.Method.GET,
                getString(R.string.api_rest_membresias_movimientos)
                        +new cSharedPreferenSocio(StatusCuentaSociosActivity.this)
                        .leerdatosSocio().getId_token_socio()+"/movimientos", new Response.Listener<String>() {
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
                                cStatusCuentaSocios oS = new cStatusCuentaSocios();
                                oS.setFecha_pago(mJsonObjectR.getString("movimientoFechaPago"));
                                oS.setPrecio_total(mJsonObjectR.getString("movimientoImporte"));
                                oS.setTitulo_card_casillero(mJsonObjectR.getString("movimientoConcepto"));
                                oS.setIdMovimiento(mJsonObjectR.getInt("idMovimiento"));
                                oS.setMovientoEstado(mJsonObjectR.getString("movientoEstado"));
                                oS.setMovimientoLeyenda(mJsonObjectR.getString("movimientoLeyenda"));
                                oS.setIdMembresia(mJsonObjectR.getInt("idMembresia"));
                                oS.setMovimientoURLPago(mJsonObjectR.getString("movimientoURLPago"));

                                mStatusCuentaSociosArrayList.add(oS);
                            }

                            llenarREcyclerView();

                        }else
                            {
                                new cAlertDialogProgress().closeAlertProgress(mAlertDialogProgress);
                                Toasty.info(StatusCuentaSociosActivity.this,"SIN MOVIMIENTOS",Toasty.LENGTH_LONG)
                                        .show();
                            }
                    }else
                        {
                            new cAlertDialogProgress().closeAlertProgress(mAlertDialogProgress);
                            Toasty.error(StatusCuentaSociosActivity.this,"No se pudo consultar los datos",Toasty.LENGTH_LONG)
                                    .show();
                        }
                } catch (JSONException e)
                {
                    new cAlertDialogProgress().closeAlertProgress(mAlertDialogProgress);
                    Toasty.warning(StatusCuentaSociosActivity.this,e.getMessage(),Toasty.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                new cAlertDialogProgress().closeAlertProgress(mAlertDialogProgress);
                Toasty.error(StatusCuentaSociosActivity.this,error.getMessage(),Toasty.LENGTH_LONG).show();
            }
        }){
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> stringHashMap = new HashMap<>();
                stringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                stringHashMap.put("token",new cSharedPreferenSocio(StatusCuentaSociosActivity.this)
                        .leerdatosSocio().getToken());
                return stringHashMap;
            }

        };
        mRequestQueue = Volley.newRequestQueue(StatusCuentaSociosActivity.this);
        mRequestQueue.add(mStringRequestStatusCuenta);

    }

    private void llenarREcyclerView()
    {
        mAdapterStatusCuentaSocios = new cAdapterStatusCuentaSocios(mStatusCuentaSociosArrayList
                , StatusCuentaSociosActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (mStatusCuentaSociosArrayList
                        .get(mRecyclerViewStateCuenta.getChildAdapterPosition(view)).getMovientoEstado().equals("A"))
                {
                    Intent mIntent = new Intent(StatusCuentaSociosActivity.this, ViewPdfActivity.class);
                    mIntent.putExtra("pdf_url",mStatusCuentaSociosArrayList
                            .get(mRecyclerViewStateCuenta.getChildAdapterPosition(view)).getMovimientoURLPago());
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mIntent);
                }else
                    {
                        Intent mIntent = new Intent(StatusCuentaSociosActivity.this, RecivosCuentaActivity.class);
                        /*mIntent.putExtra("pdf_url",mStatusCuentaSociosArrayList
                                .get(mRecyclerViewStateCuenta.getChildAdapterPosition(view)).getMovimientoURLPago());*/
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mIntent);
                    }
            }
        });
        mRecyclerViewStateCuenta.setAdapter(mAdapterStatusCuentaSocios);
        new cAlertDialogProgress().closeAlertProgress(mAlertDialogProgress);
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