package com.virtualcode7ecuadorvigitrack.myapplication.adapters.invitados;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.cOnClickInvitados;
import com.virtualcode7ecuadorvigitrack.myapplication.models.*;

import java.util.List;

public class cAdapterListInvitadosAdd extends RecyclerView.Adapter<cAdapterListInvitadosAdd.mViewHolderInvitadosAdd>
{
    private List<cInvitados> mListInvitados;
    private cOnClickInvitados mOnClickInvitados;
    private RecyclerView mRecyclerView;

    public cAdapterListInvitadosAdd(List<cInvitados> mListInvitados,
                                    cOnClickInvitados mOnClickInvitados,
                                    RecyclerView mRecyclerView)
    {
        this.mListInvitados = mListInvitados;
        this.mOnClickInvitados = mOnClickInvitados;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public mViewHolderInvitadosAdd onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_add_invitados,parent,false);
        return new mViewHolderInvitadosAdd(mView);
    }

    @Override
    public void onBindViewHolder(cAdapterListInvitadosAdd.mViewHolderInvitadosAdd holder,
                                 int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return mListInvitados.size();
    }


    public class mViewHolderInvitadosAdd extends RecyclerView.ViewHolder
    {


        public mViewHolderInvitadosAdd(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mOnClickInvitados.OnClickListener(mListInvitados,
                            mRecyclerView.getChildAdapterPosition(v));
                }
            });
        }

    }

}
