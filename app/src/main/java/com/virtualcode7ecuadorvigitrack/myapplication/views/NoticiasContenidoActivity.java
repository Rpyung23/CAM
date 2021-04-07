package com.virtualcode7ecuadorvigitrack.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.noticias.cAdapterNoticiasDetailsPicture;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class NoticiasContenidoActivity extends AppCompatActivity
{
    private cNoticias oNoticias;
    private cAdapterNoticiasDetailsPicture mAdapterNoticiasDetailsPicture;
    private RecyclerView mRecyclerView;
    private TextView mTextViewFecha;
    private TextView mTextViewContenido;
    private JsonObjectRequest mJsonObjectRequest;
    private RequestQueue mRequestQueue;
    private ImageView mImageView;
    private AlertDialog mAlertDialog;
    private TextView mTextViewNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_contenido);
        mRecyclerView = findViewById(R.id.id_recyclerViewNoticiasFotosDetails);
        mTextViewFecha = findViewById(R.id.id_textview_fecha_noticia_details);
        mTextViewContenido = findViewById(R.id.id_textview_contenido);
        mImageView = findViewById(R.id.id_image_noticia_details);
        mTextViewNoticia = findViewById(R.id.id_titulo);


        oNoticias = (cNoticias) getIntent().getSerializableExtra("oNoticia");
        new cToolbar().show(NoticiasContenidoActivity.this,"",true,0);


        mAlertDialog = new cAlertDialogProgress().showAlertProgress(NoticiasContenidoActivity.this,
                "CONSULTANDO...",false);
        mAlertDialog.show();

        mTextViewNoticia.setText(oNoticias.getTitulo());

        consumirapirest();



    }

    private void consumirapirest()
    {
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.api_rest_noticias_id)+oNoticias.getId_noticias(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if(response.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArray = response.getJSONArray("resultado");
                        JSONObject mJsonObject = mJsonArray.getJSONObject(0);
                        mTextViewFecha.setText(" "+oNoticias.getFecha());
                        Picasso.with(getApplicationContext())
                                .load(mJsonObject.getString("url_imagen_principal"))
                                .placeholder(R.drawable.img_load).error(R.drawable.img_error)
                                .into(mImageView);

                        mTextViewContenido.setText(Html.fromHtml(mJsonObject.getString("contenido")));

                        ArrayList<String> urls = new ArrayList<>();

                        urls.add(oNoticias.getmUriPicturePrincipalNoticia());
                        urls.add(mJsonObject.getString("url_imagen_principal"));

                        oNoticias.setmUriArrayListGaleriaNoticia(urls);


                        mAdapterNoticiasDetailsPicture = new
                                cAdapterNoticiasDetailsPicture(oNoticias.getmUriArrayListGaleriaNoticia(),
                                NoticiasContenidoActivity.this);

                        mRecyclerView.setAdapter(mAdapterNoticiasDetailsPicture);


                    }else
                        {
                            Toasty.warning(getApplicationContext(),"No se puedo realizar la consulta"
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
                Toasty.error(getApplicationContext(),
                        error.getMessage(),Toasty.LENGTH_LONG).show();
                new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
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
}