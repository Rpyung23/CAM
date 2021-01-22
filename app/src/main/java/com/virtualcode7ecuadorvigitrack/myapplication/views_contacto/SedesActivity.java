package com.virtualcode7ecuadorvigitrack.myapplication.views_contacto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

public class SedesActivity extends AppCompatActivity implements View.OnClickListener
{

    private CardView mCardViewParque;
    private CardView mCardViewSocio;
    private CardView mCardViewEco;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedes);
        new cToolbar().show(SedesActivity.this,"SEDES",true,1);

        mCardViewParque = findViewById(R.id.mCardViewParque);
        mCardViewSocio = findViewById(R.id.mCardViewSocio);
        mCardViewEco = findViewById(R.id.mCardViewEco);

        mCardViewEco.setOnClickListener(this);
        mCardViewSocio.setOnClickListener(this);
        mCardViewParque.setOnClickListener(this);

    }


    @Override
    public void onClick(View view)
    {
        Intent mIntentEco = new Intent(SedesActivity.this,SedesDetailsActivity.class);
        switch (view.getId())
        {
            case R.id.mCardViewEco:

                mIntentEco.putExtra("sede",3);

                break;
            case R.id.mCardViewSocio:
                mIntentEco.putExtra("sede",2);
                break;
            case R.id.mCardViewParque:
                mIntentEco.putExtra("sede",1);
                break;
        }
        mIntentEco.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntentEco);
    }
}