package com.virtualcode7ecuadorvigitrack.myapplication.handlers;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.virtualcode7ecuadorvigitrack.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class cNotitifactionHandlers
{
    private Context mContext;
    private RequestQueue mRequestQueueNotification;
    private StringRequest mStringRequestNotification;
    private Handler mHandler;
    private int id_noti;
    private String token;

    public int getId_noti() {
        return id_noti;
    }

    public void setId_noti(int id_noti) {
        this.id_noti = id_noti;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run()
        {
            deleteNotification(getId_noti(),getToken());
        }
    };


    private Runnable mRunnableRead = new Runnable() {
        @Override
        public void run()
        {
            readNotification(getId_noti(),getToken());
        }
    };

    private Runnable mRunnableNoLeido = new Runnable() {
        @Override
        public void run()
        {
            NoLeidoNotification(getId_noti(),getToken());
        }
    };


    private Runnable mRunnableLeido = new Runnable() {
        @Override
        public void run()
        {
            LeidoNotification(getId_noti(),getToken());
        }
    };

    public cNotitifactionHandlers(Context mContext)
    {
        this.mContext = mContext;
        mHandler = new Handler();
        initRequestQueue();
    }

    private void initRequestQueue()
    {
        mRequestQueueNotification = Volley.newRequestQueue(mContext);
    }

    public void runDelete()
    {
        mHandler.post(mRunnable);
    }

    public void runRead()
    {
        mHandler.post(mRunnableRead);
    }

    public void runNoLido()
    {
        mHandler.post(mRunnableNoLeido);
    }
    public void runLeido()
    {
        mHandler.post(mRunnableLeido);
    }

    public void deleteNotification(int id_noti,String token)
    {
        /**https://centroasturianodemexico.mx/api/v1/notificaciones/3/estado/D**/
        mStringRequestNotification =
                new StringRequest(mContext.getString(R.string.update_notification)+id_noti+"/estado/D"
                        , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                /**
                 *     "resultado": "3",
                 *     "mensaje": "Se a cambiado el estado de la notificacion",
                 *     "codigo": "200"
                 * **/

                try {
                    JSONObject mJsonObject = new JSONObject(response);

                    if (mJsonObject.getInt("codigo") != 200)
                    {
                        Toasty.error(mContext, "No se pudo Eliminar", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toasty.error(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
                {
                    @Override
                    public HashMap<String, String> getHeaders() throws AuthFailureError
                    {
                        HashMap<String,String> stringStringHashMap = new HashMap<>();
                        stringStringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                        stringStringHashMap.put("token",token);
                        return stringStringHashMap;
                    }
                };


        mRequestQueueNotification.add(mStringRequestNotification);

    }

    public void LeidoNotification(int id_noti,String token)
    {
        /**https://centroasturianodemexico.mx/api/v1/notificaciones/3/estado/D**/
        mStringRequestNotification =
                new StringRequest(mContext.getString(R.string.update_notification)+id_noti+"/estado/P"
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        /**
                         *     "resultado": "3",
                         *     "mensaje": "Se a cambiado el estado de la notificacion",
                         *     "codigo": "200"
                         * **/

                        try {
                            JSONObject mJsonObject = new JSONObject(response);

                            if (mJsonObject.getInt("codigo") != 200)
                            {
                                Toasty.error(mContext, "No se pudo Eliminar", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toasty.error(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    public HashMap<String, String> getHeaders() throws AuthFailureError
                    {
                        HashMap<String,String> stringStringHashMap = new HashMap<>();
                        stringStringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                        stringStringHashMap.put("token",token);
                        return stringStringHashMap;
                    }
                };


        mRequestQueueNotification.add(mStringRequestNotification);

    }

    public void readNotification(int id_noti,String token)
    {
        /**https://centroasturianodemexico.mx/api/v1/notificaciones/3/estado/D**/
        mStringRequestNotification =
                new StringRequest(mContext.getString(R.string.update_notification)+id_noti+"/estado/R"
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        /**
                         *     "resultado": "3",
                         *     "mensaje": "Se a cambiado el estado de la notificacion",
                         *     "codigo": "200"
                         * **/

                        try {
                            JSONObject mJsonObject = new JSONObject(response);

                            if (mJsonObject.getInt("codigo") == 200)
                            {
                               // Toasty.success(mContext,"Notificación Eliminada",Toasty.LENGTH_SHORT).show();
                                Log.e("NOTIFICATION","LEIDA");
                            }else
                            {
                                //Toasty.info(mContext, "No se pudo Eliminar", Toast.LENGTH_SHORT).show();
                                Log.e("NOTIFICATION","NO LEIDA");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //Toasty.error(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("NOTIFICATION",error.getMessage());
                    }
                })
                {
                    @Override
                    public HashMap<String, String> getHeaders() throws AuthFailureError
                    {
                        HashMap<String,String> stringStringHashMap = new HashMap<>();
                        stringStringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                        stringStringHashMap.put("token",token);
                        return stringStringHashMap;
                    }
                };


        mRequestQueueNotification.add(mStringRequestNotification);

    }

    public void NoLeidoNotification(int id_noti,String token)
    {
        /**https://centroasturianodemexico.mx/api/v1/notificaciones/3/estado/D**/
        mStringRequestNotification =
                new StringRequest(mContext.getString(R.string.update_notification)+id_noti+"/estado/A"
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        /**
                         *     "resultado": "3",
                         *     "mensaje": "Se a cambiado el estado de la notificacion",
                         *     "codigo": "200"
                         * **/

                        try {
                            JSONObject mJsonObject = new JSONObject(response);

                            if (mJsonObject.getInt("codigo") == 200)
                            {
                                // Toasty.success(mContext,"Notificación Eliminada",Toasty.LENGTH_SHORT).show();
                                Log.e("NOTIFICATION","LEIDA");
                            }else
                            {
                                //Toasty.info(mContext, "No se pudo Eliminar", Toast.LENGTH_SHORT).show();
                                Log.e("NOTIFICATION","NO LEIDA");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //Toasty.error(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("NOTIFICATION",error.getMessage());
                    }
                })
                {
                    @Override
                    public HashMap<String, String> getHeaders() throws AuthFailureError
                    {
                        HashMap<String,String> stringStringHashMap = new HashMap<>();
                        stringStringHashMap.put("tokenGlobal","R15wSyZka2UqSEMqeDUqUSYhcSo3d1YlbypDNHJudWo=");
                        stringStringHashMap.put("token",token);
                        return stringStringHashMap;
                    }
                };


        mRequestQueueNotification.add(mStringRequestNotification);

    }

}
