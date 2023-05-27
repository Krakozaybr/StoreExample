package com.krak.storeexample.utils.api;

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;
import com.github.leonardoxh.livedatacalladapter.LiveDataResponseBodyConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper {
    private static APIHelper instance = null;
    private static final String BASE_URL = "https://medic.madskill.ru/";
    @Getter
    private API api;

    private APIHelper(){
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .build();

        api = retrofit.create(API.class);
    }

    public static APIHelper getInstance(){
        if (instance == null){
            instance = new APIHelper();
        }
        return instance;
    }

    public API getApi() {
        return api;
    }
}
