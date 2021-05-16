package com.virtualcode7ecuadorvigitrack.myapplication.activity;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedPreferenSocio;

public class cActivityInicio extends AppCompatActivity
{
    private cSharedPreferenSocio mSharedPreferenSocio;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mSharedPreferenSocio = new cSharedPreferenSocio(getApplicationContext());
    }


    protected void onResume(NavigationView mNavigationView)
    {
        showOpctionsLogin(mNavigationView);
    }

    private void showOpctionsLogin(NavigationView mNavigationView)
    {
        if(mSharedPreferenSocio.leerdatosSocio().getToken().equals("s/n")){
            /**VISIBLE SOLO LOS DRAWER POR DEFECTO**/
            visibleItemDefault(mNavigationView);
            invisibleItemLogIn(mNavigationView);
        }else{
            visibleItemLogIn(mNavigationView);
            invisibleItemDefault(mNavigationView);
        }
    }

    private void visibleItemDefault(NavigationView mNavigationView)
    {
        mNavigationView.getMenu().findItem(R.id.opc_noticias_drawer).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.opc_eventos_drawer).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.opc_frutbol_drawer).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.opc_contacto_drawer).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.opc_acceso_drawer).setVisible(true);
    }
    private void invisibleItemDefault(NavigationView mNavigationView)
    {
        mNavigationView.getMenu().findItem(R.id.opc_noticias_drawer).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.opc_eventos_drawer).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.opc_frutbol_drawer).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.opc_contacto_drawer).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.opc_acceso_drawer).setVisible(false);
    }

    private void visibleItemLogIn(NavigationView mNavigationView)
    {
        Menu menu = mNavigationView.getMenu();
        menu.findItem(R.id.opc_my_account_inicio_login).setVisible(true);
        menu.findItem(R.id.opc_status_account_inicio_login).setVisible(true);
        menu.findItem(R.id.opc_recivos_inicio_login).setVisible(true);
        menu.findItem(R.id.opc_notification_inicio_login).setVisible(true);
    }
    private void invisibleItemLogIn(NavigationView mNavigationView)
    {
        mNavigationView.getMenu().findItem(R.id.opc_my_account_inicio_login).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.opc_status_account_inicio_login).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.opc_recivos_inicio_login).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.opc_notification_inicio_login).setVisible(false);
    }
}
