package com.virtualcode7ecuadorvigitrack.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cInfoCustionarioCovid;

import java.util.ArrayList;

public class cAdapterInfoCuestionario extends RecyclerView
        .Adapter<cAdapterInfoCuestionario.cViewHolderInfoCuestionario> implements View.OnClickListener
{
    private ArrayList<cInfoCustionarioCovid> mInfoCustionarioCovidArrayList;
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public cAdapterInfoCuestionario(ArrayList<cInfoCustionarioCovid> mInfoCustionarioCovidArrayList,
                                    Context mContext, View.OnClickListener mOnClickListener) {
        this.mInfoCustionarioCovidArrayList = mInfoCustionarioCovidArrayList;
        this.mContext = mContext;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public cViewHolderInfoCuestionario onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_cuestionario_covid,parent,false);

        mView.setOnClickListener(this);

        return new cViewHolderInfoCuestionario(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderInfoCuestionario holder, int position)
    {
        holder.mTextViewBeneficiario.setText(mInfoCustionarioCovidArrayList.get(position).getmStringBeneficiario());
        holder.mTextViewBeneficiario.setMovementMethod(new ScrollingMovementMethod());
        holder.mTextViewTipo.setText(mInfoCustionarioCovidArrayList.get(position).getmStringTipo());
        holder.mTextViewEstado.setText(" "+mInfoCustionarioCovidArrayList.get(position).getmStringEstado());
        if (mInfoCustionarioCovidArrayList.get(position).isColor_red())
        {
            holder.mTextViewEstado.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.circle_red)
                    ,null,null,null);
            holder.mTextViewEstado.setTextColor(Color.parseColor("#4e4b7f"));
        }else if(mInfoCustionarioCovidArrayList.get(position).isColor_verde())
        {
            holder.mTextViewEstado.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.circle_green)
                    ,null,null,null);
            holder.mTextViewEstado.setTextColor(mContext.getResources().getColor(R.color.black));

        }else if(mInfoCustionarioCovidArrayList.get(position).isIsrenovado())
        {
            /**RENOVADO**/

            holder.mTextViewEstado.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.timer_yellow)
                    ,null,null,null);
            holder.mTextViewEstado.setTextColor(Color.parseColor("#f4c40c"));
            holder.mTextViewEstado.setText("Procesando");
            //Toast.makeText(mContext, "REMOVADO CAMBIO", Toast.LENGTH_SHORT).show();
        }else if(mInfoCustionarioCovidArrayList.get(position).isColor_yellow())
        {
            holder.mTextViewEstado
                    .setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.circle_yellow)
                    ,null,null,null);
            holder.mTextViewEstado.setTextColor(Color.parseColor("#7b78b6"));
        }
    }

    @Override
    public int getItemCount() {
        return mInfoCustionarioCovidArrayList.size();
    }

    @Override
    public void onClick(View v)
    {
        mOnClickListener.onClick(v);
    }

    public class cViewHolderInfoCuestionario extends RecyclerView.ViewHolder
   {
       private TextView mTextViewBeneficiario;
       private TextView mTextViewTipo;
       private TextView mTextViewEstado;

       public cViewHolderInfoCuestionario(@NonNull View itemView)
       {
           super(itemView);

           mTextViewBeneficiario = itemView.findViewById(R.id.id_name);
           mTextViewTipo = itemView.findViewById(R.id.id_titular);
           mTextViewEstado = itemView.findViewById(R.id.id_estado);

       }
   }
}
