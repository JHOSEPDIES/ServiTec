package com.example.servitec.ui.equipos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.servitec.Interfaces.serviciosTec;
import com.example.servitec.R;
import com.example.servitec.clases.responseEquipos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddFragment extends Fragment {

    private ProgressBar pb;

    private Button guardar;
    public AddFragment() {
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
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pb = requireActivity().findViewById(R.id.pb_add);
        guardar = requireActivity().findViewById(R.id.btn_guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos();
            }
        });

    }

    private void guardarDatos()
    {
        pb.setVisibility(View.VISIBLE);
        //Url del servicio,sin el endpoint
        final  String url = "https://tecdies.com.mx/TECDIES_ANDROID/";

        //creamos Gson builder
        Gson gson = new GsonBuilder().setLenient().create();

        //Creamos el objeto Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//Indicamos la url del servicio
                .addConverterFactory(GsonConverterFactory.create(gson) ) //Agregue la fábrica del convertidor para la serialización y la deserialización de objetos.
                .build();//Cree la instancia de Retrofit utilizando los valores configurados.

        serviciosTec service = retrofit.create(serviciosTec.class);

        //Recuerda que debemos colocar el modo en como obtenemos esa respuesta,en este caso es una lista de objetos

        //pero puede ser simplemente un objeto.
        //indicamos el metodo que deseamos ejecutar
        Call<String> response = service.guardarEquipo("Gamin","Comunicacion",
                "Tower HP","HP", "S/N","Negra","Regular","S/Nt"
                );
        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.code() == 200)
                {
                    pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(requireActivity(), response.body(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(requireActivity(), "Error en el EndPoint", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
                Toast.makeText(requireActivity(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
