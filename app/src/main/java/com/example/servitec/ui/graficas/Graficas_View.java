package com.example.servitec.ui.graficas;

public interface Graficas_View {

    void showBar();

    void hideBar();

    void onErrorLoad(String message);

    void onFail(String message);

    void getValues(int total);
}
