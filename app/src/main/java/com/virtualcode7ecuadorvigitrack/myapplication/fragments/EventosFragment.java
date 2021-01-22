package com.virtualcode7ecuadorvigitrack.myapplication.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.Volley;
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

    private CardView mCardView;

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
        mCardView = mView.findViewById(R.id.id_card_view_evento);

        mAlertDialog = new cAlertDialogProgress().showAlertProgress(getContext(),
                "CONSULTANDO...",false);
        mAlertDialog.show();


        llenarArraysListEventos();

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
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.api_rest_eventos_all), null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if (response.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArray = response.getJSONArray("respuesta");
                        for (int i=0;i<mJsonArray.length();i++)
                        {
                            JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                            cEventos oE = new cEventos();
                            oE.setId_evento(mJsonObject.getInt("id"));
                            oE.setFecha(mJsonObject.getString("fecha_inicio"));
                            oE.setFecha_fin(mJsonObject.getString("fecha_fin"));
                            oE.setTitulo(mJsonObject.getString("titulo"));
                            oE.setUri_foto(mJsonObject.getString("url_imagen_miniatura"));
                            oE.setDireccion("Sin dirección");
                            mEventosArrayList.add(oE);
                        }

                        llenarRecyclerView();

                    }else
                        {
                            Toasty.warning(getContext(),"No se pudo encontrar los eventos",
                                    Toasty.LENGTH_LONG);
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

            if ( mNoticias.getFecha_fin()==null
                    ||mNoticias.getFecha_fin().isEmpty())
            {
                mLinearLayoutCompatFechaFin.setVisibility(View.GONE);
            }else
            {
                mLinearLayoutCompatFechaFin.setVisibility(View.VISIBLE);
                mTextViewFechaDestino.setText(new cStringMesDia().mes(mNoticias.getFecha_fin()));
                mTextViewFechaDestinoNum.setText(new cStringMesDia().dia(mNoticias.getFecha_fin()));
            }

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
                        Intent mIntent = new Intent(getActivity(), EventosViewPagerContenidoActivity.class);
                        mIntent.putExtra("eventos",(Serializable) mEventosArrayList);
                        mIntent.putExtra("posicion",mRecyclerViewEventos.getChildAdapterPosition(view));
                        startActivity(mIntent);

                    }
                });
                mRecyclerViewEventos.setAdapter(mAdapterEventos);

            }
        }else
        {
            Toasty.info(getContext(),"Lo sentimos no existen eventos disponibles"
                    ,Toasty.LENGTH_SHORT).show();
        }




    }

    @Override
    public void onResume()
    {
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //abrirActivityEventos(mNoticias);
                mEventosArrayList.add(0,mNoticias);
                Intent mIntent = new Intent(getActivity(), EventosViewPagerContenidoActivity.class);
                mIntent.putExtra("eventos",(Serializable) mEventosArrayList);
                mIntent.putExtra("posicion",0);
                startActivity(mIntent);



            }
        });
        super.onResume();
    }
}