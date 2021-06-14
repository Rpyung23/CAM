package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_reservas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.timeline.adapter.cAdapterTimeLine;
import com.virtualcode7ecuadorvigitrack.myapplication.timeline.cTimeLine;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import java.util.ArrayList;
import java.util.List;

public class SolicitudReservaActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private cAdapterTimeLine mAdapterTimeLine;
    private List<cTimeLine> mTimeLines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_reserva);

        cToolbar.show(SolicitudReservaActivity.this,getResources().getString(R.string.name_cam),true,1);


        mRecyclerView = findViewById(R.id.idRecyclerViewTimeLineSolicitudReservas);
        LinearLayoutManager manager = new LinearLayoutManager(SolicitudReservaActivity.this);
        manager.setOrientation(RecyclerView.HORIZONTAL);

        mTimeLines = llenarTimeLines();
        mAdapterTimeLine = new cAdapterTimeLine(mTimeLines,SolicitudReservaActivity.this);
        mRecyclerView.setAdapter(mAdapterTimeLine);

    }

    private List<cTimeLine> llenarTimeLines()
    {
        List<cTimeLine> mTimeLines_ = new ArrayList<>();

        cTimeLine mTimeLine = new cTimeLine();

        mTimeLine.setStatus(true);
        mTimeLine.setBackground(getResources().getDrawable(R.drawable.item_timeline));
        mTimeLine.setColor_active(getColor(R.color.ClubColorPrimary));
        mTimeLine.setColor_inactive(getColor(R.color.ClubColorPrimary_55));
        mTimeLine.setColorTimeLineActive(getColor(R.color.ClubColorPrimary));
        mTimeLine.setColorTimeLineInactive(getColor(R.color.ClubColorPrimary_55));
        mTimeLine.setImgMarker(getDrawable(R.drawable.ic_apartment_white));
        mTimeLines_.add(mTimeLine);


        cTimeLine mTimeLine2 = new cTimeLine();
        mTimeLine2.setStatus(false);
        mTimeLine2.setBackground(getResources().getDrawable(R.drawable.item_timeline_inactive));
        mTimeLine2.setColor_active(getColor(R.color.ClubColorPrimary));
        mTimeLine2.setColor_inactive(getColor(R.color.ClubColorPrimary_55));
        mTimeLine2.setColorTimeLineActive(getColor(R.color.ClubColorPrimary));
        mTimeLine2.setColorTimeLineInactive(getColor(R.color.ClubColorPrimary_55));
        mTimeLine2.setImgMarker(getDrawable(R.drawable.clipboard_check_solid));
        mTimeLines_.add(mTimeLine2);

        return mTimeLines_;
    }
}