package com.egorpetruchcho.loriandroid_api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoriService {

    @GET("app/dispatch/api/login")
    Call<String> login(@Query("u") String username, @Query("p") String password, @Query("l") String locale);
}
