package com.egorpetruchcho.loriandroid_api;


import com.egorpetruchcho.loriandroid_api.exceptions.ServerException;

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
}
