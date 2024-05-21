package com.main.app.services.auth;

import com.main.app.data.models.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IUserService {
    @GET("/user/profile")
    Call<User> getUserProfiile();
}
