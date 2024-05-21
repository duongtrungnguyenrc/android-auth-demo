package com.main.app.ui.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.main.app.R;
import com.main.app.callbacks.IActionCallback;
import com.main.app.data.dtos.SignInRequestDto;
import com.main.app.data.dtos.SignInResponseDto;
import com.main.app.data.models.User;
import com.main.app.databinding.ActivityMainBinding;
import com.main.app.repositories.implementations.AuthRepository;
import com.main.app.repositories.implementations.UserRepository;
import com.main.app.ui.SignIn.SignInActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private UserRepository userRepository = UserRepository.getInstance();
    private AuthRepository authRepository = AuthRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        init();
        loadProfile();
    }

    void init() {
        this.activityMainBinding.btnSignOut.setOnClickListener(view -> {
            this.authRepository.signOut(new IActionCallback<String>() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    void loadProfile() {
        userRepository.loadProfile(new IActionCallback<User>() {
            @Override
            public void onSuccess(User response) {
                activityMainBinding.txtEmail.setText(response.name);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}