package com.example.servitec.ui.graficas;

import android.widget.Toast;

import com.example.servitec.API.RetroClient;
import com.example.servitec.clases.Modelos.POJOGraficas;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Graficas_Presenter {

    Graficas_View view;

    public Graficas_Presenter(Graficas_View view) {
        this.view = view;
    }

     void getChart()
    {
        view.showBar();

        Call<POJOGraficas> call = RetroClient.getInstance().getApi().getData_Services_Chart();
        call.enqueue(new Callback<POJOGraficas>() {
            @Override
            public void onResponse(Call<POJOGraficas> call, Response<POJOGraficas> response) {
                if (response.isSuccessful() && response.code() == 200)
                {
                    view.hideBar();
                    view.getValues(response.body().getServicios());
                }
                else
                {
                    view.hideBar();
                    view.onErrorLoad("Error en el EndPoint");
                }
            }

            @Override
            public void onFailure(Call<POJOGraficas> call, Throwable t)
            {
                view.hideBar();
                view.onFail(t.toString());
            }
        });
    }
}
