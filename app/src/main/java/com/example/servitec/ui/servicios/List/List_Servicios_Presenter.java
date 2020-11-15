package com.example.servitec.ui.servicios.List;

import android.view.View;

import com.example.servitec.API.RetroClient;
import com.example.servitec.clases.Modelos.POJOServiciosGet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class List_Servicios_Presenter {

    private List_Servicios_View view;

    public List_Servicios_Presenter(List_Servicios_View view) {
        this.view = view;
    }

     void getData()
    {
        view.showBar();

        Call<List<POJOServiciosGet>> response = RetroClient.getInstance().getApi().getservicios();

        response.enqueue(new Callback<List<POJOServiciosGet>>() {
            @Override
            public void onResponse(Call<List<POJOServiciosGet>> call, Response<List<POJOServiciosGet>> response) {
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

                }
                catch (Exception e)
                {
                    view.hideBar();
                    view.onErrorLoad(e.toString());
                }
            }

            @Override
            public void onFailure(Call<List<POJOServiciosGet>> call, Throwable t) {
                view.hideBar();
                view.onErrorLoad(t.toString());
            }
        });
    }


}
