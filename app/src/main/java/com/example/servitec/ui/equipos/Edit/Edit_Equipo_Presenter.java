package com.example.servitec.ui.equipos.Edit;

import android.view.View;
import android.widget.EditText;

import com.example.servitec.API.RetroClient;
import com.example.servitec.clases.Modelos.POJOEquipos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Equipo_Presenter {

    private EditText codigo,nombre,dependencia,modelo,marca,ns,color,estado,notas;

    private Edit_Equipo_View view;

    public Edit_Equipo_Presenter(Edit_Equipo_View view) {
        this.view = view;

    }

      void callEquipobyId(String id)
    {
        view.showBar();

        Call<List<POJOEquipos>> response = RetroClient.getInstance().getApi().getequipobyId(id);


        response.enqueue(new Callback<List<POJOEquipos>>() {
            @Override
            public void onResponse(Call<List<POJOEquipos>> call, Response<List<POJOEquipos>> response)
            {
                try
                {
                    if (response.isSuccessful() && response.code() == 200 && response.body() != null)
                    {
                        view.hideBar();
                        for (POJOEquipos elemento: response.body())
                        {
                        view.getValues(elemento.getNombre(),elemento.getDependencia(),
                                elemento.getModelo(), elemento.getMarca(), elemento.getSn(),
                                elemento.getColor(),elemento.getEstado(),elemento.getNotas());
                        }
                    }
                    else
                    {
                        view.onFail("Error en el End Point");
                    }

                }catch (Exception e)
                {
                    view.hideBar();
                    view.onError(e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<POJOEquipos>> call, Throwable t) {
                view.hideBar();
                view.onError(t.toString());
                view.cleanContainers();
            }
        });

    }

}
