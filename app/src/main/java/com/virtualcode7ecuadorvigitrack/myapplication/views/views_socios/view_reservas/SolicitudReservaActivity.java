package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_reservas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.timeline.adapter.cAdapterTimeLine;
import com.virtualcode7ecuadorvigitrack.myapplication.timeline.cTimeLine;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;
import com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_reservas.invitados.AddReservasInvitadosActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_reservas.socio.AddReservaSocioActivity;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SolicitudReservaActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener
{
    private RecyclerView mRecyclerView;
    private cAdapterTimeLine mAdapterTimeLine;
    private List<cTimeLine> mTimeLines;
    private MaterialButton mMaterialButtonAddReserva;
    private MaterialButton mMaterialButtonSaveReserva;
    private MaterialButton mMaterialButtonProcessReserva;
    private AppCompatRadioButton mRadioButtonSocio;
    private AppCompatRadioButton mRadioButtonInvitados;
    private RadioGroup mRadioGroupTypeReservacion;
    private int REQUEST_CODE_RESERVA = 237;
    private View mViewAlertConfirmeReserva;
    private AlertDialog mAlertDialogConfirmeReserva;
    private MaterialCardView mMaterialCardViewAlert;
    private TextView mTextViewTitleAlert;
    private ImageView mImageViewCancelAlertTitle;
    private ImageView mImageViewCompatCloseAlertTitle;


    private View mViewResumen;


    private LinearLayoutCompat mLinearLayoutCompatView1;
    private LinearLayoutCompat mLinearLayoutCompatView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_reserva);

        cToolbar.show(SolicitudReservaActivity.this,getResources().getString(R.string.name_cam),true,1);

        mLinearLayoutCompatView1 = findViewById(R.id.idLinearView1);
        mLinearLayoutCompatView2 = findViewById(R.id.idLinearView2);

        mMaterialCardViewAlert = findViewById(R.id.idcardTitleAlert);
        mTextViewTitleAlert = findViewById(R.id.idTextCardTitleAlert);
        mImageViewCancelAlertTitle = findViewById(R.id.ImgCancel);
        mImageViewCompatCloseAlertTitle = findViewById(R.id.ImgClose);


        mMaterialButtonProcessReserva = findViewById(R.id.idMaterialProcesarReservaSolicitud);
        mMaterialButtonAddReserva = findViewById(R.id.idMaterialAddReserva);
        mViewResumen = findViewById(R.id.idViewResumen);
        mRecyclerView = findViewById(R.id.idRecyclerViewTimeLineSolicitudReservas);
        mRadioButtonSocio = findViewById(R.id.idRadioButtonSocio);
        mRadioButtonInvitados = findViewById(R.id.idRadioButtonInvitado);
        mMaterialButtonSaveReserva = findViewById(R.id.idMaterialButtonSaveReservaSolicitud);

        mRadioGroupTypeReservacion = findViewById(R.id.idRadioGroupTypeReservacion);

        LinearLayoutManager manager = new LinearLayoutManager(SolicitudReservaActivity.this);
        manager.setOrientation(RecyclerView.HORIZONTAL);

        mTimeLines = llenarTimeLines();
        mAdapterTimeLine = new cAdapterTimeLine(mTimeLines,SolicitudReservaActivity.this,null,0,null);
        mRecyclerView.setAdapter(mAdapterTimeLine);

        mRadioButtonInvitados.setOnCheckedChangeListener(this::onCheckedChanged);
        mRadioButtonSocio.setOnCheckedChangeListener(this::onCheckedChanged);

        mImageViewCompatCloseAlertTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mMaterialCardViewAlert.setVisibility(View.GONE);
            }
        });
        createViewAlertSaveConfirmeReserva();
    }

    private void createViewAlertSaveConfirmeReserva()
    {
        mViewAlertConfirmeReserva = LayoutInflater
                .from(SolicitudReservaActivity.this).inflate(R.layout.view_solicitud_enviada,
                        null,false);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SolicitudReservaActivity.this);
        mBuilder.setView(mViewAlertConfirmeReserva);
        mBuilder.setCancelable(false);
        MaterialButton mMaterialButtonAccept = mViewAlertConfirmeReserva
                .findViewById(R.id.idBtnAcceprSolicitudReserva);
        mMaterialButtonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (mAlertDialogConfirmeReserva.isShowing()){
                    mAlertDialogConfirmeReserva.cancel();
                    mAlertDialogConfirmeReserva.hide();
                    finish();
                }
            }
        });
        mAlertDialogConfirmeReserva = mBuilder.create();
    }

    private List<cTimeLine> llenarTimeLines()
    {
        List<cTimeLine> mTimeLines_ = new ArrayList<>();

        cTimeLine mTimeLine = new cTimeLine();

        mTimeLine.setStatus(true);
        mTimeLine.setBackground(getResources().getDrawable(R.drawable.item_timeline));
        mTimeLine.setColor_active(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine.setColor_inactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine.setColor_check(getResources().getColor(R.color.btn_add_invitado_55));
        mTimeLine.setColorTimeLineActive(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine.setColorTimeLineInactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine.setImgMarker(getDrawable(R.drawable.ic_apartment_white));
        mTimeLine.setImgCircleCheck(getDrawable(R.drawable.ic_baseline_check_24));
        mTimeLines_.add(mTimeLine);


        cTimeLine mTimeLine2 = new cTimeLine();
        mTimeLine2.setStatus(false);
        mTimeLine2.setBackground(getResources().getDrawable(R.drawable.item_timeline_inactive));
        mTimeLine2.setColor_active(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine2.setColor_inactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine2.setColor_check(getResources().getColor(R.color.btn_add_invitado_55));
        mTimeLine2.setColorTimeLineActive(getResources().getColor(R.color.ClubColorPrimary));
        mTimeLine2.setColorTimeLineInactive(getResources().getColor(R.color.ClubColorPrimary_55));
        mTimeLine2.setImgCircleCheck(getDrawable(R.drawable.ic_baseline_check_24));
        mTimeLine2.setImgMarker(getDrawable(R.drawable.clipboard_check_solid));
        mTimeLines_.add(mTimeLine2);

        return mTimeLines_;
    }


    @Override
    protected void onResume()
    {
        mMaterialButtonAddReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mIntent = null;

                if (mRadioButtonSocio.isChecked()){
                    mIntent =  new Intent(SolicitudReservaActivity.this, AddReservaSocioActivity.class);
                }else if (mRadioButtonInvitados.isChecked()){
                    mIntent = new Intent(SolicitudReservaActivity.this, AddReservasInvitadosActivity.class);
                }

                if (mIntent!=null){
                    //mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivityForResult(mIntent,REQUEST_CODE_RESERVA);
                }else {
                    Toasty.info(SolicitudReservaActivity.this,getResources().getString(R.string.select_tipo_reservacion),
                            Toasty.LENGTH_SHORT).show();
                }

            }
        });
        mMaterialButtonSaveReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //mAlertDialogConfirmeReserva.show();
                mTimeLines.get(0).setCheckCircle(true);/**en color greenn y marker visto**/
                //mTimeLines.get(0).setStatus(false);
                mAdapterTimeLine.inactiveElements();

                /**significa el focus actual**/
                mTimeLines.get(1).setStatus(true);
                mTimeLines.get(1).setBackground(getResources().getDrawable(R.drawable.item_timeline));

                mAdapterTimeLine.notifyDataSetChanged();

                mLinearLayoutCompatView1.setVisibility(View.GONE);
                mLinearLayoutCompatView2.setVisibility(View.VISIBLE);
            }
        });
        mMaterialButtonProcessReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                mAlertDialogConfirmeReserva.show();
            }
        });
        super.onResume();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_RESERVA && resultCode == RESULT_OK) {
            mMaterialButtonSaveReserva.setEnabled(true);
            mMaterialButtonAddReserva.setEnabled(false);
            mViewResumen.setVisibility(View.VISIBLE);

            mMaterialCardViewAlert.setCardBackgroundColor(getResources().getColor(R.color.btn_add_invitado));
            mTextViewTitleAlert.setText(getResources().getString(R.string.reserva_ok));

            Picasso.with(SolicitudReservaActivity.this)
                    .load(R.drawable.check_circle)
                    .into(mImageViewCancelAlertTitle);
            mMaterialCardViewAlert.setVisibility(View.VISIBLE);
            //mImageViewCancelAlertTitle.getDrawable().setTint(getResources().getColor(R.color.white));

        } else {
            mViewResumen.setVisibility(View.GONE);
            mMaterialButtonSaveReserva.setEnabled(false);
        }
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (isChecked){
            mMaterialButtonAddReserva.setEnabled(true);
        }
    }
}