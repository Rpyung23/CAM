package com.virtualcode7ecuadorvigitrack.myapplication.views.view_recovery_pass;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.virtualcode7ecuadorvigitrack.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class UpdatePasswordActivity extends AppCompatActivity
{
    private MaterialButton mButton;

    private AlertDialog mAlertDialog;
    private String mMembresia;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequestRecovery;


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

        mMembresia = getIntent().getStringExtra("membresia");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showProgress();
            }
        });
    }

    private void showProgress()
    {
        /*****/
        SpotsDialog.Builder mBuilder = new SpotsDialog.Builder();
        mBuilder.setContext(UpdatePasswordActivity.this);
        mBuilder.setMessage("ACTUALIZANDO!!");
        mBuilder.setCancelable(false);
        mAlertDialog = mBuilder.build();
        mAlertDialog.show();
    }


    private void recoveryAccount()
    {

        mStringRequestRecovery = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.api_rest_recovery_account), new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                closeAlertDialogProgress();

                try {
                    JSONObject mJsonObject = new JSONObject(response);
                    if(mJsonObject.getBoolean("success")){
                        /**Envio Correcto**/
                        Toasty.success(getApplicationContext(),mJsonObject.getString("descripcion"),
                                Toasty.LENGTH_SHORT).show();
                        finish();
                    }else{
                        /**No se envio**/
                        Toasty.info(getApplicationContext(),mJsonObject.getString("descripcion"),
                                Toasty.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                closeAlertDialogProgress();
                Toasty.error(getApplicationContext(),error.getMessage(),
                        Toasty.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("membresiaNo",mMembresia);
                return map;
            }
        };

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(mStringRequestRecovery);
    }
}