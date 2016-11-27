package com.egorpetruchcho.loriandroid.state;


import com.egorpetruchcho.loriandroid_api.model.User;

public class AuthState {
    private static AuthState instance;

    public static AuthState getInstance() {
        if (instance == null) {
            instance = new AuthState();
        }
        return instance;
    }

    private User currentUser;

    private AuthState() {
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
