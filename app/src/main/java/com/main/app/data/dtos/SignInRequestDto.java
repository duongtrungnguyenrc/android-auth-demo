package com.main.app.data.dtos;

import android.text.Editable;

import lombok.Data;

@Data
public class SignInRequestDto {
    private String email;
    private String password;

    public SignInRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
