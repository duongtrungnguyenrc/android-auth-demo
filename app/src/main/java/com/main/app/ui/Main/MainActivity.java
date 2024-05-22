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
import com.main.app.utils.SecureTokenStorageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private UserRepository userRepository;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        try {
            init();
            loadProfile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void init() throws Exception {
        userRepository = UserRepository.getInstance(getApplicationContext());
        authRepository = AuthRepository.getInstance(getApplicationContext());
        SecureTokenStorageUtil secureTokenStorageUtil = SecureTokenStorageUtil.getInstance(getApplicationContext());

        this.activityMainBinding.btnSignOut.setOnClickListener(view -> {
            this.authRepository.signOut(new IActionCallback<String>() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    try {
                        secureTokenStorageUtil.storeTokens("", "");
                        finish();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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
                activityMainBinding.txtName.setText(response.name);
                activityMainBinding.txtEmail.setText(response.email);
                activityMainBinding.txtRole.setText(response.role);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}