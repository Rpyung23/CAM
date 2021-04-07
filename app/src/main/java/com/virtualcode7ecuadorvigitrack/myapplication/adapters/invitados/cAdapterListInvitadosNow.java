package com.virtualcode7ecuadorvigitrack.myapplication.adapters.invitados;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.*;

import java.util.ArrayList;

public class cAdapterListInvitadosNow extends RecyclerView.Adapter<cAdapterListInvitadosNow.cViewHolderListInvitadosNow>
        implements View.OnClickListener
{

    private ArrayList<cListInvitadosActuales> mListInvitadosActualesArrayList;
    private View.OnClickListener mOnClickListener;
    private Context mContext;

    public cAdapterListInvitadosNow(ArrayList<cListInvitadosActuales> mListInvitadosActualesArrayList,
                                    View.OnClickListener mOnClickListener,Context mContext) {
        this.mListInvitadosActualesArrayList = mListInvitadosActualesArrayList;
        this.mOnClickListener = mOnClickListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public cViewHolderListInvitadosNow onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_invitado_actuales,
                parent,false);

        mView.setOnClickListener(this);

        return new cViewHolderListInvitadosNow(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderListInvitadosNow holder, int position)
    {
        String separator  = "\n";
        String names =  mListInvitadosActualesArrayList.get(position).getmNombresCompletos();
        String[] vector_names =  names.split(",");
        String names_final = "";


        for (int i = 0;i<vector_names.length;i++)
        {
            if(i == vector_names.length-1 )
            {
                names_final = names_final.concat(vector_names[i]);
            }else {
                names_final = names_final.concat(vector_names[i].concat(separator));
            }
        }

        holder.mTextViewList.setText(names_final);

        holder.mTextViewFechaI.setText(mListInvitadosActualesArrayList.get(position)
                .getmFechaInicial());

        holder.mTextViewFechaF.setText(mListInvitadosActualesArrayList.get(position)
                .getmFechaFinal());

        switch (mListInvitadosActualesArrayList.get(position).getStatusListInvitadosNow())
        {
            case 1:
                //colorText(R.color.ClubColorPrimary,holder.mTextViewLabelInvitados,holder.mTextViewList);

                holder.mTextViewLabelInvitados.setTextColor(mContext.getResources().getColor(R.color.ClubColorPrimary));
                holder.mTextViewList.setTextColor(mContext.getResources().getColor(R.color.ClubColorPrimary));

                insertImagen(R.drawable.ic_check_box,holder.mAppCompatImageView);

                break;
            case 2:
                //colorText(R.color.ClubColorPrimary_55,holder.mTextViewLabelInvitados,holder.mTextViewList);

                holder.mTextViewLabelInvitados.setTextColor(mContext.getResources().getColor(R.color.ClubColorPrimary_55));
                holder.mTextViewList.setTextColor(mContext.getResources().getColor(R.color.ClubColorPrimary_55));

                insertImagen(R.drawable.time_list_invitados,holder.mAppCompatImageView);


                break;
            case 3:

                //colorText(R.color.red,holder.mTextViewLabelInvitados,holder.mTextViewList);
                holder.mTextViewLabelInvitados.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.mTextViewList.setTextColor(mContext.getResources().getColor(R.color.red));

                insertImagen(R.drawable.block_list_invitados,holder.mAppCompatImageView);

                break;
        }

    }

    private void insertImagen(int ic_check_box, AppCompatImageView mAppCompatImageView)
    {
        Picasso.with(mContext)
                .load(ic_check_box)
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_load)
                .into(mAppCompatImageView);
    }



    @Override
    public int getItemCount() {
        return mListInvitadosActualesArrayList.size();
    }

    @Override
    public void onClick(View v)
    {
        if (mOnClickListener!=null)
        {
            mOnClickListener.onClick(v);
        }
    }

    class cViewHolderListInvitadosNow extends RecyclerView.ViewHolder
    {
        private TextView mTextViewLabelInvitados;
        private TextView mTextViewList;
        private TextView mTextViewFechaI;
        private TextView mTextViewFechaF;
        private AppCompatImageView mAppCompatImageView;

        public cViewHolderListInvitadosNow(@NonNull View itemView) {
            super(itemView);

            mTextViewList =  itemView.findViewById(R.id.tvListNameInvitados);
            mTextViewFechaI = itemView.findViewById(R.id.tvListInvitadoFechaDesde);
            mTextViewFechaF = itemView.findViewById(R.id.tvListInvitadoFechaHasta);
            mAppCompatImageView = itemView.findViewById(R.id.imgStatusInvitado);
            mTextViewLabelInvitados = itemView.findViewById(R.id.tvInvitados);

        }
    }
}
