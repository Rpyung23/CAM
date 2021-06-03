package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_invitado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.activity.cActivityInicioSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.HideKeys;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SolicitudInvitadosActivity extends cActivityInicioSocio
        implements DialogInterface.OnDismissListener, View.OnClickListener,
        DialogInterface.OnCancelListener,MaterialPickerOnPositiveButtonClickListener
{
    private static final String TAG_DATE_SHOW = "TAG_CALENDAR_INICIO";
    private MaterialCardView mMaterialCardViewFechasSolicitud;
    private MaterialCardView mMaterialCardViewAddInvitados;
    private AlertDialog mAlertDialogFechas;
    private ImageView mImageViewCloseAlert;

    private MaterialDatePicker.Builder mBuilderMaterialDate;
    private MaterialDatePicker mMaterialDatePickerDate;

    private TextInputEditText mTextInputEditTextDateInicio;
    private TextInputEditText mTextInputEditTextDateFin;
    private boolean bandera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_invitados);
        cToolbar.show(this,getResources().getString(R.string.name_cam),true,1);

        mMaterialCardViewFechasSolicitud = findViewById(R.id.cardViewAddFechasSolicitud);
        mMaterialCardViewAddInvitados = findViewById(R.id.cardAddInvitados);

        initMaterialDatePicker();

        mMaterialCardViewFechasSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (mAlertDialogFechas == null){
                    createAlertFechas();
                }else{
                    mAlertDialogFechas.show();
                }
            }
        });

        mMaterialCardViewAddInvitados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mIntent = new Intent(SolicitudInvitadosActivity.this,AddInvitadoActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
            }
        });
    }

    private void initMaterialDatePicker()
    {
        CalendarConstraints.Builder mBuilderContraints = new CalendarConstraints.Builder();
        mBuilderContraints.setValidator(DateValidatorPointForward.now());
        mBuilderMaterialDate = MaterialDatePicker.Builder.datePicker();
        mBuilderMaterialDate.setCalendarConstraints(mBuilderContraints.build());
        mMaterialDatePickerDate = mBuilderMaterialDate.build();


    }

    private void createAlertFechas()
    {
        View mView = LayoutInflater.from(SolicitudInvitadosActivity.this).inflate(R.layout.alert_fechas,null,false);
        mImageViewCloseAlert = mView.findViewById(R.id.imgcloseTitle);
        mTextInputEditTextDateInicio = mView.findViewById(R.id.txtInputDateDesde);
        mTextInputEditTextDateFin = mView.findViewById(R.id.txtInputDateFin);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SolicitudInvitadosActivity.this);
        mBuilder.setView(mView);
        mAlertDialogFechas = mBuilder.create();
        mAlertDialogFechas.getWindow()
                .setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.trnsparente)));

        mTextInputEditTextDateInicio.setOnClickListener(this::onClick);
        mTextInputEditTextDateFin.setOnClickListener(this::onClick);
        mImageViewCloseAlert.setOnClickListener(this::onClick);
        mAlertDialogFechas.setOnCancelListener(this::onCancel);
        mAlertDialogFechas.setOnDismissListener(this::onDismiss);
        mAlertDialogFechas.show();

        /*mTextInputEditTextDateFin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (v.getId() == R.id.txtInputDateFin){
                    mTextInputEditTextDateFin.setFocusable(false);
                    bandera = false;
                    if (hasFocus){
                        showMaterialPicker();
                    }
                }
            }
        });

        mTextInputEditTextDateInicio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (v.getId() == R.id.txtInputDateDesde){
                    mTextInputEditTextDateFin.setFocusable(false);
                    bandera = true;
                    if (hasFocus){
                        showMaterialPicker();
                    }
                }

            }
        });*/
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        if (mAlertDialogFechas == null){
            return;
        }


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.imgcloseTitle:
                closeAlertFechas();
                break;
            case R.id.txtInputDateDesde:
                bandera = true;
               showMaterialPicker();
                break;
            case R.id.txtInputDateFin:
                bandera = false;
                showMaterialPicker();
                break;
        }
    }

    private void showMaterialPicker()
    {
        mMaterialDatePickerDate.show(this.getSupportFragmentManager(),TAG_DATE_SHOW);

        mMaterialDatePickerDate.addOnPositiveButtonClickListener(this::onPositiveButtonClick);
    }

    private void closeAlertFechas()
    {
        if (mAlertDialogFechas!=null){
            mAlertDialogFechas.cancel();
            mAlertDialogFechas.hide();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        if (mAlertDialogFechas == null){
            return;
        }
    }


    @Override
    public void onPositiveButtonClick(Object selection)
    {
        Log.e("date",selection.toString());

        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = mSimpleDateFormat.format(selection);

        if (bandera){
            //mTextInputEditTextDateInicio.setText(selection.toString());
            mTextInputEditTextDateInicio.setText(fecha);
        }else{
            mTextInputEditTextDateFin.setText(fecha);
        }
    }
}