package com.virtualcode7ecuadorvigitrack.myapplication.framents_socios;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferenSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferencesMembresia;
import com.virtualcode7ecuadorvigitrack.myapplication.views_socios.InicioSociosActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views_socios.RecivosCuentaActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views_socios.StatusCuentaSociosActivity;

public class ProfileSocioFragment extends Fragment implements View.OnClickListener
{
    private View mView;
    private View mViewStatusCuenta;
    private View mViewRecivos;
    private cSharedPreferenSocio mSharedPreferenSocio;
    private TextView mTextViewNameSocio;
    private TextView mTextViewNumMembre;

    private TextView mTextViewTipoMem;
    private TextView mTextViewFechadesde;
    private TextView mTextViewSaldo;

    private cSharedPreferencesMembresia mSharedPreferencesMembresia;

    public ProfileSocioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_profile_socio, container, false);
        mViewRecivos = mView.findViewById(R.id.id_views_recibos_cuenta);
        mViewStatusCuenta = mView.findViewById(R.id.id_views_status_cuenta);
        mTextViewNameSocio = mView.findViewById(R.id.id_Textview_name_socio);
        mTextViewNumMembre = mView.findViewById(R.id.id_textview_num_membre_);
        mTextViewTipoMem = mView.findViewById(R.id.id_type_membresia);
        mTextViewFechadesde = mView.findViewById(R.id.id_socio_desde);
        mTextViewSaldo = mView.findViewById(R.id.id_membresia_saldo);

        mViewStatusCuenta.setOnClickListener(this);
        mViewRecivos.setOnClickListener(this);
        mSharedPreferenSocio = new cSharedPreferenSocio(getContext());
        mSharedPreferencesMembresia = new cSharedPreferencesMembresia(getContext());

        mTextViewNameSocio.setText(mSharedPreferenSocio.leerdatosSocio().getNombre_socio());
        mTextViewNumMembre.setText(mSharedPreferenSocio.leerdatosSocio().getNum_membresia());
        mTextViewTipoMem.setText(mSharedPreferencesMembresia.readMembresia().getMembresiaTipo());
        mTextViewFechadesde.setText(mSharedPreferencesMembresia.readMembresia().getMembresiaFechaIngreso());
        mTextViewSaldo.setText("$ "+mSharedPreferencesMembresia.readMembresia().getMembresiaSaldo());
        return mView;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_views_status_cuenta:
                /**ESTADO DE CUENTA**/
                Intent mIntent = new Intent(getActivity(), StatusCuentaSociosActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
                break;
            case R.id.id_views_recibos_cuenta:
                /**RECIBOS CUENTA**/
                Intent mIntentRecibos = new Intent(getActivity(), RecivosCuentaActivity.class);
                mIntentRecibos.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntentRecibos);
                break;
        }
    }

}