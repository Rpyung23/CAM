package com.virtualcode7ecuadorvigitrack.myapplication.views_socios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
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
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.cAdapterNotificationScroll;
import com.virtualcode7ecuadorvigitrack.myapplication.handlers.cNotitifactionHandlers;
import com.virtualcode7ecuadorvigitrack.myapplication.interfaces.cInterfaceNotification;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNotificationSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferenSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cStringMesDia;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;
import com.virtualcode7ecuadorvigitrack.myapplication.views.LogOutActivity;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class NotificationDetailsSocioActivity extends AppCompatActivity
{
    private ArrayList<cNotificationSocio> mNotificationSocios;
    /*private TextView mTextViewFecha;
    private TextView mTextViewTextMsm;
    private TextView mTextViewTipoNoti;*/

    private cNotificationSocio mNotificationSocioAux;


    private AlertDialog mAlertDialog;
    private cNotitifactionHandlers mNotitifactionHandlers;
    private cSharedPreferenSocio mSharedPreferenSocio;
    private TextView mTextViewTop;
    private TextView mTextViewDeleteNotification;
    private SeekBar mSeekBar;
    private int CantNotification = 0;
    private int ProgresoNotification = 0;
    private ViewPager2 mViewPagerNotificationDetails;
    private cAdapterNotificationScroll mAdapterNotificationScroll;

    private TextView mTextViewNoLeido;
    private boolean banderaCambios = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details_socio);

        CantNotification = getIntent().getIntExtra("cantidadNotification",0);
        ProgresoNotification = getIntent().getIntExtra("PositionSeekbar",0);


        mNotificationSocios = (ArrayList<cNotificationSocio>) getIntent().getSerializableExtra("NotificationSocios");

        mNotitifactionHandlers = new cNotitifactionHandlers(NotificationDetailsSocioActivity.this);


        mSharedPreferenSocio = new cSharedPreferenSocio(NotificationDetailsSocioActivity.this);


        mNotitifactionHandlers.setToken(mSharedPreferenSocio.leerdatosSocio().getToken());


        new cToolbar().show(NotificationDetailsSocioActivity.this,"",true,3);
        mSeekBar = findViewById(R.id.seekBar);
        /*mTextViewFecha = findViewById(R.id.id_textview_date_noti);
        mTextViewTextMsm = findViewById(R.id.id_textview_body_noti);
        mTextViewTipoNoti = findViewById(R.id.id_textview_tipo_noti);*/
        mTextViewDeleteNotification = findViewById(R.id.txtDeleteNotification);
        mViewPagerNotificationDetails = findViewById(R.id.viewPagerNotificaciones);
        mTextViewNoLeido = findViewById(R.id.tvNoLeido);
        mTextViewTop = findViewById(R.id.id_detalles_toolbar_top);

        /***Cantidad de Notification**/
        mSeekBar.setEnabled(false);
        mSeekBar.setMax(CantNotification);
        mSeekBar.setProgress(ProgresoNotification+1);

        /*********/

        mTextViewTop.setText("DETALLE");


       /* mTextViewFecha.setText(new cStringMesDia().dia(mNotificationSocio.getFecha())+" "+new cStringMesDia().mes(mNotificationSocio.getFecha())+" de "+new cStringMesDia().year(mNotificationSocio.getFecha()));

        mTextViewTextMsm.setText(mNotificationSocio.getMensaje());
        mTextViewTipoNoti.setText(mNotificationSocio.getNotificacion_titulo());

        if (!new cSharedTokenValidation(NotificationDetailsSocioActivity.this).readTokenValitation())
        {
            alertDialogTimeOut();
        }

        mNotitifactionHandlers.setId_noti(mNotificationSocio.getId_notificacion());
        mNotitifactionHandlers.setToken(mSharedPreferenSocio.leerdatosSocio().getToken());

        mNotitifactionHandlers.runRead();*/

        mAdapterNotificationScroll = new cAdapterNotificationScroll(mNotificationSocios);
        mViewPagerNotificationDetails.setAdapter(mAdapterNotificationScroll);

        mViewPagerNotificationDetails.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mViewPagerNotificationDetails.setCurrentItem(ProgresoNotification);
        mViewPagerNotificationDetails.setClipToPadding(false);
        mViewPagerNotificationDetails.setClipChildren(false);

        mViewPagerNotificationDetails.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                if(mNotificationSocios.size() > 0)
                {
                    if(position == 0)
                    {
                        mSeekBar.setProgress(1);
                    }

                    mSeekBar.setProgress(position+1);


                    mNotificationSocioAux = mNotificationSocios.get(position);
                }

                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });



        mTextViewNoLeido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mNotitifactionHandlers
                        .setId_noti(mNotificationSocioAux
                                .getId_notificacion());

                mNotitifactionHandlers.runNoLido();
                banderaCambios = true;
            }
        });


        mTextViewDeleteNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mAdapterNotificationScroll.notifyItemRemoved(ProgresoNotification);
                mNotificationSocios.remove(mNotificationSocioAux);
                mSeekBar.setMax(mNotificationSocios.size());
                mSeekBar.setProgress(ProgresoNotification-1);

                mNotitifactionHandlers
                        .setId_noti(mNotificationSocioAux
                                .getId_notificacion());

                mNotitifactionHandlers.runDelete();

                banderaCambios = true;

                if(mNotificationSocios.size() == 0)
                {
                    send_intent_result();
                    finish();
                }

            }
        });



        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if (progress == 0 || progress == 1)
                {
                    mSeekBar.setProgress(1);
                }else
                    {
                        if(progress == mNotificationSocios.size())
                        {
                            mSeekBar.setProgress(mNotificationSocios.size());
                            //mSeekBar.setProgress(progress-1);
                        }else
                            {
                                mSeekBar.setProgress(progress);
                                //mSeekBar.setProgress(progress-1);
                            }
                    }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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

        if (!new cSharedTokenValidation(NotificationDetailsSocioActivity.this).readTokenValitation())
        {
            Intent mIntent = new Intent(getApplicationContext(), LogOutActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mIntent);
        }


        /*mTextViewDeleteNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent mIntent = new Intent();
                mIntent.putExtra("Notifications",mNotificationSocios);
                setResult(RESULT_OK,mIntent);

                Toast.makeText(NotificationDetailsSocioActivity.this, "TAM : "+mNotificationSocios.size(), Toast.LENGTH_SHORT).show();


                finish();

            }
        });*/
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



    private void send_intent_result()
    {

        Intent mIntent = new Intent();

        if (banderaCambios)
        {
            mIntent.putExtra("bandera",1);
        }else
            {
                mIntent.putExtra("bandera",0);
            }

        setResult(Activity.RESULT_OK,mIntent);
    }


    @Override
    public void onBackPressed()
    {
        send_intent_result();
        finish();
    }
}