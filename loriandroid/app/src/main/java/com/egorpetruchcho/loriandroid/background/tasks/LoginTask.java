package com.egorpetruchcho.loriandroid.background.tasks;


import com.egorpetruchcho.loriandroid.background.BackgroundTask;
import com.egorpetruchcho.loriandroid_api.LoriApi;
import com.egorpetruchcho.loriandroid_api.model.Locale;

public class LoginTask extends BackgroundTask<String> {

    private final String username;
    private final String password;
    private final Locale locale;

    public LoginTask(String username, String password, Locale locale) {
        super(String.class);
        this.username = username;
        this.password = password;
        this.locale = locale;
    }

    @Override
    protected String execute() throws Exception {
        return LoriApi.getInstance().login(username, password, locale);
    }
}
