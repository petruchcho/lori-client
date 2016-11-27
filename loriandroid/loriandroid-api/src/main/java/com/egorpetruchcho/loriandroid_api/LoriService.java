package com.egorpetruchcho.loriandroid_api;

import com.egorpetruchcho.loriandroid_api.model.Project;
import com.egorpetruchcho.loriandroid_api.model.Task;
import com.egorpetruchcho.loriandroid_api.model.TimeEntry;
import com.egorpetruchcho.loriandroid_api.model.TimeEntryCommit;
import com.egorpetruchcho.loriandroid_api.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoriService {

    @GET("app/dispatch/api/query.json?view=project-full")
    Call<List<Project>> requestProjects(@Query("e") String tableName, @Query("q") String jpql, @Query("s") String sessionToken);

    @GET("app/dispatch/api/query.json")
    Call<List<TimeEntry>> requestTimeEntries(@Query("e") String tableName, @Query("q") String jpql, @Query("s") String sessionToken,
                                             @Query("startDate") String startDate, @Query("endDate") String endDate);

    @GET("app/dispatch/api/query.json")
    Call<List<User>> requestUser(@Query("e") String tableName, @Query("q") String jpql, @Query("s") String sessionToken,
                           @Query("username") String username);

    @POST("app/dispatch/api/commit")
    Call<Void> createTimeEntry(@Body TimeEntryCommit timeEntry, @Query("s") String sessionToken);
}
