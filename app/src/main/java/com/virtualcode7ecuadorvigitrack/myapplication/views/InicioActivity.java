package com.virtualcode7ecuadorvigitrack.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.AccesoSociosFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.EventosFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.NoticiasFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.shared_preferences.cSharedTokenValidation;
import com.virtualcode7ecuadorvigitrack.myapplication.views.views_contacto.ContactoInicioActivity;
import com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.InicioSociosActivity;

import java.util.List;


public class InicioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private  BottomNavigationView mBottomNavigationView;
    private View mViewToolbarTop;
    private Toolbar toolbar;
    private NoticiasFragment mNoticiasFragment = new NoticiasFragment();
    private AccesoSociosFragment mAccesoSociosFragment = new AccesoSociosFragment();
    private EventosFragment mEventosFragment = new EventosFragment();
    private FragmentManager oFragmentManager;
    private TextView mTextViewToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private int bandera = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        mBottomNavigationView = findViewById(R.id.navigationView_1);
        mTextViewToolbar = findViewById(R.id.id_detalles_toolbar_top);
        mViewToolbarTop = findViewById(R.id.id_view_toolbar_top);
        toolbar = findViewById(R.id.toolbar);
        mNavigationView = findViewById(R.id.navigation_view);
        mDrawerLayout = findViewById(R.id.id_drawer_layout);


        bandera = getIntent().getIntExtra("bandera",0);


        //MaterialShapeDrawable materialShapeDrawable = (MaterialShapeDrawable) mBottomNavigationView.getBackground();

        //materialShapeDrawable = materialShapeDrawable.getShapeAppearanceModel().toBuilder();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.vacio, R.string.vacio);


        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.trnsparente));

        mDrawerLayout.addDrawerListener(toggle);

        toggle.syncState();


        oFragmentManager = getSupportFragmentManager();


        oFragmentManager.beginTransaction().replace(R.id.fragment_container_1,mNoticiasFragment,"news")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack("news")
                .commit();
        mNavigationView.setCheckedItem(R.id.opc_noticias_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);
        mBottomNavigationView.setSelectedItemId(R.id.opc_noticias_1);
    }

    @Override
    protected void onPostResume()
    {

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case  R.id.opc_menu_1:
                        //visibleViewToolbarTop();
                        //llenarFragmentContainer();

                        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                            mDrawerLayout.closeDrawer(GravityCompat.START);
                        } else {
                            mDrawerLayout.openDrawer(GravityCompat.START);
                        }

                        //Toast.makeText(InicioActivity.this, "menu", Toast.LENGTH_SHORT).show();
                        break;
                    case  R.id.opc_noticias_1:
                        visibleViewToolbarTop();
                        /*FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(R.id.fragment_container_1,mNoticiasFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();*/

                        llenarFragmentContainer("news",mNoticiasFragment);

                        mNavigationView.setCheckedItem(R.id.opc_noticias_drawer);


                        mTextViewToolbar.setText("NOTICIAS");
                        //Toast.makeText(InicioActivity.this, "opc_noticias_1", Toast.LENGTH_SHORT).show();
                        break;
                    case  R.id.opc_eventos_1:
                        visibleViewToolbarTop();
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_1,mEventosFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();*/
                        llenarFragmentContainer("event",mEventosFragment);
                        mTextViewToolbar.setText("EVENTOS");
                        //Toast.makeText(InicioActivity.this, "opc_eventos_1", Toast.LENGTH_SHORT).show();
                        mNavigationView.setCheckedItem(R.id.opc_eventos_drawer);
                        break;
                    case  R.id.opc_acceso_socios_1:
                        goneViewToolbarTop();

                        if (new cSharedTokenValidation(InicioActivity.this).readTokenValitationFragmentInicio())
                        {
                            Intent mIntent = new Intent(InicioActivity.this,
                                    InicioSociosActivity.class);
                            startActivity(mIntent);
                        }else
                            {
                                                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_1,mAccesoSociosFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();*/
                                llenarFragmentContainer("acc_socio",mAccesoSociosFragment);
                                mTextViewToolbar.setText("LOGIN");
                                //Toast.makeText(InicioActivity.this, "opc_acceso_socios_1", Toast.LENGTH_SHORT).show();
                                mNavigationView.setCheckedItem(R.id.opc_acceso_drawer);
                            }




                        break;
                }
                return true;
            }
        });

        if (bandera == 1)
        {
            mBottomNavigationView.setSelectedItemId(R.id.opc_acceso_socios_1);
        }
        super.onPostResume();
    }


    private void visibleViewToolbarTop()
    {
        if (mViewToolbarTop.getVisibility()==View.GONE)
        {
            mViewToolbarTop.setVisibility(View.VISIBLE);
        }
    }

    private void goneViewToolbarTop()
    {
        mViewToolbarTop.setVisibility(View.GONE);
    }

    private void llenarFragmentContainer(String Tag,Fragment oF)
    {
        List<Fragment> fragmentList = oFragmentManager.getFragments();
        boolean bandera = false;/**bandera para saber si el Fragment ya esta agregada
     a la pila de procesos**/
        int pos = 0;/**variable para guardar la posicion de la pila donde se
     encuentra el Fragment**/

        for (int i = 0; i < fragmentList.size(); i++)
        {
            if (fragmentList.get(i).getTag().equals(Tag)) {
                /**YA ESTA DISPONIBLE EN LA PILA**/
                bandera = true;
                pos = i;
            }
        }

        /**Oculatr los Fragment Visibles**/
        FragmentTransaction  fragmentTransaction = oFragmentManager.beginTransaction();

        for (int i=0;i<fragmentList.size();i++)
        {
            fragmentTransaction.hide(fragmentList.get(i));
        }

        if (bandera)
        {
            /**ESTA EN LA PILA**/
            fragmentTransaction
                    .show(fragmentList.get(pos))
                    .addToBackStack(Tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }else
        {
            /**NO SE ENCUANTRA EN LA PILA (ES UN NUEVO FRAGMET)**/
            fragmentTransaction
                    .add(R.id.fragment_container_1,oF,Tag)
                    .addToBackStack(Tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.opc_noticias_drawer:
                //llenarFragmentContainer("news",mNoticiasFragment);
                onBackPressed();
                mBottomNavigationView.setSelectedItemId(R.id.opc_noticias_1);
                mTextViewToolbar.setText("NOTICIAS");

                break;
            case R.id.opc_eventos_drawer:
                onBackPressed();
                //llenarFragmentContainer("event",mEventosFragment);
                mTextViewToolbar.setText("EVENTOS");
                mBottomNavigationView.setSelectedItemId(R.id.opc_eventos_1);

                break;
            case R.id.opc_frutbol_drawer:

                Uri uri = Uri.parse("http://centroasturianodemexico.mx/futbol");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri );
                startActivity(intent);
                onBackPressed();
                break;
            case R.id.opc_contacto_drawer:
                Intent mIntentC = new Intent(InicioActivity.this, ContactoInicioActivity.class);
                mIntentC.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntentC);
                onBackPressed();
                break;
            case R.id.opc_acceso_drawer:
                onBackPressed();
                //llenarFragmentContainer("acc_socio",mAccesoSociosFragment);
                mTextViewToolbar.setText("LOGIN");
                mBottomNavigationView.setSelectedItemId(R.id.opc_acceso_socios_1);
                break;
        }

        return true;
    }
}