package com.virtualcode7ecuadorvigitrack.myapplication.adapters.eventos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.cEventRecyclerViewEvento;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cStringMesDia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class cAdapterEventos extends RecyclerView.Adapter<cAdapterEventos.cViewHolderEventos>
        implements Serializable
{
    private List<cEventos> mEventosArrayList;
    private Context mContext;
    private RecyclerView mRecyclerViewEventos;
    private cEventRecyclerViewEvento mEventRecyclerViewEvento;


    public cAdapterEventos(List<cEventos> mEventosArrayList,
                           Context mContext, RecyclerView mRecyclerViewEventos,
                           cEventRecyclerViewEvento mEventRecyclerViewEvento) {

        this.mEventosArrayList = mEventosArrayList;
        this.mContext = mContext;
        this.mRecyclerViewEventos = mRecyclerViewEventos;
        this.mEventRecyclerViewEvento = mEventRecyclerViewEvento;
    }

    @NonNull
    @Override
    public cViewHolderEventos onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.card_eventos_3,parent,false);

        return new cViewHolderEventos(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderEventos holder, int position)
    {
        if(getItemCount()>0)
        {

            holder.mTextViewFechaDesde.setVisibility(View.VISIBLE);

            if ( mEventosArrayList.get(position).getFecha_fin()==null
                    || mEventosArrayList.get(position).getFecha_fin().isEmpty())
            {
                holder.mLinearLayoutCompatFechaFin.setVisibility(View.GONE);
                holder.mTextViewFechaDesde.setVisibility(View.GONE);
            }else
            {
                holder.mLinearLayoutCompatFechaFin.setVisibility(View.VISIBLE);
                holder.mTextViewFechaDestino.setText(new cStringMesDia().mes(mEventosArrayList.get(position).getFecha_fin()));
                holder.mTextViewFechaDestinoNum.setText(new cStringMesDia().dia(mEventosArrayList.get(position).getFecha_fin()));

            }

            if(mEventosArrayList.get(position).getFecha()
                    .equals(mEventosArrayList.get(position).getFecha_fin()))
            {
                /****/
                holder.mLinearLayoutCompatFechaFin.setVisibility(View.GONE);
                holder.mTextViewFechaDesde.setVisibility(View.GONE);

                holder.mTextViewFechaInicio.setPadding(3,0,3,0);

                ViewGroup.LayoutParams params = holder.mLinearLayoutCompat_Dates_All.getLayoutParams();

                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.mLinearLayoutCompat_Dates_All.setLayoutParams(params);

            }


            holder.mTextViewTitulo.setText(mEventosArrayList.get(position).getTitulo());
            //holder.mTextViewTitulo.setText(mEventosArrayList.get(position).getTitulo());
            holder.mTextViewDireccion.setText(mEventosArrayList.get(position).getDireccion());
            holder.mTextViewFechaInicio.setText(new cStringMesDia().mes(mEventosArrayList.get(position).getFecha()));

            holder.mTextViewFechaInicioNum.setText(new cStringMesDia().dia(mEventosArrayList.get(position).getFecha()));


            Picasso.with(mContext).load(mEventosArrayList.get(position).getUri_foto())
                    .error(R.drawable.img_error)
                    .placeholder(R.drawable.img_load)
                    .into(holder.mImageView);

        }
    }

    @Override
    public int getItemCount() {
        return mEventosArrayList.size();
    }



    public class cViewHolderEventos extends RecyclerView.ViewHolder
    {
        private ImageView mImageView;
        private MaterialCardView mCardView;
        private TextView mTextViewTitulo;
        private TextView mTextViewDireccion;
        private TextView mTextViewFechaInicio;
        private TextView mTextViewFechaDestino;
        private TextView mTextViewFechaInicioNum;
        private TextView mTextViewFechaDestinoNum;
        private LinearLayoutCompat mLinearLayoutCompatFechaFin;


        private LinearLayoutCompat mLinearLayoutCompat_Dates_All;


        private TextView mTextViewFechaDesde;



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
            mCardView = itemView.findViewById(R.id.id_card_view_evento);

            mLinearLayoutCompat_Dates_All = itemView.findViewById(R.id.linear_layout_cotainer_fechas_all);

            mLinearLayoutCompatFechaFin = itemView.findViewById(R.id.id_linear_layout_fecha_fin);
            mTextViewFechaDesde = itemView.findViewById(R.id.id_textview_desde_texto);


            mTextViewTitulo.setTextSize(17);
            mTextViewDireccion.setTextSize(14);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int pos = mRecyclerViewEventos.getChildAdapterPosition(itemView);
                    pos+=1;
                    mEventRecyclerViewEvento.onClickEvento(pos);
                }
            });


        }
    }
}
