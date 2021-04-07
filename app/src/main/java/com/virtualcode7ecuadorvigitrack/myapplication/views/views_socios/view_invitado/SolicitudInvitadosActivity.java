package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_invitado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

public class SolicitudInvitadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_invitados);
        new cToolbar().show(this,"Centro Asturiano de México A.C",true,1);
    }
}