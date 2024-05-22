package com.main.app.repositories.implementations;

import android.content.Context;

import com.main.app.callbacks.IActionCallback;
import com.main.app.data.dtos.SignInRequestDto;
import com.main.app.data.dtos.SignInResponseDto;
import com.main.app.data.dtos.SignUpRequestDto;
import com.main.app.data.models.User;
import com.main.app.repositories.abstractions.IAuthRepository;
import com.main.app.services.api.APIClient;
import com.main.app.services.auth.IAuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository implements IAuthRepository {
    private final IAuthService authService;
    private static AuthRepository instance;

    private AuthRepository(Context context){
        APIClient apiClient = APIClient.getInstance();
        this.authService = apiClient.getClient(context).create(IAuthService.class);
    }

    public static AuthRepository getInstance(Context context) {
        if(instance == null)
            instance = new AuthRepository(context);
        return instance;
    }

    @Override
    public void signIn(SignInRequestDto payload, IActionCallback<SignInResponseDto> action) {
        Call<SignInResponseDto> call = this.authService.signIn(payload);

        call.enqueue(new Callback<SignInResponseDto>() {
            @Override
            public void onResponse(Call<SignInResponseDto> call, Response<SignInResponseDto> response) {
                if(response.isSuccessful()) {
                    action.onSuccess(response.body());
                }
                else {
                    action.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<SignInResponseDto> call, Throwable t) {
                action.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void signUp(SignUpRequestDto payload, IActionCallback<User> action) {
        Call<User> call = this.authService.signUp(payload);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    action.onSuccess(response.body());
                }
                else {
                    action.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                action.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void signOut(IActionCallback<String> action) {
        Call<String> call = this.authService.signOut();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    action.onSuccess(response.body());
                }
                else {
                    action.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                action.onFailure(t.getMessage());
            }
        });
    }
}

