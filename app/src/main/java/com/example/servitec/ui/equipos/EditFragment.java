package com.example.servitec.ui.equipos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.example.servitec.adapters.EquiposAdapter;
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


public class EditFragment extends Fragment {

    private ProgressBar pb;

    ArrayList<responseEquipos> equipos = new ArrayList<>();

    private Button btn_buscar;

    private EditText codigo,nombre,dependencia,modelo,marca,ns,color,estado,notas;

    public EditFragment() {
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
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pb = requireActivity().findViewById(R.id.pb_edit);

        btn_buscar = requireActivity().findViewById(R.id.btn_find);


        codigo = requireActivity().findViewById(R.id.txt_code_edit);
        nombre = requireActivity().findViewById(R.id.nombre_comun_editar);
        dependencia = requireActivity().findViewById(R.id.dependencia_editar);
        modelo = requireActivity().findViewById(R.id.modelo_editar);
        marca = requireActivity().findViewById(R.id.marca_editar);
        ns= requireActivity().findViewById(R.id.ns_editar);
        color = requireActivity().findViewById(R.id.color_editar);
        estado = requireActivity().findViewById(R.id.estado_editar);
        notas = requireActivity().findViewById(R.id.notas_editar);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!codigo.getText().toString().equals(""))
                {
                    callEquipobyId(codigo.getText().toString());
                }
                else
                {
                    showToast("!No dejar Campos Vacios¡");
                }
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

        Call<List<responseEquipos>> response = service.getequipobyId(id);

        response.enqueue(new Callback<List<responseEquipos>>() {
            @Override
            public void onResponse(Call<List<responseEquipos>> call, Response<List<responseEquipos>> response)
            {
                try
                {
                    if (response.isSuccessful() && response.code() == 200)
                    {

                        for (responseEquipos elemento : response.body())
                        {
                            nombre.setText(elemento.getNombre());
                            dependencia.setText(elemento.getDependencia());
                            modelo.setText(elemento.getModelo());
                            marca.setText(elemento.getMarca());
                            ns.setText(elemento.getSn());
                            color.setText(elemento.getColor());
                            estado.setText(elemento.getEstado());
                            notas.setText(elemento.getNotas());
                        }

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