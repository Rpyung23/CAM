package com.virtualcode7ecuadorvigitrack.myapplication.views_contacto;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.android.volley.toolbox.StringRequest;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;
import com.virtualcode7ecuadorvigitrack.myapplication.views_socios.NotificationDetailsSocioActivity;

import java.security.Permission;

public class ContactoInicioActivity extends AppCompatActivity implements View.OnClickListener
{
    private View mCardViewLlamada;
    private View mCardViewEmail;
    private View mCardViewSedes;
    private View mCardViewFacebook;
    private View mCardViewSoporte;
    private View mCardViewAcerca_de;

    private View mCardViewArquimides;
    private View mCardViewCCEcologico;
    private View mCardViewHotelVillas;
    private View mCardViewClubGolf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto_inicio);
        new cToolbar().show(ContactoInicioActivity.this,"CONTACTO",true,1);

        mCardViewLlamada =  findViewById(R.id.mCardViewLlamada);
        mCardViewEmail =  findViewById(R.id.mCardViewEmail);
        mCardViewSedes =  findViewById(R.id.mCardViewSedes);
        mCardViewFacebook =  findViewById(R.id.mCardViewFacebook);
        mCardViewSoporte =  findViewById(R.id.mCardViewSoporte);
        mCardViewAcerca_de =  findViewById(R.id.mCardViewAcerca_de);

        mCardViewArquimides =  findViewById(R.id.mCardViewArquimides);
        mCardViewCCEcologico =  findViewById(R.id.mCardViewCCEcologico);
        mCardViewHotelVillas =  findViewById(R.id.mCardViewHotelVillas);
        mCardViewClubGolf =  findViewById(R.id.mCardViewClubGolf);


        mCardViewLlamada.setOnClickListener(this);
        mCardViewEmail.setOnClickListener(this);
        mCardViewSedes.setOnClickListener(this);
        mCardViewFacebook.setOnClickListener(this);
        mCardViewSoporte.setOnClickListener(this);
        mCardViewAcerca_de.setOnClickListener(this);


        mCardViewArquimides.setOnClickListener(this);
        mCardViewCCEcologico.setOnClickListener(this);
        mCardViewHotelVillas.setOnClickListener(this);
        mCardViewClubGolf.setOnClickListener(this);

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
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.mCardViewLlamada:
                if (ContextCompat.checkSelfPermission(ContactoInicioActivity.this,
                        Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
                {
                    /*****/
                    llamadaCentro();
                }else
                    {
                        solicitaPermisosCall();
                    }

                break;
            case R.id.mCardViewEmail:
                sendEmail("contacto@centroasturianodemexico.mx","Contáctame","");
                break;
            case R.id.mCardViewSedes:
                Intent mIntentSed = new Intent(ContactoInicioActivity.this,SedesActivity.class);
                mIntentSed.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntentSed);
                break;
            case R.id.mCardViewFacebook:
                Uri uri = Uri.parse("https://www.facebook.com/CentroAsturianoDeMexico/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri );
                startActivity(intent);
                break;
            case R.id.mCardViewSoporte:
                sendEmail("contacto@centroasturianodemexico.mx","Soporte","");
                break;
            case R.id.mCardViewAcerca_de:
                Intent mIntentAce = new Intent(ContactoInicioActivity.this,AcercaAppActivity.class);
                mIntentAce.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntentAce);
                break;
            case R.id.mCardViewArquimides:
                if (ContextCompat.checkSelfPermission(ContactoInicioActivity.this,
                        Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
                {
                    /*****/
                    llamadaArqui();
                }else
                {
                    solicitaPermisosCall();
                }
                break;
            case R.id.mCardViewCCEcologico:
                if (ContextCompat.checkSelfPermission(ContactoInicioActivity.this,
                        Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
                {
                    /*****/
                    llamadaCCEcolo();
                }else
                {
                    solicitaPermisosCall();
                }
                break;
            case R.id.mCardViewHotelVillas:
                if (ContextCompat.checkSelfPermission(ContactoInicioActivity.this,
                        Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
                {
                    /*****/
                    llamadaHotelV();
                }else
                {
                    solicitaPermisosCall();
                }
                break;
            case R.id.mCardViewClubGolf:
                if (ContextCompat.checkSelfPermission(ContactoInicioActivity.this,
                        Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
                {
                    /*****/
                    llamadaGolf();
                }else
                {
                    solicitaPermisosCall();
                }
                break;

        }
    }

    private void sendEmail(String email, String subject, String etxto)
    {
        // Defino mi Intent y hago uso del objeto ACTION_SEND
        Intent intent = new Intent(Intent.ACTION_SEND);

        // Defino los Strings Email, Asunto y Mensaje con la función putExtra
        intent.putExtra(Intent.EXTRA_EMAIL,
                new String[] { email });
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, etxto);

        // Establezco el tipo de Intent
        intent.setType("message/rfc822");

        // Lanzo el selector de cliente de Correo
        startActivity(
                Intent.createChooser(intent,
                                "Elije un cliente de Correo:"));

    }

    private void llamadaGolf()
    {
        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"+525971061090"));
        startActivity(intentCall);
    }

    private void llamadaHotelV()
    {
        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"+525971061040"));
        startActivity(intentCall);
    }

    private void llamadaCCEcolo()
    {
        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"+525971061070"));
        startActivity(intentCall);
    }

    private void llamadaArqui()
    {
        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"+525552810024"));
        startActivity(intentCall);
    }

    private void solicitaPermisosCall()
    {
        ActivityCompat.requestPermissions(ContactoInicioActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},150);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode==150)
        {
            if (permissions.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                llamadaCentro();
            }
        }
    }

    private void llamadaCentro()
    {
        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"+525556101124"));
        startActivity(intentCall);
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}