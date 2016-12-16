package com.egorpetruchcho.loriandroid.background.tasks;


import com.egorpetruchcho.loriandroid.background.BackgroundTask;
import com.egorpetruchcho.loriandroid.state.ApplicationSavedState;
import com.egorpetruchcho.loriandroid_api.LoriApi;
import com.egorpetruchcho.loriandroid_api.exceptions.NotAuthorizedException;
import com.egorpetruchcho.loriandroid_api.model.TimeEntryDelete;

public class DeleteTimeEntryTask extends BackgroundTask<Void> {

    private final TimeEntryDelete timeEntry;

    public DeleteTimeEntryTask(TimeEntryDelete timeEntry) {
        super(Void.class);
        this.timeEntry = timeEntry;
    }

    @Override
    protected Void execute() throws Exception {
        String sessionToken = ApplicationSavedState.getInstance().getAuthKey();
        if (sessionToken == null) {
            throw new NotAuthorizedException();
        }
        LoriApi.getInstance().deleteTimeEntry(sessionToken, timeEntry);
        return null;
    }
}
