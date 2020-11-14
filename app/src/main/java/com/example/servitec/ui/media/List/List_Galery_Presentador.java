package com.example.servitec.ui.media.List;

import android.view.View;

import com.example.servitec.API.RetroClient;
import com.example.servitec.clases.Modelos.POJOMedia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class List_Galery_Presentador {

    List_Galery_View view;

    public List_Galery_Presentador(List_Galery_View view) {
        this.view = view;
    }

     void callMedia() {

        view.showBar();

        Call<List<POJOMedia>> response = RetroClient.getInstance().getApi().getmedia();

        response.enqueue(new Callback<List<POJOMedia>>() {
            @Override
            public void onResponse(Call<List<POJOMedia>> call, Response<List<POJOMedia>> response) {

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

            @Override
            public void onFailure(Call<List<POJOMedia>> call, Throwable t) {
                view.hideBar();
                view.onErrorLoad(t.toString());
            }
        });

    }

}
