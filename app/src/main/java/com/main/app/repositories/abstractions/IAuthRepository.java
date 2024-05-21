package com.main.app.repositories.abstractions;

import com.main.app.callbacks.IActionCallback;
import com.main.app.data.dtos.SignInRequestDto;
import com.main.app.data.dtos.SignInResponseDto;
import com.main.app.data.dtos.SignUpRequestDto;
import com.main.app.data.models.User;

public interface IAuthRepository {
    void signIn(SignInRequestDto payload, IActionCallback<SignInResponseDto> action);
    void signUp(SignUpRequestDto payload, IActionCallback<User> action);

    void signOut(IActionCallback<String> action);
}
