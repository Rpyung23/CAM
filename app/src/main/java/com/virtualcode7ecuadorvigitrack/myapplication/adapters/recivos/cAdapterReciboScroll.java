package com.virtualcode7ecuadorvigitrack.myapplication.adapters.recivos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cReciver;

import java.util.ArrayList;

public class cAdapterReciboScroll extends
        RecyclerView.Adapter<cAdapterReciboScroll.cViewHolderRecivosScroll>
{

    private Context mContext;
    private ArrayList<cReciver> mReciverArrayList;

    public cAdapterReciboScroll(Context mContext, ArrayList<cReciver> mReciverArrayList) {
        this.mContext = mContext;
        this.mReciverArrayList = mReciverArrayList;
    }

    @NonNull
    @Override
    public cViewHolderRecivosScroll onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(mContext)
                .inflate(R.layout.card_item_recibo_detalle_scroll,parent,false);
        return new cViewHolderRecivosScroll(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderRecivosScroll holder, int position)
    {
        /**
         *
         *         mTextViewnum.setText(mReciver.getNum_recivo());
         *         mTextViewfecha.setText(mReciver.getFecha());
         *         mTextViewconcepto.setText(mReciver.getTexto_main());
         *         mTextViewmonto.setText("$ "+mReciver.getDinero_total());
         * **/


        holder.mTextViewConcepto.setText(mReciverArrayList.get(position).getTexto_main());
        holder.mTextViewFechaPago.setText(mReciverArrayList.get(position).getFecha());
        holder.mTextViewMonto.setText("$ "+mReciverArrayList.get(position).getDinero_total());
        holder.mTextViewNumOpe.setText(mReciverArrayList.get(position).getNum_recivo());
    }

    @Override
    public int getItemCount() {
        return mReciverArrayList.size();
    }

    public class cViewHolderRecivosScroll extends RecyclerView.ViewHolder
    {

        private TextView mTextViewNumOpe;
        private TextView mTextViewFechaPago;
        private TextView mTextViewConcepto;
        private TextView mTextViewMonto;


        public cViewHolderRecivosScroll(@NonNull View itemView)
        {
            super(itemView);

            mTextViewNumOpe = itemView.findViewById(R.id.id_textview_num_ope);
            mTextViewFechaPago = itemView.findViewById(R.id.id_textview_fecha_pago);
            mTextViewConcepto = itemView.findViewById(R.id.id_textview_concepto);
            mTextViewMonto = itemView.findViewById(R.id.id_textview_monto);

        }
    }
}
