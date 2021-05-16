package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_invitado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.activity.cActivityInicioSocio;

public class AddInvitadoActivity extends cActivityInicioSocio {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invitado);
    }
}