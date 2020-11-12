package com.example.servitec.ui.servicios.Add;

public interface Add_Servicios_View {

    void showBar();
    void hideBar();
    void onSuccess(String message);
    void onFailure(String message);
    void onError(String Message);
    void cleanContainers();
}
