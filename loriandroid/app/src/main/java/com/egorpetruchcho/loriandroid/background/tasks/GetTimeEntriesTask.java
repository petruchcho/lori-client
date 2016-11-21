package com.egorpetruchcho.loriandroid.background.tasks;


import com.egorpetruchcho.loriandroid.background.BackgroundTask;
import com.egorpetruchcho.loriandroid.background.results.TimeEntriesResult;
import com.egorpetruchcho.loriandroid.state.ApplicationSavedState;
import com.egorpetruchcho.loriandroid_api.LoriApi;
import com.egorpetruchcho.loriandroid_api.exceptions.NotAuthorizedException;
import com.egorpetruchcho.loriandroid_api.model.Week;

public class GetTimeEntriesTask extends BackgroundTask<TimeEntriesResult> {
    private final Week week;

    public GetTimeEntriesTask(Week week) {
        super(TimeEntriesResult.class);
        this.week = week;
    }

    @Override
    protected TimeEntriesResult execute() throws Exception {
        String sessionToken = ApplicationSavedState.getInstance().getAuthKey();
        if (sessionToken == null) {
            throw new NotAuthorizedException();
        }
        return new TimeEntriesResult(LoriApi.getInstance().getTimeEntries(sessionToken, week.getStartDate(), week.getEndDate()));
    }
}
