package com.virtualcode7ecuadorvigitrack.myapplication.adapters.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas_socio.AddExtrasReservaFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas_socio.AddFechasReservaFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas_socio.AddHabitacionesFragment;
import com.virtualcode7ecuadorvigitrack.myapplication.fragments.fragment_reservas_socio.ResumenReservacionSocioFragment;

public class ViewPagerAdapterReservasSocio extends FragmentStateAdapter
{
    public ViewPagerAdapterReservasSocio(FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);
    }


    @Override
    public Fragment createFragment(int position)
    {
        Fragment mFragment;
        switch (position)
        {
            case 0:
                mFragment =   new AddFechasReservaFragment();
                break;
            case 1:
                mFragment = new AddHabitacionesFragment();
                break;
            case 2:
                mFragment = new AddExtrasReservaFragment();
                break;
            case 3:
                mFragment = new ResumenReservacionSocioFragment();
                break;
            default:
                mFragment = new AddFechasReservaFragment();
        }
        return mFragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
