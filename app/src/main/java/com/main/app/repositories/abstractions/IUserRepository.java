package com.main.app.repositories.abstractions;

import com.main.app.callbacks.IActionCallback;
import com.main.app.data.models.User;

public interface IUserRepository {
    void loadProfile(IActionCallback<User> action);
}
