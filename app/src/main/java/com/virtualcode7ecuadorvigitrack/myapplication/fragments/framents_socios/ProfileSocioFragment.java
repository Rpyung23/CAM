package com.virtualcode7ecuadorvigitrack.myapplication.fragments.framents_socios;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterInfoCuestionario;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cInfoCustionarioCovid;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferenSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferencesMembresia;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_invitado.InvitadosActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.RecivosCuentaActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.StatusCuentaSociosActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_reservas.ReservasActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class ProfileSocioFragment extends Fragment implements View.OnClickListener
{
    private View mView;
    private View mViewStatusCuenta;
    private View mViewRecivos;
    private View mViewInvitados;
    private View mViewReservaciones;
    private cSharedPreferenSocio mSharedPreferenSocio;
    private TextView mTextViewNameSocio;
    private TextView mTextViewNumMembre;

    private TextView mTextViewTipoMem;
    private TextView mTextViewFechadesde;
    private TextView mTextViewSaldo;

    private TextView mTextViewCovid;
    private AlertDialog AlertInfoCovid;

    private StringRequest mStringRequest;
    private RequestQueue mRequestQueueCuestionario;

    private cAdapterInfoCuestionario mAdapterInfoCuestionario;

    private cSharedPreferencesMembresia mSharedPreferencesMembresia;
    private StringRequest mStringRequestRenovacionTokenAmarillo;
    private StringRequest mStringRequestRenovacionTokenRojo;

    private androidx.appcompat.app.AlertDialog mAlertDialog;
    private android.app.AlertDialog mAlertDialogProgressCuestionario;

    private ImageView mImageViewSemaforo ;

    private boolean isBanderaEncuesta = false;

    private ArrayList<cInfoCustionarioCovid> mInfoCustionarioCovidArrayList;

    private AlertDialog mAlertDialogCheckCovid;
    private  int posRenovado = 0;


    private RecyclerView mRecyclerViewCuestionario;

    public ProfileSocioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_profile_socio, container, false);
        mViewRecivos = mView.findViewById(R.id.id_views_recibos_cuenta);
        mViewStatusCuenta = mView.findViewById(R.id.id_views_status_cuenta);
        mTextViewNameSocio = mView.findViewById(R.id.id_Textview_name_socio);
        mTextViewNumMembre = mView.findViewById(R.id.id_textview_num_membre_);
        mTextViewTipoMem = mView.findViewById(R.id.id_type_membresia);
        mTextViewFechadesde = mView.findViewById(R.id.id_socio_desde);
        mTextViewSaldo = mView.findViewById(R.id.id_membresia_saldo);
        mTextViewCovid = mView.findViewById(R.id.id_consultar_alert_dialog);
        mImageViewSemaforo = mView.findViewById(R.id.id_circle_covid_semaforo);
        mViewInvitados = mView.findViewById(R.id.id_views_invitados);
        mViewReservaciones = mView.findViewById(R.id.id_views_reservaciones);

        mSharedPreferenSocio = new cSharedPreferenSocio(getContext());



        mTextViewCovid.setOnClickListener(this);
        mImageViewSemaforo.setOnClickListener(this);
        mViewStatusCuenta.setOnClickListener(this);
        mViewRecivos.setOnClickListener(this);
        mViewInvitados.setOnClickListener(this);
        mViewReservaciones.setOnClickListener(this);

        /*mViewReservaciones.setBackgroundColor(getContext().getColor(R.color.backgrount_beigs_op_40));
        mViewInvitados.setBackgroundColor(getContext().getColor(R.color.backgrount_beigs_op_40));*/

        mSharedPreferenSocio = new cSharedPreferenSocio(getContext());
        mSharedPreferencesMembresia = new cSharedPreferencesMembresia(getContext());

        /*if (!new cSharedTokenValidation(getContext()).readTokenValitation())
        {
            alertDialogTimeOut();
        }*/


        if (mSharedPreferencesMembresia.readMembresia().getMembresiaCuestionarioResultado()==1)
        {
            Picasso.with(getContext()).load(R.drawable.circle_green).into(mImageViewSemaforo);
            //isBanderaEncuesta = true;
        }else if(mSharedPreferencesMembresia.readMembresia().getMembresiaCuestionarioResultado()==2)
            {
                Picasso.with(getContext()).load(R.drawable.circle_yellow).into(mImageViewSemaforo);
                //isBanderaEncuesta = true;
            }else
                {
                    Picasso.with(getContext()).load(R.drawable.circle_red).into(mImageViewSemaforo);
                    isBanderaEncuesta = true;
                }


        mTextViewNameSocio.setText(mSharedPreferenSocio.leerdatosSocio().getNombre_socio());
        mTextViewNumMembre.setText(mSharedPreferenSocio.leerdatosSocio().getNum_membresia());
        mTextViewTipoMem.setText(mSharedPreferencesMembresia.readMembresia().getMembresiaTipo());
        mTextViewFechadesde.setText(mSharedPreferencesMembresia.readMembresia().getMembresiaFechaIngreso());
        mTextViewSaldo.setText("$ "+mSharedPreferencesMembresia.readMembresia().getMembresiaSaldo());
        return mView;
    }


    private void sweetAlertErrorCuestionarioRenovar(String error)
    {
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("Existió un problema en la renovación, por favor ingrese a ");
        pDialog.setContentText(error);

        if (!error.contains("Debe") && !error.contains("iniciar"))
        {
            pDialog.setConfirmButton("Acceder", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog)
                {

                    Uri uri = Uri.parse(error);
                    Intent intentCovid = new Intent(Intent.ACTION_VIEW,uri );
                    startActivity(intentCovid);

                }
            });

        }


        pDialog.show();


    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_views_status_cuenta:
                /**ESTADO DE CUENTA**/

                /*if (!new cSharedTokenValidation(getContext()).readTokenValitation())
                {
                    alertDialogTimeOut();
                }*/

                Intent mIntent = new Intent(getActivity(), StatusCuentaSociosActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
                break;
            case R.id.id_views_recibos_cuenta:
                /**RECIBOS CUENTA**/

                /*if (!new cSharedTokenValidation(getContext()).readTokenValitation())
                {
                    alertDialogTimeOut();
                }*/
                Intent mIntentRecibos = new Intent(getActivity(), RecivosCuentaActivity.class);
                mIntentRecibos.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntentRecibos);
                break;

            case R.id.id_circle_covid_semaforo:

                if (isBanderaEncuesta)
                {
                    Uri uri = Uri.parse("https://www.socioscam.com.mx/index/cuestionario-covid");
                    Intent intentCovid = new Intent(Intent.ACTION_VIEW,uri );
                    startActivity(intentCovid);
                }
                break;

            case R.id.id_consultar_alert_dialog:
                readDatosCuestionario();
                break;

            case R.id.id_views_invitados:
                Intent mIntentInvitados = new Intent(getContext(), InvitadosActivity.class);
                mIntentInvitados.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntentInvitados);
                break;

            case R.id.id_views_reservaciones:
                Intent mIntentReservas = new Intent(getContext(), ReservasActivity.class);
                mIntentReservas.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntentReservas);
                break;
        }
    }


    private void showOpenAlertCovid()
    {


        View mView_ = LayoutInflater.from(getContext()).inflate(R.layout.alert_cuestionario_covid,null,false);
        mRecyclerViewCuestionario = mView_.findViewById(R.id.id_recyclerview_cuestionario_covid);
        TextView mTextViewCerar = mView_.findViewById(R.id.id_cerrar_alert);

        llenarListaCovid();

        //mRecyclerViewCuestionario.setHasFixedSize(true);

        mTextViewCerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertInfoCovid.hide();
            }
        });

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setView(mView_);
        AlertInfoCovid = mBuilder.create();


        AlertInfoCovid.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        AlertInfoCovid.show();
        AlertInfoCovid.getWindow().setGravity(Gravity.CENTER);
        AlertInfoCovid.getWindow().setLayout(getContext().getResources().getDisplayMetrics().widthPixels - 80
                ,ViewGroup.LayoutParams.WRAP_CONTENT);

        new cAlertDialogProgress()
                .closeAlertProgress(mAlertDialogProgressCuestionario);
    }

    private void llenarListaCovid()
    {
        mAdapterInfoCuestionario =
                new cAdapterInfoCuestionario(mInfoCustionarioCovidArrayList,
                        getContext(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        /*****/
                        if (mInfoCustionarioCovidArrayList.get(mRecyclerViewCuestionario
                                .getChildAdapterPosition(v)).isColor_red())
                        {
                            /**Abre el navegador web (cuestionario)**/
                            //boolean bandera_type,String id_,String status

                            api_restAmarilloRojoEstado(true,String.valueOf(mInfoCustionarioCovidArrayList.get(mRecyclerViewCuestionario
                                    .getChildAdapterPosition(v)).getmIntbeneficiarioContador()),"N");

                        }else if (mInfoCustionarioCovidArrayList.get(mRecyclerViewCuestionario
                                .getChildAdapterPosition(v)).isColor_yellow())
                        {
                            posRenovado = mRecyclerViewCuestionario
                                    .getChildAdapterPosition(v);

                            showAlertCheckRenovacion(mInfoCustionarioCovidArrayList.get(mRecyclerViewCuestionario
                                    .getChildAdapterPosition(v)),mRecyclerViewCuestionario);

                        }
                    }
                });

        mAdapterInfoCuestionario.notifyDataSetChanged();

        mRecyclerViewCuestionario.setAdapter(mAdapterInfoCuestionario);
    }

    private void api_restAmarilloRojoEstado(boolean bandera_type,String id_,String status)
    {
        String mStringUrl = getContext().getString(R.string.semaforo_amarillo_rojo)+id_+"/t/"+mSharedPreferenSocio
                .leerdatosSocio().getToken()+"/v/"+status;

        Log.e("ROJOAMARILLO",mStringUrl);
        Log.e("ROJOAMARILLO",mSharedPreferenSocio.leerdatosSocio().getToken());

        mStringRequestRenovacionTokenAmarillo =
                new StringRequest(mStringUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONArray mJsonArray = new JSONArray(response);
                    JSONObject mJsonObject = mJsonArray.getJSONObject(0);


                    Log.e("codigo"," : "+mJsonObject.getInt("codigo"));
                    Log.e("codigo"," : "+mJsonObject.getString("mensaje"));

                    if (mJsonObject.getInt("codigo")==200)
                    {

                        if (bandera_type)/**Open Navegador**/
                        {
                            Uri uri = Uri.parse(mJsonObject.getString("mensaje"));
                            Intent intentCovid = new Intent(Intent.ACTION_VIEW,uri );
                            startActivity(intentCovid);
                        }else
                            {
                                sweetAlertInfo("Actualización en proceso");

                                /**
                                 * Update RecyclerView Renovar
                                 * **/


                                if(mAlertDialogCheckCovid!=null)
                                {
                                    mAlertDialogCheckCovid.cancel();
                                    mAlertDialogCheckCovid.hide();
                                }


                              /*cInfoCustionarioCovid mCovid =  mInfoCustionarioCovidArrayList.get(posRenovado);
                              mCovid.setIsrenovado(true);

                              mInfoCustionarioCovidArrayList.add(mCovid);*/

                                mInfoCustionarioCovidArrayList.get(posRenovado).setIsrenovado(true);
                              ArrayList<cInfoCustionarioCovid>
                                      mInfoCustionarioCovidArrayListAux = mInfoCustionarioCovidArrayList;

                                mInfoCustionarioCovidArrayList = new ArrayList<>();

                                mInfoCustionarioCovidArrayList = mInfoCustionarioCovidArrayListAux;

                                llenarListaCovid();


                                //mAdapterInfoCuestionario.notifyItemRangeChanged(0,posRenovado);
                               //mRecyclerViewCuestionario.notify();
                            }

                    }else
                        {
                            if(mAlertDialogCheckCovid!=null)
                            {
                                mAlertDialogCheckCovid.cancel();
                                mAlertDialogCheckCovid.hide();
                            }

                            if(!bandera_type)
                            {
                                sweetAlertErrorCuestionarioRenovar(mJsonObject.getString("mensaje"));
                            }else
                                {
                                    if(AlertInfoCovid!=null && AlertInfoCovid.isShowing())
                                    {
                                        AlertInfoCovid.hide();
                                        AlertInfoCovid.cancel();
                                    }
                                    Intent mIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(mJsonObject.getString("mensaje")));
                                    startActivity(mIntent);
                                }
                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("Error_Cuestionario",""+error.getMessage());
                        sweetAlertError(error.getMessage());
                    }
                })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError
                    {
                        HashMap<String,String> stringHashMap = new HashMap<>();
                        stringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                        stringHashMap.put("token",mSharedPreferenSocio.leerdatosSocio().getToken());
                        stringHashMap.put("id",id_);
                        stringHashMap.put("v",status);
                        return stringHashMap;
                    }
                };

        mRequestQueueCuestionario.add(mStringRequestRenovacionTokenAmarillo);
    }

    private void showAlertCheckRenovacion(cInfoCustionarioCovid mInfoCustionarioCovid,RecyclerView mRecyclerView)
    {
        View mViewCheck = LayoutInflater.from(getContext()).inflate(R.layout.view_alert_check_renovacion_cuestionario
                ,null,false);

        MaterialButton materialButton = mViewCheck.findViewById(R.id.id_afirmo_alert_covid);
        MaterialCheckBox materialCheckBox = mViewCheck.findViewById(R.id.id_checkbox_renovar);
        TextView mTextViewCerrar = mViewCheck.findViewById(R.id.id_cerrar_alert_check);

        mTextViewCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(mAlertDialogCheckCovid!=null)
                {
                    mAlertDialogCheckCovid.cancel();
                    mAlertDialogCheckCovid.hide();
                }
            }
        });

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(materialCheckBox.isChecked())
                {
                    /**AlertRenovacion**/
                    api_restAmarilloRojoEstado(false
                            ,String.valueOf(mInfoCustionarioCovid.getmIntbeneficiarioContador())
                            ,"R");

                }
            }
        });

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

        mBuilder.setView(mViewCheck);

        mAlertDialogCheckCovid = mBuilder.create();
        mAlertDialogCheckCovid.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mAlertDialogCheckCovid.show();

    }

    private void sweetAlertError(String error)
    {
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Error");
        pDialog.setContentText(error);
        pDialog.show();
    }

    private void sweetAlertInfo(String mensaje)
    {
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
        //pDialog.setTitleText("Renovación");
        pDialog.setContentText(mensaje);
        pDialog.show();
    }


    private void readDatosCuestionario()
    {

        mAlertDialogProgressCuestionario = new cAlertDialogProgress()
                .showAlertProgress(getContext(),"Consultando",false);

        mAlertDialogProgressCuestionario.show();

        String url = getContext().getResources().getString(R.string.cuestionario)
                +mSharedPreferenSocio.leerdatosSocio().getId_token_socio();

        //Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

        mStringRequest = new StringRequest(url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                if(response!=null)
                {
                    try {
                        JSONArray mJsonArray = new JSONArray(response);

                        mInfoCustionarioCovidArrayList = new ArrayList<>();

                        for (int i =0 ;i<mJsonArray.length();i++)
                        {
                            JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                            cInfoCustionarioCovid mInfoCustionarioCovid = new cInfoCustionarioCovid();

                            mInfoCustionarioCovid.setmStringBeneficiario(mJsonObject.getString("beneficiarioNombre"));

                            mInfoCustionarioCovid.setmIntbeneficiarioContador(mJsonObject.getInt("beneficiarioContador"));

                            if(mJsonObject.getString("beneficiarioTipo").equals("C"))
                            {
                                mInfoCustionarioCovid.setmStringTipo("Conyugé");
                            }else if(mJsonObject.getString("beneficiarioTipo").equals("T"))
                            {
                                mInfoCustionarioCovid.setmStringTipo("Titular");
                            }else if(mJsonObject.getString("beneficiarioTipo").equals("H"))
                                {
                                    mInfoCustionarioCovid.setmStringTipo("Hijo");
                                }else
                                    {
                                        mInfoCustionarioCovid.setmStringTipo("Especial");
                                    }

                            if (mJsonObject.getInt("beneficiarioCuestionario")==1)
                            {
                                mInfoCustionarioCovid.setColor_verde(true);
                                mInfoCustionarioCovid.setmStringEstado("Vigente");
                            }else if(mJsonObject.getInt("beneficiarioCuestionario")==2)
                            {
                                mInfoCustionarioCovid.setColor_yellow(true);
                                mInfoCustionarioCovid.setmStringEstado("Renovar");
                            }else if(mJsonObject.getInt("beneficiarioCuestionario")==4)
                            {
                                mInfoCustionarioCovid.setIsrenovado(true);
                                mInfoCustionarioCovid.setmStringEstado("Procesando");
                            }else
                                {
                                    mInfoCustionarioCovid.setColor_red(true);
                                    mInfoCustionarioCovid.setmStringEstado("Realizar");
                                }

                            mInfoCustionarioCovidArrayList.add(mInfoCustionarioCovid);
                        }


                        showOpenAlertCovid();

                    } catch (JSONException e)
                    {
                        new cAlertDialogProgress()
                                .closeAlertProgress(mAlertDialogProgressCuestionario);
                        e.printStackTrace();
                    }
                }else
                    {
                        new cAlertDialogProgress()
                                .closeAlertProgress(mAlertDialogProgressCuestionario);
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                new cAlertDialogProgress()
                        .closeAlertProgress(mAlertDialogProgressCuestionario);
                Toasty.error(getContext(),error.getMessage(),Toasty.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> stringHashMap = new HashMap<>();
                stringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                stringHashMap.put("token",mSharedPreferenSocio.leerdatosSocio().getToken());
                return stringHashMap;
            }
        };

        mRequestQueueCuestionario = Volley.newRequestQueue(getContext());
        mRequestQueueCuestionario.add(mStringRequest);


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



}