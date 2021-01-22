package com.virtualcode7ecuadorvigitrack.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNotificationSocio;

import java.util.ArrayList;

public class cAdapterNotificationSocios extends
        RecyclerView.Adapter<cAdapterNotificationSocios.cViewHolderNotification>
    implements View.OnClickListener
{
    private ArrayList<cNotificationSocio> mNotificationSocioArrayList;
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public cAdapterNotificationSocios(ArrayList<cNotificationSocio> mNotificationSocioArrayList,
                                      Context mContext, View.OnClickListener mOnClickListener) {
        this.mNotificationSocioArrayList = mNotificationSocioArrayList;
        this.mContext = mContext;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public cViewHolderNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_notification
                ,parent,false);
        mView.setOnClickListener(this);
        return new cViewHolderNotification(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderNotification holder, int position)
    {
        if (mNotificationSocioArrayList.get(position).getMensaje().length()>10)
        {
            holder.mTextViewPreviewMsm.setText(mNotificationSocioArrayList.get(position).getMensaje()
                    .substring(0,10));
        }else
            {
                holder.mTextViewPreviewMsm.setText(mNotificationSocioArrayList.get(position).getMensaje()
                        .substring(0,5));
            }
        holder.mTextViewFecha.setText(mNotificationSocioArrayList.get(position).getFecha());
        if (mNotificationSocioArrayList.get(position).isLeido())
        {
            holder.mImageView.setBackground(mContext.getDrawable(R.drawable.bg_img_notification_close));
            holder.mImageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_mark_email_read));
        }

        holder.mTextViewTitulo.setText(mNotificationSocioArrayList.get(position).getNotificacion_titulo());
    }

    @Override
    public int getItemCount() {
        return mNotificationSocioArrayList.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnClickListener!=null)
        {
            mOnClickListener.onClick(view);
        }
    }

    public class cViewHolderNotification extends RecyclerView.ViewHolder
    {
        private ImageView mImageView;
        private TextView mTextViewPreviewMsm;
        private TextView mTextViewFecha;
        private TextView mTextViewTitulo;

        public cViewHolderNotification(@NonNull View itemView)
        {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView_item_notification_icon);
            mTextViewFecha = itemView.findViewById(R.id.textViewitem_notification_date);
            mTextViewPreviewMsm = itemView.findViewById(R.id.textViewitem_notification_sub_text);
            mTextViewTitulo = itemView.findViewById(R.id.textView__item_notification_title);
        }
    }
}
