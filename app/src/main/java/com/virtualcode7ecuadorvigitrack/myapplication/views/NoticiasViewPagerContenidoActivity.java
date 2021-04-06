package com.virtualcode7ecuadorvigitrack.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterNoticiasDetailsPicture;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterNoticiasDetailsScroll;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class NoticiasViewPagerContenidoActivity extends AppCompatActivity
{
    private ViewPager2 mViewPager2Noticias;
    private ArrayList<cNoticias> mNoticiasArrayList;
    private int posInicial = 0;
    private cAdapterNoticiasDetailsScroll mAdapterNoticias;
    private JsonObjectRequest mJsonObjectRequest;
    private RequestQueue mRequestQueue;
    private AlertDialog mAlertDialog;
    private cNoticias oN =  new cNoticias();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_view_pager_contenido);



        mViewPager2Noticias = findViewById(R.id.viewpager2noticias);

        posInicial = getIntent().getIntExtra("posicion",0);
        oN = (cNoticias) getIntent().getSerializableExtra("noticia");
        new cToolbar().show(NoticiasViewPagerContenidoActivity.this,"",true,0);
        mNoticiasArrayList = (ArrayList<cNoticias>) getIntent().getSerializableExtra("noticias");

        mNoticiasArrayList.add(0,oN);

        mAdapterNoticias = new cAdapterNoticiasDetailsScroll(NoticiasViewPagerContenidoActivity.this
                ,mNoticiasArrayList);

        mViewPager2Noticias.setAdapter(mAdapterNoticias);


        mViewPager2Noticias.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
        {
            @Override
            public void onPageSelected(int position)
            {
                readNoticiaApiRest(position);
            }
        });

        mViewPager2Noticias.setCurrentItem(posInicial);

        mAdapterNoticias.notifyDataSetChanged();

    }

    private void readNoticiaApiRest(int position)
    {
        mAlertDialog = new cAlertDialogProgress().showAlertProgress(NoticiasViewPagerContenidoActivity.this,
            "CONSULTANDO...",false);
        mAlertDialog.show();
        //Toast.makeText(this, "pos : "+position, Toast.LENGTH_SHORT).show();

        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.api_rest_noticias_id)+mNoticiasArrayList.get(position).getId_noticias(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if(response.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArray = response.getJSONArray("resultado");
                        JSONObject mJsonObject = mJsonArray.getJSONObject(0);


                        mNoticiasArrayList.get(position).setTextoNoticia(mJsonObject.getString("contenido"));



                        /*mTextViewFecha.setText(" "+mNoticiasArrayList.get(position).getFecha());
                        Picasso.with(getApplicationContext())
                                .load(mJsonObject.getString("url_imagen_principal"))
                                .placeholder(R.drawable.img_load).error(R.drawable.img_error)
                                .into(mImageView);*/

                        /*View mView = mViewPager2Noticias.getChildAt(position);

                        TextView mTextView = mView.findViewById(R.id.id_textview_contenido);

                        mTextView.setText(Html.fromHtml(mJsonObject.getString("contenido")));*/

                        /*ArrayList<String> urls = new ArrayList<>();

                        urls.add(oNoticias.getmUriPicturePrincipalNoticia());
                        urls.add(mJsonObject.getString("url_imagen_principal"));*/

                        /*mNoticiasArrayList.get(position).setmUriArrayListGaleriaNoticia(urls);*/


                        /*mAdapterNoticiasDetailsPicture = new
                                cAdapterNoticiasDetailsPicture(oNoticias.getmUriArrayListGaleriaNoticia(),
                                NoticiasContenidoActivity.this);*/

                        /*mRecyclerView.setAdapter(mAdapterNoticiasDetailsPicture);*/

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

    @Override
    public void onBackPressed() {
        finish();
    }
}