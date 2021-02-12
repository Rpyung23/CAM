package com.virtualcode7ecuadorvigitrack.myapplication.adapters;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;

import java.util.ArrayList;

public class cAdapterNoticiasDetailsScroll extends RecyclerView.Adapter<cAdapterNoticiasDetailsScroll.cViewHolderNoticiasScroll>
{
    private Context mContext;
    private ArrayList<cNoticias> mNoticiasArrayList;

    public cAdapterNoticiasDetailsScroll(Context mContext, ArrayList<cNoticias> mNoticiasArrayList) {
        this.mContext = mContext;
        this.mNoticiasArrayList = mNoticiasArrayList;
    }

    @NonNull
    @Override
    public cViewHolderNoticiasScroll onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_noticia_detalles_scroll,parent,false);

        return new cViewHolderNoticiasScroll(mView);
    }


    @Override
    public void onBindViewHolder(@NonNull cViewHolderNoticiasScroll holder, int position)
    {
            holder.mTextViewTitulo.setText(mNoticiasArrayList.get(position).getTitulo());
            holder.mTextViewFecha.setText(mNoticiasArrayList.get(position).getFecha());

            Picasso.with(mContext).load(mNoticiasArrayList.get(position).getmUriPicturePrincipalNoticia())
                    .error(R.drawable.img_error)
                    .placeholder(R.drawable.img_load)
                    .into(holder.mImageView);

        holder.mTextViewContenido
                .setText(Html.fromHtml(mNoticiasArrayList.get(position)
                .getTextoNoticia()));


        Log.e("HTML",mNoticiasArrayList
                .get(position).getTextoNoticia());

    }



    @Override
    public int getItemCount() {
        return mNoticiasArrayList.size();
    }


    public class cViewHolderNoticiasScroll extends RecyclerView.ViewHolder
    {
        private ImageView mImageView;
        private TextView mTextViewFecha;
        private TextView mTextViewContenido;
        private TextView mTextViewTitulo;


        public cViewHolderNoticiasScroll(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.id_image_noticia_details);
            mTextViewFecha = itemView.findViewById(R.id.id_textview_fecha_noticia_details);
            mTextViewContenido = itemView.findViewById(R.id.id_textview_contenido);
            mTextViewTitulo = itemView.findViewById(R.id.id_titulo);
        }

        public TextView getmTextViewContenido() {
            return mTextViewContenido;
        }
    }
}
