package com.virtualcode7ecuadorvigitrack.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cStatusCuentaSocios;

import java.util.ArrayList;

public class cAdapterStatusCuentaSocios
        extends RecyclerView.Adapter<cAdapterStatusCuentaSocios.cViewHolderStatusCuentaSocios>
    implements View.OnClickListener
{

    private ArrayList<cStatusCuentaSocios> mStatusCuentaSociosArrayList;
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public cAdapterStatusCuentaSocios(ArrayList<cStatusCuentaSocios> mStatusCuentaSociosArrayList,
                                      Context mContext, View.OnClickListener mOnClickListener) {
        this.mStatusCuentaSociosArrayList = mStatusCuentaSociosArrayList;
        this.mContext = mContext;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public cViewHolderStatusCuentaSocios onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_state_account,parent,false);
        mView.setOnClickListener(this);
        return new cViewHolderStatusCuentaSocios(mView);
    }


    @Override
    public void onBindViewHolder(@NonNull cViewHolderStatusCuentaSocios holder, int position)
    {
        if (mStatusCuentaSociosArrayList.get(position).getFecha_pago()==null ||
                mStatusCuentaSociosArrayList.get(position).getFecha_pago().isEmpty() ||
                mStatusCuentaSociosArrayList.get(position).getFecha_pago().equals("null"))
        {
            holder.mTextViewFechaPago.setText("Sin Fecha");
        }else
            {
                holder.mTextViewFechaPago.setText(mStatusCuentaSociosArrayList.get(position).getFecha_pago());
            }

        holder.mTextViewCasillero.setText(mStatusCuentaSociosArrayList.get(position).getTitulo_card_casillero());
        holder.mTextViewPago.setText("$ "+mStatusCuentaSociosArrayList.get(position).getPrecio_total());

        if (mStatusCuentaSociosArrayList.get(position).getMovientoEstado().equals("T"))
        {
            Picasso.with(mContext).load(R.drawable.timer_green)
                    .into(holder.mImageViewStatus);
        }

        if (mStatusCuentaSociosArrayList.get(position).getMovientoEstado().equals("A"))
        {
            Picasso.with(mContext).load(R.drawable.checkbox_gris)
                    .into(holder.mImageViewStatus);
        }

    }

    @Override
    public int getItemCount()
    {
        return mStatusCuentaSociosArrayList.size();
    }

    @Override
    public void onClick(View view)
    {
        if (mOnClickListener!=null)
        {
            mOnClickListener.onClick(view);
        }
    }

    public class cViewHolderStatusCuentaSocios extends RecyclerView.ViewHolder
    {
        private TextView mTextViewFechaPago;
        private TextView mTextViewPago;
        private TextView mTextViewCasillero;
        private ImageView mImageViewStatus;

        public cViewHolderStatusCuentaSocios(@NonNull View itemView)
        {
            super(itemView);

            mTextViewFechaPago = itemView.findViewById(R.id.textView_nro_receipt);
            mTextViewPago = itemView.findViewById(R.id.textView_amount);
            mTextViewCasillero = itemView.findViewById(R.id.textView_main);
            mImageViewStatus = itemView.findViewById(R.id.imageView3);
        }
    }
}
