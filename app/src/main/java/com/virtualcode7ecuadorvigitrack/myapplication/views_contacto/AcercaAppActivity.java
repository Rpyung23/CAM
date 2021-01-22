package com.virtualcode7ecuadorvigitrack.myapplication.views_contacto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

public class AcercaAppActivity extends AppCompatActivity
{
    private TextView mTextViewAviso;
    private TextView mTextViewTermi;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_app);
        new cToolbar().show(AcercaAppActivity.this,"ACERCA DE LA APP",true,1);


        mTextViewAviso = findViewById(R.id.id_aviso);
        mTextViewTermi = findViewById(R.id.id_terminos);


        mTextViewAviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AcercaAppActivity.this,TerminosPrivacidadActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title","Aviso de Privacidad");
                startActivity(intent);
            }
        });

        mTextViewTermi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AcercaAppActivity.this,TerminosPrivacidadActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title","Términos y Condiciones de Uso");
                startActivity(intent);
            }
        });

    }
}