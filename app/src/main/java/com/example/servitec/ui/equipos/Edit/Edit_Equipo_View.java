package com.example.servitec.ui.equipos.Edit;

public interface Edit_Equipo_View {

    void showBar();
    void hideBar();
    void onSuccess(String message);
    void onFail(String message);
    void onError(String Message);
    void cleanContainers();

}
