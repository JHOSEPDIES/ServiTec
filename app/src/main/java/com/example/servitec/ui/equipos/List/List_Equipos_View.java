package com.example.servitec.ui.equipos.List;

import com.example.servitec.clases.Modelos.POJOEquipos;

import java.util.List;

public interface List_Equipos_View {

    void showBar();

    void hideBar();

    void onErrorLoad(String Message);

    void onGetResult(List<POJOEquipos> equipos);


}
