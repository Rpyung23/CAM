package com.virtualcode7ecuadorvigitrack.myapplication.views.view_recovery_pass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.virtualcode7ecuadorvigitrack.myapplication.R;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class UpdatePasswordActivity extends AppCompatActivity
{
    private MaterialButton mButton;
    private Handler mHandlerPass;
    private AlertDialog mAlertDialog;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run()
        {
            closeAlertDialogProgress();
            finish();
            Toasty.success(UpdatePasswordActivity.this,"CONTRASEÑA ACTUALIZADA!"
                    ,Toasty.LENGTH_LONG).show();
        }
    };

    private void closeAlertDialogProgress()
    {
        mAlertDialog.cancel();
        mAlertDialog.hide();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        mButton = findViewById(R.id.id_button_update_pass);
        mHandlerPass = new Handler();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /*****/
                SpotsDialog.Builder mBuilder = new SpotsDialog.Builder();
                mBuilder.setContext(UpdatePasswordActivity.this);
                mBuilder.setMessage("ACTUALIZANDO!!");
                mBuilder.setCancelable(false);
                mAlertDialog = mBuilder.build();
                mAlertDialog.show();
                mHandlerPass.postDelayed(mRunnable,1500);
            }
        });
    }
}