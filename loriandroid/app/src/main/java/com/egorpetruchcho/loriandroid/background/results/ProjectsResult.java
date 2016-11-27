package com.egorpetruchcho.loriandroid.background.results;


import com.egorpetruchcho.loriandroid_api.model.Project;

import java.util.List;

public class ProjectsResult {
    public final List<Project> projects;

    public ProjectsResult(List<Project> projects) {
        this.projects = projects;
    }
}
