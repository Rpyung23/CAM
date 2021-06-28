package com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas;

import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virtualcode7ecuadorvigitrack.myapplication.R;


public class AddHabitacionesFragment extends Fragment
{

    private String type_add_habitacion;

    private LinearLayoutCompat mLinearLayoutCompat;
    private View mView;


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

        if (!type_add_habitacion.equals("socio"))
        {
            mLinearLayoutCompat.setVisibility(View.GONE);
        }
        return mView;
    }
}