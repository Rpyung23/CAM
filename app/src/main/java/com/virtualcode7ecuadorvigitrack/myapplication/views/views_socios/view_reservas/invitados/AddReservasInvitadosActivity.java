package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_reservas.invitados;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas.AddExtrasReservaFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas.AddHabitacionesFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas.ResumenReservacionFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.timeline.adapter.cAdapterTimeLine;
import com.virtualcode7ecuadorvigitrack.myapplication.timeline.cTimeLine;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import java.util.ArrayList;
import java.util.List;

public class AddReservasInvitadosActivity extends AppCompatActivity {

    private List<cTimeLine> mTimeLineList;
    private cAdapterTimeLine mAdapterTimeLine;
    private RecyclerView mRecyclerViewTimeLine;
    private int position_viewpager_fragment = 0;
    private MaterialButton mMaterialButtonSiguiente;
    private MaterialButton mMaterialButtonAnterior;
    private ConstraintLayout mConstraintLayout;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservas_invitados);
        cToolbar.show(AddReservasInvitadosActivity.this,getResources().getString(R.string.name_cam),true,1);
        mMaterialButtonSiguiente = findViewById(R.id.idbtnSiguiente);
        mMaterialButtonAnterior = findViewById(R.id.idbtnAnterior);
        mRecyclerViewTimeLine = findViewById(R.id.idRecyclerViewTimeLineSolicitudReservas);
        mConstraintLayout = findViewById(R.id.ConstraintButtonNextBack);

        mFragmentManager = getSupportFragmentManager();

        mTimeLineList = llenarElementTimeLine();
        mFragmentList = llenarListaFragment();

        mAdapterTimeLine = new cAdapterTimeLine(mTimeLineList,AddReservasInvitadosActivity.this
                ,mFragmentList
                ,R.id.FragmentContainerAddSolicitud,mFragmentManager);
        mRecyclerViewTimeLine.setAdapter(mAdapterTimeLine);


        mMaterialButtonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                eventosRecyclerViewTimeLine();
            }
        });


        mMaterialButtonAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (position_viewpager_fragment>0)
                {
                    position_viewpager_fragment= position_viewpager_fragment-1;
                    uncheckedItemLine(position_viewpager_fragment);
                    mAdapterTimeLine.inactiveElements();
                    mTimeLineList.get(position_viewpager_fragment).setStatus(true);
                    mTimeLineList.get(position_viewpager_fragment).setBackground(getResources().getDrawable(R.drawable.item_timeline));
                    mAdapterTimeLine.notifyDataSetChanged();
                }else if(position_viewpager_fragment == 0){

                    uncheckedItemLine(position_viewpager_fragment);
                }

            }
        });
    }
    private void uncheckedItemLine(int position_viewpager_fragment_)
    {
        mTimeLineList.get(position_viewpager_fragment_).setCheckCircle(false);
        mAdapterTimeLine.inactiveElements();
    }
    private List<Fragment> llenarListaFragment()
    {
        List<Fragment> mList = new ArrayList<>();

        AddHabitacionesFragment oAH =  new AddHabitacionesFragment();
        oAH.setType_add_habitacion("invitado");

        mList.add(oAH);
        mList.add(new AddExtrasReservaFragment());
        mList.add(new ResumenReservacionFragment());

        return mList;
    }

    private void eventosRecyclerViewTimeLine()
    {
        mTimeLineList.get(position_viewpager_fragment).setCheckCircle(true);

        mAdapterTimeLine.inactiveElements();



        if (position_viewpager_fragment<3)
        {
            position_viewpager_fragment = position_viewpager_fragment + 1;


        }else{
            position_viewpager_fragment = 0;
        }


        if (position_viewpager_fragment==2){
            mConstraintLayout.setVisibility(View.GONE);
        }



        mTimeLineList.get(position_viewpager_fragment).setStatus(true);
        mTimeLineList.get(position_viewpager_fragment).setBackground(getResources().getDrawable(R.drawable.item_timeline));
        mAdapterTimeLine.notifyDataSetChanged();
    }

    private List<cTimeLine> llenarElementTimeLine()
    {
        List<cTimeLine> mTimeLines_ = new ArrayList<>();

        cTimeLine mTimeLine = new cTimeLine();


        cTimeLine mTimeLine1 = new cTimeLine();
        mTimeLine1.setStatus(true);
        mTimeLine1.setBackground(getResources().getDrawable(R.drawable.item_timeline));
        mTimeLine1.setColor_active(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine1.setColor_inactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine1.setColorTimeLineActive(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine1.setColorTimeLineInactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine1.setImgMarker(getResources().getDrawable(R.drawable.bed_hotel));
        mTimeLine1.setImgCircleCheck(getResources().getDrawable(R.drawable.ic_baseline_check_24));
        mTimeLine1.setColor_check(getColor(R.color.btn_add_invitado_55));
        mTimeLines_.add(mTimeLine1);


        cTimeLine mTimeLine2 = new cTimeLine();
        mTimeLine2.setStatus(false);
        mTimeLine2.setBackground(getResources().getDrawable(R.drawable.item_timeline_inactive));
        mTimeLine2.setColor_active(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine2.setColor_inactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine2.setColorTimeLineActive(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine2.setColorTimeLineInactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine2.setImgMarker(getResources().getDrawable(R.drawable.plus_not_eve));
        mTimeLine2.setImgCircleCheck(getResources().getDrawable(R.drawable.ic_baseline_check_24));
        mTimeLine2.setColor_check(getColor(R.color.btn_add_invitado_55));
        mTimeLines_.add(mTimeLine2);


        cTimeLine mTimeLine3 = new cTimeLine();
        mTimeLine3.setStatus(false);
        mTimeLine3.setBackground(getResources().getDrawable(R.drawable.item_timeline_inactive));
        mTimeLine3.setColor_active(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine3.setColor_inactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine3.setColorTimeLineActive(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine3.setColorTimeLineInactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine3.setImgMarker(getResources().getDrawable(R.drawable.clipboard_check_solid));
        mTimeLine3.setImgCircleCheck(getResources().getDrawable(R.drawable.ic_baseline_check_24));
        mTimeLine3.setColor_check(getColor(R.color.btn_add_invitado_55));
        mTimeLines_.add(mTimeLine3);

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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}