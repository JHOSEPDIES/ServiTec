package com.example.servitec.ui.servicios.Add;

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
import com.example.servitec.clases.Modelos.POJORespuesta;
import com.example.servitec.clases.Modelos.POJOServicios;
import com.example.servitec.API.RetroClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Boolean.TRUE;

public class Add_Servicios_Fragment extends Fragment implements Add_Servicios_View{

    private Button btn_guardar,btn_buscar;

    private EditText id,nombre,dependencia,modelo,marca,ns,color,servicio;

    private ProgressBar pb;

    Add_Servicios_Presenter presenter;

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

        btn_buscar = requireActivity().findViewById(R.id.btn_find_serv);

        pb = requireActivity().findViewById(R.id.pb_add_servicio);

        presenter = new Add_Servicios_Presenter(this);


        nombre = requireActivity().findViewById(R.id.nombre_comun_servicios);
        dependencia = requireActivity().findViewById(R.id.dependencia_servicios);
        modelo = requireActivity().findViewById(R.id.modelo_servicios);
        marca = requireActivity().findViewById(R.id.marca_servicios);
        ns = requireActivity().findViewById(R.id.ns_servicios);
        color = requireActivity().findViewById(R.id.color_servicios);
        servicio = requireActivity().findViewById(R.id.trabajo_servicio);


        Bundle extras = getArguments();

        if (extras == null)
        {
        }
        else
        {
            String code = extras.getString("codigo");
            Toast.makeText(requireActivity(), code, Toast.LENGTH_SHORT).show();
        }


        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!id.getText().toString().equals(""))
                {
                     presenter.callEquipobyId(id.getText().toString());
                }
                else
                {
                    Toast.makeText(requireContext(), "¡Ingrese Código!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!id.getText().toString().equals("") && !nombre.getText().toString().equals("") && !dependencia.getText().toString().equals("") && !modelo.getText().toString().equals("") && !marca.getText().toString().equals("") &&
                        !ns.getText().toString().equals("") && !color.getText().toString().equals("") && !servicio.getText().toString().equals(""))
                {
                    presenter.guardarDatos(nombre.getText().toString(),dependencia.getText().toString(),modelo.getText().toString(),marca.getText().toString(),
                            ns.getText().toString(),color.getText().toString(),servicio.getText().toString());
                }
                else
                {
                    Toast.makeText(requireContext(), "¡No Dejar Campos Vacios!", Toast.LENGTH_SHORT).show();
                }
            }


        });

    }


    @Override
    public void showBar()
    {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBar()
    {
        pb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSuccess(String message)
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
    public void onFailure(String message)
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
    public void onError(String Message)
    {
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast, requireActivity().findViewById(R.id.layout_toast));

        TextView txt_mensaje = layout.findViewById(R.id.txt_mensaje);

        txt_mensaje.setText(Message);

        Toast toast = new Toast(requireActivity());

        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,0,0);

        toast.setDuration(Toast.LENGTH_SHORT);

        toast.setView(layout);

        toast.show();
    }

    @Override
    public void getResult(String nom, String dep, String mod, String mar, String sn, String col) {
        nombre.setText(nom);
        dependencia.setText(dep);
        modelo.setText(mod);
        marca.setText(mar);
        ns.setText(sn);
        color.setText(col);
    }

    @Override
    public void cleanContainers()
    {
        id.setText("");
        nombre.setText("");
        dependencia.setText("");
        modelo.setText("");
        marca.setText("");
        ns.setText("");
        color.setText("");
        servicio.setText("");
    }
}