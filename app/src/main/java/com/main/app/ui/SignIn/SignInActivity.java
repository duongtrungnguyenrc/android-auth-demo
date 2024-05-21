package com.main.app.ui.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.main.app.callbacks.IActionCallback;
import com.main.app.data.dtos.SignInRequestDto;
import com.main.app.data.dtos.SignInResponseDto;
import com.main.app.databinding.ActivitySignInBinding;
import com.main.app.repositories.implementations.AuthRepository;
import com.main.app.ui.Main.MainActivity;
import com.main.app.ui.SignUp.SignUpActivity;
import com.main.app.utils.SecureTokenStorageUtil;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding activitySignInBinding;
    private final AuthRepository authRepository = AuthRepository.getInstance();

    private SecureTokenStorageUtil secureTokenStorageUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(activitySignInBinding.getRoot());
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws Exception {
        this.secureTokenStorageUtil = SecureTokenStorageUtil.getInstance(getApplicationContext());

        this.activitySignInBinding.txtToRegister.setOnClickListener((View view) -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        this.activitySignInBinding.btnLogin.setOnClickListener((View view) -> {
            authRepository.signIn(
                    new SignInRequestDto(
                            activitySignInBinding.etUsername.getText().toString(),
                            activitySignInBinding.etPassword.getText().toString()
                    ), new IActionCallback<SignInResponseDto>() {
                        @Override
                        public void onSuccess(SignInResponseDto response) {
                            activitySignInBinding.etUsername.setText("");
                            activitySignInBinding.etPassword.setText("");
                            try {
                                secureTokenStorageUtil.storeTokens(response.getAccessToken(), response.getRefreshToken());
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                            } catch (Exception exception) {

                            }

                        }

                        @Override
                        public void onFailure(String message) {
                            Log.d("errr", message);
                            Toast.makeText(SignInActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    });
        });

    }
}