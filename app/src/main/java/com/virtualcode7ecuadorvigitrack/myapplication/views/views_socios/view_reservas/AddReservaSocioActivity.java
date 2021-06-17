package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_reservas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.viewpager2.ViewPagerAdapterReservasSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.timeline.adapter.cAdapterTimeLine;
import com.virtualcode7ecuadorvigitrack.myapplication.timeline.cTimeLine;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import java.util.ArrayList;
import java.util.List;

public class AddReservaSocioActivity extends AppCompatActivity
{
    private List<cTimeLine> mTimeLineList;
    private cAdapterTimeLine mAdapterTimeLine;
    private RecyclerView mRecyclerViewTimeLine;
    private ViewPager2 mViewPager2AddReservaTimeLine;

    private ViewPagerAdapterReservasSocio mViewPagerAdapterReservasSocio;
    private MaterialButton mMaterialButtonSiguiente;
    private MaterialButton mMaterialButtonAnterior;
    private int position_viewpager_fragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reserva_socio);
        cToolbar.show(AddReservaSocioActivity.this,getResources().getString(R.string.name_cam),true,1);
        mRecyclerViewTimeLine = findViewById(R.id.idRecyclerViewTimeLineSolicitudReservas);
        mViewPager2AddReservaTimeLine = findViewById(R.id.ViewPagerAddSolicitud);
        mMaterialButtonSiguiente = findViewById(R.id.idbtnSiguiente);
        mMaterialButtonAnterior = findViewById(R.id.idbtnAnterior);


        mTimeLineList = llenarElementTimeLine();
        mAdapterTimeLine = new cAdapterTimeLine(mTimeLineList,AddReservaSocioActivity.this);
        mRecyclerViewTimeLine.setAdapter(mAdapterTimeLine);

        mViewPagerAdapterReservasSocio = new ViewPagerAdapterReservasSocio(this);
        mViewPager2AddReservaTimeLine.setAdapter(mViewPagerAdapterReservasSocio);



        mViewPager2AddReservaTimeLine.setCurrentItem(position_viewpager_fragment);

        mMaterialButtonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                mAdapterTimeLine.inactiveElements();


                if (position_viewpager_fragment<4)
                {
                    position_viewpager_fragment = position_viewpager_fragment + 1;

                    mViewPager2AddReservaTimeLine.setCurrentItem(position_viewpager_fragment);
                }else{
                    position_viewpager_fragment = 0;
                }

                mTimeLineList.get(position_viewpager_fragment).setStatus(true);
                mTimeLineList.get(position_viewpager_fragment).setBackground(getResources().getDrawable(R.drawable.item_timeline));
                mAdapterTimeLine.notifyDataSetChanged();
            }
        });

        mMaterialButtonAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (position_viewpager_fragment>0)
                {
                    position_viewpager_fragment= position_viewpager_fragment-1;
                    mAdapterTimeLine.inactiveElements();

                    mViewPager2AddReservaTimeLine.setCurrentItem(position_viewpager_fragment);

                    mTimeLineList.get(position_viewpager_fragment).setStatus(true);
                    mTimeLineList.get(position_viewpager_fragment).setBackground(getResources().getDrawable(R.drawable.item_timeline));
                    mAdapterTimeLine.notifyDataSetChanged();
                }

            }
        });
    }

    private List<cTimeLine> llenarElementTimeLine()
    {
        List<cTimeLine> mTimeLines_ = new ArrayList<>();

        cTimeLine mTimeLine = new cTimeLine();

        mTimeLine.setStatus(true);
        mTimeLine.setBackground(getResources().getDrawable(R.drawable.item_timeline));
        mTimeLine.setColor_active(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine.setColor_inactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine.setColorTimeLineActive(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine.setColorTimeLineInactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine.setImgMarker(getResources().getDrawable(R.drawable.calendar_cuadriculado));
        mTimeLines_.add(mTimeLine);


        cTimeLine mTimeLine3 = new cTimeLine();
        mTimeLine3.setStatus(false);
        mTimeLine3.setBackground(getResources().getDrawable(R.drawable.item_timeline_inactive));
        mTimeLine3.setColor_active(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine3.setColor_inactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine3.setColorTimeLineActive(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine3.setColorTimeLineInactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine3.setImgMarker(getResources().getDrawable(R.drawable.bed_hotel));
        mTimeLines_.add(mTimeLine3);


        cTimeLine mTimeLine4 = new cTimeLine();
        mTimeLine4.setStatus(false);
        mTimeLine4.setBackground(getResources().getDrawable(R.drawable.item_timeline_inactive));
        mTimeLine4.setColor_active(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine4.setColor_inactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine4.setColorTimeLineActive(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine4.setColorTimeLineInactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine4.setImgMarker(getResources().getDrawable(R.drawable.plus_not_eve));
        mTimeLines_.add(mTimeLine4);


        cTimeLine mTimeLine5 = new cTimeLine();
        mTimeLine5.setStatus(false);
        mTimeLine5.setBackground(getResources().getDrawable(R.drawable.item_timeline_inactive));
        mTimeLine5.setColor_active(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine5.setColor_inactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine5.setColorTimeLineActive(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine5.setColorTimeLineInactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine5.setImgMarker(getResources().getDrawable(R.drawable.clipboard_check_solid));
        mTimeLines_.add(mTimeLine5);

        return mTimeLines_;
    }


    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    

}