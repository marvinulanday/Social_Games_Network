package com.stucom.socialgamesnetwork.callbacks;

import com.stucom.socialgamesnetwork.model.User;

import java.io.Serializable;

public interface CallbackUpdateUser extends Serializable {
    void UpdateUserByEmail(User user, String oldPassword, String newPassword, String passwordConfirm);
}
