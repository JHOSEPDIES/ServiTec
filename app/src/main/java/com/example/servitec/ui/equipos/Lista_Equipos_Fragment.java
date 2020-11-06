package com.example.servitec.ui.equipos;

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
import com.example.servitec.clases.POJOEquipos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Lista_Equipos_Fragment extends Fragment {

    ArrayList<POJOEquipos> equipos = new ArrayList<>();

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

        Call<List<POJOEquipos>> response = service.getequipos();

        response.enqueue(new Callback<List<POJOEquipos>>() {
            @Override
            public void onResponse(Call<List<POJOEquipos>> call, Response<List<POJOEquipos>> response)
            {
                try
                {
                    if (response.isSuccessful() && response.code() == 200)
                    {
                        equipos = new ArrayList<>();

                        for (POJOEquipos elemento : response.body())
                        {
                            equipos.add(new POJOEquipos("Nombre: "+elemento.getNombre(), "Dependencia: "+elemento.getDependencia(), "Modelo: "+elemento.getModelo(),
                                    "Marca: "+elemento.getMarca(), "SN: "+elemento.getSn(), "Color: "+elemento.getColor(),
                                    "Estado: "+elemento.getEstado(),"Notas:"+elemento.getNotas()));
                        }
                        showList(equipos);

                        pb.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        showToast("Error en el EndPoint");
                    }

                }catch (Exception e)
                {
                    showToast(e.toString());
                    pb.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<POJOEquipos>> call, Throwable t) {

                showToast(t.toString());
                pb.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void showList(ArrayList<POJOEquipos> lista)
    {
        adapter.actualizar(lista);
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