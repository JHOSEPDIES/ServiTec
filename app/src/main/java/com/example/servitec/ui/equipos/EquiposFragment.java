package com.example.servitec.ui.equipos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.servitec.R;
import com.example.servitec.adapters.PageAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class EquiposFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_equipos, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = requireActivity().findViewById(R.id.tabLayout);
        viewPager = requireActivity().findViewById(R.id.viewpager);


        setUpViewPager();
    }

    private ArrayList<Fragment> agregarFragments()
    {
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new Lista_Equipos_Fragment());
        fragments.add(new Add_Equipo_Fragment());
        fragments.add(new Edit_Equipo_Fragment());
        fragments.add(new Delete_Equipo_Fragment());

        return fragments;
    }

    private void setUpViewPager() {

        viewPager.setAdapter(new PageAdapter(getParentFragmentManager(), agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Listado").setIcon(R.drawable.lista);
        tabLayout.getTabAt(1).setText("Agregar").setIcon(R.drawable.add);
        tabLayout.getTabAt(2).setText("Editar").setIcon(R.drawable.edit);
        tabLayout.getTabAt(3).setText("Eliminar").setIcon(R.drawable.delete);
    }
}