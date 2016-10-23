package com.egorpetruchcho.loriandroid_api;

import com.egorpetruchcho.loriandroid_api.exceptions.ServerException;
import com.egorpetruchcho.loriandroid_api.model.Locale;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoriApi {

    private static LoriApi instance;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.LORI_HOST)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final LoriService service = retrofit.create(LoriService.class);

    private LoriApi() {
    }

    public static LoriApi getInstance() {
        return instance == null ? instance = new LoriApi() : instance;
    }

    public String login(String username, String password, Locale locale) throws IOException, ServerException {
        Response<String> response = service.login(username, password, locale.name().toLowerCase()).execute();
        return ApiHelper.assertResponseCode(response);
    }
}
