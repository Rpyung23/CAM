package com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas;

import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.reservaciones.cAdapterHabitaciones;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.reservaciones.cAdapterPaquete;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.cOnClickHabitacionPaquete;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cHabitacion;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cPaquete;

import java.util.ArrayList;
import java.util.List;


public class AddHabitacionesFragment extends Fragment implements cOnClickHabitacionPaquete
{

    private String type_add_habitacion;


    private RecyclerView mRecyclerViewHabitaciones;
    private RecyclerView mRecyclerViewPaquete;

    private List<cHabitacion> mHabitacionList;
    private cAdapterHabitaciones mAdapterHabitaciones;


    private List<cPaquete> mPaqueteList;
    private cAdapterPaquete mAdapterPaquete;

    private LinearLayoutCompat mLinearLayoutCompat;
    private View mView;

    private MaterialRadioButton mMaterialRadioBoxHotelNuevo;
    private MaterialRadioButton mMaterialRadioBoxHotelAnterior;




    public AddHabitacionesFragment() {
        this.type_add_habitacion = "socio";
    }

    public String getType_add_habitacion() {
        return type_add_habitacion;
    }

    public void setType_add_habitacion(String type_add_habitacion) {
        this.type_add_habitacion = type_add_habitacion;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_add_habitaciones, container, false);
        mLinearLayoutCompat = mView.findViewById(R.id.idLinearTopAddHabitaciones);
        mMaterialRadioBoxHotelNuevo=mView.findViewById(R.id.idCheckHotelNuevo);
        mMaterialRadioBoxHotelAnterior=mView.findViewById(R.id.idCheckHotelAnterior);
        mRecyclerViewHabitaciones = mView.findViewById(R.id.idRyclerViewTypeHabitacion);
        mRecyclerViewPaquete = mView.findViewById(R.id.idRecyclerViewPaquete);

        if (!type_add_habitacion.equals("socio"))
        {
            mLinearLayoutCompat.setVisibility(View.GONE);
        }


        mHabitacionList =  createListaHabitaciones();
        mPaqueteList =  createListaPaquetes();

        mAdapterHabitaciones = new cAdapterHabitaciones(mHabitacionList,this,
                mRecyclerViewHabitaciones,getContext());


        mAdapterPaquete = new cAdapterPaquete(mPaqueteList,this,
                mRecyclerViewPaquete,getContext());


        mRecyclerViewHabitaciones.setAdapter(mAdapterHabitaciones);
        mRecyclerViewHabitaciones.setHasFixedSize(true);
        mRecyclerViewPaquete.setAdapter(mAdapterPaquete);
        mRecyclerViewPaquete.setHasFixedSize(true);

        mMaterialRadioBoxHotelNuevo.setChecked(true);

        return mView;
    }

    private List<cPaquete> createListaPaquetes()
    {
        List<cPaquete> mPaqueteList = new ArrayList<>();
        for (int i=0;i<4;i++){
            mPaqueteList.add(new cPaquete());
        }
        return mPaqueteList;
    }

    private List<cHabitacion> createListaHabitaciones()
    {
        List<cHabitacion> mHabitacionList = new ArrayList<>();
        for (int i=0;i<4;i++){
            mHabitacionList.add(new cHabitacion());
        }
        return mHabitacionList;
    }

    @Override
    public void OnCLickListener(int pos_Ant, int posAct)
    {
        mHabitacionList.get(pos_Ant).setActive(false);
        mHabitacionList.get(posAct).setActive(true);

        mAdapterHabitaciones.notifyItemChanged(pos_Ant);
        mAdapterHabitaciones.notifyItemChanged(posAct);
        mAdapterHabitaciones.setPosAnterior(posAct);
    }

    @Override
    public void OnCLickListenerPaquete(int pos_Ant, int posAct)
    {
        mPaqueteList.get(pos_Ant).setActive(false);
        mPaqueteList.get(posAct).setActive(true);

        mAdapterPaquete.notifyItemChanged(pos_Ant);
        mAdapterPaquete.notifyItemChanged(posAct);

        mAdapterPaquete.setPosAnterior(posAct);
    }
}