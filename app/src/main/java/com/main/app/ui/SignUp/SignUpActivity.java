package com.main.app.ui.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.main.app.R;
import com.main.app.callbacks.IActionCallback;
import com.main.app.data.dtos.SignUpRequestDto;
import com.main.app.data.models.User;
import com.main.app.databinding.ActivitySignUpBinding;
import com.main.app.repositories.implementations.AuthRepository;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding activitySignUpBinding;
    private AuthRepository authRepository = AuthRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(this.activitySignUpBinding.getRoot());
        init();
    }

    void init() {
        this.activitySignUpBinding.btnRegister.setOnClickListener(view -> {
            String name = this.activitySignUpBinding.etUsername.getText().toString();
            String email = this.activitySignUpBinding.etEmail.getText().toString();
            String password = this.activitySignUpBinding.etPassword.getText().toString();

            SignUpRequestDto payload = new SignUpRequestDto(name, email, password);

            this.authRepository.signUp(payload, new IActionCallback<User>() {
                @Override
                public void onSuccess(User user) {
                    IActionCallback.super.onSuccess();
                    Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}