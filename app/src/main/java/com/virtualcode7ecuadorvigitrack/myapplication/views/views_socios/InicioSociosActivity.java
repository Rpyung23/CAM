package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.activity.cActivityInicioSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.framents_socios.NotificationSocioFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.framents_socios.ProfileSocioFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.services.cServiceTimerToken;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferenSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;
import com.virtualcode7ecuadorvigitrack.myapplication.views.InicioActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.LogOutActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.views_contacto.ContactoInicioActivity;

public class InicioSociosActivity extends cActivityInicioSocio
        implements  NavigationView.OnNavigationItemSelectedListener
{
    private BottomNavigationView mBottomNavigationView;
    private ProfileSocioFragment mProfileSocioFragment = new ProfileSocioFragment();
    private NotificationSocioFragment mNotificationSocioFragment = new NotificationSocioFragment();
    private TextView mTextViewTop;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private View header;

    private AlertDialog mAlertDialog ;
    private String mStringFragmentBandera = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_socios);

        toolbar = findViewById(R.id.toolbar);
        mBottomNavigationView = findViewById(R.id.navigationView_2);
        mTextViewTop = findViewById(R.id.id_detalles_toolbar_top);


        mNavigationView = findViewById(R.id.navigation_view_2);

        mDrawerLayout = findViewById(R.id.id_drawer_layout);

        mStringFragmentBandera = getIntent().getStringExtra("fragment");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.vacio, R.string.vacio);


        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.trnsparente));

        mDrawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        header = mNavigationView.getHeaderView(0);

        TextView mTextViewName = header.findViewById(R.id.id_textview_name);
        TextView mTextViewNum = header.findViewById(R.id.id_textview_num);

        mTextViewName.setText(new cSharedPreferenSocio(InicioSociosActivity.this).leerdatosSocio().getNombre_socio());
        mTextViewNum.setText("No socio "+new cSharedPreferenSocio(InicioSociosActivity.this).leerdatosSocio().getNum_membresia());

        openFragmentInicio();
        mNavigationView.setNavigationItemSelectedListener(this);



    }

    private void openFragmentInicio()
    {
        if(mStringFragmentBandera!=null && mStringFragmentBandera.equals("notification"))
        {
            mTextViewTop.setText("NOTIFICACIONES");

            FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.replace(R.id.fragment_container_2,mNotificationSocioFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            mNavigationView.setCheckedItem(R.id.opc_noti_drawer2);
            mBottomNavigationView.setSelectedItemId(R.id.opc_notification);

        }else{
            mTextViewTop.setText("MI PERFIL");

            FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.replace(R.id.fragment_container_2,mProfileSocioFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            mNavigationView.setCheckedItem(R.id.opc_perfil_drawer2);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            return;
        }
    }

    @Override
    protected void onResume()
    {

        /*if (!new cSharedTokenValidation(InicioSociosActivity.this).readTokenValitation())
        {
            Intent mIntent = new Intent(getApplicationContext(), LogOutActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mIntent);
        }*/
        super.onResume();


        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.opc_profile:

                        /*if (!new cSharedTokenValidation(InicioSociosActivity.this).readTokenValitation())
                        {
                            alertDialogTimeOut();
                        }*/

                        mTextViewTop.setText("MI PERFIL");
                        openFragments(mProfileSocioFragment);
                        mNavigationView.setCheckedItem(R.id.opc_perfil_drawer2);
                        break;
                    case R.id.opc_notification:
                        /*if (!new cSharedTokenValidation(InicioSociosActivity.this).readTokenValitation())
                        {
                            alertDialogTimeOut();
                        }*/
                        mTextViewTop.setText("NOTIFICACIONES");
                        openFragments(mNotificationSocioFragment);
                        mNavigationView.setCheckedItem(R.id.opc_noti_drawer2);
                        break;
                    case R.id.opc_menu_1:

                        /*if (!new cSharedTokenValidation(InicioSociosActivity.this).readTokenValitation())
                        {
                            alertDialogTimeOut();
                        }*/


                        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                            mDrawerLayout.closeDrawer(GravityCompat.START);
                        } else {
                            mDrawerLayout.openDrawer(GravityCompat.START);
                        }

                        break;
                    case R.id.opc_close:

                        /*if (!new cSharedTokenValidation(InicioSociosActivity.this).readTokenValitation())
                        {
                            alertDialogTimeOut();
                        }*/

                        mNavigationView.setCheckedItem(R.id.opc_cerrar_drawer2);
                        alertDialog();
                        break;
                }
                return true;
            }
        });
        //mBottomNavigationView.setSelectedItemId(R.id.opc_profile);
    }

    private void alertDialogTimeOut()
    {
        AlertDialog mAlertDialog = null;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(InicioSociosActivity.this);
        mBuilder.setMessage("Lo sentimos su tiempo se agotado");
        mBuilder.setTitle("Login");
        mBuilder.setCancelable(false);
        mBuilder.setIcon(R.drawable.ic_asturian_primary_color);
        AlertDialog finalMAlertDialog = mAlertDialog;
        mBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Intent mIntentS = new Intent(InicioSociosActivity.this, cServiceTimerToken.class);
                stopService(mIntentS);

                finalMAlertDialog.cancel();

                Intent mIntent = new Intent(InicioSociosActivity.this, InicioActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("bandera",1);
                startActivity(mIntent);

                finish();
            }
        });
        mAlertDialog = mBuilder.create();
        mAlertDialog.show();
        return;
    }


    private void alertDialog()
    {


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(InicioSociosActivity.this);
        mBuilder.setCancelable(false);
        mBuilder.setIcon(R.drawable.ic_asturian_primary_color);
        mBuilder.setMessage("Desea cerrar sesión ?");
        mBuilder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Intent mIntentS = new Intent(InicioSociosActivity.this,cServiceTimerToken.class);
                stopService(mIntentS);

                mAlertDialog.cancel();
                mAlertDialog.hide();


                /*Intent mIntentService = new Intent(InicioSociosActivity.this,cServiceTimerToken.class);

                stopService(mIntentService);*/



                //new cSharedTokenValidation(InicioSociosActivity.this).writeToken("error");


                clearToken();


                Intent mIntent = new Intent(InicioSociosActivity.this,InicioActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("bandera",1);
                startActivity(mIntent);


                finish();
            }
        });
        mBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                mAlertDialog.cancel();
                mAlertDialog.hide();
            }
        });

        mAlertDialog = mBuilder.create();
        mAlertDialog.show();
    }

    private void clearToken()
    {
        new cSharedPreferenSocio(getApplicationContext()).clearSharedSocio();
    }

    private void openFragments(Fragment oFragment)
    {
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container_2,oFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.opc_perfil_drawer2:
                onBackPressed();
                mTextViewTop.setText("PERFIL");
                mBottomNavigationView.setSelectedItemId(R.id.opc_profile);

                break;
            case R.id.opc_status_drawer2:
                //mTextViewTop.setText("EVENTOS");

                Intent mIntent = new Intent(InicioSociosActivity.this, StatusCuentaSociosActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
                onBackPressed();
                break;
            case R.id.opc_recibos_drawer2:

                Intent mIntentRecibos = new Intent(InicioSociosActivity.this, RecivosCuentaActivity.class);
                mIntentRecibos.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntentRecibos);
                onBackPressed();
                break;
            case R.id.opc_noti_drawer2:
                onBackPressed();
                mTextViewTop.setText("NOTIFICACIONES");
                mBottomNavigationView.setSelectedItemId(R.id.opc_notification);

                break;
            case R.id.opc_contacto_drawer2:

                Intent mIntentC = new Intent(InicioSociosActivity.this, ContactoInicioActivity.class);
                mIntentC.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntentC);
                onBackPressed();
                break;
            case R.id.opc_cerrar_drawer2:
                onBackPressed();
                alertDialog();

                break;
        }

        return true;
    }





}