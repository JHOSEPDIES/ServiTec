package com.example.servitec.ui.equipos;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.servitec.Interfaces.serviciosTec;
import com.example.servitec.R;
import com.example.servitec.adapters.EquiposAdapter;
import com.example.servitec.clases.responseEquipos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Lista_Equipos_Fragment extends Fragment {

    ArrayList<responseEquipos> equipos = new ArrayList<>();

    private RecyclerView listaequipos;

    private EquiposAdapter adapter;

    private SwipeRefreshLayout swp;

    private ProgressBar pb;

    public Lista_Equipos_Fragment() {
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
        return inflater.inflate(R.layout.fragment_lista__equipos_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pb = requireActivity().findViewById(R.id.pb_equipos);

        swp = requireActivity().findViewById(R.id.swp_datos);

        listaequipos = requireActivity().findViewById(R.id.rv_equipos);

        StaggeredGridLayoutManager sgl = new StaggeredGridLayoutManager (1,StaggeredGridLayoutManager.VERTICAL);
        listaequipos.setLayoutManager(sgl);

        inicializarAdaptador();

        callEquipos();

        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callEquipos();
                swp.setRefreshing(false);
            }
        });
    }


    private void inicializarAdaptador() {
        adapter = new EquiposAdapter(equipos);
        listaequipos.setAdapter(adapter);
    }

    private void callEquipos()
    {
        pb.setVisibility(View.VISIBLE);
        final  String url = "https://tecdies.com.mx/TECDIES_ANDROID/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create() )
                .build();

        serviciosTec service = retrofit.create(serviciosTec.class);

        Call<List<responseEquipos>> response = service.getequipos();

        response.enqueue(new Callback<List<responseEquipos>>() {
            @Override
            public void onResponse(Call<List<responseEquipos>> call, Response<List<responseEquipos>> response)
            {
                try
                {
                    if (response.isSuccessful() && response.code() == 200)
                    {
                        equipos = new ArrayList<>();

                        for (responseEquipos elemento : response.body())
                        {
                            equipos.add(new responseEquipos("Nombre: "+elemento.getNombre(), "Dependencia: "+elemento.getDependencia(), "Modelo: "+elemento.getModelo(),
                                    "Marca: "+elemento.getMarca(), "SN: "+elemento.getSn(), "Color: "+elemento.getColor(),
                                    "Estado: "+elemento.getEstado(),"Notas:"+elemento.getNotas()));
                        }
                        showList(equipos);

                        pb.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        Toast.makeText(requireActivity(),"Error en EndPoint", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e)
                {
                    Toast.makeText(requireActivity(), "Error: "+e.toString(), Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<responseEquipos>> call, Throwable t) {

                Toast.makeText(requireActivity(), "Falló: "+t.toString(), Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.INVISIBLE);
                t.printStackTrace();

            }
        });
    }

    public void showList(ArrayList<responseEquipos> lista)
    {
        adapter.actualizar(lista);
    }




}