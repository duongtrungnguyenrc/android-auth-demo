package com.main.app.services.auth;

import com.main.app.data.dtos.SignInRequestDto;
import com.main.app.data.dtos.SignInResponseDto;
import com.main.app.data.dtos.SignUpRequestDto;
import com.main.app.data.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthService {
    @POST("auth/sign-in")
    Call<SignInResponseDto> signIn(@Body SignInRequestDto payload);

    @POST("auth/sign-up")
    Call<User> signUp(@Body SignUpRequestDto payload);

    @POST("auth/sign-out")
    Call<String> signOut();
}
