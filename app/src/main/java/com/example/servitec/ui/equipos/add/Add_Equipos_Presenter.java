package com.example.servitec.ui.equipos.add;

import android.view.View;

import com.example.servitec.API.RetroClient;
import com.example.servitec.clases.Modelos.POJORespuesta;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Boolean.TRUE;

public class Add_Equipos_Presenter {

    private Add_Equipos_View view;

    public Add_Equipos_Presenter(Add_Equipos_View view) {
        this.view = view;
    }

    void guardarDatos(String nom,String dep,String mod,String mar, String ns,String col,String edo,String not)
    {
        view.showBar();

        Call<POJORespuesta> response = RetroClient.getInstance().getApi().guardarEquipo(nom,dep,mod,mar, ns,col,edo,not);

        response.enqueue(new Callback<POJORespuesta>() {
            @Override
            public void onResponse(Call<POJORespuesta> call, Response<POJORespuesta> response) {

                if (response.isSuccessful() && response.code() == 200 && response.body().estado() == TRUE)
                {
                    view.hideBar();
                    view.onSuccess(response.body().respuesta());
                    view.cleanContainers();
                }
                else
                {
                    view.hideBar();
                    view.onFailure(response.body().respuesta());
                    view.cleanContainers();
                }
            }
            @Override
            public void onFailure(Call<POJORespuesta> call, Throwable t) {
                view.hideBar();
                view.onError(t.toString());
                view.cleanContainers();
            }
        });
    }



}
