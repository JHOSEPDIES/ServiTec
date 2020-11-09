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

import com.example.servitec.R;
import com.example.servitec.clases.POJO.POJOEquipos;
import com.example.servitec.clases.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Equipo_Fragment extends Fragment {

    private ProgressBar pb;

    ArrayList<POJOEquipos> equipos = new ArrayList<>();

    private Button btn_buscar;

    private EditText codigo,nombre,dependencia,modelo,marca,ns,color,estado,notas;

    public Edit_Equipo_Fragment() {
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

        Call<List<POJOEquipos>> response = RetroClient.getInstance().getApi().getequipobyId(id);

        response.enqueue(new Callback<List<POJOEquipos>>() {
            @Override
            public void onResponse(Call<List<POJOEquipos>> call, Response<List<POJOEquipos>> response)
            {
                try
                {
                    if (response.isSuccessful() && response.code() == 200)
                    {

                        for (POJOEquipos elemento : response.body())
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