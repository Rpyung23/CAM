package com.virtualcode7ecuadorvigitrack.myapplication.adapters.noticias;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
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
import com.virtualcode7ecuadorvigitrack.myapplication.models.cImageGetter;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;

import java.util.ArrayList;

public class cAdapterNoticiasDetailsScroll extends RecyclerView.Adapter<cAdapterNoticiasDetailsScroll.cViewHolderNoticiasScroll>
{
    private Context mContext;
    private ArrayList<cNoticias> mNoticiasArrayList;
    private cImageGetter mCImageGetter;

    public cAdapterNoticiasDetailsScroll(Context mContext,
                                         ArrayList<cNoticias> mNoticiasArrayList) {
        this.mContext = mContext;
        this.mNoticiasArrayList = mNoticiasArrayList;
        this.mCImageGetter = new cImageGetter(mContext);
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



            Picasso.with(mContext).load(mNoticiasArrayList.get(position)
                    .getmUriPicturePrincipalNoticia())
                    .error(R.drawable.img_error)
                    .placeholder(R.drawable.img_load)
                    .into(holder.mImageView);

        Spanned mSpanned = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            mSpanned = (Html.fromHtml(mNoticiasArrayList.get(position)
                            .getTextoNoticia(),Html.FROM_HTML_MODE_LEGACY,
                    mCImageGetter,null));
        }else {
            mSpanned = (Html.fromHtml(mNoticiasArrayList.get(position)
                            .getTextoNoticia(),mCImageGetter,null));
        }

        holder.mTextViewContenido.setText(mSpanned);

        holder.mTextViewContenido.setMovementMethod(LinkMovementMethod.getInstance());

        Log.e("HTML",mNoticiasArrayList
                .get(position).getTextoNoticia());

        Log.e("LINE CONT"," : "+holder.mTextViewTitulo.getLineCount());



        /*if(holder.mTextViewTitulo.getLineCount()==1)
        {

            LinearLayoutCompat.LayoutParams mLayoutParams = (LinearLayoutCompat.LayoutParams)
                    holder.mLinearLayoutCompat.getLayoutParams();

            mLayoutParams.setMargins(0,-230,0,0);

            holder.mLinearLayoutCompat.setLayoutParams(mLayoutParams);
        }

        if(holder.mTextViewTitulo.getLineCount() == 2)
        {

            LinearLayoutCompat.LayoutParams mLayoutParams = (LinearLayoutCompat.LayoutParams)
                    holder.mLinearLayoutCompat.getLayoutParams();

            mLayoutParams.setMargins(0,-240,0,0);

            holder.mLinearLayoutCompat.setLayoutParams(mLayoutParams);
        }



        if(holder.mTextViewTitulo.getLineCount()>=3)
        {

            LinearLayoutCompat.LayoutParams mLayoutParams = (LinearLayoutCompat.LayoutParams)
                    holder.mLinearLayoutCompat.getLayoutParams();

            mLayoutParams.setMargins(0,-275,0,0);

            holder.mLinearLayoutCompat.setLayoutParams(mLayoutParams);
        }*/

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
        //private LinearLayoutCompat mLinearLayoutCompat;


        public cViewHolderNoticiasScroll(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.id_image_noticia_details);
            mTextViewFecha = itemView.findViewById(R.id.id_textview_fecha_noticia_details);
            mTextViewContenido = itemView.findViewById(R.id.id_textview_contenido);
            mTextViewTitulo = itemView.findViewById(R.id.id_titulo);
            //mLinearLayoutCompat = itemView.findViewById(R.id.linearLayoutNoticia);
        }

    }
}
