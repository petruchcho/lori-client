package com.egorpetruchcho.loriandroid.background.tasks;


import com.egorpetruchcho.loriandroid.background.BackgroundTask;
import com.egorpetruchcho.loriandroid.background.results.ProjectsResult;
import com.egorpetruchcho.loriandroid.state.ApplicationSavedState;
import com.egorpetruchcho.loriandroid_api.LoriApi;
import com.egorpetruchcho.loriandroid_api.exceptions.NotAuthorizedException;

public class GetProjectsTask extends BackgroundTask<ProjectsResult> {

    public GetProjectsTask() {
        super(ProjectsResult.class);
    }

    @Override
    protected ProjectsResult execute() throws Exception {
        String sessionToken = ApplicationSavedState.getInstance().getAuthKey();
        if (sessionToken == null) {
            throw new NotAuthorizedException();
        }
        return new ProjectsResult(LoriApi.getInstance().getProjects(sessionToken));
    }
}
