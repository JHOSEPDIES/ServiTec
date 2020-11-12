package com.example.servitec.ui.servicios.List;

import com.example.servitec.clases.Modelos.POJOServiciosGet;

import java.util.List;

public interface List_Servicios_View {

    void showBar();

    void hideBar();

    void onErrorLoad(String Message);

    void onGetResult(List<POJOServiciosGet> servicios);



}
