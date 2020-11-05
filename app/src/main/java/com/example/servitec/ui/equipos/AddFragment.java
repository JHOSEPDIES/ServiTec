package com.example.servitec.ui.equipos;

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

    private EditText nombre,dependencia,modelo,marca,ns,color,estado,notas;

    public AddFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pb = requireActivity().findViewById(R.id.pb_add);

        guardar = requireActivity().findViewById(R.id.btn_guardar);

        nombre = requireActivity().findViewById(R.id.txt_nombre_comun);
        dependencia = requireActivity().findViewById(R.id.txt_dependencia);
        modelo = requireActivity().findViewById(R.id.txt_modelo);
        marca = requireActivity().findViewById(R.id.txt_marca);
        ns = requireActivity().findViewById(R.id.txt_ns);
        estado = requireActivity().findViewById(R.id.txt_estado);
        color = requireActivity().findViewById(R.id.txt_color);
        notas = requireActivity().findViewById(R.id.txt_notas);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nombre.getText().toString().equals("") && !dependencia.getText().toString().equals("") && !modelo.getText().toString().equals("") && !marca.getText().toString().equals("") &&
                    !ns.getText().toString().equals("") && !estado.getText().toString().equals("") && !color.getText().toString().equals("") && !notas.getText().toString().equals(""))
                {
                    guardarDatos(nombre.getText().toString(),dependencia.getText().toString(),modelo.getText().toString(),marca.getText().toString(),
                            ns.getText().toString(),estado.getText().toString(),color.getText().toString(),notas.getText().toString());
                }
                else
                {
                    showToast("¡No Dejar Campos Vacios!");
                }
            }
        });

    }

    private void guardarDatos(String nom, String dep, String mod, String mar, String ns, String edo, String col, String not)
    {
        pb.setVisibility(View.VISIBLE);
        final  String url = "https://tecdies.com.mx/TECDIES_ANDROID/";
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson) )
                .build();
        serviciosTec service = retrofit.create(serviciosTec.class);

        Call<String> response = service.guardarEquipo(nom,dep,mod,mar, ns,col,edo,not);

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body().equals("1"))
                {
                    pb.setVisibility(View.INVISIBLE);
                    showToast("Guardado Correctamente");
                    limpiarContainers();
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
        nombre.setText("");
        dependencia.setText("");
        modelo.setText("");
        marca.setText("");
        ns.setText("");
        estado.setText("");
        color.setText("");
        notas.setText("");
    }

}
