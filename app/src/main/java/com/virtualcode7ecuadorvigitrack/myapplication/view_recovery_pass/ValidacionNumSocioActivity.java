package com.virtualcode7ecuadorvigitrack.myapplication.view_recovery_pass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

public class ValidacionNumSocioActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button mButtonRecoveryPass;
    private AlertDialog mAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacion_num_socio);
        new cToolbar().show(ValidacionNumSocioActivity.this,"",true,0);
        mButtonRecoveryPass = findViewById(R.id.id_button_validar);
        mButtonRecoveryPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_button_validar:
                showOpenAlert();
                break;
        }
    }

    private void showOpenAlert()
    {
        View mView = LayoutInflater.from(ValidacionNumSocioActivity.this).inflate(R.layout.alert_dialog_info_recovery_pass,
                null,false);
        Button mButton = mView.findViewById(R.id.id_button_accep);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /**open activity**/
                mAlertDialog.cancel();
                mAlertDialog.hide();
                /*Intent mIntent = new Intent(ValidacionNumSocioActivity.this,UpdatePasswordActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);*/

                finish();
            }
        });
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ValidacionNumSocioActivity.this);
        mBuilder.setView(mView);

        mAlertDialog = mBuilder.create();
        mAlertDialog.getWindow().setLayout(400,400);
        mAlertDialog.show();
    }
}