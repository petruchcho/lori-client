package com.egorpetruchcho.loriandroid.background.tasks;


import com.egorpetruchcho.loriandroid.background.BackgroundTask;
import com.egorpetruchcho.loriandroid.background.results.TimeEntriesResult;
import com.egorpetruchcho.loriandroid.state.ApplicationSavedState;
import com.egorpetruchcho.loriandroid_api.LoriApi;
import com.egorpetruchcho.loriandroid_api.exceptions.NotAuthorizedException;

import java.util.Date;

abstract class GetTimeEntriesTask extends BackgroundTask<TimeEntriesResult> {

    public GetTimeEntriesTask() {
        super(TimeEntriesResult.class);
    }

    @Override
    protected TimeEntriesResult execute() throws Exception {
        String sessionToken = ApplicationSavedState.getInstance().getAuthKey();
        if (sessionToken == null) {
            throw new NotAuthorizedException();
        }
        return new TimeEntriesResult(LoriApi.getInstance().getTimeEntries(sessionToken, getStartDate(), getEndDate()));
    }

    protected abstract Date getStartDate();

    protected abstract Date getEndDate();
}
