package com.virtualcode7ecuadorvigitrack.myapplication.pdf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

public class ViewPdfActivity extends AppCompatActivity
{
    private WebView mWebView;
    private String pdf_url;
    private ImageView mImageViewClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        pdf_url = getIntent().getStringExtra("pdf_url");
        mWebView = findViewById(R.id.id_pdf_web_view);
        mImageViewClose = findViewById(R.id.id_close_pdf);
        //Habilitamos el javaScript y el zoom
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.loadUrl(pdf_url);

        //Este método es para que el navegador se quede en nuestra aplicación
        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return false;
            }
        });
        mImageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}