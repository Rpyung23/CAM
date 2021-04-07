package com.virtualcode7ecuadorvigitrack.myapplication.adapters.notificacion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNotificationSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cStringMesDia;

import java.util.ArrayList;

public class cAdapterNotificationScroll extends RecyclerView.Adapter<cAdapterNotificationScroll.cViewHolderNotificationScroll>
{
    private ArrayList<cNotificationSocio> mNotificationSocios;

    public cAdapterNotificationScroll(ArrayList<cNotificationSocio> mNotificationSocios) {
        this.mNotificationSocios = mNotificationSocios;
    }

    @NonNull
    @Override
    public cViewHolderNotificationScroll onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification_details,parent,false);

        return new cViewHolderNotificationScroll(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderNotificationScroll holder, int position)
    {
        holder.mTextViewTitulo.setText(mNotificationSocios.get(position).getNotificacion_titulo());
        String fecha = mNotificationSocios.get(position).getFecha();
        if(fecha!=null && !fecha.isEmpty())
        {
            holder.mTextViewFecha.setText(new cStringMesDia().dia(fecha)+" "+new cStringMesDia().mes(fecha)+" de "+new cStringMesDia().year(fecha));
        }else
            {
                holder.mTextViewFecha.setText("Sin Fecha");
            }
        holder.mTextViewDetalle.setText(mNotificationSocios.get(position).getMensaje());
    }

    @Override
    public int getItemCount() {
        return mNotificationSocios.size();
    }

    public class cViewHolderNotificationScroll extends RecyclerView.ViewHolder
    {
        private TextView mTextViewTitulo;
        private TextView mTextViewFecha;
        private TextView mTextViewDetalle;
        public cViewHolderNotificationScroll(@NonNull View itemView) {
            super(itemView);

            mTextViewDetalle  = itemView.findViewById(R.id.id_textview_body_noti);
            mTextViewFecha =  itemView.findViewById(R.id.id_textview_date_noti);
            mTextViewTitulo = itemView.findViewById(R.id.id_textview_tipo_noti);

        }
    }
}
