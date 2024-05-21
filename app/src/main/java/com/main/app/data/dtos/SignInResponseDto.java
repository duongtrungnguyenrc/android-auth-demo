package com.main.app.data.dtos;

import com.main.app.data.models.User;

public class SignInResponseDto {
    private String accessToken;
    private String refreshToken;
    User user;

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }
}
