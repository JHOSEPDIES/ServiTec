package com.example.servitec.Interfaces;

import com.example.servitec.clases.POJOEquipos;
import com.example.servitec.clases.POJORespuesta;
import com.example.servitec.clases.POJOServicios;
import com.example.servitec.clases.POJOServiciosGet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface serviciosTec {

    @GET("buscar_Equipos.php")
    Call<List<POJOEquipos>> getequipos();

    @FormUrlEncoded
    @POST("buscar_componentes.php")
    Call<List<POJOEquipos>> getequipobyId(@Field("codigo") String codigo);

    @FormUrlEncoded
    @POST("buscar_componentes.php")
    Call<List<POJOServicios>> getequipobyIdServicio(@Field("codigo") String codigo);


    @FormUrlEncoded
    @POST("guardar_componente.php")
    Call<POJORespuesta> guardarEquipo(
            @Field("nombre_comun") String nombre, @Field("dependencia")String dependencia,
            @Field("modelo") String modelo, @Field("marca") String Marca, @Field("ns") String ns,
            @Field("color") String color,@Field("estado") String estado,@Field("notas") String notas
    );

    @FormUrlEncoded
    @POST("eliminar_componente.php")
    Call<POJORespuesta> eliminarEquipo(@Field("codigo") String codigo);


    @FormUrlEncoded
    @POST("guardar_servicio.php")
    Call<POJORespuesta> guardarServicio(
            @Field("nombre_comun") String nombre, @Field("dependencia")String dependencia,
            @Field("modelo") String modelo, @Field("marca") String Marca, @Field("ns") String ns,@Field("color") String color,
            @Field("servicio") String servicio
    );

    @GET("buscar_servicios.php")
    Call<List<POJOServiciosGet>> getservicios();
}
