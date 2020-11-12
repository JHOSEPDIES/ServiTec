package com.example.servitec.ui.equipos.Delete;

import android.view.View;

import com.example.servitec.API.RetroClient;
import com.example.servitec.clases.Modelos.POJORespuesta;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Boolean.TRUE;

public class Delete_Equipo_Presenter {

    private Delete_Equipos_View view;

    public Delete_Equipo_Presenter(Delete_Equipos_View view) {
        this.view = view;
    }

    void eliminarEquipo(String id)
    {
        view.showBar();

        Call<POJORespuesta> response = RetroClient.getInstance().getApi().eliminarEquipo(id);

        response.enqueue(new Callback<POJORespuesta>() {
            @Override
            public void onResponse(Call<POJORespuesta> call, Response<POJORespuesta> response) {

                if (response.isSuccessful() && response.code() == 200 && response.body().estado() == TRUE)
                {
                    try
                    {
                        view.hideBar();
                        view.onSuccess(response.body().respuesta());
                        view.cleanContainers();

                    }catch (Exception e)
                    {
                        view.hideBar();
                        view.onError(response.body().respuesta());
                        view.cleanContainers();
                    }
                }
                else
                {
                    view.hideBar();
                    view.onError(response.body().respuesta());
                    view.cleanContainers();
                }
            }

            @Override
            public void onFailure(Call<POJORespuesta> call, Throwable t) {
                view.hideBar();
                view.onFail(t.toString());
                view.cleanContainers();
            }
        });


    }


}
