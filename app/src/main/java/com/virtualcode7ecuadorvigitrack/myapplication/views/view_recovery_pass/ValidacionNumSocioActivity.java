package com.virtualcode7ecuadorvigitrack.myapplication.views.view_recovery_pass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cToolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class ValidacionNumSocioActivity extends AppCompatActivity implements View.OnClickListener
{
    private MaterialButton mButtonRecoveryPass;
    private android.app.AlertDialog mAlertDialog;

    private AlertDialog mAlertDialogViewEmailSend;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequestRecovery;

    private String mMembresia;

    private TextInputEditText mTextInputEditTextMembresia;
    private TextInputLayout mTextInputLayoutMembresia;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacion_num_socio);
        new cToolbar().show(ValidacionNumSocioActivity.this,"",true,0);
        mButtonRecoveryPass = findViewById(R.id.id_button_validar);
        mTextInputEditTextMembresia = findViewById(R.id.id_textviewnumSocio);
        mTextInputLayoutMembresia = findViewById(R.id.idTextInputLayoutError);
        mButtonRecoveryPass.setOnClickListener(this);
        mMembresia = getIntent().getStringExtra("membresia");
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_button_validar:
                if(!mTextInputEditTextMembresia.getText().toString().isEmpty()) {
                    recoveryAccount();
                }else {
                    //mTextInputEditTextMembresia.setError(getResources().getString(R.string.text_empty),getResources().getDrawable(R.drawable.ic_baseline_highlight_off_24));
                    mTextInputLayoutMembresia.setErrorEnabled(true);
                    mTextInputLayoutMembresia.setError(getResources().getString(R.string.text_empty));
                }
                break;
        }
    }

    private void closeAlertDialogProgress()
    {
        if(mAlertDialog!=null && mAlertDialog.isShowing()){
            mAlertDialog.cancel();
            mAlertDialog.hide();
        }
    }

    private void showOpenAlert()
    {
        View mView = LayoutInflater.from(ValidacionNumSocioActivity.this)
                .inflate(R.layout.alert_dialog_info_recovery_pass,
                null,false);
        MaterialButton mButton = mView.findViewById(R.id.id_button_accep);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /**open activity**/
                mAlertDialogViewEmailSend.cancel();
                mAlertDialogViewEmailSend.hide();
                /*Intent mIntent = new Intent(ValidacionNumSocioActivity.this,UpdatePasswordActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);*/

                finish();
            }
        });
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ValidacionNumSocioActivity.this);
        mBuilder.setView(mView);

        mAlertDialogViewEmailSend = mBuilder.create();
        mAlertDialogViewEmailSend.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mAlertDialogViewEmailSend.show();
    }

    private void recoveryAccount()
    {
        mTextInputLayoutMembresia.setErrorEnabled(false);
        showProgress();

        mStringRequestRecovery = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.api_rest_recovery_account),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                closeAlertDialogProgress();

                try {
                    JSONObject mJsonObject = new JSONObject(response);
                    if(mJsonObject.getBoolean("success")){
                        /**Envio Correcto**/
                        showOpenAlert();
                    }else{
                        /**No se envio**/
                        showSweetAlertNoseEnvio(mJsonObject
                                .getString("descripcion"));
                    }
                } catch (JSONException e) {
                    showSweetAlertNoseEnvio("Socio no válido");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                closeAlertDialogProgress();
                openErrorServer(error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("membresiaNo",mTextInputEditTextMembresia.getText().toString());
                return map;
            }
        };

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(mStringRequestRecovery);
    }

    private void showSweetAlertNoseEnvio(String msm)
    {
        SweetAlertDialog mSweetAlertDialog = new SweetAlertDialog(ValidacionNumSocioActivity.this
                ,SweetAlertDialog.WARNING_TYPE);
        mSweetAlertDialog.setTitle("Recuperar Contraseña");
        mSweetAlertDialog.setContentText(msm);
        mSweetAlertDialog.setConfirmButton("Aceptar", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                finish();
            }
        });
        mSweetAlertDialog.show();
    }

    private void openErrorServer(String msm)
    {
        SweetAlertDialog mSweetAlertDialog = new SweetAlertDialog(ValidacionNumSocioActivity.this
                ,SweetAlertDialog.ERROR_TYPE);
        mSweetAlertDialog.setTitle("Recuparar Contraseña");
        mSweetAlertDialog.setContentText(msm);
        mSweetAlertDialog.setConfirmButton("Aceptar", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                finish();
            }
        });
        mSweetAlertDialog.show();
    }


    private void showProgress()
    {
        /*****/
        SpotsDialog.Builder mBuilder = new SpotsDialog.Builder();
        mBuilder.setContext(ValidacionNumSocioActivity.this);
        mBuilder.setMessage("Por favor espere.");
        mBuilder.setCancelable(false);
        mAlertDialog = mBuilder.build();
        mAlertDialog.show();
    }

}