package com.virtualcode7ecuadorvigitrack.myapplication.views_socios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.handlers.cNotitifactionHandlers;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.cInterfaceNotification;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNotificationSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferenSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cStringMesDia;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class NotificationDetailsSocioActivity extends AppCompatActivity
{
    private cNotificationSocio mNotificationSocio;
    private ArrayList<cNotificationSocio> mNotificationSocios;
    private TextView mTextViewFecha;
    private TextView mTextViewTextMsm;
    private TextView mTextViewTipoNoti;
    private AlertDialog mAlertDialog;
    private cNotitifactionHandlers mNotitifactionHandlers;
    private cSharedPreferenSocio mSharedPreferenSocio;
    private TextView mTextViewTop;
    private TextView mTextViewDeleteNotification;
    private SeekBar mSeekBar;
    private int CantNotification = 0;
    private int ProgresoNotification = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details_socio);

        CantNotification = getIntent().getIntExtra("cantidadNotification",0);
        ProgresoNotification = getIntent().getIntExtra("PositionSeekbar",0);

        mNotificationSocio = (cNotificationSocio) getIntent()
                .getSerializableExtra("oNotificationSocio");

        mNotificationSocios = (ArrayList<cNotificationSocio>) getIntent().getSerializableExtra("NotificationSocios");

        mNotitifactionHandlers = new cNotitifactionHandlers(NotificationDetailsSocioActivity.this);

        mSharedPreferenSocio = new cSharedPreferenSocio(NotificationDetailsSocioActivity.this);

        new cToolbar().show(NotificationDetailsSocioActivity.this,"",true,3);
        mSeekBar = findViewById(R.id.seekBar);
        mTextViewFecha = findViewById(R.id.id_textview_date_noti);
        mTextViewTextMsm = findViewById(R.id.id_textview_body_noti);
        mTextViewTipoNoti = findViewById(R.id.id_textview_tipo_noti);
        mTextViewDeleteNotification = findViewById(R.id.txtDeleteNotification);

        mTextViewTop = findViewById(R.id.id_detalles_toolbar_top);

        /***Cantidad de Notification**/
        mSeekBar.setMax(CantNotification);
        mSeekBar.setProgress(ProgresoNotification);
        /*********/

        mTextViewTop.setText("DETALLE");


        mTextViewFecha.setText(new cStringMesDia().dia(mNotificationSocio.getFecha())+" "+new cStringMesDia().mes(mNotificationSocio.getFecha())+" de "+new cStringMesDia().year(mNotificationSocio.getFecha()));

        mTextViewTextMsm.setText(mNotificationSocio.getMensaje());
        mTextViewTipoNoti.setText(mNotificationSocio.getNotificacion_titulo());

        if (!new cSharedTokenValidation(NotificationDetailsSocioActivity.this).readTokenValitation())
        {
            alertDialogTimeOut();
        }

        mNotitifactionHandlers.setId_noti(mNotificationSocio.getId_notificacion());
        mNotitifactionHandlers.setToken(mSharedPreferenSocio.leerdatosSocio().getToken());

        mNotitifactionHandlers.runRead();


    }


    private void alertDialogTimeOut()
    {


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(NotificationDetailsSocioActivity.this);
        mBuilder.setMessage("Lo sentimos su tiempo se agotado");
        mBuilder.setTitle("Login");
        mBuilder.setCancelable(false);
        mBuilder.setIcon(R.drawable.ic_asturian_primary_color);
        mBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                mAlertDialog.cancel();
                finish();
            }
        });
        mAlertDialog = mBuilder.create();
        mAlertDialog.show();
        return;
    }


    @Override
    protected void onResume() {
        super.onResume();

        mTextViewDeleteNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent mIntent = new Intent();
                mIntent.putExtra("Notifications",mNotificationSocios);
                setResult(RESULT_OK,mIntent);

                Toast.makeText(NotificationDetailsSocioActivity.this, "TAM : "+mNotificationSocios.size(), Toast.LENGTH_SHORT).show();


                finish();

            }
        });
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