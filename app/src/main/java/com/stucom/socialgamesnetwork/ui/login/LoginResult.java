package com.stucom.socialgamesnetwork.ui.login;

import androidx.annotation.Nullable;

import com.stucom.socialgamesnetwork.model.User;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private User user;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable User user) {
        this.user = user;
    }

    @Nullable
    User getUser() {
        return user;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
