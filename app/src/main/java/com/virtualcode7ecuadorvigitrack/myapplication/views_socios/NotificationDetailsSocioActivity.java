package com.virtualcode7ecuadorvigitrack.myapplication.views_socios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNotificationSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

public class NotificationDetailsSocioActivity extends AppCompatActivity
{
    private cNotificationSocio mNotificationSocio;
    private TextView mTextViewFecha;
    private TextView mTextViewTextMsm;
    private TextView mTextViewTipoNoti;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details_socio);
        mNotificationSocio = (cNotificationSocio) getIntent()
                .getSerializableExtra("oNotificationSocio");

        new cToolbar().show(NotificationDetailsSocioActivity.this,"DETALLE",true,1);
        mTextViewFecha = findViewById(R.id.id_textview_date_noti);
        mTextViewTextMsm = findViewById(R.id.id_textview_body_noti);
        mTextViewTipoNoti = findViewById(R.id.id_textview_tipo_noti);

        mTextViewFecha.setText(mNotificationSocio.getFecha());
        mTextViewTextMsm.setText(mNotificationSocio.getMensaje());
        mTextViewTipoNoti.setText(mNotificationSocio.getNotificacion_titulo());
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