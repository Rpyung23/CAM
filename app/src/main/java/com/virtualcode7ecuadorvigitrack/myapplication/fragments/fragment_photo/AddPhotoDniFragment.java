package com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_photo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virtualcode7ecuadorvigitrack.myapplication.R;

public class AddPhotoDniFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_add_photo_dni, container, false);
    }
}