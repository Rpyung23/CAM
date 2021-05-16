package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_invitado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.activity.cActivityInicioSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

public class SolicitudInvitadosActivity extends cActivityInicioSocio
{
    private MaterialCardView mMaterialCardViewFechasSolicitud;
    private MaterialCardView mMaterialCardViewAddInvitados;
    private AlertDialog mAlertDialogFechas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_invitados);

        new cToolbar().show(this,"Centro Asturiano de México A.C",true,1);

        mMaterialCardViewFechasSolicitud = findViewById(R.id.cardViewAddFechasSolicitud);
        mMaterialCardViewAddInvitados = findViewById(R.id.cardAddInvitados);

        mMaterialCardViewFechasSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                View mView = LayoutInflater.from(SolicitudInvitadosActivity.this).inflate(R.layout.alert_fechas,null,false);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SolicitudInvitadosActivity.this);
                mBuilder.setView(mView);
                mAlertDialogFechas = mBuilder.create();
                mAlertDialogFechas.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.trnsparente)));
                mAlertDialogFechas.show();
            }
        });


        mMaterialCardViewAddInvitados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mIntent = new Intent(SolicitudInvitadosActivity.this,AddInvitadoActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
            }
        });
    }
}