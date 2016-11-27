package com.egorpetruchcho.loriandroid.background.tasks;


import com.egorpetruchcho.loriandroid.background.BackgroundTask;
import com.egorpetruchcho.loriandroid.state.ApplicationSavedState;
import com.egorpetruchcho.loriandroid_api.LoriApi;
import com.egorpetruchcho.loriandroid_api.exceptions.NotAuthorizedException;
import com.egorpetruchcho.loriandroid_api.model.TimeEntryCommit;

public class CreateTimeEntryTask extends BackgroundTask<Void> {

    private final TimeEntryCommit timeEntry;

    public CreateTimeEntryTask(TimeEntryCommit timeEntry) {
        super(Void.class);
        this.timeEntry = timeEntry;
    }

    @Override
    protected Void execute() throws Exception {
        String sessionToken = ApplicationSavedState.getInstance().getAuthKey();
        if (sessionToken == null) {
            throw new NotAuthorizedException();
        }
        LoriApi.getInstance().createTimeEntry(sessionToken, timeEntry);
        return null;
    }
}
