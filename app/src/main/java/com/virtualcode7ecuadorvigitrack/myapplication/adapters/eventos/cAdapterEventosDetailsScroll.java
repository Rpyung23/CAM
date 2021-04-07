package com.virtualcode7ecuadorvigitrack.myapplication.adapters.eventos;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cEventos;

import java.util.ArrayList;

public class cAdapterEventosDetailsScroll extends RecyclerView.Adapter<cAdapterEventosDetailsScroll.cViewHolderEventosDetailsScroll>
{
    private Context mContext;
    private ArrayList<cEventos> mEventosArrayList;

    public cAdapterEventosDetailsScroll(Context mContext, ArrayList<cEventos> mEventosArrayList) {
        this.mContext = mContext;
        this.mEventosArrayList = mEventosArrayList;
    }

    @NonNull
    @Override
    public cViewHolderEventosDetailsScroll onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View mView = LayoutInflater.from(mContext).inflate(R.layout.card_eventos_detalles_scroll,parent,false);

        return new cViewHolderEventosDetailsScroll(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderEventosDetailsScroll holder, int position)
    {
        if (mEventosArrayList.get(position).getFecha()!=null)
        {
            holder.mTextViewFechaPubli.setText(mEventosArrayList.get(position).getFecha());
        }else
            {
                holder.mTextViewFechaPubli.setText("Sin Fecha");
            }





        if (mEventosArrayList.get(position).getDireccion()!=null)
        {
            holder.mTextViewDirec.setText(mEventosArrayList.get(position).getDireccion());
        }else
        {
            holder.mTextViewDirec.setText("Sin Dirección");
        }


        holder.mTextViewTitle.setText(mEventosArrayList.get(position).getTitulo());










        //holder.mTextViewAcerca.setText(mEventosArrayList.get(position).getAcerca_de());




        Picasso.with(mContext).load(mEventosArrayList.get(position).getUri_foto())
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_load)
                .into(holder.mImageView);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            holder.mTextViewAcerca
                    .setText(Html.fromHtml(mEventosArrayList.get(position)
                            .getAcerca_de(),Html.FROM_HTML_MODE_LEGACY));
        }else {
            holder.mTextViewAcerca
                    .setText(Html.fromHtml(mEventosArrayList.get(position)
                            .getAcerca_de()));
        }


        holder.mTextViewAcerca.setMovementMethod(LinkMovementMethod.getInstance());




        if(holder.mTextViewTitle.getLineCount()==1)
        {

            LinearLayoutCompat.LayoutParams mLayoutParams = (LinearLayoutCompat.LayoutParams)
                    holder.mLinearLayoutCompat.getLayoutParams();

            mLayoutParams.setMargins(0,-230,0,0);

            holder.mLinearLayoutCompat.setLayoutParams(mLayoutParams);
        }

        if(holder.mTextViewTitle.getLineCount() == 2)
        {

            LinearLayoutCompat.LayoutParams mLayoutParams = (LinearLayoutCompat.LayoutParams)
                    holder.mLinearLayoutCompat.getLayoutParams();

            mLayoutParams.setMargins(0,-240,0,0);

            holder.mLinearLayoutCompat.setLayoutParams(mLayoutParams);
        }



        if(holder.mTextViewTitle.getLineCount()>=3)
        {

            LinearLayoutCompat.LayoutParams mLayoutParams = (LinearLayoutCompat.LayoutParams)
                    holder.mLinearLayoutCompat.getLayoutParams();

            mLayoutParams.setMargins(0,-275,0,0);

            holder.mLinearLayoutCompat.setLayoutParams(mLayoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return mEventosArrayList.size();
    }

    public class cViewHolderEventosDetailsScroll extends RecyclerView.ViewHolder
    {
        private ImageView mImageView;
        private TextView mTextViewFechaPubli;
        private TextView mTextViewDirec;
        private TextView mTextViewTitle;
        private TextView mTextViewAcerca;

        private LinearLayoutCompat mLinearLayoutCompat;

        public cViewHolderEventosDetailsScroll(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.id_img_principal_evento);
            mTextViewFechaPubli = itemView.findViewById(R.id.textView_date_event);
            mTextViewDirec = itemView.findViewById(R.id.textView2);
            mTextViewTitle = itemView.findViewById(R.id.id_titulo_evento);
            mTextViewAcerca = itemView.findViewById(R.id.textView_about_event);
            mLinearLayoutCompat = itemView.findViewById(R.id.linearEventosDetails);

        }

    }
}
