package com.egorpetruchcho.loriandroid_api;

import com.egorpetruchcho.loriandroid_api.exceptions.LoginException;
import com.egorpetruchcho.loriandroid_api.exceptions.NotAuthorizedException;
import com.egorpetruchcho.loriandroid_api.exceptions.ServerException;
import com.egorpetruchcho.loriandroid_api.model.Locale;
import com.egorpetruchcho.loriandroid_api.model.Project;
import com.egorpetruchcho.loriandroid_api.model.TimeEntry;
import com.egorpetruchcho.loriandroid_api.model.TimeEntryCommit;
import com.egorpetruchcho.loriandroid_api.model.TimeEntryDelete;
import com.egorpetruchcho.loriandroid_api.model.User;
import com.egorpetruchcho.loriandroid_api.utils.DateUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoriApi {

    private static final String TIME_ENTRY_TABLE = "ts$TimeEntry";
    private static final String PROJECT_TABLE = "ts$Project";
    private static final String USER_TABLE = "ts$ExtUser";

    private static final String LOGIN_URL = "app/dispatch/api/login?u=%s&p=%s&l=%s";

    private static final String JPQL_SELECT_ALL = "select c from %s c";
    private static final String JPQL_SELECT_WHERE = "select c from %s c where %s";

    private static LoriApi instance;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.LORI_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private final LoriService service = retrofit.create(LoriService.class);

    private LoriApi() {
    }

    public static LoriApi getInstance() {
        return instance == null ? instance = new LoriApi() : instance;
    }

    // Uses separate okHttpClient to avoid json parsing problems
    public String login(String username, String password, Locale locale) throws IOException, ServerException, LoginException, NotAuthorizedException {
        String loginUrl = String.format(LOGIN_URL, username, password, locale.name().toLowerCase());
        Request request = new Request.Builder()
                .get()
                .url(BuildConfig.LORI_HOST + loginUrl)
                .build();
        okhttp3.Response response = okHttpClient.newCall(request).execute();
        return ApiHelper.validateLogin(response);
    }

    public User getUser(String username, String sessionToken) throws IOException, ServerException, NotAuthorizedException {
        String condition = "c.login = :username";
        String jpql = String.format(JPQL_SELECT_WHERE, USER_TABLE, condition);
        Response<List<User>> response = service.requestUser(USER_TABLE, jpql, sessionToken, username).execute();
        return ApiHelper.assertResponseCode(response).get(0);
    }

    public List<Project> getProjects(String sessionToken) throws IOException, ServerException, NotAuthorizedException {
        String jpql = String.format(JPQL_SELECT_ALL, PROJECT_TABLE);
        Response<List<Project>> response = service.requestProjects(PROJECT_TABLE, jpql, sessionToken).execute();
        return ApiHelper.assertResponseCode(response);
    }

    public List<TimeEntry> getTimeEntries(String sessionToken, Date startDate, Date endDate) throws IOException, ServerException, NotAuthorizedException {
        String condition = "c.date between :startDate and :endDate";

        String jpql = String.format(JPQL_SELECT_WHERE, TIME_ENTRY_TABLE, condition);

        Response<List<TimeEntry>> response = service.requestTimeEntries(TIME_ENTRY_TABLE, jpql, sessionToken,
                DateUtils.apiString(startDate), DateUtils.apiString(endDate)).execute();
        return ApiHelper.assertResponseCode(response);
    }

    public void createTimeEntry(String sessionToken, TimeEntryCommit timeEntryCommit) throws IOException, ServerException, NotAuthorizedException {
        Response<Void> response = service.createTimeEntry(timeEntryCommit, sessionToken).execute();
        ApiHelper.assertResponseCode(response);
    }

    public void deleteTimeEntry(String sessionToken, TimeEntryDelete timeEntryDelete) throws IOException, ServerException, NotAuthorizedException {
        Response<Void> response = service.deleteTimeEntry(timeEntryDelete, sessionToken).execute();
        ApiHelper.assertResponseCode(response);
    }
}
