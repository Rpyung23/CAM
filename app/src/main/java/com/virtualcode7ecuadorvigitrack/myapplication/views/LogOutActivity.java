package com.virtualcode7ecuadorvigitrack.myapplication.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.SplashScreen;
import com.virtualcode7ecuadorvigitrack.myapplication.notificaciones.cNotificationTokenLogin;

public class LogOutActivity extends AppCompatActivity
{
    private TextView mTextViewLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);




        mTextViewLogOut = findViewById(R.id.btnAceptarLogOut);

        mTextViewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mIntent = new Intent(LogOutActivity.this, InicioActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        return;
    }
}