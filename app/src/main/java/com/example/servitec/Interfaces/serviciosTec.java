package com.example.servitec.Interfaces;

import com.example.servitec.clases.responseEquipos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface serviciosTec {

    @GET("buscar_Equipos.php")
    Call<List<responseEquipos>> getequipos();
}
