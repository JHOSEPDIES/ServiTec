package com.example.servitec.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final String BASE_URL="https://tecdies.com.mx/TECDIES_ANDROID/";

    private static RetroClient myClient;
    private Retrofit retrofit;

    //Este se usara en caso de no gregresar una respuesta de tipo String
    private RetroClient(){
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetroClient getInstance(){
        if (myClient==null){
            myClient=new RetroClient();
        }
        return myClient;
    }

    public serviciosTec getApi()
    {
        return retrofit.create(serviciosTec.class);
    }


}
