package com.virtualcode7ecuadorvigitrack.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class EventosContenidoActivity extends AppCompatActivity
{
    private TextView mTextViewTitulo;
    private TextView mTextViewFecha;
    private TextView mTextViewDirec;
    private TextView mTextViewContenido;
    private ImageView mImageView;
    private cEventos oEventos;

    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;

    private AlertDialog mAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_contenido);
        new cToolbar().show(EventosContenidoActivity.this,"",true,0);

        oEventos = (cEventos) getIntent().getSerializableExtra("oEvento");

        mTextViewTitulo = findViewById(R.id.id_titulo_evento);
        mTextViewFecha = findViewById(R.id.textView_date_event);
        mTextViewDirec = findViewById(R.id.textView2);
        mTextViewContenido = findViewById(R.id.textView_about_event);
        mImageView = findViewById(R.id.id_img_principal_evento);

        mAlertDialog = new cAlertDialogProgress().showAlertProgress(EventosContenidoActivity.this,
                "CONSULTANDO...",false);
        mAlertDialog.show();
    }

    @Override
    protected void onPostResume()
    {
        mTextViewTitulo.setText(oEventos.getTitulo());
        mTextViewDirec.setText(oEventos.getDireccion());
        mTextViewFecha.setText(oEventos.getFecha());

        Picasso.with(getApplicationContext())
                .load(oEventos.getUri_foto())
                .placeholder(R.drawable.img_load).error(R.drawable.img_error)
                .into(mImageView);

        consumirApiRestDetailsEvent();
        super.onPostResume();
    }

    private void consumirApiRestDetailsEvent()
    {

        mStringRequest = new StringRequest(Request.Method.GET, getString(R.string.api_rest_eventos_id) + oEventos.getId_evento(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObjectRes = new JSONObject(response);
                    if (jsonObjectRes.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArray = jsonObjectRes.getJSONArray("resultado");
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
}