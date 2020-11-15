package com.example.servitec.ui.equipos.Edit;

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
import com.example.servitec.clases.Modelos.POJOEquipos;
import com.example.servitec.API.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Equipo_Fragment extends Fragment implements Edit_Equipo_View{

    private ProgressBar pb;

    ArrayList<POJOEquipos> equipos = new ArrayList<>();

    private Button btn_buscar;

    private EditText codigo,nombre,dependencia,modelo,marca,ns,color,estado,notas;

    Edit_Equipo_Presenter presenter;

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

        presenter = new Edit_Equipo_Presenter(this);

        init();

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!codigo.getText().toString().equals(""))
                {
                    presenter.callEquipobyId(codigo.getText().toString());
                }
                else
                {
                    Toast.makeText(requireActivity(), "!No dejar Campos Vacios¡", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void init()
    {
        codigo = requireActivity().findViewById(R.id.txt_code_edit);
        nombre = requireActivity().findViewById(R.id.nombre_comun_editar);
        dependencia = requireActivity().findViewById(R.id.dependencia_editar);
        modelo = requireActivity().findViewById(R.id.modelo_editar);
        marca = requireActivity().findViewById(R.id.marca_editar);
        ns= requireActivity().findViewById(R.id.ns_editar);
        color = requireActivity().findViewById(R.id.color_editar);
        estado = requireActivity().findViewById(R.id.estado_editar);
        notas = requireActivity().findViewById(R.id.notas_editar);
    }

    private void showToast(String message)
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
        showToast(message);
    }

    @Override
    public void onFail(String message)
    {
        showToast(message);
    }

    @Override
    public void onError(String Message)
    {
        showToast(Message);
    }

    @Override
    public void cleanContainers()
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

    @Override
    public void getValues(String nom, String dep, String mod, String mar, String sn, String col, String est, String not) {
        nombre.setText(nom);
        dependencia.setText(dep);
        modelo.setText(mod);
        marca.setText(mar);
        ns.setText(sn);
        estado.setText(est);
        color.setText(col);
        notas.setText(not);
    }
}