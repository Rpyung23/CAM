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
import com.virtualcode7ecuadorvigitrack.myapplication.models.cPaquete;

import java.util.List;

public class cAdapterPaquete extends RecyclerView.Adapter<cAdapterPaquete.cViewHolderPaquete>
{
    private List<cPaquete> mListPaquetes;
    private cOnClickHabitacionPaquete mOnClickListener;
    private int posAnterior;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public cAdapterPaquete(List<cPaquete> mListP,
                           cOnClickHabitacionPaquete mOnClickListener,
                           RecyclerView mRecyclerView, Context mContext) {
        this.mListPaquetes = mListP;
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
    public cViewHolderPaquete onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tipo_paquete,
                parent,false);
        return new cViewHolderPaquete(mView);
    }

    @Override
    public void onBindViewHolder(cAdapterPaquete.cViewHolderPaquete holder, int position)
    {
        holder.mMaterialCardViewPaquete
                .setCardBackgroundColor(getColorActiveInactive(mListPaquetes.get(position).isActive(),true));
        holder.mTextViewPricePaquete
                .setTextColor(getColorActiveInactive(mListPaquetes.get(position).isActive(),false));
        holder.mTextViewSubTitlePaquete
                .setTextColor(getColorActiveInactive(mListPaquetes.get(position).isActive(),false));
        holder.mTextViewTitlePaquete
                .setTextColor(getColorActiveInactive(mListPaquetes.get(position).isActive(),false));
        holder.mTextViewNumDias
                .setTextColor(getColorActiveInactive(mListPaquetes.get(position).isActive(),false));
        holder.mImageViewLogo.getDrawable().setTint(getColorActiveInactive(mListPaquetes.get(position).isActive(),false));

    }

    private int getColorActiveInactive(boolean active,boolean is_card)
    {
        if (is_card){
            return  !active ? mContext.getResources().getColor(R.color.backgrount_beigs) : mContext.getResources().getColor(R.color.ClubColorPrimary);
        }else{
            return  active ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.ClubColorPrimary);
        }
    }

    @Override
    public int getItemCount() {
        return mListPaquetes.size();
    }

    public class cViewHolderPaquete extends RecyclerView.ViewHolder
    {

        private MaterialCardView mMaterialCardViewPaquete;
        private TextView mTextViewNumDias;
        private TextView mTextViewTitlePaquete;
        private TextView mTextViewSubTitlePaquete;
        private TextView mTextViewPricePaquete;
        private ImageView mImageViewLogo;


        public cViewHolderPaquete(View itemView)
        {
            super(itemView);

            mMaterialCardViewPaquete = itemView.findViewById(R.id.idCardViewPaquete);
            mTextViewNumDias = itemView.findViewById(R.id.idTxtDias);
            mTextViewTitlePaquete = itemView.findViewById(R.id.idTxtNamePaquete);
            mTextViewSubTitlePaquete = itemView.findViewById(R.id.idTxtSubTipoPaquete);
            mTextViewPricePaquete = itemView.findViewById(R.id.idTxtPricePaquete);
            mImageViewLogo = itemView.findViewById(R.id.idImgPaquete);

            mMaterialCardViewPaquete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mOnClickListener.OnCLickListenerPaquete(posAnterior,mRecyclerView.getChildAdapterPosition(v));
                }
            });
        }
    }
}
