package com.virtualcode7ecuadorvigitrack.myapplication.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.noticias.cAdapterNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.application.cApplication;
import com.virtualcode7ecuadorvigitrack.myapplication.broadcast.cBroadCastNetworkStatus;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.cCheckNetwork;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.cEventRecyclerViewNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.sqlite.cSQLNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.views.NoticiasViewPagerContenidoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class NoticiasFragment extends Fragment implements View.OnClickListener,
        cCheckNetwork, cEventRecyclerViewNoticias {

    private View mView;
    private View mViewCardPrincipal;
    private ImageView mImageView;
    private TextView mTextViewTituloNoticia;
    private TextView mTextViewFechaNoticia;
    private RecyclerView mRecyclerViewNoticias;

    private cAdapterNoticias mAdapterNoticias;

    private List<cNoticias> mNoticiasList = new ArrayList<>();
    private cNoticias mNoticias_ = new cNoticias();

    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private AlertDialog mAlertDialog;
    private MaterialButton materialButton;

    private int page_cont = 1;

    private  int desface_ = 0;
    private  boolean first_desplazo = true;
    private boolean first_consulta = true;

    private StaggeredGridLayoutManager mLayoutManager_;



    private SwipeRefreshLayout mSwipeRefreshLayoutNoticias;

    private cSQLNoticias mSqlNoticias = new cSQLNoticias();


    private cBroadCastNetworkStatus mBroadCastNetworkStatus;



    private int findFirstVisibleItemPosition =  0;
    private int findFirstCompletelyVisibleItemPosition = 0;
    private int findLastVisibleItemPosition = 0;
    private int findLastCompletelyVisibleItemPosition = 0;


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
        mSwipeRefreshLayoutNoticias = mView.findViewById(R.id.swipeRefreshNoticias);

        mBroadCastNetworkStatus = new cBroadCastNetworkStatus();


        mViewCardPrincipal.setOnClickListener(this);
        materialButton.setOnClickListener(this);

        mLayoutManager_ = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerViewNoticias.setLayoutManager(mLayoutManager_);
        mRecyclerViewNoticias.setHasFixedSize(true);

        initRecyclerViewNoticias();

        //llenarArraysListNoticias();

        mSwipeRefreshLayoutNoticias.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                if (cApplication.getmApplicationInstance().checkInternet()){
                    llenarArraysListNoticias();
                }else {
                    mSwipeRefreshLayoutNoticias.setRefreshing(false);
                }
            }
        });


        return  mView;
    }

    private void initRecyclerViewNoticias()
    {
        mAdapterNoticias = new cAdapterNoticias(getContext(),mNoticiasList,this
                ,mRecyclerViewNoticias);

        /**llenar recyclerView**/

        mRecyclerViewNoticias.setAdapter(mAdapterNoticias);
    }

    @Override
    public void onResume() {
        initBroadCastReciver();
        scrollNoticias();
        super.onResume();
    }

    private void llenarArraysListNoticias()
    {
        if (cApplication.getmApplicationInstance().checkInternet())
        {
            consumirApiNoticias();
        }else{
            showAlertConsultando();
            consumirApiNoticiasLocalBD();
            hideAlertDialogConsultando();
        }
    }

    private void consumirApiNoticiasLocalBD()
    {
        mNoticiasList = mSqlNoticias.readNoticias(page_cont,mNoticiasList);
        if (mNoticiasList.size()>0){
            controlPageCont();
        }
    }

    private void consumirApiNoticias()
    {

        showAlertConsultando();

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
                            Log.e("pageCont",String.valueOf(page_cont));

                            List<cNoticias> mNoticiasListAuxiliar = new ArrayList<>();

                            for (int i=0;i<mJsonArray.length();i++)
                            {
                                JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                                cNoticias oN = new cNoticias();
                                oN.setNumPage(page_cont);
                                oN.setId_noticias(mJsonObject.getInt("id"));
                                oN.setTitulo(mJsonObject.getString("titulo"));
                                oN.setFecha(mJsonObject.getString("fecha_publicacion"));
                                //oN.setTextoNoticia(mJsonObject.getString("contenido"));
                                oN.setmUriPicturePrincipalNoticia(mJsonObject.getString("url_imagen_miniatura"));
                                oN.setDescriptionCorta(mJsonObject.getString("descripcion_corta"));

                                if (!verificar(oN))
                                {
                                    mNoticiasList.add(oN);
                                    mNoticiasListAuxiliar.add(oN);
                                }
                            }

                            mSqlNoticias.InsertNoticias(page_cont,mNoticiasListAuxiliar);

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
                mSwipeRefreshLayoutNoticias.setRefreshing(false);
                new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toasty.error(getContext(),error.toString()
                        ,Toasty.LENGTH_LONG).show();
                new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
                mSwipeRefreshLayoutNoticias.setRefreshing(false);
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
            mAdapterNoticias.notifyDataSetChanged();
            page_cont++;
            desface_++;
            Log.e("NotifyDataSetChanged","Add plus");
        }
    }

    private boolean verificar(cNoticias oN)
    {
        for (int i=0;i<mNoticiasList.size();i++)
        {
            if (mNoticiasList.get(i).getId_noticias() == oN.getId_noticias())
            {
                return true;
            }
        }

        return false;
    }

    private void cargar_datosRecyclerView()
    {

        if (mNoticiasList.size()>0)
        {
            config_FirstConsulta();

            mAdapterNoticias.notifyDataSetChanged();
            if(mSwipeRefreshLayoutNoticias.isRefreshing())
            {mSwipeRefreshLayoutNoticias.setRefreshing(false);}
        }else
        {
            Toasty.info(getContext(),"Lo sentimos no existen noticias disponibles"
                    ,Toasty.LENGTH_SHORT).show();
        }

    }

    private void config_FirstConsulta()
    {
        if (first_consulta)
        {
            mNoticias_ = mNoticiasList.get(0);

            mTextViewTituloNoticia.setText(mNoticiasList.get(0).getTitulo());
            mTextViewFechaNoticia.setText(mNoticiasList.get(0).getFecha());
            //Log.e("URI",cApplication.getmApplicationInstance().getmNoticiasList().get(0).getmUriPicturePrincipalNoticia().toString());
            Picasso.with(getContext()).load(mNoticiasList.get(0).getmUriPicturePrincipalNoticia())
                    .error(R.drawable.img_error)
                    .placeholder(R.drawable.img_load)
                    .into(mImageView);

            mNoticiasList.remove(0);

            if (mViewCardPrincipal.getVisibility() == View.GONE)
            {
                mViewCardPrincipal.setVisibility(View.VISIBLE);
            }
            first_consulta = false;
        }
    }

    private void hideAlertDialogConsultando()
    {
        if (mAlertDialog!=null && mAlertDialog.isShowing()){
            new cAlertDialogProgress().closeAlertProgress(mAlertDialog);
        }
    }

    private void scrollNoticias()
    {
        mRecyclerViewNoticias.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                Log.e("dy",String.valueOf(dy));

                if (dy>0){
                    StaggeredGridLayoutManager  mLayoutManager = ((StaggeredGridLayoutManager)mRecyclerViewNoticias.getLayoutManager());
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
                        scrollReadNoticias(mLayoutManager,findLastCompletelyVisibleItemPosition);
                    }
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void scrollReadNoticias(StaggeredGridLayoutManager mLayoutManager,int findLastCompletelyVisibleItemPosition)
    {
        if(mAlertDialog!=null && !mAlertDialog.isShowing())
        {
            Log.e("desface",String.valueOf(desface_));
            Log.e("comparacion",String.valueOf(findLastCompletelyVisibleItemPosition) +"  >= "+ String.valueOf(mLayoutManager.getItemCount()-desface_));

            Log.e("PageTotal",String.valueOf(page_cont));

            if (first_desplazo)
            {
                /**llamado nuevamente**/
                llenarArraysListNoticias();
            }else
            {
                if(findLastCompletelyVisibleItemPosition >= mLayoutManager.getItemCount()-desface_)
                {
                    llenarArraysListNoticias();

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
            case R.id.card_principal:
                //abrirActivityNoticia(mNoticias);

                Intent intent = new Intent(getActivity(), NoticiasViewPagerContenidoActivity.class);
                //mNoticiasList.add(0,mNoticias);
                intent.putExtra("noticias", (Serializable) mNoticiasList);
                intent.putExtra("noticia", (Serializable) mNoticias_);
                intent.putExtra("posicion",0);
                startActivity(intent);


                break;
            case R.id.id_cargar_mas:
                page_cont++;
                llenarArraysListNoticias();
                       break;
        }
    }


    public void initBroadCastReciver()
    {
        cBroadCastNetworkStatus.mCheckNetwork = this;

        IntentFilter mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mIntentFilter.addAction(ConnectivityManager.EXTRA_CAPTIVE_PORTAL);
        getContext().registerReceiver(mBroadCastNetworkStatus,mIntentFilter);
    }

    @Override
    public void checkNetwork(boolean bandera)
    {
        if (bandera)
        {
            if (cApplication.bandera_fragment == 1) /**NOTICIAS**/
            {
                //Toast.makeText(getContext(), "eweew", Toast.LENGTH_SHORT).show();
                Log.e("page_network",String.valueOf(page_cont));

                if (mAdapterNoticias!=null)
                {
                    if (page_cont == 1){
                        //scrollReadNoticias();
                        llenarArraysListNoticias();
                    }else
                    {
                        mAdapterNoticias.notifyDataSetChanged();
                        llenarArraysListNoticias();
                    }

                }

            }else
                {

                }
        }else
            {
                Toasty.warning(getContext(),getResources()
                    .getString(R.string.off_network),Toasty.LENGTH_SHORT).show();
                llenarArraysListNoticias();
            }
    }

    @Override
    public void onClickNoticia(int pos)
    {
        Intent intent = new Intent(getActivity(), NoticiasViewPagerContenidoActivity.class);
        //mNoticiasList.add(0,mNoticias);
        intent.putExtra("noticias", (Serializable) mNoticiasList);
        intent.putExtra("noticia", (Serializable) mNoticias_);
        intent.putExtra("posicion",pos);
        startActivity(intent);
    }


}