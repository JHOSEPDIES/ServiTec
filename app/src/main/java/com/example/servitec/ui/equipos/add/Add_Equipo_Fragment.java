package com.example.servitec.ui.equipos.add;

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
import com.example.servitec.API.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Boolean.TRUE;

public class Add_Equipo_Fragment extends Fragment implements Add_Equipos_View{

    private ProgressBar pb;

    private Button guardar;

    private EditText nombre,dependencia,modelo,marca,ns,color,estado,notas;

    Add_Equipos_Presenter presenter;

    public Add_Equipo_Fragment() {
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


        presenter = new Add_Equipos_Presenter(this);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nombre.getText().toString().equals("") && !dependencia.getText().toString().equals("") && !modelo.getText().toString().equals("") && !marca.getText().toString().equals("") &&
                    !ns.getText().toString().equals("") && !estado.getText().toString().equals("") && !color.getText().toString().equals("") && !notas.getText().toString().equals(""))
                {
                    presenter.guardarDatos(nombre.getText().toString(),dependencia.getText().toString(),modelo.getText().toString(),marca.getText().toString(),
                            ns.getText().toString(),estado.getText().toString(),color.getText().toString(),notas.getText().toString());
                }
                else
                {
                    Toast.makeText(requireActivity(), "¡No Dejar Campos Vacios!", Toast.LENGTH_SHORT).show();
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
    public void cleanContainers() {
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
