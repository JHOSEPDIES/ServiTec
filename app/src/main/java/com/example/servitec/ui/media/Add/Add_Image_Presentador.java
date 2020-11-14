package com.example.servitec.ui.media.Add;

import android.view.View;

import com.example.servitec.API.RetroClient;
import com.example.servitec.clases.Modelos.POJORespuesta;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Image_Presentador {

    private  Add_Image_View view;

    public Add_Image_Presentador(Add_Image_View view) {
        this.view = view;
    }

    void subirImagen(String encodedImage)
    {
        Call<POJORespuesta> call = RetroClient.getInstance().getApi().uploadImage(encodedImage);

        call.enqueue(new Callback<POJORespuesta>() {
            @Override
            public void onResponse(Call<POJORespuesta> call, Response<POJORespuesta> response) {

                try
                {
                    if (response.isSuccessful() && response.code() == 200 && response.body().estado() == Boolean.TRUE)
                    {
                        view.hideBar();
                        view.onSuccess(response.body().respuesta());
                        view.enable(true);
                    }
                    else
                    {
                        view.hideBar();
                        view.onError(response.body().respuesta());
                        view.enable(true);
                    }
                }
                catch (Exception e)
                {
                    view.hideBar();
                    view.onError("Error al Subir la Imagen");
                    view.enable(true);
                }
            }

            @Override
            public void onFailure(Call<POJORespuesta> call, Throwable t)
            {
                view.hideBar();
                view.onFailure(t.toString());
                view.enable(true);
            }
        });
    }
}
