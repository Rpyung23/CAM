package com.virtualcode7ecuadorvigitrack.myapplication.timeline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.timeline.cTimeLine;

import java.util.List;

public class cAdapterTimeLine
        extends RecyclerView.Adapter<cAdapterTimeLine.cViewHolderTimeLine>
{
    private List<cTimeLine> mTimeLineList;
    private Context mContext;


    public cAdapterTimeLine(List<cTimeLine> mTimeLineList, Context mContext) {
        this.mTimeLineList = mTimeLineList;
        this.mContext = mContext;
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
    public void onBindViewHolder(cAdapterTimeLine.cViewHolderTimeLine holder,
                                 int position)
    {

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
