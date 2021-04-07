package com.virtualcode7ecuadorvigitrack.myapplication.views.pdf;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.virtualcode7ecuadorvigitrack.myapplication.R;

public class ViewPdfActivity extends AppCompatActivity
{
    private WebView mWebView;
    private String pdf_url;
    private ImageView mImageViewClose;
    private String title = "";
    private TextView mTextView;
    private int pdf = 0;

    private CircularProgressIndicator mCircularProgressIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        pdf_url = getIntent().getStringExtra("pdf_url");
        title =getIntent().getStringExtra("title");
        pdf = getIntent().getIntExtra("pdf_ban",0);
        mWebView = findViewById(R.id.id_pdf_web_view);
        mTextView = findViewById(R.id.id_title);
        mImageViewClose = findViewById(R.id.id_close_pdf);
        mCircularProgressIndicator = findViewById(R.id.circleIndicator);
        mTextView.setText(title);
        //Habilitamos el javaScript y el zoom
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        if (pdf == 1)
        {
            //String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
            mWebView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf_url);
        }else
            {
                mWebView.loadUrl(pdf_url);
            }



        //Este método es para que el navegador se quede en nuestra aplicación
        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                mCircularProgressIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                if (mCircularProgressIndicator.getVisibility()==View.VISIBLE){
                    mCircularProgressIndicator.setVisibility(View.GONE);
                }
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