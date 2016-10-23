package com.egorpetruchcho.loriandroid_api.exceptions;

import retrofit2.Response;

public class ServerException extends Exception {
    private final Response response;

    public ServerException(Response response) {
        this.response = response;
    }
}
