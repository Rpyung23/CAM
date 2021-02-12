package com.virtualcode7ecuadorvigitrack.myapplication.framents_socios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterNotificationSocios;
import com.virtualcode7ecuadorvigitrack.myapplication.gestos.cRecyclerItemTouchHelperNoti;
import com.virtualcode7ecuadorvigitrack.myapplication.handlers.cNotitifactionHandlers;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNotificationSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferenSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.views_socios.InicioSociosActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views_socios.NotificationDetailsSocioActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class NotificationSocioFragment extends Fragment
        implements cRecyclerItemTouchHelperNoti.cRecyclerItemTouchHelperListene
{
    private View mView;
    private RecyclerView mRecyclerViewNotification;
    private cAdapterNotificationSocios mAdapterNotificationSocios;
    private ArrayList<cNotificationSocio> mNotificationSocios;
    private cSharedPreferenSocio mSharedPreferenSocio;
    private StringRequest mStringRequestNotfication;
    private RequestQueue mRequestQueue;
    private AlertDialog mAlertDialogNotiProg;
    private androidx.appcompat.app.AlertDialog mAlertDialog;
    private cNotitifactionHandlers mNotificationHandlers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_notification_socio, container, false);
        mRecyclerViewNotification = mView.findViewById(R.id.id_recyclerview_notificationSocios);
        mSharedPreferenSocio = new cSharedPreferenSocio(getContext());

        mNotificationHandlers = new cNotitifactionHandlers(getContext());

        ItemTouchHelper.SimpleCallback simpleCallback =
                new cRecyclerItemTouchHelperNoti(0,ItemTouchHelper.LEFT,this);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerViewNotification);

        if (!new cSharedTokenValidation(getContext()).readTokenValitation())
        {
            alertDialogTimeOut();
        }



        mAlertDialogNotiProg = new cAlertDialogProgress().showAlertProgress(getContext(),"CONSULTANDO",false);
        mAlertDialogNotiProg.show();
        llenearNotificationArraysList();
        return mView;
    }

    private void openActivityDetailsNotification(cNotificationSocio oNotificationSocio)
    {
        Intent mIntent = new Intent(getActivity(), NotificationDetailsSocioActivity.class);
        mIntent.putExtra("oNotificationSocio",oNotificationSocio);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }

    private void llenearNotificationArraysList()
    {

        mStringRequestNotfication= new StringRequest(Request.Method.GET,
                getString(R.string.api_rest_notificaciones)
                        +mSharedPreferenSocio.leerdatosSocio().getId_token_socio()+"/notificaciones",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                try {
                    JSONObject mJsonObject = new JSONObject(response);
                    if (mJsonObject.getString("codigo").equals("200"))
                    {
                        JSONArray mJsonArrayNoti = mJsonObject.getJSONArray("resultado");
                        if (mJsonArrayNoti.length()>0)
                        {
                            mNotificationSocios = new ArrayList<>();
                            for (int i=0;i<mJsonArrayNoti.length();i++)
                            {
                                JSONObject mJsonObjectN = mJsonArrayNoti.getJSONObject(i);
                                cNotificationSocio oNotificationSocio = new cNotificationSocio();
                                oNotificationSocio.setFecha(mJsonObjectN.getString("notificacion_fecha_envio"));

                                if (mJsonObjectN.getString("notificacion_estado").equals("A"))
                                {
                                    oNotificationSocio.setLeido(false);
                                }else
                                {
                                    oNotificationSocio.setLeido(true);
                                }
                                oNotificationSocio.setMensaje(mJsonObjectN.getString("notificacion_contenido"));

                                oNotificationSocio.setId_notificacion(mJsonObjectN.getInt("id_notificacion"));
                                oNotificationSocio.setNotificacion_titulo(mJsonObjectN.getString("notificacion_titulo"));

                               if (!mJsonObjectN.getString("notificacion_estado").equals("D"))
                                {
                                    mNotificationSocios.add(oNotificationSocio);
                                }

                                //mNotificationSocios.add(oNotificationSocio);
                            }
                            llenarRecycelrView();


                        }else
                            {
                                new cAlertDialogProgress().closeAlertProgress(mAlertDialogNotiProg);
                                Toasty.info(getContext(),"Sin notificaciones",Toasty.LENGTH_LONG).show();
                            }
                    }else
                        {
                            new cAlertDialogProgress().closeAlertProgress(mAlertDialogNotiProg);
                            Toasty.warning(getContext(),"No se pudo consultar las notificaciones",Toasty.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toasty.error(getContext(),"TRYCATH "+e.getMessage(),Toasty.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                new cAlertDialogProgress().closeAlertProgress(mAlertDialogNotiProg);
                Toasty.error(getContext(),error.getMessage(),Toasty.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> stringHashMap = new HashMap<>();
                stringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                stringHashMap.put("token",mSharedPreferenSocio.leerdatosSocio().getToken());
                return stringHashMap;
            }

        };


        mRequestQueue = Volley.newRequestQueue(getContext());
        mRequestQueue.add(mStringRequestNotfication);

       /* for (int i=1;i<11;i++)
        {
            cNotificationSocio oNotificationSocio = new cNotificationSocio();
            oNotificationSocio.setFecha("12/05/2020");
            if (i%2==0)
            {
                oNotificationSocio.setLeido(true);
            }else
                {
                    oNotificationSocio.setLeido(false);
                }
            oNotificationSocio.setMensaje(getString(R.string.loren_ipsum));

            mNotificationSocios.add(oNotificationSocio);
        }*/
    }

    private void llenarRecycelrView()
    {
        new cAlertDialogProgress().closeAlertProgress(mAlertDialogNotiProg);
        mAdapterNotificationSocios = new cAdapterNotificationSocios(mNotificationSocios,
                getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Toasty.success(getContext(),"click",Toasty.LENGTH_LONG).show();
                int  pos = mRecyclerViewNotification.getChildAdapterPosition(view);
                mNotificationSocios.get(pos).setLeido(true);

                //mAdapterNotificationSocios.notifyItemChanged(mRecyclerViewNotification.getChildAdapterPosition(view), null);
                openActivityDetailsNotification(mNotificationSocios.get(mRecyclerViewNotification.getChildAdapterPosition(view)));
                mAdapterNotificationSocios.notifyItemChanged(pos);
            }
        });
        mAdapterNotificationSocios.notifyDataSetChanged();
        mRecyclerViewNotification.setAdapter(mAdapterNotificationSocios);
    }


    private void alertDialogTimeOut()
    {


        androidx.appcompat.app.AlertDialog.Builder mBuilder =
                new androidx.appcompat.app.AlertDialog.Builder(getContext());
        mBuilder.setMessage("Lo sentimos su tiempo se agotado");
        mBuilder.setTitle("Login");
        mBuilder.setCancelable(false);
        mBuilder.setIcon(R.drawable.ic_asturian_primary_color);
        mBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                mAlertDialog.cancel();
                getActivity().finish();
            }
        });
        mAlertDialog = mBuilder.create();
        mAlertDialog.show();
        return;
    }


    @Override
    public void onSwipe(RecyclerView.ViewHolder mViewHolder, int direction, int position)
    {
        if (mViewHolder instanceof  cAdapterNotificationSocios.cViewHolderNotification)
        {

            mNotificationHandlers
                    .setId_noti(mNotificationSocios
                            .get(mViewHolder.getAdapterPosition()).getId_notificacion());
            mNotificationHandlers.setToken(mSharedPreferenSocio.leerdatosSocio().getToken());
            mNotificationHandlers.runDelete();
            mAdapterNotificationSocios.removiItem(mViewHolder.getAdapterPosition());
            /**REMOVE MOTIFICATION BACK - END**/

        }
    }
}