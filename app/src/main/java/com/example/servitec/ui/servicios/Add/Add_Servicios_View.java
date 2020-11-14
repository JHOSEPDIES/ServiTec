package com.example.servitec.ui.servicios.Add;

public interface Add_Servicios_View {

    void showBar();
    void hideBar();
    void onSuccess(String message);
    void onFailure(String message);
    void onError(String Message);

    void getResult(String nom, String dep, String mod, String mar, String sn, String col);

    void cleanContainers();
}
