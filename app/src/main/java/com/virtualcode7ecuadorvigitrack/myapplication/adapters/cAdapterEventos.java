package com.virtualcode7ecuadorvigitrack.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cStringMesDia;

import java.util.ArrayList;

public class cAdapterEventos extends RecyclerView.Adapter<cAdapterEventos.cViewHolderEventos> implements View.OnClickListener
{
    private ArrayList<cEventos> mEventosArrayList;
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public cAdapterEventos(ArrayList<cEventos> mEventosArrayList, Context mContext) {
        this.mEventosArrayList = mEventosArrayList;
        this.mContext = mContext;
    }

    public cAdapterEventos(ArrayList<cEventos> mEventosArrayList, Context mContext, View.OnClickListener mOnClickListener) {
        this.mEventosArrayList = mEventosArrayList;
        this.mContext = mContext;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public cViewHolderEventos onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.card_eventos,parent,false);
        mView.setOnClickListener(this);
        return new cViewHolderEventos(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderEventos holder, int position)
    {
        if ( mEventosArrayList.get(position).getFecha_fin()==null
                ||mEventosArrayList.get(position).getFecha_fin().isEmpty())
        {
            holder.mLinearLayoutCompatFechaFin.setVisibility(View.GONE);
        }else
            {
                holder.mLinearLayoutCompatFechaFin.setVisibility(View.VISIBLE);
                holder.mTextViewFechaDestino.setText(new cStringMesDia().mes(mEventosArrayList.get(position).getFecha_fin()));
                holder.mTextViewFechaDestinoNum.setText(new cStringMesDia().dia(mEventosArrayList.get(position).getFecha_fin()));
            }

        if(mEventosArrayList.get(position).getTitulo().length()>16)
        {
            holder.mTextViewTitulo.setText(mEventosArrayList.get(position).getTitulo().substring(0,11)+"");
        }else
            {
                holder.mTextViewTitulo.setText(mEventosArrayList.get(position).getTitulo());
            }
        //holder.mTextViewTitulo.setText(mEventosArrayList.get(position).getTitulo());
        holder.mTextViewDireccion.setText(mEventosArrayList.get(position).getDireccion());
        holder.mTextViewFechaInicio.setText(new cStringMesDia().mes(mEventosArrayList.get(position).getFecha()));
        holder.mTextViewFechaInicioNum.setText(new cStringMesDia().dia(mEventosArrayList.get(position).getFecha()));


        Picasso.with(mContext).load(mEventosArrayList.get(position).getUri_foto())
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_load)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mEventosArrayList.size();
    }

    @Override
    public void onClick(View view)
    {
        if (mOnClickListener!=null)
        {
            mOnClickListener.onClick(view);
        }
    }

    public class cViewHolderEventos extends RecyclerView.ViewHolder
    {
        private ImageView mImageView;
        private TextView mTextViewTitulo;
        private TextView mTextViewDireccion;
        private TextView mTextViewFechaInicio;
        private TextView mTextViewFechaDestino;
        private TextView mTextViewFechaInicioNum;
        private TextView mTextViewFechaDestinoNum;
        private LinearLayoutCompat mLinearLayoutCompatFechaFin;

        public cViewHolderEventos(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.id_image_evento);
            mTextViewTitulo = itemView.findViewById(R.id.id_textview_titulo);
            mTextViewDireccion = itemView.findViewById(R.id.id_textview_direccion);
            mTextViewFechaInicio = itemView.findViewById(R.id.id_textview_fecha_inicio);
            mTextViewFechaDestino = itemView.findViewById(R.id.id_textview_fecha_fin);
            mTextViewFechaInicioNum = itemView.findViewById(R.id.id_textview_fecha_inicio_num);
            mTextViewFechaDestinoNum = itemView.findViewById(R.id.id_textview_fecha_fin_num);
            mLinearLayoutCompatFechaFin = itemView.findViewById(R.id.id_linear_layout_fecha_fin);

            mTextViewFechaInicioNum.setTextSize(20);
            mTextViewFechaDestinoNum.setTextSize(20);
            mTextViewTitulo.setTextSize(17);
            mTextViewDireccion.setTextSize(14);
        }
    }
}
