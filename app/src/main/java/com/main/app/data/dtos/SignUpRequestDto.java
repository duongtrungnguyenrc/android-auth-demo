package com.main.app.data.dtos;

import lombok.Data;

@Data
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;

    public SignUpRequestDto(String name, String email, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
