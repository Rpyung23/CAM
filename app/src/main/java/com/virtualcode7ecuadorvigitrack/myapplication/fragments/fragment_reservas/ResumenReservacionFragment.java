package com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.virtualcode7ecuadorvigitrack.myapplication.R;


public class ResumenReservacionFragment extends Fragment implements View.OnClickListener
{

    private MaterialButton mMaterialButtonSave;
    private View mView;

    public ResumenReservacionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_resumen_reservacion_socio,
                container, false);
        mMaterialButtonSave = mView.findViewById(R.id.id_btn_save_rsumen_reserva);
        mMaterialButtonSave.setOnClickListener(this::onClick);
        return mView;

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.id_btn_save_rsumen_reserva:
                Intent mIntent = new Intent();
                mIntent.putExtra("bandera_save_close_resumen",true);
                getActivity().setResult(Activity.RESULT_OK,mIntent);
                getActivity().finish();
                break;
        }
    }
}