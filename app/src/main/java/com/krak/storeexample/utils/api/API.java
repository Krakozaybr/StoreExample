package com.krak.storeexample.utils.api;

import androidx.lifecycle.LiveData;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.krak.storeexample.utils.api.entities.Item;
import com.krak.storeexample.utils.api.entities.TokenResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface API {
    @POST("/api/sendCode")
    Call<Void> sendCode(@Header("email") String email);

    @POST("/api/signin")
    LiveData<Resource<TokenResponse>> signIn(@Header("email") String email, @Header("code") String code);

    @GET("/api/catalog")
    LiveData<Resource<List<Item>>> catalog(@Header("Authorization") String token);
}
