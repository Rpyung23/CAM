package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_invitado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.activity.cActivityInicioSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cInvitados;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.HideKeys;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cFormatFechas;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.invitados.*;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.*;

public class SolicitudInvitadosActivity extends cActivityInicioSocio
        implements DialogInterface.OnDismissListener, View.OnClickListener,
        DialogInterface.OnCancelListener,MaterialPickerOnPositiveButtonClickListener ,cOnClickInvitados
{
    private static final String TAG_DATE_SHOW = "TAG_CALENDAR_INICIO";
    private MaterialCardView mMaterialCardViewFechasSolicitud;
    private MaterialCardView mMaterialCardViewAddInvitados;
    private AlertDialog mAlertDialogFechas;
    private ImageView mImageViewCloseAlert;

    private MaterialDatePicker.Builder mBuilderMaterialDate;
    private MaterialDatePicker mMaterialDatePickerDate;

    private TextView mTextInputEditTextDateInicio;
    private TextView mTextInputEditTextDateFin;
    private boolean bandera = true;

    private Timestamp mTimestampInicio = null;
    private Timestamp mTimestampFin = null;
    private ImageView mImageViewCheckFechas;

    private MaterialButton mMaterialButtonAddFechas;
    private MaterialButton mMaterialButtonCancelFechas;

    private MaterialButton mMaterialButtonProceSolicitud;



    private TextView mTextViewDesdeLabel;
    private TextView mTextViewHastaLabel;
    private TextView mTextViewDesde;
    private TextView mTextViewHasta;
    private View mViewSeparador;
    private MaterialButton mMaterialButtonEditarSolicitud;


    private RecyclerView mRecyclerViewListInvitados;

    private AlertDialog mAlertDialogOkInvitado;


    private List<cInvitados> mInvitadosList;
    private cAdapterListInvitadosAdd mAdapterListInvitadosAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_invitados);
        cToolbar.show(this,getResources().getString(R.string.name_cam),true,1);


        mTextViewDesdeLabel = findViewById(R.id.lblDesde);
        mTextViewHastaLabel = findViewById(R.id.lblHasta);
        mTextViewDesde = findViewById(R.id.tvFechaDesde);
        mTextViewHasta = findViewById(R.id.tvFechaHasta);
        mViewSeparador = findViewById(R.id.ViewEdit);
        mMaterialButtonEditarSolicitud = findViewById(R.id.btnEdit);
        mRecyclerViewListInvitados = findViewById(R.id.idRecyclerViewListInvitados);

        mMaterialCardViewFechasSolicitud = findViewById(R.id.cardViewAddFechasSolicitud);
        mMaterialCardViewAddInvitados = findViewById(R.id.cardAddInvitados);
        mMaterialButtonProceSolicitud = findViewById(R.id.btnSaveSolicitud);


        mImageViewCheckFechas = findViewById(R.id.img_check_date);


        initMaterialDatePicker();

        mTimestampInicio = new Timestamp(0);
        mTimestampFin = new Timestamp(0);

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

        mMaterialButtonProceSolicitud.setOnClickListener(this::onClick);

    }

    private void initMaterialDatePicker()
    {
        CalendarConstraints.Builder mBuilderContraints = new CalendarConstraints.Builder();
        mBuilderContraints.setValidator(DateValidatorPointForward.now());
        mBuilderMaterialDate = MaterialDatePicker.Builder.datePicker();

        mBuilderMaterialDate.setCalendarConstraints(mBuilderContraints.build());
        mMaterialDatePickerDate = mBuilderMaterialDate.build();
        mMaterialDatePickerDate.addOnPositiveButtonClickListener(this::onPositiveButtonClick);

    }

    private void createAlertFechas()
    {
        View mView = LayoutInflater.from(SolicitudInvitadosActivity.this).inflate(R.layout.alert_fechas,null,false);
        mImageViewCloseAlert = mView.findViewById(R.id.imgcloseTitle);
        mTextInputEditTextDateInicio = mView.findViewById(R.id.txtInputDateDesde);
        mTextInputEditTextDateFin = mView.findViewById(R.id.txtInputDateFin);
        mMaterialButtonAddFechas = mView.findViewById(R.id.btnAddFechas);
        mMaterialButtonCancelFechas = mView.findViewById(R.id.btnCancelFechas);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SolicitudInvitadosActivity.this);
        mBuilder.setView(mView);
        mAlertDialogFechas = mBuilder.create();
        mAlertDialogFechas.getWindow()
                .setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.trnsparente)));


        mMaterialButtonAddFechas.setOnClickListener(this::onClick);
        mMaterialButtonCancelFechas.setOnClickListener(this::onClick);
        mTextInputEditTextDateInicio.setOnClickListener(this::onClick);
        mTextInputEditTextDateFin.setOnClickListener(this::onClick);
        mImageViewCloseAlert.setOnClickListener(this::onClick);
        mAlertDialogFechas.setOnCancelListener(this::onCancel);
        mAlertDialogFechas.setOnDismissListener(this::onDismiss);
        mAlertDialogFechas.show();

    }

    @Override
    protected void onPostResume()
    {
        mInvitadosList = ListInvitados();

        mAdapterListInvitadosAdd
                = new cAdapterListInvitadosAdd(mInvitadosList,this
                ,mRecyclerViewListInvitados);

        mRecyclerViewListInvitados.setAdapter(mAdapterListInvitadosAdd);

        super.onPostResume();
    }

    private List<cInvitados> ListInvitados()
    {
        List<cInvitados> mInvitadosList = new ArrayList<>();

        for (int i=1;i<=2;i++)
        {
            cInvitados mInvitados = new cInvitados();
            mInvitados.setEdad(22);
            mInvitados.setNombres_invitado("Jorge Dorantes Montemayor");
            mInvitados.setCurp("DOMJ891001HDFNES00");
            mInvitadosList.add(mInvitados);
        }
        return  mInvitadosList;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        if (mAlertDialogFechas == null){
            return;
        }
        comparateFechas();

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.imgcloseTitle:
                closeAlertFechas(false);
                break;
            case R.id.txtInputDateDesde:
                bandera = true;
               showMaterialPicker();
                break;
            case R.id.txtInputDateFin:
                bandera = false;
                showMaterialPicker();
                break;
            case R.id.btnAddFechas:
                if(mTextInputEditTextDateInicio.getText().toString().isEmpty()
                        &&mTextInputEditTextDateFin.getText().toString().isEmpty())
                {
                    mTextInputEditTextDateInicio.setError(getResources().getString(R.string.datos_vacios));
                    mTextInputEditTextDateFin.setError(getResources().getString(R.string.datos_vacios));
                }else{
                    if (!mTextInputEditTextDateInicio.getText().toString().isEmpty())
                    {
                        if (!mTextInputEditTextDateFin.getText().toString().isEmpty())
                        {
                            closeAlertFechas(true);
                        }else{

                            mTextInputEditTextDateFin.setError(getResources().getString(R.string.datos_vacios));
                        }
                    }else{
                        mTextInputEditTextDateInicio.setError(getResources().getString(R.string.datos_vacios));
                    }
                }
                break;
            case R.id.btnCancelFechas:
                closeAlertFechas(false);
                mAlertDialogFechas = null;
                break;
            case R.id.btnSaveSolicitud:
                createAlertSolicitudEnviada();
                break;

        }
    }


    private void showMaterialPicker()
    {
        if (!mMaterialDatePickerDate.isVisible()){
            mMaterialDatePickerDate.show(this.getSupportFragmentManager(),TAG_DATE_SHOW);
        }
    }

    private void closeAlertFechas(boolean ban)
    {
        if (ban){
            if (mAlertDialogFechas!=null){
                mAlertDialogFechas.cancel();
                mAlertDialogFechas.hide();
            }
        }else{
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

        comparateFechas();

    }

    private void comparateFechas()
    {
        if (mTimestampInicio.getTime()>0 && mTimestampFin.getTime()>0){
            Long mTo = Math.abs(mTimestampFin.getTime() - mTimestampInicio.getTime());
            Log.e("range_date",mTimestampFin.getTime() +" - "+ mTimestampInicio.getTime());
            Log.e("range_date",mTo.toString());
            if (mTo>=1296000000){
                //Toast.makeText(this, "RANGO DE 15 dias", Toast.LENGTH_SHORT).show();
                visibleCheckImageFechas(true);
            }else{
                visibleCheckImageFechas(false);
            }
        }
    }

    private void visibleCheckImageFechas(boolean status)
    {
        if (status) {
            mImageViewCheckFechas.setVisibility(View.VISIBLE);
            mMaterialCardViewFechasSolicitud.setCardBackgroundColor(Color.parseColor("#F3F3F3"));

            showDatosResumenSolicitud(View.VISIBLE);


            mTextViewHasta.setText(cFormatFechas.Timestamp_from_String(mTimestampFin.getTime()));
            mTextViewDesde.setText(cFormatFechas.Timestamp_from_String(mTimestampInicio.getTime()));


        } else {
            mImageViewCheckFechas.setVisibility(View.GONE);
            mMaterialCardViewFechasSolicitud.setCardBackgroundColor(R.color.white);
        }
    }

    private void showDatosResumenSolicitud(int status)
    {
        mTextViewDesde.setVisibility(status);
        mTextViewHasta.setVisibility(status);
        mTextViewDesdeLabel.setVisibility(status);
        mTextViewHastaLabel.setVisibility(status);
        mViewSeparador.setVisibility(status);
        mMaterialButtonEditarSolicitud.setVisibility(status);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onPositiveButtonClick(Object selection)
    {
        if (bandera){
            //mTextInputEditTextDateInicio.setText(selection.toString());
            mTextInputEditTextDateInicio.setError(null);
            mTextInputEditTextDateInicio.setText(" "+ cFormatFechas.Timestamp_from_String((Long) selection));
            mTimestampInicio.setTime((Long) selection);
        }else{
            mTextInputEditTextDateFin.setError(null);
            mTextInputEditTextDateFin.setText(" "+ cFormatFechas.Timestamp_from_String((Long) selection));
            mTimestampFin.setTime((Long)selection);
        }
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


    private void createAlertSolicitudEnviada(){



        View mView = LayoutInflater.from(SolicitudInvitadosActivity.this)
                .inflate(R.layout.view_solicitud_enviada,null);

        MaterialButton mMaterialButtonAccept = mView.findViewById(R.id.idBtnAcceprSolicitudReserva);

        mMaterialButtonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(mAlertDialogOkInvitado!= null && mAlertDialogOkInvitado.isShowing()){
                    mAlertDialogOkInvitado.cancel();
                    mAlertDialogOkInvitado.hide();
                    finish();
                }
            }
        });

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SolicitudInvitadosActivity.this);
        mBuilder.setView(mView);
        mAlertDialogOkInvitado = mBuilder.create();
        mAlertDialogOkInvitado.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        mAlertDialogOkInvitado.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mAlertDialogOkInvitado.show();



    }


    @Override
    public void OnClickListener(List<cInvitados> mInvitadosList, int positionCurrent) {

    }
}