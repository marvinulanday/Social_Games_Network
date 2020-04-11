package com.stucom.socialgamesnetwork.data;

import android.content.Context;

import com.stucom.socialgamesnetwork.DAO.DAO;
import com.stucom.socialgamesnetwork.exceptions.SGNException;
import com.stucom.socialgamesnetwork.model.User;
import com.stucom.socialgamesnetwork.ui.login.MyCallback;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public void login(Context context, MyCallback callback, String username, String password) {
        DAO dao = new DAO();
        dao.getUser(context, callback, username, password);
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
