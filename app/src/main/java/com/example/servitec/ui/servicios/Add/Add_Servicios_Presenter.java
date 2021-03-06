package com.example.servitec.ui.servicios.Add;



import com.example.servitec.API.RetroClient;
import com.example.servitec.clases.Modelos.POJORespuesta;
import com.example.servitec.clases.Modelos.POJOServicios;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Boolean.TRUE;

public class Add_Servicios_Presenter {

    Add_Servicios_View view;

    public Add_Servicios_Presenter(Add_Servicios_View view) {
        this.view = view;
    }

     void guardarDatos(String nom, String dep, String mod, String mar, String ns, String col, String ser)
    {
        view.showBar();

        Call<POJORespuesta> response = RetroClient.getInstance().getApi().guardarServicio(nom,dep,mod,mar,ns,col,ser);

        response.enqueue(new Callback<POJORespuesta>() {
            @Override
            public void onResponse(Call<POJORespuesta> call, Response<POJORespuesta> response)
            {
                try
                {
                    if (response.isSuccessful() && response.code() == 200 && response.body().estado() == TRUE)
                    {
                        view.hideBar();
                        view.onSuccess(response.body().respuesta());
                        view.cleanContainers();
                    }
                    else
                    {
                        view.hideBar();
                        view.onError(response.body().respuesta());
                        view.cleanContainers();
                    }
                }catch (Exception e)
                {
                    view.hideBar();
                    view.onError(e.toString());
                    view.cleanContainers();
                }

            }
            @Override
            public void onFailure(Call<POJORespuesta> call, Throwable t)
            {
                view.hideBar();
                view.onFailure(t.toString());
                view.cleanContainers();
            }
        });

    }

    void callEquipobyId(String id)
    {
        view.showBar();

        Call<List<POJOServicios>> response = RetroClient.getInstance().getApi().getequipobyIdServicio(id);

        response.enqueue(new Callback<List<POJOServicios>>() {
            @Override
            public void onResponse(Call<List<POJOServicios>> call, Response<List<POJOServicios>> response) {
                try
                {
                    if (response.isSuccessful() && response.code() == 200)
                    {
                        view.hideBar();

                        for (POJOServicios elemento : response.body()) {
                            view.getResult(elemento.getNombre(),elemento.getDependencia(),
                                    elemento.getModelo(), elemento.getMarca(), elemento.getSn(),
                                    elemento.getColor()
                                    );
                        }
                    }
                    else
                    {
                        view.hideBar();
                        view.onError("no Existe el Equipo");
                    }

                }catch (Exception e)
                {
                    view.hideBar();
                    view.onError("no Existe el Equipo");
                }
            }

            @Override
            public void onFailure(Call<List<POJOServicios>> call, Throwable t)
            {
                view.hideBar();
                view.onFailure(t.toString());
            }
        });


    }


}
