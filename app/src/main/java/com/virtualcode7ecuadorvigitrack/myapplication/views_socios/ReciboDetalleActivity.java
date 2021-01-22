package com.virtualcode7ecuadorvigitrack.myapplication.views_socios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cReciver;
import com.virtualcode7ecuadorvigitrack.myapplication.pdf.ViewPdfActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import es.dmoral.toasty.Toasty;

public class ReciboDetalleActivity extends AppCompatActivity implements View.OnClickListener
{
    private View mViewCardPdf;
    private View mViewSolicitarFactura;
    private cReciver mReciver;
    private TextView mTextViewnum;
    private TextView mTextViewfecha;
    private TextView mTextViewconcepto;
    private TextView mTextViewmonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo_detalle);
        new cToolbar().show(ReciboDetalleActivity.this,"DETALLE",true,1);
        mReciver = (cReciver) getIntent().getSerializableExtra("oReciver");
        mViewCardPdf = findViewById(R.id.id_views_card_pdf);
        mViewSolicitarFactura = findViewById(R.id.id_views_card_soli_factura);
        mTextViewnum = findViewById(R.id.id_textview_num_ope);
        mTextViewfecha = findViewById(R.id.id_textview_fecha_pago);
        mTextViewconcepto = findViewById(R.id.id_textview_concepto);
        mTextViewmonto = findViewById(R.id.id_textview_monto);



        mTextViewnum.setText(mReciver.getNum_recivo());
        mTextViewfecha.setText(mReciver.getFecha());
        mTextViewconcepto.setText(mReciver.getTexto_main());
        mTextViewmonto.setText("$ "+mReciver.getDinero_total());




        mViewCardPdf.setOnClickListener(this);
        mViewSolicitarFactura.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_views_card_pdf:
                Toasty.info(ReciboDetalleActivity.this,"OBSERVANDO PDF",Toasty.LENGTH_SHORT)
                        .show();
                Intent mIntent = new Intent(ReciboDetalleActivity.this, ViewPdfActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("pdf_url",mReciver.getRecibo_pdf());
                startActivity(mIntent);


                break;
            case R.id.id_views_card_soli_factura:
                Toasty.info(ReciboDetalleActivity.this,"SOLICITANDO FACTURA",Toasty.LENGTH_SHORT)
                        .show();
                Uri uri = Uri.parse(mReciver.getRecibo_factura());
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


}