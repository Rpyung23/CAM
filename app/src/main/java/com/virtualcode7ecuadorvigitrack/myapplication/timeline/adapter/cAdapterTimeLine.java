package com.virtualcode7ecuadorvigitrack.myapplication.timeline.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.timeline.cTimeLine;

import java.util.List;

public class cAdapterTimeLine
        extends RecyclerView.Adapter<cAdapterTimeLine.cViewHolderTimeLine>
{
    private List<cTimeLine> mTimeLineList;
    private Context mContext;
    private List<Fragment> mFragmentList;
    private int fragmentContainer;
    private FragmentManager mFragmentManagerM;

    public cAdapterTimeLine(List<cTimeLine> mTimeLineList, Context mContext,
                            List<Fragment> mFragmentList, int fragmentContainer,
                            FragmentManager mFragmentManagerM)
    {
        this.mTimeLineList = mTimeLineList;
        this.mContext = mContext;
        this.mFragmentList = mFragmentList;
        this.fragmentContainer = fragmentContainer;
        this.mFragmentManagerM = mFragmentManagerM;
    }

    @Override
    public cViewHolderTimeLine onCreateViewHolder(ViewGroup parent,
                                                  int viewType)
    {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline,parent,false);
        return new cViewHolderTimeLine(mView);
    }

    @Override
    public void onBindViewHolder(cViewHolderTimeLine holder,
                                 int position)
    {

        if (mTimeLineList.get(position).isCheckCircle())/**Los datos fueron llenados con exito**/
        {
            holder.mLinearLayoutCompatCircle.setBackground(mTimeLineList.get(position).getBackground());


            holder.mViewLine.setBackgroundColor(mTimeLineList.get(position).getColor_check());


            /**El tamaño es el mismo q si estuviera desactivado solo cambia color
             *  y la imagen del marker**/


            holder.mImageViewMarker
                    .setImageDrawable(mTimeLineList.get(position).getImgCircleCheck());


            LinearLayoutCompat.LayoutParams mLayoutParams
                    = (LinearLayoutCompat.LayoutParams) holder.mLinearLayoutCompatCircle.getLayoutParams();
            mLayoutParams.width = (int) mContext.getResources()
                    .getDimension(R.dimen.circle_timeline_inactive);
            mLayoutParams.height = (int) mContext.getResources()
                    .getDimension(R.dimen.circle_timeline_inactive);


            ViewGroup.LayoutParams params = holder.mImageViewMarker.getLayoutParams();

            holder.mImageViewMarker.getLayoutParams().height = (int)mContext.getResources()
                    .getDimension(R.dimen.marker_timeline_inactive);
            holder.mImageViewMarker.getLayoutParams().width = (int)mContext.getResources()
                    .getDimension(R.dimen.marker_timeline_inactive);


            holder.mLinearLayoutCompatCircle.setLayoutParams(mLayoutParams);

        }else{
            if(mTimeLineList.get(position).isStatus())
            {
                holder.mLinearLayoutCompatCircle.setBackground(mTimeLineList.get(position).getBackground());
                holder.mImageViewMarker
                        .setImageDrawable(mTimeLineList.get(position).getImgMarker());

                holder.mViewLine.setBackgroundColor(mTimeLineList.get(position).getColorTimeLineActive());

                LinearLayoutCompat.LayoutParams mLayoutParams
                        = (LinearLayoutCompat.LayoutParams) holder.mLinearLayoutCompatCircle.getLayoutParams();
                mLayoutParams.width = (int) mContext.getResources()
                        .getDimension(R.dimen.circle_timeline_active);
                mLayoutParams.height = (int) mContext.getResources()
                        .getDimension(R.dimen.circle_timeline_active);
                holder.mLinearLayoutCompatCircle.setLayoutParams(mLayoutParams);


                if (mFragmentList!=null)
                {
                    String tag = "fragmentimeline"+position;
                    llenarFragmentContainer(tag,mFragmentList.get(position));
                }


            }else if (!mTimeLineList.get(position).isStatus())
            {
                holder.mLinearLayoutCompatCircle.setBackground(mTimeLineList.get(position).getBackground());
                holder.mImageViewMarker
                        .setImageDrawable(mTimeLineList.get(position).getImgMarker());

                holder.mViewLine.setBackgroundColor(mTimeLineList.get(position).getColorTimeLineInactive());

                LinearLayoutCompat.LayoutParams mLayoutParams
                        = (LinearLayoutCompat.LayoutParams) holder.mLinearLayoutCompatCircle.getLayoutParams();
                mLayoutParams.width = (int) mContext.getResources()
                        .getDimension(R.dimen.circle_timeline_inactive);
                mLayoutParams.height = (int) mContext.getResources()
                        .getDimension(R.dimen.circle_timeline_inactive);


                ViewGroup.LayoutParams params = holder.mImageViewMarker.getLayoutParams();

                holder.mImageViewMarker.getLayoutParams().height = (int)mContext.getResources()
                        .getDimension(R.dimen.marker_timeline_inactive);
                holder.mImageViewMarker.getLayoutParams().width = (int)mContext.getResources()
                        .getDimension(R.dimen.marker_timeline_inactive);


                holder.mLinearLayoutCompatCircle.setLayoutParams(mLayoutParams);
            }
        }

        if (position == mTimeLineList.size()-1){
            holder.mViewLine.setVisibility(View.GONE);
        }
    }

    public void inactiveElements()
    {
        for (int i=0;i<mTimeLineList.size();i++)
        {
            mTimeLineList.get(i).setStatus(false);
            mTimeLineList.get(i).setBackground(mContext.getResources().getDrawable(R.drawable.item_timeline_inactive));
        }
    }

    private void llenarFragmentContainer(String Tag,Fragment oF)
    {
        List<Fragment> fragmentList = mFragmentManagerM.getFragments();
        boolean bandera = false;/**bandera para saber si el Fragment ya esta agregada
     a la pila de procesos**/
        int pos = 0;/**variable para guardar la posicion de la pila donde se
     encuentra el Fragment**/

        for (int i = 0; i < fragmentList.size(); i++)
        {
            if (fragmentList.get(i).getTag().equals(Tag)) {
                /**YA ESTA DISPONIBLE EN LA PILA**/
                bandera = true;
                pos = i;
            }
        }

        /**Oculatr los Fragment Visibles**/
        FragmentTransaction  fragmentTransaction = mFragmentManagerM.beginTransaction();

        for (int i=0;i<fragmentList.size();i++)
        {
            fragmentTransaction.hide(fragmentList.get(i));
        }

        if (bandera)
        {
            /**ESTA EN LA PILA**/
            fragmentTransaction
                    .show(fragmentList.get(pos))
                    .addToBackStack(Tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }else
        {
            /**NO SE ENCUANTRA EN LA PILA (ES UN NUEVO FRAGMET)**/
            fragmentTransaction
                    .add(fragmentContainer,oF,Tag)
                    .addToBackStack(Tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    @Override
    public int getItemCount() {
        return mTimeLineList.size();
    }

    public class cViewHolderTimeLine extends RecyclerView.ViewHolder
    {
        private ImageView mImageViewMarker;
        private LinearLayoutCompat mLinearLayoutCompatCircle;
        private View mViewLine;
        private LinearLayoutCompat mLinearLayoutCompatContainer;

        public cViewHolderTimeLine(View itemView)
        {
            super(itemView);
            mImageViewMarker = itemView.findViewById(R.id.idImagenMarkerTimeLine);
            mLinearLayoutCompatCircle = itemView.findViewById(R.id.CircleContainerTimeLine);
            mViewLine = itemView.findViewById(R.id.idLineTimeLine);
            mLinearLayoutCompatContainer = itemView.findViewById(R.id.idLinearContainer);
        }
    }
}
