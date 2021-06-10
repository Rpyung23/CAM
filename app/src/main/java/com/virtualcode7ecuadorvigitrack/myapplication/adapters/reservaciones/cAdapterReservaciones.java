package com.virtualcode7ecuadorvigitrack.myapplication.adapters.reservaciones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cReservaciones;

import java.util.List;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.*;
public class cAdapterReservaciones extends
        RecyclerView.Adapter<cAdapterReservaciones.cViewHolderReservaciones>
{

    private Context mContext;
    private List<cReservaciones> mReservacionesList;
    private cOnClickHistoryReservas mClickHistoryReservas;
    private RecyclerView mRecyclerView;

    public cAdapterReservaciones(Context mContext
            ,List<cReservaciones> mReservacionesList
            ,cOnClickHistoryReservas mCOnClickHistoryReservas
            ,RecyclerView mRecyclerView)
    {
        this.mContext = mContext;
        this.mReservacionesList = mReservacionesList;
        this.mClickHistoryReservas = mCOnClickHistoryReservas;
        this.mRecyclerView = mRecyclerView;
    }

    @NonNull
    @Override
    public cViewHolderReservaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_list_reservas_,parent,false);
        return new cViewHolderReservaciones(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderReservaciones holder, int position)
    {

    }

    @Override
    public int getItemCount() {
        return mReservacionesList.size();
    }

    public class cViewHolderReservaciones extends RecyclerView.ViewHolder
    {

        public cViewHolderReservaciones(@NonNull View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mClickHistoryReservas.OnClickListenerHistoryReserva(mReservacionesList
                            ,mRecyclerView.getChildAdapterPosition(v));
                }
            });
        }
    }
}
