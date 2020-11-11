package com.example.servitec.ui.inicio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.servitec.MainActivity;
import com.example.servitec.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {

    private ImageView scan_code,equipos,graficas,usuarios;

    private static final int CAMERA_PERMISSION_CODE=101;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_inicio, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        scan_code = requireActivity().findViewById(R.id.scan_qr_code);
        equipos = requireActivity().findViewById(R.id.componentes);
        graficas = requireActivity().findViewById(R.id.graficas);
        usuarios = requireActivity().findViewById(R.id.usuarios);

        scan_code.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(Build.VERSION.SDK_INT>=23)
                {
                    if(checkPermission(Manifest.permission.CAMERA))
                    {
                        openScanner();
                    }
                    else
                        {
                        requestPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
                        }
                }
                else
                    {
                    openScanner();
                    }
            }
        });

        equipos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_add_Equipo_Fragment);
            }
        });

        graficas.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_graficasFragment);
            }
        });

        usuarios.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_add_Usuarios_Fragment);
            }
        });
    }

    private void openScanner()
    {

        IntentIntegrator.forSupportFragment(InicioFragment.this).initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(requireActivity(), "Blank", Toast.LENGTH_SHORT).show();
            }
            else
                {

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

                    Bundle args = new Bundle();

                    args.putString("codigo",result.getContents());

                    navController.navigate(R.id.action_nav_home_to_add_Servicios_Fragment,args);
            }
        }
        else{
            Toast.makeText(requireActivity(), "Blank", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkPermission(String permission){
        int result= ContextCompat.checkSelfPermission(requireActivity(),permission);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    private void requestPermission(String permision,int code){
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),permision)){

        }
        else{
            ActivityCompat.requestPermissions(requireActivity(),new String[]{permision},code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_PERMISSION_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openScanner();
                }
        }
    }
}