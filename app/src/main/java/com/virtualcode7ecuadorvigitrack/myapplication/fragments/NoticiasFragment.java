package com.virtualcode7ecuadorvigitrack.myapplication.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;
import com.virtualcode7ecuadorvigitrack.myapplication.views.NoticiasContenidoActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.NoticiasViewPagerContenidoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class NoticiasFragment extends Fragment implements View.OnClickListener
{
    private View mView;
    private View mViewCardPrincipal;
    private ImageView mImageView;
    private TextView mTextViewTituloNoticia;
    private TextView mTextViewFechaNoticia;
    private RecyclerView mRecyclerViewNoticias;
    private List<cNoticias> mNoticiasArrayList = new ArrayList<>();
    private cAdapterNoticias mAdapterNoticias;
    private cNoticias mNoticias = new cNoticias();
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private AlertDialog mAlertDialog;
    private MaterialButton materialButton;

    private int page_cont = 1;

    private  int desface_ = 0;
    private  boolean first_desplazo = true;


    private StaggeredGridLayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_noticias, container, false);
        mImageView = mView.findViewById(R.id.id_image_noticia);
        mTextViewTituloNoticia = mView.findViewById(R.id.id_titulo_noticia);
        mTextViewFechaNoticia = mView.findViewById(R.id.id_fecha_noticia);
        mRecyclerViewNoticias = mView.findViewById(R.id.id_recyclerViewNoticias);
        mViewCardPrincipal = mView.findViewById(R.id.card_principal);
        materialButton = mView.findViewById(R.id.id_cargar_mas);
        mViewCardPrincipal.setOnClickListener(this);
        materialButton.setOnClickListener(this);

        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerViewNoticias.setLayoutManager(mLayoutManager);
        mRecyclerViewNoticias.setHasFixedSize(true);

        llenarArraysListNoticias();

        return  mView;
    }


    private void abrirActivityNoticia(cNoticias oNoticias)
    {
        Intent mIntent = new Intent(getActivity(), NoticiasContenidoActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra("oNoticia", oNoticias);
        startActivity(mIntent);
    }

    private void llenarArraysListNoticias()
    {


            mAlertDialog = new cAlertDialogProgress().showAlertProgress(getContext(),
                    "CONSULTANDO...",false);
            mAlertDialog.show();

            mStringRequest = new StringRequest(Request.Method.GET, getString(R.string.api_rest_noticias_all)+"?page="+page_cont, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    try {
                        JSONObject mJsonObjectRes = new JSONObject(response);
                        if(mJsonObjectRes.getString("codigo").equals("200"))
                        {
                            JSONArray mJsonArray = mJsonObjectRes.getJSONArray("resultado");
                            if(mJsonArray.length() > 0)
                            {
                                for (int i=0;i<mJsonArray.length();i++)
                                {
                                    JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                                    cNoticias oN = new cNoticias();
                                    oN.setId_noticias(mJsonObject.getInt("id"));
                                    oN.setTitulo(mJsonObject.getString("titulo"));
                                    oN.setFecha(mJsonObject.getString("fecha_publicacion"));
                                    oN.setmUriPicturePrincipalNoticia(mJsonObject.getString("url_imagen_miniatura"));
                                    oN.setTextoNoticia(mJsonObject.getString("descripcion_corta"));

                                    if (!verificar(oN))
                                    {
                                        mNoticiasArrayList.add(oN);
                                    }
                                }


                                if(page_cont<=1)
                                {
                                    cargar_datosRecyclerView();
                                    page_cont++;
                                }else
                                {
                                    mAdapterNoticias.notifyDataSetChanged();
                                    page_cont++;
                                    desface_++;
                                    Log.e("NotifyDataSetChanged","Add plus");
                                }

                            }


                        }else
                        {
                            Toasty.warning(getContext(),"No se puede mostrar los datos"
                                    ,Toasty.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toasty.warning(getContext(),e.getMessage()
                                ,Toasty.LENGTH_LONG).show();
                    }
                    new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toasty.error(getContext(),error.toString()
                            ,Toasty.LENGTH_LONG).show();
                    new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
                }
            }){
                @Override
                protected HashMap<String, String> getParams() throws AuthFailureError
                {
                    HashMap<String,String> oMap = new HashMap<>();
                    oMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                    return oMap;
                }

                @Override
                public HashMap<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> oMap = new HashMap<>();
                    oMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");

                    return oMap;
                }
            };
        /*
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getString(R.string.api_rest_noticias_all)
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if(response.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArray = response.getJSONArray("resultado");
                        for (int i=0;i<mJsonArray.length();i++)
                        {
                            JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                            cNoticias oN = new cNoticias();
                            oN.setId_noticias(mJsonObject.getInt("id"));
                            oN.setTitulo(mJsonObject.getString("titulo"));
                            oN.setFecha(mJsonObject.getString("fecha_publicacion"));
                            oN.setmUriPicturePrincipalNoticia(mJsonObject.getString("url_imagen_miniatura"));
                            oN.setTextoNoticia(mJsonObject.getString("descripcion_corta"));

                            if (!verificar(oN))
                            {
                                mNoticiasArrayList.add(oN);
                            }
                        }
                        cargar_datosRecyclerView();
                    }else
                        {
                            Toasty.warning(getContext(),"No se puede mostrar los datos"
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
                Toasty.error(getContext(),error.getMessage()
                        ,Toasty.LENGTH_LONG).show();
                new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
            }
        });*/

            mRequestQueue = Volley.newRequestQueue(getContext());
            mRequestQueue.add(mStringRequest);

        /*
        for (int i=1;i<10;i++)
        {
            cNoticias oN = new cNoticias();
            oN.setTitulo("Noticia "+i);
            oN.setFecha("0"+i+"/01/2021");
            oN.setTextoNoticia(getContext().getString(R.string.loren_ipsum));
            oN.setmUriPicturePrincipalNoticia("https://img.olympicchannel.com/images/image/private/t_16-9_1280/primary/tdyjbhcmhsbadlxopda0");
            ArrayList<String> mUriArrayList = new ArrayList<>();
            for (int j = 0;j<5;j++)
            {
                String oUri = "https://media3.minutemediacdn.com/process?url=http%3A%2F%2Fftbpro-post-images.s3-eu-west-1.amazonaws.com%2Fproduction%2F56511d0005df7acefb000003.jpeg&filters%5Bcrop%5D%5Bw%5D=0.560639070442992&filters%5Bcrop%5D%5Bh%5D=0.9975433460421206&filters%5Bcrop%5D%5Bo_x%5D=0.2696078431372549&filters%5Bcrop%5D%5Bo_y%5D=0.0&filters%5Bresize%5D%5Bw%5D=912&filters%5Bresize%5D%5Bh%5D=516&filters%5Bresize%5D%5Bgravity%5D=Center&filters%5Bquality%5D%5Btarget%5D=80&type=.jpg";
                mUriArrayList.add(oUri);
            }
            oN.setmUriArrayListGaleriaNoticia(mUriArrayList);
            mNoticiasArrayList.add(oN);
        }*/

    }

    private boolean verificar(cNoticias oN)
    {
        for (int i=0;i<mNoticiasArrayList.size();i++)
        {
            if (mNoticiasArrayList.get(i).getId_noticias() == oN.getId_noticias())
            {
                return true;
            }
        }

        return false;
    }

    private void cargar_datosRecyclerView()
    {
        if (mNoticiasArrayList.size()>0)
        {
            mNoticias = mNoticiasArrayList.get(0);

            mTextViewTituloNoticia.setText(mNoticiasArrayList.get(0).getTitulo());
            mTextViewFechaNoticia.setText(mNoticiasArrayList.get(0).getFecha());
            Log.e("URI",mNoticiasArrayList.get(0).getmUriPicturePrincipalNoticia().toString());
            Picasso.with(getContext()).load(mNoticiasArrayList.get(0).getmUriPicturePrincipalNoticia())
                    .error(R.drawable.img_error)
                    .placeholder(R.drawable.img_load)
                    .into(mImageView);

            mNoticiasArrayList.remove(0);

            if (mNoticiasArrayList.size()>0)
            {
                /****/
                mAdapterNoticias = new cAdapterNoticias(getContext(), mNoticiasArrayList,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                //abrirActivityNoticia(mNoticiasArrayList.get(mRecyclerViewNoticias.getChildAdapterPosition(view)));
                                int pos = mRecyclerViewNoticias.getChildAdapterPosition(view);
                                pos+=1;

                                Intent intent = new Intent(getActivity(), NoticiasViewPagerContenidoActivity.class);
                                intent.putExtra("noticias", (Serializable) mNoticiasArrayList);
                                intent.putExtra("noticia", (Serializable) mNoticias);
                                intent.putExtra("posicion",pos);
                                startActivity(intent);

                            }
                        });

                /**llenar recyclerView**/

                mRecyclerViewNoticias.setAdapter(mAdapterNoticias);

            }
        }else
        {
            Toasty.info(getContext(),"Lo sentimos no existen noticias disponibles"
                    ,Toasty.LENGTH_SHORT).show();
        }



        mRecyclerViewNoticias.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                StaggeredGridLayoutManager mLayoutManager = ((StaggeredGridLayoutManager)mRecyclerViewNoticias.getLayoutManager());
                //int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();

                    int findFirstVisibleItemPosition =  mLayoutManager.findFirstVisibleItemPositions(null)[0];
                    int findFirstCompletelyVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPositions(null)[0];
                    int findLastVisibleItemPosition = mLayoutManager.findLastVisibleItemPositions(null)[0];
                    int findLastCompletelyVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPositions(null)[0];

                    Log.e("total",String.valueOf(mLayoutManager.getItemCount()));

                    Log.e("findFirstViItemPosition",String.valueOf(findFirstVisibleItemPosition));
                    Log.e("findFirstComViItemPos",String.valueOf(findFirstCompletelyVisibleItemPosition));
                    Log.e("findLastVisItemPos",String.valueOf(findLastVisibleItemPosition));
                    Log.e("findLastComViItemPos",String.valueOf(findLastCompletelyVisibleItemPosition));



                if(mAlertDialog!=null && !mAlertDialog.isShowing())
                {
                    Log.e("desface",String.valueOf(desface_));
                    Log.e("comparacion",String.valueOf(mLayoutManager.getItemCount()-desface_)+"  == "+ String.valueOf(findLastCompletelyVisibleItemPosition));

                    Log.e("PageTotal",String.valueOf(page_cont));

                    if (first_desplazo)
                    {
                        /**llamado nuevamente**/
                        llenarArraysListNoticias();
                    }else
                        {
                            if(mLayoutManager.getItemCount()-desface_ == findLastCompletelyVisibleItemPosition)
                            {
                                llenarArraysListNoticias();

                            }
                        }

                    first_desplazo = false;

                }



                    super.onScrolled(recyclerView, dx, dy);
            }
        });



    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.card_principal:
                //abrirActivityNoticia(mNoticias);

                Intent intent = new Intent(getActivity(), NoticiasViewPagerContenidoActivity.class);
                //mNoticiasArrayList.add(0,mNoticias);
                intent.putExtra("noticias", (Serializable) mNoticiasArrayList);
                intent.putExtra("noticia", (Serializable) mNoticias);
                intent.putExtra("posicion",0);
                startActivity(intent);


                break;
            case R.id.id_cargar_mas:
                page_cont++;
                llenarArraysListNoticias();
                       break;
        }
    }
}