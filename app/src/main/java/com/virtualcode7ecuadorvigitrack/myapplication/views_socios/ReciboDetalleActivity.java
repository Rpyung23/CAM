package com.virtualcode7ecuadorvigitrack.myapplication.views_socios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.*;
import com.virtualcode7ecuadorvigitrack.myapplication.pdf.ViewPdfActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferenSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class ReciboDetalleActivity extends AppCompatActivity implements View.OnClickListener
{
    private View mViewCardPdf;
    private View mViewSolicitarFactura;
    private cStatusCuentaSocios mStatusCuentaSocios;
    private TextView mTextViewnum;
    private TextView mTextViewfecha;
    private TextView mTextViewconcepto;
    private TextView mTextViewmonto;
    private cReciver mReciver;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private cSharedPreferenSocio mSharedPreferenSocio;
    private AlertDialog mAlertDialogPro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo_detalle);
        new cToolbar().show(ReciboDetalleActivity.this,"DETALLE",true,1);
        mStatusCuentaSocios = (cStatusCuentaSocios) getIntent().getSerializableExtra("movimiento");
        mReciver = new cReciver();
        mSharedPreferenSocio = new cSharedPreferenSocio(ReciboDetalleActivity.this);
        mViewCardPdf = findViewById(R.id.id_views_card_pdf);
        mViewSolicitarFactura = findViewById(R.id.id_views_card_soli_factura);
        mTextViewnum = findViewById(R.id.id_textview_num_ope);
        mTextViewfecha = findViewById(R.id.id_textview_fecha_pago);
        mTextViewconcepto = findViewById(R.id.id_textview_concepto);
        mTextViewmonto = findViewById(R.id.id_textview_monto);


        consultaMovimeintoRecibo();


        mViewCardPdf.setOnClickListener(this);
        mViewSolicitarFactura.setOnClickListener(this);
    }

    private void consultaMovimeintoRecibo()
    {

        mAlertDialogPro = new cAlertDialogProgress()
                .showAlertProgress(ReciboDetalleActivity.this,"CONSULTANDO",false);
        mAlertDialogPro.show();

        String url = getResources().getString(R.string.api_rest_recibo)+mSharedPreferenSocio.leerdatosSocio().getId_token_socio()
                +"/recibos/"+mStatusCuentaSocios.getIdRecibo();
        //Toast.makeText(this, "URL : "+url, Toast.LENGTH_LONG).show();
        mStringRequest =
                new StringRequest(url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject mJsonObject = new JSONObject(response);

                    if(mJsonObject.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArray = mJsonObject.getJSONArray("resultado");

                        mReciver.setNum_recivo(mJsonArray.getJSONObject(0).getString("no_de_recibo"));
                        mReciver.setFecha(mJsonArray.getJSONObject(0).getString("recibo_fecha"));
                        mReciver.setTexto_main(mJsonArray.getJSONObject(0).getString("recibo_concepto"));
                        mReciver.setDinero_total(mJsonArray.getJSONObject(0).getString("recibo_monto"));


                        mReciver.setId_recibo(mJsonArray.getJSONObject(0).getInt("id"));

                        mReciver.setRecibo_facturado(mJsonArray.getJSONObject(0).getString("recibo_facturado"));

                        mReciver.setRecibo_pdf(mJsonArray.getJSONObject(0).getString("recibo_pdf"));

                        mReciver.setRecibo_factura(mJsonArray.getJSONObject(0).getString("recibo_factura"));




                        mTextViewnum.setText(mReciver.getNum_recivo());
                        mTextViewfecha.setText(mReciver.getFecha());
                        mTextViewconcepto.setText(mReciver.getTexto_main());
                        mTextViewmonto.setText("$ "+mReciver.getDinero_total());
                        new cAlertDialogProgress().closeAlertProgress(mAlertDialogPro);
                    }else
                        {
                            new cAlertDialogProgress().closeAlertProgress(mAlertDialogPro);
                            Toasty.error(ReciboDetalleActivity.this,"ERROR DE CONSULTA ",
                                    Toasty.LENGTH_LONG).show();
                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                new cAlertDialogProgress().closeAlertProgress(mAlertDialogPro);
                Toasty.warning(ReciboDetalleActivity.this,"TRY RECIVOS "+error.getMessage(),
                        Toasty.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String>stringHashMap = new HashMap<>();
                stringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                stringHashMap.put("token",new cSharedPreferenSocio(ReciboDetalleActivity.this)
                        .leerdatosSocio().getToken());
                return stringHashMap;
            }

        };;

        mRequestQueue = Volley.newRequestQueue(ReciboDetalleActivity.this);
        mRequestQueue.add(mStringRequest);



    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_views_card_pdf:
                Toasty.info(ReciboDetalleActivity.this,"OBSERVANDO PDF",Toasty.LENGTH_SHORT)
                        .show();
                Intent mIntent = new Intent(ReciboDetalleActivity.this, ViewPdfActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("pdf_url",mReciver.getRecibo_pdf());
                mIntent.putExtra("pdf_ban",1);
                startActivity(mIntent);


                break;
            case R.id.id_views_card_soli_factura:
                Toasty.info(ReciboDetalleActivity.this,"SOLICITANDO FACTURA",Toasty.LENGTH_SHORT)
                        .show();
                Uri uri = Uri.parse(mReciver.getRecibo_factura());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri );
                startActivity(intent);
                break;
        }
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