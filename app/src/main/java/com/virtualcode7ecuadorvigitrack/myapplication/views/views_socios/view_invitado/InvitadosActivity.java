package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_invitado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.activity.cActivityInicioSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import java.util.ArrayList;
import com.virtualcode7ecuadorvigitrack.myapplication.models.*;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.invitados.*;

public class InvitadosActivity extends cActivityInicioSocio
{

    private TextView mTextViewTitulo;
    private ArrayList<cListInvitadosActuales> mListInvitadosActuales
            = new ArrayList<>();
    private RecyclerView mRecyclerViewListInvitados;
    private View mViewCardSolicitudInvitado;

    private String mStringUnNames = "Juan Mendoza Alcázar";
    private String mStringDosNames = "María Martínez Cruz,Juan Mendoza Alcázar";
    private String mStringTresNames = "Javier Mendoza Martínez,María Martínez Cruz,Juan Mendoza Alcázar";
    private cAdapterListInvitadosNow mAdapterListInvitadosNow;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitados);
        new cToolbar().show(this," ",true,3);
        mTextViewTitulo = findViewById(R.id.id_detalles_toolbar_top);
        mRecyclerViewListInvitados = findViewById(R.id.rvIntavacionesActuales);
        mViewCardSolicitudInvitado = findViewById(R.id.id_views_recibos_cuenta);
        mTextViewTitulo.setText("INVITADO");


        /**LLENADO DE DATOS***/
        for (int i =1;i<=12;i++)
        {
            cListInvitadosActuales oL = new cListInvitadosActuales();
            oL.setmFechaFinal("2021/10/15");
            oL.setmFechaInicial("2021/10/30");

            if(i%2==0)
            {
                oL.setmNombresCompletos(mStringUnNames);
                oL.setStatusListInvitadosNow(1);
            }else if(i%3==0)
            {
                oL.setmNombresCompletos(mStringDosNames);
                oL.setStatusListInvitadosNow(2);
            }else
            {
                oL.setmNombresCompletos(mStringTresNames);
                oL.setStatusListInvitadosNow(3);
            }
            mListInvitadosActuales.add(oL);
        }

        mAdapterListInvitadosNow = new cAdapterListInvitadosNow(mListInvitadosActuales, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        },this);


        mRecyclerViewListInvitados.setAdapter(mAdapterListInvitadosNow);
        mAdapterListInvitadosNow.notifyDataSetChanged();

        mViewCardSolicitudInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mIntent = new Intent(InvitadosActivity.this,SolicitudInvitadosActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed()
    {
        finish();
    }
}