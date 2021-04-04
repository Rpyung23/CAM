package com.virtualcode7ecuadorvigitrack.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.AccesoSociosFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cMembresiaSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.provider.cToken;
import com.virtualcode7ecuadorvigitrack.myapplication.services.cServiceTimerToken;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferenSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferencesMembresia;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.HideKeys;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.views_socios.InicioSociosActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LoginPassActivity extends AppCompatActivity
{
    private Button mButton;
    private cSocio mSocio;
    private TextView mTextViewName1;
    private TextView mTextViewName2;
    private String mStringNames[];
    private TextInputEditText mTextInputEditTextPass;
    private StringRequest mStringRequestPass;
    private StringRequest mStringRequestMem;
    private RequestQueue mRequestQueue;
    private AlertDialog mAlertDialog;
    private TextView mTextViewNumMem;
    private cToken mToken;
    private TextView mTextViewNoSoyYo;
    private Activity mActivity;
    private LinearLayoutCompat mLinearLayoutCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pass);
        mButton = findViewById(R.id.id_button_login_socios);
        mSocio = (cSocio) getIntent().getSerializableExtra("oSocio");
        mToken = new cToken();

        mActivity = this;

        mTextViewNumMem = findViewById(R.id.id_textview_num_membre);
        mTextViewName1 = findViewById(R.id.id_name_titulo_1);
        mTextViewName2 = findViewById(R.id.id_name_titulo_2);
        mTextViewNoSoyYo = findViewById(R.id.id_no_soy_yo);

        mLinearLayoutCompat = findViewById(R.id.LinearLayout);


        mTextInputEditTextPass = findViewById(R.id.id_edittextpassword);

        mStringNames =  mSocio.getNombre_socio().split(" ");
        Log.e("name",mSocio.getNombre_socio());

        mAlertDialog = new cAlertDialogProgress()
                .showAlertProgress(LoginPassActivity.this,"Verificando...",false);

        mTextViewNumMem.setText(mSocio.getNum_membresia());
        if (mStringNames.length==1)
        {
            mTextViewName1.setText(mStringNames[0].charAt(0)+"****");
        }else if(mStringNames.length==2)
        {
            mTextViewName1.setText(mStringNames[0].charAt(0)+"**** "+mStringNames[1].charAt(0)+"****");
        }else if (mStringNames.length==3)
        {
            mTextViewName1.setText(mStringNames[0].charAt(0)+"**** "+mStringNames[1].charAt(0)+"****");
            mTextViewName2.setText(mStringNames[2].charAt(0)+"****");
        }else if (mStringNames.length==4)
            {
                mTextViewName1.setText(mStringNames[0].charAt(0)+"**** "+mStringNames[1].charAt(0)+"****");
                mTextViewName2.setText(mStringNames[2].charAt(0)+"**** "+mStringNames[3].charAt(0)+"****");
            }


        mLinearLayoutCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new HideKeys().ocultar(mActivity);
            }
        });

        mTextViewNoSoyYo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new HideKeys().ocultar(mActivity);
                finish();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (!mTextInputEditTextPass.getText().toString().isEmpty())
                {
                    new HideKeys().ocultar(mActivity);

                    mAlertDialog.show();
                    mToken.readTokenMovil().addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String token)
                        {
                            apirestLoginPass(token);
                        }
                    });

                    mToken.readTokenMovil().addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            closeAlertProgress();
                            Toasty.error(LoginPassActivity.this,"Error Token",Toasty.LENGTH_LONG).show();

                        }
                    });



                    //openActivity();
                }else
                    {
                        Toasty.info(getApplicationContext(),"Por favor ingrese su contraseña",
                                Toasty.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void openActivity()
    {
        closeAlertProgress();
        new cSharedTokenValidation(LoginPassActivity.this).writeToken("ok");

        Intent mIntent = new Intent(LoginPassActivity.this, InicioSociosActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
        finish();
    }

    private void closeAlertProgress()
    {
        mAlertDialog.cancel();
        mAlertDialog.dismiss();
    }

    private void apirestLoginPass(String token)
    {
        mStringRequestPass = new StringRequest(Request.Method.POST, getString(R.string.api_rest_login_pass),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject mJsonObject = new JSONObject(response);
                    if (mJsonObject.getString("codigo").equals("200"))
                    {
                        mSocio.setToken(mJsonObject.getString("token"));
                        mSocio.setId_token_socio(mJsonObject.getInt("id"));
                        if (new cSharedPreferenSocio(LoginPassActivity.this).registerdatosSocio(mSocio))
                        {
                            consumirApiRestMembresia();
                        }
                        else
                            {
                                Toasty.warning(LoginPassActivity.this,"Error shared loginPass",Toasty.LENGTH_SHORT)
                                        .show();
                            }
                    }else
                        {
                            Toasty.warning(LoginPassActivity.this,"Contraseña no valida",Toasty.LENGTH_SHORT)
                                    .show();
                        }

                    closeAlertProgress();

                } catch (JSONException e) {
                    Toasty.error(LoginPassActivity.this,"TryResponse "+e.getMessage(),
                            Toasty.LENGTH_SHORT).show();
                    closeAlertProgress();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toasty.error(LoginPassActivity.this,"VolleyError "+error.getMessage(),Toasty.LENGTH_SHORT).show();
                closeAlertProgress();
            }
        })
        {
            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("membresiaPassword",mTextInputEditTextPass.getText().toString());
                stringStringHashMap.put("membresiaNo",mSocio.getNum_membresia());
                stringStringHashMap.put("tokenAplicacion",token);
                return stringStringHashMap;
            }

            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                return stringStringHashMap;
            }
        };

        mRequestQueue =  Volley.newRequestQueue(LoginPassActivity.this);
        mRequestQueue.add(mStringRequestPass);
    }

    private void consumirApiRestMembresia()
    {
        Log.e("MEMBRESIA",getString(R.string.api_rest_membresia)
                +new cSharedPreferenSocio(LoginPassActivity.this)
                .leerdatosSocio().getId_token_socio());
        Log.e("MEMBRESIA",new cSharedPreferenSocio(LoginPassActivity.this)
                .leerdatosSocio().getToken());

        mStringRequestMem = new StringRequest(Request.Method.GET, getString(R.string.api_rest_membresia)
                +new cSharedPreferenSocio(LoginPassActivity.this)
                .leerdatosSocio().getId_token_socio(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject mJsonObject = new JSONObject(response);
                    if (mJsonObject.getString("codigo").equals("200"))
                    {
                        JSONObject mJsonObjectResult = mJsonObject.getJSONObject("resultado");
                        cMembresiaSocio oM = new cMembresiaSocio();
                        oM.setIdMembresia(mJsonObjectResult.getInt("idMembresia"));
                        oM.setMembresiaToken(mJsonObjectResult.getString("membresiaToken"));
                        oM.setMembresiaTitular(mJsonObjectResult.getString("membresiaTitular"));
                        oM.setMembresiaPassword(mJsonObjectResult.getString("membresiaPassword"));
                        oM.setMembresiaEmail(mJsonObjectResult.getString("membresiaEmail"));
                        oM.setMembresiaNo(mJsonObjectResult.getString("membresiaNo"));
                        oM.setMembresiaEstado(mJsonObjectResult.getString("membresiaEstado"));
                        oM.setMembresiaFechaIngreso(mJsonObjectResult.getString("membresiaFechaIngreso"));
                        oM.setMembresiaConexion(mJsonObjectResult.getString("membresiaFechaConexion"));
                        oM.setMembresiaTokenNotification(mJsonObjectResult.getString("membresiaTokenNotificaciones"));
                        oM.setMembresiaTipo(mJsonObjectResult.getString("membresiaTipo"));
                        oM.setMembresiaTokenAplicacion(mJsonObjectResult.getString("membresiaTokenAplicacion"));
                        oM.setMembresiaCelular(mJsonObjectResult.getString("membresiaCelular"));
                        oM.setMembresiaSaldo(mJsonObjectResult.getString("membresiaSaldo"));
                        oM.setMembresiaCuestionarioResultado(mJsonObjectResult.getInt("membresiaCuestionarioResultado"));

                        if (new cSharedPreferencesMembresia(LoginPassActivity.this).writeMembresia(oM))
                        {
                            /**lanzar service***/

                            cServiceTimerToken serviceTimerToken = new cServiceTimerToken();

                            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
                            {
                                Intent mIntent = new Intent(LoginPassActivity.this,serviceTimerToken.getClass());
                                startForegroundService(mIntent);
                            }else
                                {
                                    Intent mIntent = new Intent(LoginPassActivity.this,serviceTimerToken.getClass());
                                    startService(mIntent);
                                }

                            openActivity();
                        }else
                            {
                                closeAlertProgress();
                                Toasty.info(LoginPassActivity.this,"Error Shared Membresia",Toasty.LENGTH_LONG).show();
                            }

                    }else
                    {
                        closeAlertProgress();
                        Toasty.warning(LoginPassActivity.this,"No se puedo consultar su membresia",
                                Toasty.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toasty.error(LoginPassActivity.this,"TRY Membresia "+e.getMessage(),
                            Toasty.LENGTH_LONG).show();
                    closeAlertProgress();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toasty.error(LoginPassActivity.this,"Error Mem : "+error.getMessage(),Toasty.LENGTH_LONG).show();
                closeAlertProgress();
            }
        })
        {
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> stringHashMap = new HashMap<>();
                stringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                stringHashMap.put("token",new cSharedPreferenSocio(LoginPassActivity.this)
                        .leerdatosSocio().getToken());
                return stringHashMap;
            }

        };

        mRequestQueue.add(mStringRequestMem);
    }

}