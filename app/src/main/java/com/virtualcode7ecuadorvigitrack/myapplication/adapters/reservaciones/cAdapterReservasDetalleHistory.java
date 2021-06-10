package com.virtualcode7ecuadorvigitrack.myapplication.adapters.reservaciones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cReservaciones;

import java.util.List;

public class cAdapterReservasDetalleHistory extends RecyclerView.Adapter<cAdapterReservasDetalleHistory.cViewHolderReservasDetalleHistory>
{

    private List<cReservaciones> mReservacionesList;

    public cAdapterReservasDetalleHistory(List<cReservaciones> mReservacionesList) {
        this.mReservacionesList = mReservacionesList;
    }

    @Override
    public cViewHolderReservasDetalleHistory onCreateViewHolder(ViewGroup parent,
                                                                int viewType)
    {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_recibo_reservaciones_detalle_scroll
                ,parent,false);
        return new cViewHolderReservasDetalleHistory(mView);
    }

    @Override
    public void onBindViewHolder(cAdapterReservasDetalleHistory
                                             .cViewHolderReservasDetalleHistory holder,
                                 int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return mReservacionesList.size();
    }

    public class cViewHolderReservasDetalleHistory extends RecyclerView.ViewHolder
    {

        public cViewHolderReservasDetalleHistory(View itemView)
        {
            super(itemView);
        }
    }
}
