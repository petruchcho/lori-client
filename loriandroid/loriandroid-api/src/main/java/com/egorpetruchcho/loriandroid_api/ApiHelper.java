package com.egorpetruchcho.loriandroid_api;


import com.egorpetruchcho.loriandroid_api.exceptions.LoginException;
import com.egorpetruchcho.loriandroid_api.exceptions.ServerException;

import java.io.IOException;

import retrofit2.Response;

public class ApiHelper {
    private ApiHelper() {
    }

    public static <T> T assertResponseCode(Response<T> response) throws ServerException {
        if (response.code() != 200) {
            throw new ServerException(response);
        }
        return response.body();
    }

    public static String validateLogin(okhttp3.Response response) throws IOException, LoginException {
        if (response.code() != 200) {
            throw new LoginException(response.body().string());
        }
        return response.body().string();
    }
}
