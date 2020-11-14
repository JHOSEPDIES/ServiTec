package com.example.servitec.ui.media.List;

import com.example.servitec.clases.Modelos.POJOMedia;

import java.util.List;

public interface List_Galery_View {

    void showBar();

    void hideBar();

    void onErrorLoad(String Message);

    void onGetResult(List<POJOMedia> medias);
}
