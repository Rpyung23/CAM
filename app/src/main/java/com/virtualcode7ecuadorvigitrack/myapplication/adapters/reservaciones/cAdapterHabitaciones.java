package com.virtualcode7ecuadorvigitrack.myapplication.adapters.reservaciones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.cOnClickHabitacionPaquete;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cHabitacion;

import java.util.List;

public class cAdapterHabitaciones extends RecyclerView.Adapter<cAdapterHabitaciones.cViewHolderHabitaciones>
{
    private List<cHabitacion> mListHabitacions;
    private cOnClickHabitacionPaquete mOnClickListener;
    private int posAnterior;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public cAdapterHabitaciones(List<cHabitacion> mListHabitacions,
                                cOnClickHabitacionPaquete mOnClickListener,
                                RecyclerView mRecyclerView, Context mContext) {
        this.mListHabitacions = mListHabitacions;
        this.mOnClickListener = mOnClickListener;
        this.mRecyclerView = mRecyclerView;
        this.mContext = mContext;
        this.posAnterior = 0;
    }

    public int getPosAnterior() {
        return posAnterior;
    }

    public void setPosAnterior(int posAnterior) {
        this.posAnterior = posAnterior;
    }

    @Override
    public cViewHolderHabitaciones onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tipo_habitacion,
                parent,false);
        return new cViewHolderHabitaciones(mView);
    }

    @Override
    public void onBindViewHolder(cAdapterHabitaciones.cViewHolderHabitaciones holder, int position)
    {
        holder.mMaterialCardViewHabitacion
                .setCardBackgroundColor(getColorActiveInactive(mListHabitacions.get(position).isActive(),true));
        holder.mTextViewPriceHabit
                .setTextColor(getColorActiveInactive(mListHabitacions.get(position).isActive(),false));
        holder.mTextViewSubTitleHabit
                .setTextColor(getColorActiveInactive(mListHabitacions.get(position).isActive(),false));
        holder.mTextViewTitleHabit
                .setTextColor(getColorActiveInactive(mListHabitacions.get(position).isActive(),false));
        holder.mTextViewNumDias
                .setTextColor(getColorActiveInactive(mListHabitacions.get(position).isActive(),false));
        holder.mImageViewLogo.getDrawable().setTint(getColorActiveInactive(mListHabitacions.get(position).isActive(),false));
    }


    private int getColorActiveInactive(boolean active,boolean is_card)
    {
        if (is_card){
            return  !active ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.ClubColorPrimary);
        }else{
            return  active ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.ClubColorPrimary);
        }
    }


    @Override
    public int getItemCount() {
        return mListHabitacions.size();
    }

    public class cViewHolderHabitaciones extends RecyclerView.ViewHolder
    {

        private MaterialCardView mMaterialCardViewHabitacion;
        private TextView mTextViewNumDias;
        private TextView mTextViewTitleHabit;
        private TextView mTextViewSubTitleHabit;
        private TextView mTextViewPriceHabit;
        private ImageView mImageViewLogo;


        public cViewHolderHabitaciones(View itemView)
        {
            super(itemView);

            mMaterialCardViewHabitacion = itemView.findViewById(R.id.idCardViewHabitacion);
            mTextViewNumDias = itemView.findViewById(R.id.idTxtDias);
            mTextViewTitleHabit = itemView.findViewById(R.id.idTxtTipoHabitacion);
            mTextViewSubTitleHabit = itemView.findViewById(R.id.idTxtSubTipoHabitacion);
            mTextViewPriceHabit = itemView.findViewById(R.id.idTxtPricehabitacion);
            mImageViewLogo = itemView.findViewById(R.id.idImgHabitacion);

            mMaterialCardViewHabitacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mOnClickListener.OnCLickListener(posAnterior,mRecyclerView.getChildAdapterPosition(v));
                }
            });
        }
    }
}
