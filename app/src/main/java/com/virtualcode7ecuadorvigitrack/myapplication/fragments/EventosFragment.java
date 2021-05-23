package com.virtualcode7ecuadorvigitrack.myapplication.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
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
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.eventos.cAdapterEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.noticias.cAdapterNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.application.cApplication;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.cEventRecyclerViewEvento;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.sqlite.cSQLEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.sqlite.cSQLNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cStringMesDia;
import com.virtualcode7ecuadorvigitrack.myapplication.views.EventosViewPagerContenidoActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.NoticiasViewPagerContenidoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class EventosFragment extends Fragment implements cEventRecyclerViewEvento,View.OnClickListener
{

    private List<cEventos> mEventosArrayList = new ArrayList<>();
    private View mView;
    public static cAdapterEventos mAdapterEventos;
    private RecyclerView mRecyclerViewEventos;
    //private JsonObjectRequest mJsonObjectRequest;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private AlertDialog mAlertDialog;
    private cEventos mEventos;

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


    private int page_cont = 1;

    private  int desface_ = 0;
    private  boolean first_desplazo = true;
    private boolean first_consulta = true;

    private StaggeredGridLayoutManager mLayoutManager_;



    private SwipeRefreshLayout mSwipeRefreshLayoutEventos;

    private cSQLEventos mSqlEventos = new cSQLEventos();


    private int findFirstVisibleItemPosition =  0;
    private int findFirstCompletelyVisibleItemPosition = 0;
    private int findLastVisibleItemPosition = 0;
    private int findLastCompletelyVisibleItemPosition = 0;


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


        mCardView = mView.findViewById(R.id.id_include_evento);
        mTextViewFechaDesde = mView.findViewById(R.id.id_textview_desde_texto);
        mViewFechasContainer = mView.findViewById(R.id.id_view_ContainerFechas);


        mLayoutManager_ = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerViewEventos.setLayoutManager(mLayoutManager_);
        mRecyclerViewEventos.setHasFixedSize(true);

        initRecyclerViewEventos();

        llenarArraysListEventos();


        mCardView.setOnClickListener(this);
        mSwipeRefreshLayoutEventos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                if (cApplication.getmApplicationInstance().checkInternet()){
                    llenarArraysListEventos();
                }else {
                    mSwipeRefreshLayoutEventos.setRefreshing(false);
                }
            }
        });

        return mView;
    }

    private void initRecyclerViewEventos()
    {
        mAdapterEventos = new cAdapterEventos(mEventosArrayList,getContext(),
                mRecyclerViewEventos,this);

        /**llenar recyclerView**/

        mRecyclerViewEventos.setAdapter(mAdapterEventos);
    }

    @Override
    public void onResume() {
        //initBroadCastReciver();
        scrollEventos();
        super.onResume();
    }

    @Override
    public void onPause() {
        //getContext().unregisterReceiver(mBroadCastNetworkStatus);
        Log.e("cicloVidaFragment","NOTICIAS");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("cicloVidaFragment","NOTICIAS");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e("cicloVidaFragment","NOTICIAS");
        super.onDestroyView();
    }

    private void llenarArraysListEventos()
    {
        if (cApplication.getmApplicationInstance().checkInternet())
        {
            Log.e("pageCont","ON Network");
            consumirApiNoticias();
        }else{
            Log.e("pageCont","OFF Network");
            showAlertConsultando();
            consumirApiEventosLocalBD();
            hideAlertDialogConsultando();
        }
    }

    private void consumirApiEventosLocalBD()
    {
        List<cEventos> mList  = mSqlEventos.readEventos(page_cont);

        if (mList.size()>0)
        {
            mEventosArrayList.addAll(mList);
            controlPageCont();
        }
    }

    private void consumirApiNoticias()
    {

        showAlertConsultando();

        mStringRequest = new StringRequest(Request.Method.GET, getString(R.string.api_rest_eventos_all)+"?page="+page_cont, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.e("pageCont",String.valueOf(page_cont));

                try {
                    JSONObject mJsonObjectRes = new JSONObject(response);
                    if(mJsonObjectRes.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArray = mJsonObjectRes.getJSONArray("respuesta");
                        if(mJsonArray.length() > 0)
                        {

                            Log.e("pageCont","mayor a 0 noticias");
                            List<cEventos> mEventosListAuxiliar = new ArrayList<>();

                            for (int i=0;i<mJsonArray.length();i++){
                                JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                                cEventos oE = new cEventos();

                                oE.setNumPage(page_cont);

                                oE.setId_evento(mJsonObject.getInt("id"));
                                oE.setFecha(mJsonObject.getString("fecha_inicio"));
                                oE.setFecha_fin(mJsonObject.getString("fecha_fin"));
                                oE.setTitulo(mJsonObject.getString("titulo"));
                                oE.setUri_foto(mJsonObject.getString("url_imagen_miniatura"));
                                oE.setDireccion(mJsonObject.getString("direccion"));

                                if (!verificar(oE)) {
                                    mEventosArrayList.add(oE);
                                    mEventosListAuxiliar.add(oE);
                                }
                            }

                            mSqlEventos.InsertEventos(page_cont,mEventosArrayList);
                            controlPageCont();
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
                mSwipeRefreshLayoutEventos.setRefreshing(false);
                new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toasty.error(getContext(),error.toString()
                        ,Toasty.LENGTH_LONG).show();
                new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
                mSwipeRefreshLayoutEventos.setRefreshing(false);
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


        mRequestQueue = Volley.newRequestQueue(getContext());
        mRequestQueue.add(mStringRequest);


    }

    private void showAlertConsultando()
    {
        mAlertDialog = new cAlertDialogProgress().showAlertProgress(getContext(),
                "CONSULTANDO...",false);
        mAlertDialog.show();
    }

    private void controlPageCont()
    {
        if(page_cont<=1)
        {
            cargar_datosRecyclerView();
            page_cont++;
        }else
        {
            mAdapterEventos.notifyDataSetChanged();
            page_cont++;
            Log.e("pageCont","CONTROL PAGE CONT : "+String.valueOf(page_cont));
            desface_++;
        }
    }

    private boolean verificar(cEventos oE)
    {
        for (int i=0;i<mEventosArrayList.size();i++)
        {
            if (mEventosArrayList.get(i).getId_evento() == oE.getId_evento())
            {
                return true;
            }
        }

        return false;
    }

    private void cargar_datosRecyclerView()
    {

        if (mEventosArrayList.size()>0)
        {
            config_FirstConsulta();

            mAdapterEventos.notifyDataSetChanged();
            if(mSwipeRefreshLayoutEventos.isRefreshing())
            {mSwipeRefreshLayoutEventos.setRefreshing(false);}
        }else
        {
            Toasty.info(getContext(),"Lo sentimos no existen noticias disponibles"
                    ,Toasty.LENGTH_SHORT).show();
        }

    }

    private void config_FirstConsulta()
    {
        if (first_consulta && mEventosArrayList.size()>0)
        {

            mCardView.setVisibility(View.VISIBLE);

            mEventos = mEventosArrayList.get(0);


            if (mEventos.getFecha_fin()!= null)
            {
                if (mEventos.getFecha_fin().isEmpty() ||
                        (mEventos.getFecha().equals(mEventos.getFecha_fin())))
                {
                    ConstraintLayout.LayoutParams mLayoutParams =
                            (ConstraintLayout.LayoutParams) mViewFechasContainer.getLayoutParams();

                    mLayoutParams.horizontalBias = (float) 0.01;

                    mViewFechasContainer.setLayoutParams(mLayoutParams);

                    mTextViewFechaDesde.setText("Inicio");
                    mLinearLayoutCompatFechaFin.setVisibility(View.GONE);
                }
            }

            mTextViewFechaDestino.setText(new cStringMesDia().mes(mEventos.getFecha_fin()));
            mTextViewFechaDestinoNum.setText(new cStringMesDia().dia(mEventos.getFecha_fin()));


            mTextViewTitulo.setText(mEventos.getTitulo());
            mTextViewDireccion.setText(mEventos.getDireccion());
            mTextViewFechaInicio.setText(new cStringMesDia().mes(mEventos.getFecha()));
            mTextViewFechaInicioNum.setText(new cStringMesDia().dia(mEventos.getFecha()));


            Picasso.with(getContext()).load(mEventos.getUri_foto())
                    .error(R.drawable.img_error)
                    .placeholder(R.drawable.img_load)
                    .into(mImageViewEvento);

            mEventosArrayList.remove(0);

            first_consulta = false;
        }
    }

    private void hideAlertDialogConsultando()
    {
        if (mAlertDialog!=null && mAlertDialog.isShowing()){
            new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
        }
    }

    private void scrollEventos()
    {
        mRecyclerViewEventos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                Log.e("dy",String.valueOf(dy));

                if (dy>0){
                    StaggeredGridLayoutManager  mLayoutManager = ((StaggeredGridLayoutManager)mRecyclerViewEventos.getLayoutManager());
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

                    if (mLayoutManager.getItemCount()-desface_ > 0){
                        scrollReadEventos(mLayoutManager,findLastCompletelyVisibleItemPosition);
                    }
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void scrollReadEventos(StaggeredGridLayoutManager mLayoutManager,
                                   int findLastCompletelyVisibleItemPosition)
    {
        if(mAlertDialog!=null && !mAlertDialog.isShowing())
        {
            Log.e("desface",String.valueOf(desface_));
            Log.e("comparacion",String.valueOf(findLastCompletelyVisibleItemPosition) +"  >= "+ String.valueOf(mLayoutManager.getItemCount()-desface_));

            Log.e("PageTotal",String.valueOf(page_cont));

            if (first_desplazo)
            {
                /**llamado nuevamente**/
                llenarArraysListEventos();
            }else
            {
                if(findLastCompletelyVisibleItemPosition >= mLayoutManager.getItemCount()-desface_)
                {
                    llenarArraysListEventos();
                }
            }

            first_desplazo = false;

        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_include_evento:


                Intent intent = new Intent(getActivity(), EventosViewPagerContenidoActivity.class);

                intent.putExtra("eventos", (Serializable) mEventosArrayList);
                intent.putExtra("evento", (Serializable) mEventos);
                intent.putExtra("posicion",0);
                startActivity(intent);


                break;
            case R.id.id_cargar_mas:
                page_cont++;
                llenarArraysListEventos();
                break;
        }
    }

    @Override
    public void onClickEvento(int pos)
    {
        Intent intent = new Intent(getActivity(), EventosViewPagerContenidoActivity.class);

        intent.putExtra("eventos", (Serializable) mEventosArrayList);
        intent.putExtra("evento", (Serializable) mEventos);
        intent.putExtra("posicion",pos);
        startActivity(intent);
    }
}