package com.example.servitec.ui.equipos;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

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

    ProgressBar pb;

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

        listaequipos = requireActivity().findViewById(R.id.rv_equipos);

        StaggeredGridLayoutManager sgl = new StaggeredGridLayoutManager (1,StaggeredGridLayoutManager.VERTICAL);
        listaequipos.setLayoutManager(sgl);


        callEquipos();

        inicializarAdaptador();



    }


    private void inicializarAdaptador() {
        EquiposAdapter adapter = new EquiposAdapter(equipos);
        listaequipos.setAdapter(adapter);
    }

    private void callEquipos()
    {
        pb.setVisibility(View.VISIBLE);
        //Url del servicio,sin el endpoint
        final  String url = "https://tecdies.com.mx/TECDIES_ANDROID/";
        //Creamos el objeto Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//Indicamos la url del servicio
                .addConverterFactory(GsonConverterFactory.create() ) //Agregue la fábrica del convertidor para la serialización y la deserialización de objetos.
                .build();//Cree la instancia de Retrofit utilizando los valores configurados.

        serviciosTec service = retrofit.create(serviciosTec.class);

        //Recuerda que debemos colocar el modo en como obtenemos esa respuesta,en este caso es una lista de objetos

        //pero puede ser simplemente un objeto.
        Call<List<responseEquipos>> response = service.getequipos();//indicamos el metodo que deseamos ejecutar

        response.enqueue(new Callback<List<responseEquipos>>() {
            @Override
            public void onResponse(Call<List<responseEquipos>> call, Response<List<responseEquipos>> response)
            {
                try
                {
                    if (response.isSuccessful())
                    {
                        equipos = new ArrayList<>();


                        for (responseEquipos elemento : response.body())//realizamos un foreach para recorrer la lista

                        {
                            equipos.add(new responseEquipos(elemento.getId(), "Nombre: "+elemento.getNombre(), "Dependencia: "+elemento.getDependencia(), "Modelo: "+elemento.getModelo(),
                                    "Marca: "+elemento.getMarca(), "SN: "+elemento.getSn(), "Color: "+elemento.getColor(),
                                    "Estado: "+elemento.getEstado(), "Notas: "+elemento.getNotas()));
                        }
                        pb.setVisibility(View.INVISIBLE);                    }

                }catch (Exception e)
                {
                    pb.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<responseEquipos>> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
                t.printStackTrace();

            }
        });
    }


}