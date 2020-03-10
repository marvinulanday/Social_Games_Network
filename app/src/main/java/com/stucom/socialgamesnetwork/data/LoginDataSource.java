package com.stucom.socialgamesnetwork.data;

import android.content.Context;

import com.stucom.socialgamesnetwork.DAO.DAO;
import com.stucom.socialgamesnetwork.data.model.User;
import com.stucom.socialgamesnetwork.exceptions.SGNException;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<User> login(Context context, String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            DAO dao = new DAO();
            User user = new User("Marvin");
            //dao.getUser(username, password);
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<User> loginResult(User user) {
        if (user == null)
            return new Result.Error(new SGNException(SGNException.USER_NOT_FOUND));
        return new Result.Success<>(user);
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
