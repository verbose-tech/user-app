package com.userticketingsystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://52.42.47.34:8000";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if( retrofit == null ){
            Gson gson = new GsonBuilder().serializeNulls().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
