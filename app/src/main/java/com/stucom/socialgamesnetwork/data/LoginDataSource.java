package com.stucom.socialgamesnetwork.data;

import com.stucom.socialgamesnetwork.DAO.DAO;
import com.stucom.socialgamesnetwork.data.model.User;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<User> login(String username, String password) {

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

    public void logout() {
        // TODO: revoke authentication
    }
}
