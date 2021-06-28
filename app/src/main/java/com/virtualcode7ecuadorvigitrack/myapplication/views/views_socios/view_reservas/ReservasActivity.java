package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_reservas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.reservaciones.cAdapterReservaciones;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cReservaciones;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.*;
public class ReservasActivity extends AppCompatActivity implements cOnClickHistoryReservas
{
    private cAdapterReservaciones mAdapterReservaciones;
    private List<cReservaciones> mReservacionesList;
    private RecyclerView mRecyclerViewReservaciones;
    private View mViewReservaSolicitud;

    private TextView mTextViewTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        cToolbar.show(this,"",true,3);
        mTextViewTitulo = findViewById(R.id.id_detalles_toolbar_top);
        mRecyclerViewReservaciones = findViewById(R.id.rvReservasActuales);
        mViewReservaSolicitud = findViewById(R.id.id_views_solicitar_reserva);
        mTextViewTitulo.setText(getApplicationContext()
                .getResources().getString(R.string.reservas_mayus));


        mReservacionesList = llenarListReservas();

        mAdapterReservaciones = new cAdapterReservaciones(ReservasActivity.this
                ,mReservacionesList,this,mRecyclerViewReservaciones);

        mRecyclerViewReservaciones.setAdapter(mAdapterReservaciones);

    }

    private List<cReservaciones> llenarListReservas()
    {
        List<cReservaciones> mList = new ArrayList<>();

        for (int i=0;i<11;i++){
            cReservaciones mR = new cReservaciones();

            int valo = (int) Math.floor(Math.random()*3);

            mR.setType_reservacion(valo);
            mList.add(mR);
        }


        return mList;
    }

    @Override
    public void OnClickListenerHistoryReserva(List<cReservaciones> mReservacionesList,
                                              int positionCurrent)
    {
        Intent mIntent = new Intent(ReservasActivity.this
                ,DetalleReservacionsScrollActivity.class);

        mIntent.putExtra("listReservas", (Serializable) mReservacionesList);
        mIntent.putExtra("positionCurrent",positionCurrent);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }

    @Override
    protected void onPostResume() {
        mViewReservaSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mIntent = new Intent(ReservasActivity.this,SolicitudReservaActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
            }
        });
        super.onPostResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed()
    {
        finish();
    }
}