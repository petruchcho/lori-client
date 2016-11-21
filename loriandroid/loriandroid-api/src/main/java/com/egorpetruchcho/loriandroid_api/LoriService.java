package com.egorpetruchcho.loriandroid_api;

import com.egorpetruchcho.loriandroid_api.model.TimeEntry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoriService {

    @GET("app/dispatch/api/login")
    Call<String> login(@Query("u") String username, @Query("p") String password, @Query("l") String locale);

    @GET("app/dispatch/api/query.json")
    Call<List<TimeEntry>> requestFromDB(@Query("e") String tableName, @Query("q") String jpql, @Query("s") String sessionToken);

    @GET("app/dispatch/api/query.json")
    Call<List<TimeEntry>> requestTimeEntries(@Query("e") String tableName, @Query("q") String jpql, @Query("s") String sessionToken,
                                             @Query("startDate") String startDate, @Query("endDate") String endDate);
}
