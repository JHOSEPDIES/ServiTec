package com.example.servitec.ui.media.Add;

public interface Add_Image_View {

    void showBar();
    void hideBar();
    void onSuccess(String message);
    void onFailure(String message);
    void onError(String Message);
    void enable(Boolean on);
}
