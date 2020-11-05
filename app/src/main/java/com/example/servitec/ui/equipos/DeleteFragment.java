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
import com.example.servitec.clases.responseEquipos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteFragment extends Fragment {

    private ProgressBar pb;

    private Button btn_eliminar;

    private EditText tv_codigo;

    public DeleteFragment() {
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
        return inflater.inflate(R.layout.fragment_delete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pb = requireActivity().findViewById(R.id.pb_delete);

        btn_eliminar = requireActivity().findViewById(R.id.btn_delete);

        tv_codigo = requireActivity().findViewById(R.id.txt_code_delete);

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tv_codigo.getText().toString().equals(""))
                {
                    eliminarEquipo(tv_codigo.getText().toString());
                }
                else
                {
                    showToast("¡No deja Campos Vacios!");
                }
            }
        });

    }

    private void eliminarEquipo(String id)
    {
        pb.setVisibility(View.VISIBLE);
        final  String url = "https://tecdies.com.mx/TECDIES_ANDROID/";
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson) )
                .build();

        serviciosTec service = retrofit.create(serviciosTec.class);

        Call<String> response = service.eliminarEquipo(id);

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body().equals("1"))
                {
                    try
                    {
                        showToast("¡Equipo Eliminado Correctamente!");
                        pb.setVisibility(View.INVISIBLE);
                        cleanContainer();
                    }catch (Exception e)
                    {
                        showToast("Error en el EndPoint");
                        pb.setVisibility(View.INVISIBLE);
                        cleanContainer();
                    }
                }
                else
                {
                    showToast("¡No Existe Equipo!");
                    pb.setVisibility(View.INVISIBLE);
                    cleanContainer();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showToast(t.toString());
                pb.setVisibility(View.INVISIBLE);
                cleanContainer();
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

    private void cleanContainer()
    {
        tv_codigo.setText("");
    }
}