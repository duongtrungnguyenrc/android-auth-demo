package com.main.app.services.api;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class APIClient {
    private final String BASE_URL = "http://192.168.31.213:3000/api/";
    private static APIClient instance;

    private APIClient() {}

    private Interceptor interceptor;

    public  static APIClient getInstance() {
        if(instance == null) instance = new APIClient();
        return instance;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .writeTimeout(0, TimeUnit.MILLISECONDS)
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor);

        if(this.interceptor != null) {
            clientBuilder.addInterceptor(this.interceptor);
        }

        OkHttpClient client = clientBuilder.build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
