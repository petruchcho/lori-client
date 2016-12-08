package com.egorpetruchcho.loriandroid.state;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.egorpetruchcho.loriandroid.core.LoriApplication;

public class ApplicationSavedState {

    private static final String PREFERENCES_NAME = "Lori.ApplicationSavedState";

    private static final String AUTH_KEY = "auth.key";
    private static final String USERNAME = "username";

    private static ApplicationSavedState instance;

    public static ApplicationSavedState getInstance() {
        if (instance == null) {
            instance = new ApplicationSavedState();
        }
        return instance;
    }

    private SharedPreferences sharedPreferences;

    private ApplicationSavedState() {
        sharedPreferences = LoriApplication.getInstance().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Nullable
    private SharedPreferences getPreferences() {
        return sharedPreferences;
    }

    @Nullable
    public String getAuthKey() {
        SharedPreferences preferences = getPreferences();
        if (preferences != null) {
            return preferences.getString(AUTH_KEY, null);
        }
        return null;
    }

    public void setAuthKey(@Nullable String authKey) {
        SharedPreferences preferences = getPreferences();
        if (preferences != null) {
            preferences.edit().putString(AUTH_KEY, authKey).apply();
        }
    }

    @Nullable
    public String getUsername() {
        SharedPreferences preferences = getPreferences();
        if (preferences != null) {
            return preferences.getString(USERNAME, null);
        }
        return null;
    }

    public void setUsername(@Nullable String username) {
        SharedPreferences preferences = getPreferences();
        if (preferences != null) {
            preferences.edit().putString(USERNAME, username).apply();
        }
    }
}

