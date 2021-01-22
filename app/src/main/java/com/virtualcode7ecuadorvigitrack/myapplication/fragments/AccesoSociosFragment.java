package com.virtualcode7ecuadorvigitrack.myapplication.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.cAlertDialogProgress;
import com.virtualcode7ecuadorvigitrack.myapplication.view_recovery_pass.ValidacionNumSocioActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.LoginPassActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views_socios.InicioSociosActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class AccesoSociosFragment extends Fragment implements View.OnClickListener
{
    private Button mButtonContinuar;
    private View mView;
    private TextInputEditText mTextInputEditTextCodigoUser;
    private TextView mTextViewRecoveryPass;
    private StringRequest mStringRequestLogin;
    private RequestQueue mRequestQueue;
    private AlertDialog mAlertDialog;
    public AccesoSociosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_acceso_socios, container, false);

        mButtonContinuar = mView.findViewById(R.id.id_button_validar);
        mTextInputEditTextCodigoUser= mView.findViewById(R.id.id_textviewnumSocio);
        mTextViewRecoveryPass = mView.findViewById(R.id.id_recovery_text);

        mAlertDialog = new cAlertDialogProgress()
                .showAlertProgress(getContext(),"Consultando...",false);

        mTextViewRecoveryPass.setOnClickListener(this);
        mButtonContinuar.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_button_validar:
                if (!mTextInputEditTextCodigoUser.getText().toString().isEmpty())
                {
                    mAlertDialog.show();
                    cosumirApiRestLogin();

                }else
                    {
                        /****/
                        Toasty.info(getContext(), "Existen datos vacíos", Toast.LENGTH_SHORT, true).show();
                    }
                break;
            case R.id.id_recovery_text:
                Intent mIntentRecoveryPass = new Intent(getActivity(), ValidacionNumSocioActivity.class);
                mIntentRecoveryPass.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntentRecoveryPass);
                break;
        }
    }

    private void cosumirApiRestLogin()
    {
        mStringRequestLogin = new StringRequest(Request.Method.POST, getString(R.string.api_rest_login_num_socio), new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("codigo").equals("200"))
                    {
                        JSONObject mJsonObjectResult = jsonObject.getJSONObject("resultado");
                        cSocio mSocio = new cSocio();
                        mSocio.setNum_membresia(mJsonObjectResult.getString("membresia_no"));
                        mSocio.setNombre_socio(mJsonObjectResult.getString("nombre_usuario"));
                        abrirActivityLoginPass(mSocio);
                     }else
                        {
                            Toasty.warning(getContext(),"Usuario no valido",Toasty.LENGTH_LONG).show();
                            closeAlertProg();
                        }
                } catch (JSONException e)
                {
                    Toasty.error(getContext(),"TryCatch "+e.getMessage(),Toasty.LENGTH_LONG).show();
                    closeAlertProg();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toasty.error(getContext(),error.getMessage(),Toasty.LENGTH_LONG).show();
                closeAlertProg();
            }
        }){
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> oMap = new HashMap<>();
                oMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                return oMap;
            }

            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> oMap = new HashMap<>();
                oMap.put("membresiaNo",mTextInputEditTextCodigoUser.getText().toString());
                return oMap;
            }
        };

        mRequestQueue = Volley.newRequestQueue(getContext());
        mRequestQueue.add(mStringRequestLogin);
    }

    private void closeAlertProg()
    {
        mAlertDialog.cancel();
        mAlertDialog.hide();
    }

    private void abrirActivityLoginPass(cSocio oS)
    {
        closeAlertProg();
        Intent intent = new Intent(getActivity(), LoginPassActivity.class);
        intent.putExtra("oSocio",oS);
        startActivity(intent);
    }
}