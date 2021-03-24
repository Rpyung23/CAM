package com.virtualcode7ecuadorvigitrack.myapplication.views_socios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.handlers.cNotitifactionHandlers;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNotificationSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferenSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cStringMesDia;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

public class NotificationDetailsSocioActivity extends AppCompatActivity
{
    private cNotificationSocio mNotificationSocio;
    private TextView mTextViewFecha;
    private TextView mTextViewTextMsm;
    private TextView mTextViewTipoNoti;
    private AlertDialog mAlertDialog;
    private cNotitifactionHandlers mNotitifactionHandlers;
    private cSharedPreferenSocio mSharedPreferenSocio;
    private TextView mTextViewTop;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details_socio);
        mNotificationSocio = (cNotificationSocio) getIntent()
                .getSerializableExtra("oNotificationSocio");

        mNotitifactionHandlers = new cNotitifactionHandlers(NotificationDetailsSocioActivity.this);

        mSharedPreferenSocio = new cSharedPreferenSocio(NotificationDetailsSocioActivity.this);

        new cToolbar().show(NotificationDetailsSocioActivity.this,"",true,3);
        mTextViewFecha = findViewById(R.id.id_textview_date_noti);
        mTextViewTextMsm = findViewById(R.id.id_textview_body_noti);
        mTextViewTipoNoti = findViewById(R.id.id_textview_tipo_noti);

        mTextViewTop = findViewById(R.id.id_detalles_toolbar_top);
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