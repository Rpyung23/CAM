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
        View mView = LayoutInflater.from(mContext).inflate(R.layout.card_eventos_3,parent,false);
        mView.setOnClickListener(this);
        return new cViewHolderEventos(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderEventos holder, int position)
    {
        if(getItemCount()>0)
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


                /*if (holder.mTextViewFechaDestino.getText().length()>6)
                {
                    holder.mTextViewFechaDestino.setTextSize(mContext.getResources()
                            .getDimension(R.dimen.texto_fecha_destino_inicio));
                }*/
            }



            if(mEventosArrayList.get(position).getFecha().equals(mEventosArrayList.get(position).getFecha_fin()))
            {
                /****/
                holder.mLinearLayoutCompatFechaFin.setVisibility(View.GONE);
                holder.mTextViewFechaDesde.setVisibility(View.GONE);

                LinearLayoutCompat.LayoutParams mLayoutParams = (LinearLayoutCompat.LayoutParams) holder.mLinearLayoutCompat_Dates_All.getLayoutParams();

                mLayoutParams.setMargins(4,-60,4,0);

                holder.mLinearLayoutCompat_Dates_All.setLayoutParams(mLayoutParams);
            }


            holder.mTextViewTitulo.setText(mEventosArrayList.get(position).getTitulo());
            //holder.mTextViewTitulo.setText(mEventosArrayList.get(position).getTitulo());
            holder.mTextViewDireccion.setText(mEventosArrayList.get(position).getDireccion());
            holder.mTextViewFechaInicio.setText(new cStringMesDia().mes(mEventosArrayList.get(position).getFecha()));

            holder.mTextViewFechaInicioNum.setText(new cStringMesDia().dia(mEventosArrayList.get(position).getFecha()));

        /*if (holder.mTextViewFechaInicio.getText().length()>6)
        {
            holder.mTextViewFechaInicio.setTextSize(mContext.getResources()
                    .getDimension(R.dimen.texto_fecha_destino_inicio));

        }*/

            Picasso.with(mContext).load(mEventosArrayList.get(position).getUri_foto())
                    .error(R.drawable.img_error)
                    .placeholder(R.drawable.img_load)
                    .into(holder.mImageView);
            /**
             *     <dimen name="texto_fecha_destino_inicio">12sp</dimen>
             *     <dimen name="textsize_num_fecha">20sp</dimen>
             *
             * **/
        }
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
        private CardView mCardView;
        private TextView mTextViewTitulo;
        private TextView mTextViewDireccion;
        private TextView mTextViewFechaInicio;
        private TextView mTextViewFechaDestino;
        private TextView mTextViewFechaInicioNum;
        private TextView mTextViewFechaDestinoNum;
        private LinearLayoutCompat mLinearLayoutCompatFechaFin;


        private LinearLayoutCompat mLinearLayoutCompat_Dates_All;


        private TextView mTextViewFechaDesde;
        private View mViewFechasContainer;

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
            mViewFechasContainer = itemView.findViewById(R.id.id_view_ContainerFechas);



            /*mTextViewFechaInicioNum.setTextSize(20);
            mTextViewFechaDestinoNum.setTextSize(20);*/


            mTextViewTitulo.setTextSize(17);
            mTextViewDireccion.setTextSize(14);
        }
    }
}
