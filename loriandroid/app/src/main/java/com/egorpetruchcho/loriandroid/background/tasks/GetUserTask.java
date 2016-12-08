package com.egorpetruchcho.loriandroid.background.tasks;


import com.egorpetruchcho.loriandroid.background.BackgroundTask;
import com.egorpetruchcho.loriandroid.state.ApplicationSavedState;
import com.egorpetruchcho.loriandroid_api.LoriApi;
import com.egorpetruchcho.loriandroid_api.exceptions.NotAuthorizedException;
import com.egorpetruchcho.loriandroid_api.model.User;

public class GetUserTask extends BackgroundTask<User> {

    private final String username;

    public GetUserTask(String username) {
        super(User.class);
        this.username = username;
    }

    @Override
    protected User execute() throws Exception {
        String sessionToken = ApplicationSavedState.getInstance().getAuthKey();
        if (sessionToken == null) {
            throw new NotAuthorizedException();
        }
        return LoriApi.getInstance().getUser(username, sessionToken);
    }
}
