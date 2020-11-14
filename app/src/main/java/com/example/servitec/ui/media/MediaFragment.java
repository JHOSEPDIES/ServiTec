package com.example.servitec.ui.media;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.servitec.R;
import com.example.servitec.adapters.PageAdapter;
import com.example.servitec.ui.media.Add.Up_Image_Fragment;
import com.example.servitec.ui.media.List.View_Galery_Fragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MediaFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public MediaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = requireActivity().findViewById(R.id.tabLayout_media);
        viewPager = requireActivity().findViewById(R.id.viewpager_media);

        setUpViewPager();
    }

    private ArrayList<Fragment> agregarFragments()
    {
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new Up_Image_Fragment());
        fragments.add(new View_Galery_Fragment());
        fragments.add(new QrFragment());

        return fragments;
    }

    private void setUpViewPager() {

        viewPager.setAdapter(new PageAdapter(getChildFragmentManager(), agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Agregar").setIcon(R.drawable.add);
        tabLayout.getTabAt(1).setText("Listado").setIcon(R.drawable.lista);
        tabLayout.getTabAt(2).setText("QR").setIcon(R.drawable.codigo_qr);
    }
}