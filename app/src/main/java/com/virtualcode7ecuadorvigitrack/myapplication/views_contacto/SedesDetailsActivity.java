package com.virtualcode7ecuadorvigitrack.myapplication.views_contacto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;
import com.virtualcode7ecuadorvigitrack.myapplication.views_socios.ReciboDetalleActivity;

public class SedesDetailsActivity extends AppCompatActivity
{
    private TextView mTextViewPhone1;
    private TextView mTextViewPhone2;
    private TextView mTextViewPhone3;

    private TextView mTextViewTituloPhone1;
    private TextView mTextViewTituloPhone2;
    private TextView mTextViewTituloPhone3;

    private TextView mTextViewTituloSede;
    private TextView mTextViewDireccion;

    private ImageView mImageView;
    private  int sede =1;


    private CardView mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedes_details);
        new cToolbar().show(SedesDetailsActivity.this,"",true,0);
        sede = getIntent().getIntExtra("sede",1);

        mTextViewPhone1 = findViewById(R.id.id_phone1);
        mTextViewPhone2 = findViewById(R.id.id_phone2);
        mTextViewPhone3 = findViewById(R.id.id_phone3);

        mCardView = findViewById(R.id.cardView3);

        mTextViewTituloPhone1 = findViewById(R.id.textnum1);
        mTextViewTituloPhone2 = findViewById(R.id.id_textnum2);
        mTextViewTituloPhone3 = findViewById(R.id.id_textnum3);

        mTextViewTituloSede = findViewById(R.id.id_titulo);
        mTextViewDireccion = findViewById(R.id.id_direcc);

        mImageView = findViewById(R.id.id_img_fondo);


        if (sede==1)
        {
            Picasso.with(SedesDetailsActivity.this).load(R.drawable.sede_1).into(mImageView);
            mTextViewTituloPhone1.setVisibility(View.GONE);
            mTextViewTituloPhone2.setVisibility(View.GONE);
            mTextViewTituloPhone3.setVisibility(View.GONE);
            mTextViewPhone1.setText("(55) 5610 1124");
            mTextViewPhone2.setText("(55) 5610 1723");
            mTextViewTituloSede.setText("Parque Asturias");
            mTextViewDireccion.setText("Caliz 118,El Relog,Coyoacán, 04640\nCiudad de México");
            mCardView.setVisibility(View.GONE);
        }else if (sede==2)
        {
            Picasso.with(SedesDetailsActivity.this).load(R.drawable.sede_2).into(mImageView);
            mTextViewTituloPhone1.setVisibility(View.GONE);
            mTextViewTituloPhone2.setVisibility(View.GONE);
            mTextViewTituloPhone3.setVisibility(View.GONE);
            mCardView.setVisibility(View.GONE);
            mTextViewPhone1.setText("(55) 52810024");
            mTextViewTituloSede.setText("Edificio Sociocultural");
            mTextViewPhone2.setText("(55) 5281 0141");
            mTextViewDireccion.setText("Arquímedes 4Col. Chapultec Morales\nCiudad de México");
        }else
            {
                Picasso.with(SedesDetailsActivity.this).load(R.drawable.sede_3).into(mImageView);
                mTextViewPhone1.setText("(55) 5971061070");
                mTextViewPhone2.setText("(55) 5971061090");
                mTextViewPhone3.setText("(55) 5971061040");

                mTextViewTituloSede.setText("Club Campestre Ecológico");

                mTextViewTituloPhone1.setText("Casa club");
                mTextViewTituloPhone2.setText("Campo de golf");
                mTextViewTituloPhone3.setText("Hotel");
                mTextViewDireccion.setText("Carretera Federal Cuautla-Chalco-Mexico km 53");
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

}