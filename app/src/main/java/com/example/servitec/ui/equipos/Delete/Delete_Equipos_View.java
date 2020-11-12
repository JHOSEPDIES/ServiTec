package com.example.servitec.ui.equipos.Delete;

public interface Delete_Equipos_View {

    void showBar();
    void hideBar();
    void onSuccess(String message);
    void onFail(String message);
    void onError(String Message);
    void cleanContainers();
}
