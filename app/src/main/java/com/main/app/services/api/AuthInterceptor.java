package com.main.app.services.api;


import android.app.Application;

import com.main.app.utils.SecureTokenStorageUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final SecureTokenStorageUtil secureTokenStorageUtil;

    public AuthInterceptor(SecureTokenStorageUtil secureTokenStorageUtil) {
        this.secureTokenStorageUtil = secureTokenStorageUtil;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        String accessToken = null;
        try {
            accessToken = this.secureTokenStorageUtil.getAccessToken();
            Request newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            return chain.proceed(newRequest);
        } catch (Exception e) {
            return chain.proceed(originalRequest);
        }
    }
}

