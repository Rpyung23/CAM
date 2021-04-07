package com.virtualcode7ecuadorvigitrack.myapplication.views.views_contacto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

public class TerminosPrivacidadActivity extends AppCompatActivity {
    private WebView mWebView;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos_privacidad);
        title = getIntent().getStringExtra("title");
        new cToolbar().show(TerminosPrivacidadActivity.this,title,true,1);
        mWebView = findViewById(R.id.id_pdf_web_view);

        //Habilitamos el javaScript y el zoom
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.loadUrl("https://www.centroasturianodemexico.mx/aviso-privacidad");

        //Este método es para que el navegador se quede en nuestra aplicación
        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return false;
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