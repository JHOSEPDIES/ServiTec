package com.example.servitec.ui.equipos.List;

import android.view.View;

import com.example.servitec.API.RetroClient;
import com.example.servitec.clases.Modelos.POJOEquipos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class List_Equipos_Presenter {

    List_Equipos_View view;

    public List_Equipos_Presenter(List_Equipos_View view) {
        this.view = view;
    }


    void getData()
    {
        view.showBar();

        Call<List<POJOEquipos>> response = RetroClient.getInstance().getApi().getequipos();

        response.enqueue(new Callback<List<POJOEquipos>>() {
            @Override
            public void onResponse(Call<List<POJOEquipos>> call, Response<List<POJOEquipos>> response)
            {
                try
                {
                    if (response.isSuccessful() && response.code() == 200)
                    {
                        view.hideBar();
                        view.onGetResult(response.body());
                    }
                    else
                    {
                        view.hideBar();
                        view.onErrorLoad("Error en el EndPoint");
                    }

                }catch (Exception e)
                {
                    view.hideBar();
                    view.onErrorLoad(e.toString());
                }

            }

            @Override
            public void onFailure(Call<List<POJOEquipos>> call, Throwable t) {

                view.hideBar();
                view.onErrorLoad(t.toString());
            }
        });

    }
}
