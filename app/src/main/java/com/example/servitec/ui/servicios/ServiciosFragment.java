package com.example.servitec.ui.servicios;

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
import com.example.servitec.ui.equipos.AddFragment;
import com.example.servitec.ui.equipos.DeleteFragment;
import com.example.servitec.ui.equipos.EditFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ServiciosFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_servicios, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = requireActivity().findViewById(R.id.tabLayout_Servicios);
        viewPager = requireActivity().findViewById(R.id.viewpager_servicios);


        setUpViewPager();

    }
    private ArrayList<Fragment> agregarFragments()
    {
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new Add_Servicios_Fragment());
        fragments.add(new Lista_Servicios_Fragment());
        return fragments;
    }

    private void setUpViewPager() {

        viewPager.setAdapter(new PageAdapter(getParentFragmentManager(), agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Agregar").setIcon(R.drawable.add);
        tabLayout.getTabAt(1).setText("Listado").setIcon(R.drawable.lista);
    }
}