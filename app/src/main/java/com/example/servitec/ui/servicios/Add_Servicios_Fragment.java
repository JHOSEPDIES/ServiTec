package com.example.servitec.ui.servicios;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servitec.Interfaces.serviciosTec;
import com.example.servitec.R;
import com.example.servitec.clases.responseEquipos;
import com.example.servitec.clases.responseServicios;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_Servicios_Fragment extends Fragment {

    private Button btn_guardar,btn_buscar;

    private EditText id,nombre,dependencia,modelo,marca,ns,color,servicio;

    private ProgressBar pb;

    public Add_Servicios_Fragment() {
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
        return inflater.inflate(R.layout.fragment_add__servicios_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        id = requireActivity().findViewById(R.id.txt_code_servicios);

        btn_guardar = requireActivity().findViewById(R.id.btn_guardar_servicio);

        btn_buscar = requireActivity().findViewById(R.id.btn_find_servicios);

        pb = requireActivity().findViewById(R.id.pb_add_servicio);


        nombre = requireActivity().findViewById(R.id.nombre_comun_servicios);
        dependencia = requireActivity().findViewById(R.id.dependencia_servicios);
        modelo = requireActivity().findViewById(R.id.modelo_servicios);
        marca = requireActivity().findViewById(R.id.marca_servicios);
        ns = requireActivity().findViewById(R.id.ns_servicios);
        color = requireActivity().findViewById(R.id.color_servicios);
        servicio = requireActivity().findViewById(R.id.trabajo_servicio);


        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!id.getText().toString().equals(""))
                {
                    callEquipobyId(id.getText().toString());
                }
                else
                {
                    showToast("¡Ingrese Código!");
                }
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!id.getText().toString().equals("") && !nombre.getText().toString().equals("") && !dependencia.getText().toString().equals("") && !modelo.getText().toString().equals("") && !marca.getText().toString().equals("") &&
                        !ns.getText().toString().equals("") && !color.getText().toString().equals("") && !servicio.getText().toString().equals(""))
                {
                    guardarDatos(id.getText().toString(), nombre.getText().toString(),dependencia.getText().toString(),modelo.getText().toString(),marca.getText().toString(),
                            ns.getText().toString(),color.getText().toString(),servicio.getText().toString());
                }
                else
                {
                    showToast("¡No Dejar Campos Vacios!");
                }
            }


        });

    }

    private void guardarDatos(String id,String nom, String dep, String mod, String mar, String ns, String col, String ser)
    {
        pb.setVisibility(View.VISIBLE);

        final  String url = "https://tecdies.com.mx/TECDIES_ANDROID/";

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        serviciosTec service = retrofit.create(serviciosTec.class);

        Call<String> response = service.guardarServicio(nom,dep,mod,mar,ns,col,ser);

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                try
                {
                    Log.e("Respuesta",response.body());

                    if (response.isSuccessful() && response.code() == 200 && response.body().equals("1"))
                    {
                        pb.setVisibility(View.INVISIBLE);
                        showToast("Servicio Guardado!");
                        limpiarContainers();
                    }
                    else
                    {
                        pb.setVisibility(View.INVISIBLE);
                        showToast("Error al Guardar!");
                        limpiarContainers();
                    }
                }catch (Exception e)
                {
                    pb.setVisibility(View.INVISIBLE);
                    showToast(e.toString());
                    limpiarContainers();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                pb.setVisibility(View.INVISIBLE);
                showToast(t.toString());
                limpiarContainers();
            }
        });

    }

    private void callEquipobyId(String id)
    {
        pb.setVisibility(View.VISIBLE);

        final  String url = "https://tecdies.com.mx/TECDIES_ANDROID/";

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson) )
                .build();

        serviciosTec service = retrofit.create(serviciosTec.class);

        Call<List<responseServicios>> response = service.getequipobyIdServicio(id);

        response.enqueue(new Callback<List<responseServicios>>() {
            @Override
            public void onResponse(Call<List<responseServicios>> call, Response<List<responseServicios>> response) {
                try
                {
                    if (response.isSuccessful() && response.code() == 200)
                    {
                        for (responseServicios elemento : response.body()) {
                            pb.setVisibility(View.INVISIBLE);
                            nombre.setText(elemento.getNombre());
                            dependencia.setText(elemento.getDependencia());
                            modelo.setText(elemento.getModelo());
                            marca.setText(elemento.getMarca());
                            ns.setText(elemento.getSn());
                            color.setText(elemento.getColor());
                        }
                    }
                    else
                    {
                        pb.setVisibility(View.INVISIBLE);
                        showToast("No Existe El Equipo");
                        limpiarContainers();
                    }

                }catch (Exception e)
                {
                    pb.setVisibility(View.INVISIBLE);
                    showToast(e.toString());
                    limpiarContainers();
                }
            }

            @Override
            public void onFailure(Call<List<responseServicios>> call, Throwable t)
            {
                pb.setVisibility(View.INVISIBLE);
                showToast(t.toString());
                limpiarContainers();

            }
        });


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

    private void limpiarContainers()
    {
        id.setText("");
        nombre.setText("");
        dependencia.setText("");
        modelo.setText("");
        marca.setText("");
        ns.setText("");
        color.setText("");
    }


}