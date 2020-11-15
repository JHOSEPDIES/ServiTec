package com.example.servitec.ui.equipos.Edit;

import com.example.servitec.clases.Modelos.POJOEquipos;

import java.util.List;

public interface Edit_Equipo_View {

    void showBar();
    void hideBar();
    void onSuccess(String message);
    void onFail(String message);
    void onError(String Message);
    void cleanContainers();
    void getValues(String nombre,String dependencia,String modelo,String marca, String ns,String color,String estado, String notas);

}
