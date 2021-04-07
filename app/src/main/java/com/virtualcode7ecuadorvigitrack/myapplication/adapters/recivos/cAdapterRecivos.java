package com.virtualcode7ecuadorvigitrack.myapplication.adapters.recivos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cReciver;

import java.util.ArrayList;

public class cAdapterRecivos extends RecyclerView.Adapter<cAdapterRecivos.cViewHolderRecivos> implements View.OnClickListener
{
    private ArrayList<cReciver> mReciverArrayList;
    private Context mContext;
    private View.OnClickListener mOnClickListener;


    public cAdapterRecivos(ArrayList<cReciver> mReciverArrayList, Context mContext,
                           View.OnClickListener mOnClickListener)
    {
        this.mReciverArrayList = mReciverArrayList;
        this.mContext = mContext;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public cViewHolderRecivos onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_recivo,parent,false);
        mView.setOnClickListener(this);
        return new cViewHolderRecivos(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderRecivos holder, int position)
    {
        holder.mTextViewPrice.setText("$ "+mReciverArrayList.get(position).getDinero_total());
        holder.mTextViewDate.setText(mReciverArrayList.get(position).getFecha());
        holder.mTextViewTextMain.setText(mReciverArrayList.get(position).getTexto_main());
        holder.mTextViewRecivoNum.setText("Recibo "+mReciverArrayList.get(position).getNum_recivo());
    }

    @Override
    public int getItemCount() {
        return mReciverArrayList.size();
    }

    @Override
    public void onClick(View view)
    {
        if (mOnClickListener!=null)
        {
            mOnClickListener.onClick(view);
        }
    }

    public class cViewHolderRecivos extends RecyclerView.ViewHolder
    {
        private TextView mTextViewRecivoNum;
        private TextView mTextViewTextMain;
        private TextView mTextViewDate;
        private TextView mTextViewPrice;

        public cViewHolderRecivos(@NonNull View itemView)
        {
            super(itemView);

            mTextViewRecivoNum = itemView.findViewById(R.id.textView_nro_receipt);
            mTextViewTextMain = itemView.findViewById(R.id.textView_main);
            mTextViewDate = itemView.findViewById(R.id.textView_date);
            mTextViewPrice = itemView.findViewById(R.id.textView_amount);

        }
    }
}
