package com.example.servitec.ui.equipos.add;

public interface Add_Equipos_View {

    void showBar();
    void hideBar();
    void onSuccess(String message);
    void onFailure(String message);
    void onError(String Message);
    void cleanContainers();


}
