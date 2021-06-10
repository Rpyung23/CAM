package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_reservas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cReservaciones;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.reservaciones.*;

import java.util.List;

public class DetalleReservacionsScrollActivity extends AppCompatActivity
{
    private TextView mTextViewTitulo;
    private cAdapterReservasDetalleHistory mAdapterReservasDetalleHistory;
    private ViewPager2 mViewPager2HistoryReservas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reservacions_scroll);

        cToolbar.show(this,"",true,3);

        mTextViewTitulo = findViewById(R.id.id_detalles_toolbar_top);
        mViewPager2HistoryReservas = findViewById(R.id.id_viewpager2_recibos_reservaciones);

        mTextViewTitulo.setText(getApplicationContext()
                .getResources().getString(R.string.details_title));

        List<cReservaciones> mReservacionesList = (List<cReservaciones>)
                getIntent().getSerializableExtra("listReservas");
        int posInicial = getIntent().getIntExtra("positionCurrent",0);
        mAdapterReservasDetalleHistory = new cAdapterReservasDetalleHistory(mReservacionesList);

        mViewPager2HistoryReservas.setOffscreenPageLimit(3);
        mViewPager2HistoryReservas.setAdapter(mAdapterReservasDetalleHistory);
        mViewPager2HistoryReservas.setCurrentItem(posInicial);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }


}