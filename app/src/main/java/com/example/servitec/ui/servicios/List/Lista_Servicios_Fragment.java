package com.example.servitec.ui.servicios.List;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servitec.R;
import com.example.servitec.adapters.rv_adapters.ServiciosAdapter;
import com.example.servitec.API.RetroClient;
import com.example.servitec.clases.Modelos.POJOServiciosGet;
import com.example.servitec.ui.equipos.List.List_Equipos_Presenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Lista_Servicios_Fragment extends Fragment implements List_Servicios_View{

    List<POJOServiciosGet> servicio;

    private RecyclerView listaservicios;

    private ServiciosAdapter adapter;

    private SwipeRefreshLayout swp;

    List_Servicios_Presenter presenter;

    public Lista_Servicios_Fragment() {
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
        return inflater.inflate(R.layout.fragment_lista__servicios_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swp = requireActivity().findViewById(R.id.swp_serv);

        listaservicios = requireActivity().findViewById(R.id.rv_serv);

        presenter = new List_Servicios_Presenter(this);

        listaservicios.setLayoutManager(new LinearLayoutManager(requireActivity()));

        presenter = new List_Servicios_Presenter(this);

        presenter.getData();


        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData();
            }
        });
    }


    private void showToast(String message)
    {
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast, requireActivity().findViewById(R.id.layout_toast));

        TextView txt_mensaje = layout.findViewById(R.id.txt_mensaje);

        txt_mensaje.setText(message);

        Toast toast = new Toast(requireActivity());

        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,0,0);

        toast.setDuration(Toast.LENGTH_SHORT);

        toast.setView(layout);

        toast.show();
    }
    @Override
    public void showBar()
    {
        swp.setRefreshing(true);
    }

    @Override
    public void hideBar()
    {
        swp.setRefreshing(false);
    }

    @Override
    public void onErrorLoad(String Message)
    {
        showToast(Message);
    }

    @Override
    public void onGetResult(List<POJOServiciosGet> servicios)
    {
        adapter = new ServiciosAdapter(servicios);
        adapter.notifyDataSetChanged();
        listaservicios.setAdapter(adapter);
        servicio = servicios;
    }
}