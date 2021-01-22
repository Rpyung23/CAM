package com.virtualcode7ecuadorvigitrack.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterNoticias.cViewHolderNoticias;

import java.util.ArrayList;

public class cAdapterNoticiasDetailsPicture  extends RecyclerView.Adapter<cAdapterNoticiasDetailsPicture.cViewHolderDetailsPicture>
{
    private ArrayList<String> mStringArrayListPicture;
    private Context mContext;

    public cAdapterNoticiasDetailsPicture(ArrayList<String> mStringArrayListPicture, Context mContext) {
        this.mStringArrayListPicture = mStringArrayListPicture;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public cViewHolderDetailsPicture onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.card_noticias_details_imagen,parent,false);
        return new cViewHolderDetailsPicture(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderDetailsPicture holder, int position)
    {
        Picasso.with(mContext).load(mStringArrayListPicture.get(position))
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_load)
                .into(holder.mImageViewDetailsPicture);
    }

    @Override
    public int getItemCount() {
        return mStringArrayListPicture.size();
    }


    public class cViewHolderDetailsPicture extends RecyclerView.ViewHolder
    {
        ImageView mImageViewDetailsPicture;

        public cViewHolderDetailsPicture(@NonNull View itemView)
        {
            super(itemView);
            mImageViewDetailsPicture = itemView.findViewById(R.id.id_picture_noticias_details);
        }
    }
}
