package com.virtualcode7ecuadorvigitrack.myapplication.adapters.noticias;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.cEventRecyclerViewNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;
import com.virtualcode7ecuadorvigitrack.myapplication.views.NoticiasViewPagerContenidoActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class cAdapterNoticias extends RecyclerView.Adapter<cAdapterNoticias.cViewHolderNoticias>
        implements  Serializable {

    private Context mContext;
    private List<cNoticias> mNoticiasArrayList;
    private cEventRecyclerViewNoticias mEventRecyclerViewNoticias;
    private RecyclerView mRecyclerViewNoticias;
    //private View.OnClickListener mOnClickListener;


    /*public cAdapterNoticias(Context mContext, List<cNoticias>
            mNoticiasArrayList, View.OnClickListener mOnClickListener) {
        this.mContext = mContext;
        this.mNoticiasArrayList = mNoticiasArrayList;
        this.mOnClickListener = mOnClickListener;
    }*/

    public cAdapterNoticias(Context mContext, List<cNoticias>
            mNoticiasArrayListm,cEventRecyclerViewNoticias mEventRecyclerViewNoticias
            ,RecyclerView mRecyclerViewNoticias) {
        this.mContext = mContext;
        this.mNoticiasArrayList = mNoticiasArrayListm;
        this.mEventRecyclerViewNoticias = mEventRecyclerViewNoticias;
        this.mRecyclerViewNoticias = mRecyclerViewNoticias;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    /*public View.OnClickListener getmOnClickListener() {
        return mOnClickListener;
    }*/

    @NonNull
    @Override
    public cViewHolderNoticias onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.card_noticias,parent,false);
        /*if (mOnClickListener!=null)
        {
            mView.setOnClickListener(this);
        }*/
        return new cViewHolderNoticias(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderNoticias holder, int position)
    {
        holder.mTextViewTitulo.setText(mNoticiasArrayList.get(position).getTitulo());
        holder.mTextViewFecha.setText(mNoticiasArrayList.get(position).getFecha());



        String path = mNoticiasArrayList.get(position).getmUriPicturePrincipalNoticia();

        Log.e("img","path : "+path);

        Picasso.with(mContext).load(path)
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_load)
                .into(holder.mImageViewNoticia);
    }



    @Override
    public int getItemCount() {
        return mNoticiasArrayList.size();
    }

    /*public void setmOnClickListener(View.OnClickListener mOnClickListener)
    {
        if (mOnClickListener!=null)
        {
            this.mOnClickListener = mOnClickListener;
        }

    }*/

    /*@Override
    public void onClick(View view)
    {
        mOnClickListener.onClick(view);
    }*/


    public class cViewHolderNoticias extends RecyclerView.ViewHolder
    {
        private TextView mTextViewTitulo;
        private TextView mTextViewFecha;
        private ImageView mImageViewNoticia;
        private CardView mCardViewContainer;

        public cViewHolderNoticias(@NonNull View itemView)
        {
            super(itemView);
            mTextViewTitulo = itemView.findViewById(R.id.id_titulo_noticia);
            mTextViewFecha = itemView.findViewById(R.id.id_fecha_noticia);
            mImageViewNoticia = itemView.findViewById(R.id.id_image_noticia);
            mCardViewContainer = itemView.findViewById(R.id.cardView2);
            mTextViewTitulo.setTextSize(13);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    int pos = mRecyclerViewNoticias.getChildAdapterPosition(itemView);
                    pos+=1;

                    //Toast.makeText(getmContext(), " poss : "+pos, Toast.LENGTH_SHORT).show();
                    mEventRecyclerViewNoticias.onClickNoticia(pos);

                }
            });
        }
    }


}
