package com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.virtualcode7ecuadorvigitrack.myapplication.R;


public class ResumenReservacionFragment extends Fragment implements View.OnClickListener
{

    private MaterialButton mMaterialButtonSave;
    private View mView;
    private MaterialButton mMaterialButtonEliminarReserva;
    private MaterialButton mMaterialButtonEliminarExtra;
    private AlertDialog mAlertDialogDeleteReserva;
    private View mViewAlertDelete;

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
        mMaterialButtonEliminarReserva = mView.findViewById(R.id.BtnDeleteReserva);
        mMaterialButtonEliminarExtra = mView.findViewById(R.id.BtnDeleteReservaExtra);

        createAlertDeleteReserva();

        mMaterialButtonSave.setOnClickListener(this::onClick);


        return mView;

    }

    @Override
    public void onResume()
    {
        mMaterialButtonEliminarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mAlertDialogDeleteReserva.show();
            }
        });

        mMaterialButtonEliminarExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mAlertDialogDeleteReserva.show();
            }
        });
        super.onResume();
    }

    private void createAlertDeleteReserva()
    {


        mViewAlertDelete = LayoutInflater.from(getContext())
                .inflate(R.layout.alert_delete_reservacion,null,false);


        MaterialButton mMaterialButtonCancel = mViewAlertDelete.findViewById(R.id.idButtonAlertCancelReservacion);
        MaterialButton mMaterialButtonDelete = mViewAlertDelete.findViewById(R.id.idButtonAlertDeleteReservacion);
        ImageView mImageViewClose = mViewAlertDelete.findViewById(R.id.idButtonAlertCloseReservacion);


        mMaterialButtonCancel = mViewAlertDelete.findViewById(R.id.idButtonAlertCancelReservacion);
        mMaterialButtonDelete = mViewAlertDelete.findViewById(R.id.idButtonAlertDeleteReservacion);
        mImageViewClose = mViewAlertDelete.findViewById(R.id.idButtonAlertCloseReservacion);

        mMaterialButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAlertDeleteReserva();
            }
        });

        mMaterialButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                closeAlertDeleteReserva();
            }
        });

        mImageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                closeAlertDeleteReserva();
            }
        });

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setView(mViewAlertDelete);
        mAlertDialogDeleteReserva = mBuilder.create();
        mAlertDialogDeleteReserva.getWindow()
                .setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        mAlertDialogDeleteReserva.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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


    private void closeAlertDeleteReserva(){
        if (mAlertDialogDeleteReserva!= null && mAlertDialogDeleteReserva.isShowing()){
            mAlertDialogDeleteReserva.cancel();
            mAlertDialogDeleteReserva.hide();
        }
    }


}