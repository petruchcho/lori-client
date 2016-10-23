package com.egorpetruchcho.loriandroid_api;

import com.egorpetruchcho.loriandroid_api.model.Locale;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoriApi {

    private static LoriApi instance;

    public static LoriApi getInstance() {
        return instance == null ? instance = new LoriApi() : instance;
    }

    private LoriApi() {
    }

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.LORI_HOST)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    private final LoriService service = retrofit.create(LoriService.class);

    public void login(String username, String password, Locale locale, Callback<String> callback) {
        service.login(username, password, locale.name().toLowerCase()).enqueue(callback);
    }
}
