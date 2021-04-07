package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.adapters.recivos.cAdapterReciboScroll;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cReciver;
import com.virtualcode7ecuadorvigitrack.myapplication.views.pdf.ViewPdfActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;
import com.virtualcode7ecuadorvigitrack.myapplication.views.LogOutActivity;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class ReciboDetalleScrollActivity extends AppCompatActivity implements View.OnClickListener
{
    private ViewPager2 mViewPager2;
    private ArrayList<cReciver> mReciverArrayList;
    private int posicion = 0;
    private cAdapterReciboScroll mAdapterReciboScroll;
    private View mViewCardPdf;
    private View mViewSolicitarFactura;
    private int posScroll = 0;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo_detalle_scroll);

        new cToolbar().show(ReciboDetalleScrollActivity.this,"DETALLES DE RECIBOS",true,1);


        mViewPager2 = findViewById(R.id.id_viewpager2_recibos);
        mViewCardPdf = findViewById(R.id.id_views_card_pdf);
        mViewSolicitarFactura = findViewById(R.id.id_views_card_soli_factura);

        mReciverArrayList = (ArrayList<cReciver>) getIntent().getSerializableExtra("recivos");
        posicion = getIntent().getIntExtra("posicion_inicial",0);


        mAdapterReciboScroll = new cAdapterReciboScroll(ReciboDetalleScrollActivity.this,mReciverArrayList);

        mViewPager2.setAdapter(mAdapterReciboScroll);
        mViewPager2.setClipToPadding(false);
        mViewPager2.setClipChildren(false);
        mViewPager2.setOffscreenPageLimit(3);


        CompositePageTransformer mCompositePageTransformer = new CompositePageTransformer();
        mCompositePageTransformer.addTransformer(new MarginPageTransformer(40));
        mCompositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position)
            {
                float r = 1 -Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });


        mViewPager2.setPageTransformer(mCompositePageTransformer);
        mViewPager2.setCurrentItem(posicion);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                posScroll = position;
            }
        });


        mViewCardPdf.setOnClickListener(this);
        mViewSolicitarFactura.setOnClickListener(this);


        if (!new cSharedTokenValidation(ReciboDetalleScrollActivity.this).readTokenValitation())
        {
            alertDialogTimeOut();
        }



    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_views_card_pdf:

                if (!new cSharedTokenValidation(ReciboDetalleScrollActivity.this).readTokenValitation())
                {
                    alertDialogTimeOut();
                }

                Toasty.info(ReciboDetalleScrollActivity.this,"OBSERVANDO PDF",Toasty.LENGTH_SHORT)
                        .show();
                Intent mIntent = new Intent(ReciboDetalleScrollActivity.this, ViewPdfActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("pdf_url",mReciverArrayList.get(posScroll).getRecibo_pdf());
                Log.e("pdf_url",mReciverArrayList.get(posScroll).getRecibo_pdf());
                mIntent.putExtra("title","VER DETALLES DEL RECIBO");
                mIntent.putExtra("pdf_ban",1);
                startActivity(mIntent);


                break;
            case R.id.id_views_card_soli_factura:

                if (!new cSharedTokenValidation(ReciboDetalleScrollActivity.this).readTokenValitation())
                {
                    alertDialogTimeOut();
                }
                Toasty.info(ReciboDetalleScrollActivity.this,"SOLICITANDO FACTURA",Toasty.LENGTH_SHORT)
                        .show();
                Uri uri = Uri.parse(mReciverArrayList.get(posScroll).getRecibo_factura());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri );
                startActivity(intent);
                break;
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

    private void alertDialogTimeOut()
    {


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ReciboDetalleScrollActivity.this);
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


        if (!new cSharedTokenValidation(ReciboDetalleScrollActivity.this).readTokenValitation())
        {
            Intent mIntent = new Intent(getApplicationContext(), LogOutActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mIntent);
        }
    }
}