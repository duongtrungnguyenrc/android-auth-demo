package com.main.app.repositories.implementations;

import android.content.Context;

import com.main.app.callbacks.IActionCallback;
import com.main.app.data.models.User;
import com.main.app.repositories.abstractions.IUserRepository;
import com.main.app.services.api.APIClient;
import com.main.app.services.user.IUserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository implements IUserRepository {
    private final IUserService userService;
    private static UserRepository instance;

    private UserRepository(Context context){
        APIClient apiClient = APIClient.getInstance();
        this.userService = apiClient.getClient(context).create(IUserService.class);
    }

    public static UserRepository getInstance(Context context) {
        if(instance == null)
            instance = new UserRepository(context);
        return instance;
    }
    @Override
    public void loadProfile(IActionCallback<User> action) {
        Call<User> call = this.userService.getUserProfiile();

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
}
