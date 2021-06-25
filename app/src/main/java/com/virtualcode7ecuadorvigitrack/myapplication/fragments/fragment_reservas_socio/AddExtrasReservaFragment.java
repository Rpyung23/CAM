package com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas_socio;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.virtualcode7ecuadorvigitrack.myapplication.R;


public class AddExtrasReservaFragment extends Fragment implements View.OnClickListener
{
    private View mView;
    private MaterialButton mMaterialButtonAddPersonExtra;
    private View mViewAlertAddExtras;
    private AlertDialog mAlertDialogAddPersonExtra;


    public AddExtrasReservaFragment() {
        // Required empty public constructor
    }


    public static AddExtrasReservaFragment newInstance(String param1, String param2)
    {
        AddExtrasReservaFragment fragment = new AddExtrasReservaFragment();
        Bundle args = new Bundle();
        /*args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_add_extras_reserva, container, false);
        mMaterialButtonAddPersonExtra = mView.findViewById(R.id.idbtnAddPersonExtra);
        mMaterialButtonAddPersonExtra.setOnClickListener(this::onClick);
        return mView;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.idbtnAddPersonExtra:
                showAlertAddExtrasPerson();
                break;

            case R.id.idAlertButtonSaveAddPerson:
                cancelAlertAddExtrasPerson();
                break;

            case R.id.idAlertButtonCancelAddPerson:
                cancelAlertAddExtrasPerson();
                break;
        }
    }

    private void cancelAlertAddExtrasPerson()
    {
        if (mAlertDialogAddPersonExtra.isShowing())
        {
            mAlertDialogAddPersonExtra.cancel();
            mAlertDialogAddPersonExtra.hide();
        }
    }

    private void showAlertAddExtrasPerson()
    {

        if (mAlertDialogAddPersonExtra==null)
        {
            mViewAlertAddExtras = LayoutInflater.from(getContext())
                    .inflate(R.layout.alert_add_person_extra,null,false);

            MaterialButton mMaterialButtonSavePersonExtra = mViewAlertAddExtras
                    .findViewById(R.id.idAlertButtonSaveAddPerson);
            MaterialButton mMaterialButtonCancelPersonExtra = mViewAlertAddExtras
                    .findViewById(R.id.idAlertButtonCancelAddPerson);

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
            mBuilder.setView(mViewAlertAddExtras);

            mMaterialButtonSavePersonExtra.setOnClickListener(this::onClick);
            mMaterialButtonCancelPersonExtra.setOnClickListener(this::onClick);

            mAlertDialogAddPersonExtra = mBuilder.create();

            mAlertDialogAddPersonExtra.show();

        }else{
            mAlertDialogAddPersonExtra.show();
        }

    }
}