package com.virtualcode7ecuadorvigitrack.myapplication.gestos;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuadorvigitrack.myapplication.adapters.notificacion.cAdapterNotificationSocios;

public class cRecyclerItemTouchHelperNoti extends ItemTouchHelper.SimpleCallback
{
    private cRecyclerItemTouchHelperListene mRecyclerItemTouchHelperListene;

    public cRecyclerItemTouchHelperNoti(int dragDirs, int swipeDirs,
                                        cRecyclerItemTouchHelperListene mRecyclerItemTouchHelperListene) {
        super(dragDirs, swipeDirs);
        this.mRecyclerItemTouchHelperListene = mRecyclerItemTouchHelperListene;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }


    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder!=null)
        {
            View mView = ((cAdapterNotificationSocios.cViewHolderNotification)viewHolder).mConstraintLayoutNoti;
            getDefaultUIUtil().onSelected(mView);
        }
    }


    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX,
                            float dY, int actionState, boolean isCurrentlyActive)
    {
        View mView = ((cAdapterNotificationSocios.cViewHolderNotification)viewHolder).mConstraintLayoutNoti;
        getDefaultUIUtil().onDrawOver(c,recyclerView,mView,dX,dY,actionState,isCurrentlyActive);
    }


    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View mView = ((cAdapterNotificationSocios.cViewHolderNotification)viewHolder).mConstraintLayoutNoti;
        getDefaultUIUtil().clearView(mView);
    }


    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View mView = ((cAdapterNotificationSocios.cViewHolderNotification)viewHolder).mConstraintLayoutNoti;
        getDefaultUIUtil().onDraw(c,recyclerView,mView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
    {
        mRecyclerItemTouchHelperListene.onSwipe(viewHolder,direction,viewHolder.getAdapterPosition());
    }


    public interface  cRecyclerItemTouchHelperListene
    {
        void onSwipe(RecyclerView.ViewHolder mViewHolder,int direction, int position);
    }
}
