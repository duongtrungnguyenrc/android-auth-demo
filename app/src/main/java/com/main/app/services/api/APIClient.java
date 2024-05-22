package com.main.app.services.api;


import android.content.Context;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.app.utils.SecureTokenStorageUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.Interceptor;

public class APIClient {
    private final String BASE_URL = "http://192.168.1.12:3000/api/";
    private static APIClient instance;

    private APIClient() {}

    public  static APIClient getInstance() {
        if(instance == null) instance = new APIClient();
        return instance;
    }

    public Retrofit getClient(Context context) {

        SecureTokenStorageUtil tokenStorage;
        try {
            tokenStorage =  SecureTokenStorageUtil.getInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .writeTimeout(0, TimeUnit.MILLISECONDS)
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new AuthInterceptor(tokenStorage));

        OkHttpClient client = clientBuilder.build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }
}
