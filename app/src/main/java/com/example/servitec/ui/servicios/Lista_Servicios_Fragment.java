package com.example.servitec.ui.servicios;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import com.example.servitec.Interfaces.serviciosTec;
import com.example.servitec.R;
import com.example.servitec.adapters.EquiposAdapter;
import com.example.servitec.adapters.ServiciosAdapter;
import com.example.servitec.clases.responseEquipos;
import com.example.servitec.clases.responseServiciosGet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Lista_Servicios_Fragment extends Fragment {

    ArrayList<responseServiciosGet> servicios = new ArrayList<>();

    private RecyclerView listaservicios;

    private ServiciosAdapter adapter;

    private SwipeRefreshLayout swp;

    private ProgressBar pb;

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

        pb = requireActivity().findViewById(R.id.pbserv);

        swp = requireActivity().findViewById(R.id.swp_serv);

        listaservicios = requireActivity().findViewById(R.id.rv_serv);

        StaggeredGridLayoutManager sgl = new StaggeredGridLayoutManager (1,StaggeredGridLayoutManager.VERTICAL);
        listaservicios.setLayoutManager(sgl);

        inicializarAdaptador();

        callServicios();

        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callServicios();
                swp.setRefreshing(false);
            }
        });
    }

    private void callServicios()
    {
        pb.setVisibility(View.VISIBLE);
        final  String url = "https://tecdies.com.mx/TECDIES_ANDROID/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create() )
                .build();
        serviciosTec service = retrofit.create(serviciosTec.class);
        Call<List<responseServiciosGet>> response = service.getservicios();

        response.enqueue(new Callback<List<responseServiciosGet>>() {
            @Override
            public void onResponse(Call<List<responseServiciosGet>> call, Response<List<responseServiciosGet>> response) {
                try
                {
                    if (response.isSuccessful() && response.code() == 200)
                    {
                        servicios = new ArrayList<>();
                        for (responseServiciosGet elemento : response.body())
                        {
                            servicios.add(new responseServiciosGet("Nombre: "+elemento.getNombre(),
                                    "Dependencia: "+elemento.getDependencia(),
                                    "Modelo: "+elemento.getModelo(),"Marca: "+elemento.getMarca(),
                                    "SM: "+elemento.getSn(),"Color: "+elemento.getColor(),
                                    "Servicio: "+elemento.getServicio()
                                    ));
                        }
                        showList(servicios);
                        pb.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        pb.setVisibility(View.INVISIBLE);
                        showToast("Error en el EndPoint");
                    }

                }
                catch (Exception e)
                {
                    pb.setVisibility(View.INVISIBLE);
                    showToast(e.toString());
                }
            }

            @Override
            public void onFailure(Call<List<responseServiciosGet>> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
                showToast(t.toString());
            }
        });
    }

    private void showList(ArrayList<responseServiciosGet> servicios)
    {
        adapter.actualizar(servicios);
    }

    private void inicializarAdaptador() {
        adapter = new ServiciosAdapter(servicios);
        listaservicios.setAdapter(adapter);
    }

    private void showToast(String mensaje)
    {
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast, requireActivity().findViewById(R.id.layout_toast));

        TextView txt_mensaje = layout.findViewById(R.id.txt_mensaje);

        txt_mensaje.setText(mensaje);

        Toast toast = new Toast(requireActivity());

        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,0,0);

        toast.setDuration(Toast.LENGTH_SHORT);

        toast.setView(layout);

        toast.show();
    }
}