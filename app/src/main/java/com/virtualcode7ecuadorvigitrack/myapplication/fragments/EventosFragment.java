package com.virtualcode7ecuadorvigitrack.myapplication.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cStringMesDia;
import com.virtualcode7ecuadorvigitrack.myapplication.views.EventosContenidoActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.EventosViewPagerContenidoActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.NoticiasContenidoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class EventosFragment extends Fragment
{

    private ArrayList<cEventos> mEventosArrayList = new ArrayList<>();
    private View mView;
    private cAdapterEventos mAdapterEventos;
    private RecyclerView mRecyclerViewEventos;
    private JsonObjectRequest mJsonObjectRequest;
    private RequestQueue mRequestQueue;
    private AlertDialog mAlertDialog;
    private cEventos mNoticias;
    private ImageView mImageViewEvento;
    private TextView mTextViewTitulo;
    private TextView mTextViewDireccion;
    private TextView mTextViewFechaInicio;
    private TextView mTextViewFechaDestino;
    private TextView mTextViewFechaInicioNum;
    private TextView mTextViewFechaDestinoNum;
    private LinearLayoutCompat mLinearLayoutCompatFechaFin;
    private TextView mTextViewFechaDesde;
    private View mViewFechasContainer;
    private View mCardView;
    private int cont_pager = 1 ;
    private MaterialButton materialButtonMoreEventos;
    private SwipeRefreshLayout mSwipeRefreshLayoutEventos;

    private  int desface_ = 0;
    private  boolean first_desplazo = true;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_eventos, container, false);
        mRecyclerViewEventos = mView.findViewById(R.id.id_recyclerViewEventos);
        mImageViewEvento = mView.findViewById(R.id.id_image_evento);
        mTextViewTitulo = mView.findViewById(R.id.id_textview_titulo);
        mTextViewDireccion = mView.findViewById(R.id.id_textview_direccion);
        mTextViewFechaInicio = mView.findViewById(R.id.id_textview_fecha_inicio);
        mTextViewFechaDestino = mView.findViewById(R.id.id_textview_fecha_fin);
        mTextViewFechaInicioNum = mView.findViewById(R.id.id_textview_fecha_inicio_num);
        mTextViewFechaDestinoNum = mView.findViewById(R.id.id_textview_fecha_fin_num);
        mLinearLayoutCompatFechaFin = mView.findViewById(R.id.id_linear_layout_fecha_fin);
        mSwipeRefreshLayoutEventos = mView.findViewById(R.id.swipeRefreshEventos);
        materialButtonMoreEventos = mView.findViewById(R.id.id_cargar_mas);

        mCardView = mView.findViewById(R.id.id_include_evento);
        mTextViewFechaDesde = mView.findViewById(R.id.id_textview_desde_texto);
        mViewFechasContainer = mView.findViewById(R.id.id_view_ContainerFechas);


        mRecyclerViewEventos.setHasFixedSize(true);

        llenarArraysListEventos();

        materialButtonMoreEventos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });


        mSwipeRefreshLayoutEventos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                llenarArraysListEventos();
            }
        });


        return mView;
    }

    private void abrirActivityEventos(cEventos oEventos)
    {
        Intent mIntent = new Intent(getActivity(), EventosContenidoActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra("oEvento", oEventos);
        startActivity(mIntent);
    }




    private void llenarArraysListEventos()
    {
        mAlertDialog = new cAlertDialogProgress().showAlertProgress(getContext(),
                "CONSULTANDO...",false);
        mAlertDialog.show();

        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.api_rest_eventos_all)+"?page="+cont_pager, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if (response.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArray = response.getJSONArray("respuesta");

                        if(mJsonArray.length()>0)
                        {
                            for (int i=0;i<mJsonArray.length();i++)
                            {
                                JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                                cEventos oE = new cEventos();
                                oE.setId_evento(mJsonObject.getInt("id"));
                                oE.setFecha(mJsonObject.getString("fecha_inicio"));
                                oE.setFecha_fin(mJsonObject.getString("fecha_fin"));
                                oE.setTitulo(mJsonObject.getString("titulo"));
                                oE.setUri_foto(mJsonObject.getString("url_imagen_miniatura"));
                                oE.setDireccion(mJsonObject.getString("direccion"));


                                Log.e("FechasEvento",oE.getFecha()+"  -->  "+oE.getFecha_fin());


                                mEventosArrayList.add(oE);
                            }

                            if(cont_pager<=1)
                            {
                                llenarRecyclerView();
                                cont_pager++;

                                mCardView.setVisibility(View.VISIBLE);

                            }else
                            {
                                mAdapterEventos.notifyDataSetChanged();
                                cont_pager++;
                                desface_++;
                            }

                        }/*else
                            {
                                Toasty.info(getContext(),"No más existen eventos disponibles",
                                        Toasty.LENGTH_LONG).show();

                            }*/

                    }else
                        {
                            Toasty.warning(getContext(),"No se pudo encontrar los eventos",
                                    Toasty.LENGTH_LONG).show();
                        }
                } catch (JSONException e) {
                    Toasty.warning(getContext(),e.getMessage(),
                            Toasty.LENGTH_LONG).show();
                }

                mSwipeRefreshLayoutEventos.setRefreshing(false);
                new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                mSwipeRefreshLayoutEventos.setRefreshing(false);
                Toasty.error(getContext(),error.getMessage(),Toasty.LENGTH_LONG).show();
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

        mRequestQueue = Volley.newRequestQueue(getContext());
        mRequestQueue.add(mJsonObjectRequest);

        /*
        for (int i=1;i<9;i++)
        {
            cEventos oE = new cEventos();
            oE.setFecha("12/0"+i+"/2021");
            if (i%2==0)
            {
                oE.setFecha_fin("12/0"+i+"/2021");
            }
            oE.setTitulo("Titulo del evento "+i);
            oE.setDireccion("Dirección Mexico "+i);
            oE.setAcerca_de(getContext().getString(R.string.loren_xxipsum));
            oE.setUri_foto("https://media-cdn.tripadvisor.com/media/photo-s/08/60/37/47/greenfield-s-hotel.jpg");
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (int j=0;j<6;j++)
            {
                stringArrayList.add("-Asadipscing elitr, sed diam nonumy eirmod.");
            }
            oE.setmItinerarioStringArrayList(stringArrayList);
            mEventosArrayList.add(oE);
        }*/
    }

    private void llenarRecyclerView()
    {

        if (mEventosArrayList.size()>0)
        {
            mNoticias = mEventosArrayList.get(0);


            if(mEventosArrayList.get(0).getFecha().equals(mEventosArrayList.get(0).getFecha_fin()))
            {
                /****/

                mTextViewFechaDesde.setText("Inicio");
                //mTextViewFechaDesde.setVisibility(View.GONE);

                mLinearLayoutCompatFechaFin.setVisibility(View.GONE);

                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mViewFechasContainer.getLayoutParams();
                params.leftMargin = 20;
                params.gravity = Gravity.CENTER_VERTICAL;

                mViewFechasContainer.setLayoutParams(params);
                //holder.mViewFechasContainer.;
            }else
                {
                    mTextViewFechaDesde.setVisibility(View.VISIBLE);
                }

            //mLinearLayoutCompatFechaFin.setVisibility(View.VISIBLE);
            mTextViewFechaDestino.setText(new cStringMesDia().mes(mNoticias.getFecha_fin()));
            mTextViewFechaDestinoNum.setText(new cStringMesDia().dia(mNoticias.getFecha_fin()));



            if(mNoticias.getTitulo().length()>16)
            {
                mTextViewTitulo.setText(mNoticias.getTitulo().substring(0,11)+"");
            }else
            {
                mTextViewTitulo.setText(mNoticias.getTitulo());
            }
            //holder.mTextViewTitulo.setText(mEventosArrayList.get(position).getTitulo());
            mTextViewDireccion.setText(mNoticias.getDireccion());
            mTextViewFechaInicio.setText(new cStringMesDia().mes(mNoticias.getFecha()));
            mTextViewFechaInicioNum.setText(new cStringMesDia().dia(mNoticias.getFecha()));


            Picasso.with(getContext()).load(mNoticias.getUri_foto())
                    .error(R.drawable.img_error)
                    .placeholder(R.drawable.img_load)
                    .into(mImageViewEvento);

            mEventosArrayList.remove(0);
            if (mEventosArrayList.size()>0)
            {
                mAdapterEventos = new cAdapterEventos(mEventosArrayList, getContext(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        //abrirActivityEventos(mEventosArrayList.get(mRecyclerViewEventos.getChildAdapterPosition(view)));
                        int pos = mRecyclerViewEventos.getChildAdapterPosition(view);
                        pos +=1;

                        Intent mIntent = new Intent(getActivity(), EventosViewPagerContenidoActivity.class);
                        mIntent.putExtra("eventos",(Serializable) mEventosArrayList);
                        mIntent.putExtra("evento",(Serializable) mNoticias);
                        mIntent.putExtra("posicion",pos);
                        startActivity(mIntent);

                    }
                });
                mRecyclerViewEventos.setAdapter(mAdapterEventos);

            }
        }/*else
        {
            Toasty.info(getContext(),"Lo sentimos no existen eventos disponibles"
                    ,Toasty.LENGTH_SHORT).show();
        }*/



        mRecyclerViewEventos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                StaggeredGridLayoutManager mLayoutManager = ((StaggeredGridLayoutManager)mRecyclerViewEventos.getLayoutManager());
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

                    Log.e("PageTotal",String.valueOf(cont_pager));

                    if (first_desplazo)
                    {
                        /**llamado nuevamente**/
                        llenarArraysListEventos();
                    }else
                    {
                        if(mLayoutManager.getItemCount()-desface_ == findLastCompletelyVisibleItemPosition)
                        {
                            llenarArraysListEventos();
                        }
                    }

                    first_desplazo = false;

                }



                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @Override
    public void onResume()
    {
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //abrirActivityEventos(mNoticias);
                Intent mIntent = new Intent(getActivity(), EventosViewPagerContenidoActivity.class);
                mIntent.putExtra("eventos",(Serializable) mEventosArrayList);
                mIntent.putExtra("evento",(Serializable) mNoticias);
                mIntent.putExtra("posicion",0);
                startActivity(mIntent);



            }
        });
        super.onResume();
    }
}